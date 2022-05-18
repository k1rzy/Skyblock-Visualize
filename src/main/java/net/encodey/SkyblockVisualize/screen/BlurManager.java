package net.encodey.SkyblockVisualize.screen;

import net.encodey.SkyblockVisualize.utils.RenderScreenUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.util.Matrix4f;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BlurManager extends GlStateManager {
    private static class OutputStuff {
        public Framebuffer framebuffer;
        public Shader blurShaderHoriz = null;
        public Shader blurShaderVert = null;

        public OutputStuff(Framebuffer framebuffer, Shader blurShaderHoriz, Shader blurShaderVert) {
            this.framebuffer = framebuffer;
            this.blurShaderHoriz = blurShaderHoriz;
            this.blurShaderVert = blurShaderVert;
        }
    }

    private static final HashMap<Float, OutputStuff> blurOutput = new HashMap<>();
    private static final HashMap<Float, Long> lastBlurUse = new HashMap<>();
    private static long lastBlur = 0;
    private static final HashSet<Float> requestedBlurs = new HashSet<>();

    private static int fogColour = 0;
    private static boolean registered = false;

    public static void registerListener() {
        if (!registered) {
            registered = true;
            MinecraftForge.EVENT_BUS.register(new BlurManager());
        }
    }

    private static boolean shouldBlur = true;

    public static void markDirty() {
        if (Minecraft.getMinecraft().theWorld != null) {
            shouldBlur = true;
        }
    }

    public static void processBlurs() {
        if (shouldBlur) {
            shouldBlur = false;

            long currentTime = System.currentTimeMillis();

            for (float blur : requestedBlurs) {
                lastBlur = currentTime;
                lastBlurUse.put(blur, currentTime);

                int width = Minecraft.getMinecraft().displayWidth;
                int height = Minecraft.getMinecraft().displayHeight;

                OutputStuff output = blurOutput.computeIfAbsent(blur, k -> {
                    Framebuffer fb = new Framebuffer(width, height, false);
                    fb.setFramebufferFilter(GL11.GL_NEAREST);
                    return new OutputStuff(fb, null, null);
                });

                if (output.framebuffer.framebufferWidth != width || output.framebuffer.framebufferHeight != height) {
                    output.framebuffer.createBindFramebuffer(width, height);
                    if (output.blurShaderHoriz != null) {
                        output.blurShaderHoriz.setProjectionMatrix(createProjectionMatrix(width, height));
                    }
                    if (output.blurShaderVert != null) {
                        output.blurShaderVert.setProjectionMatrix(createProjectionMatrix(width, height));
                    }
                }

                blurBackground(output, blur);
            }

            Set<Float> remove = new HashSet<>();
            for (Map.Entry<Float, Long> entry : lastBlurUse.entrySet()) {
                if (currentTime - entry.getValue() > 30 * 1000) {
                    remove.add(entry.getKey());
                }
            }

            for (Map.Entry<Float, OutputStuff> entry : blurOutput.entrySet()) {
                if (remove.contains(entry.getKey())) {
                    entry.getValue().framebuffer.deleteFramebuffer();
                    entry.getValue().blurShaderHoriz.deleteShader();
                    entry.getValue().blurShaderVert.deleteShader();
                }
            }

            lastBlurUse.keySet().removeAll(remove);
            blurOutput.keySet().removeAll(remove);

            requestedBlurs.clear();
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onScreenRender(RenderGameOverlayEvent.Pre event) {
        if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
            processBlurs();
        }
        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(false);
    }

    @SubscribeEvent
    public void onFogColour(EntityViewRenderEvent.FogColors event) {
        fogColour = 0xff000000;
        fogColour |= ((int) (event.red * 255) & 0xFF) << 16;
        fogColour |= ((int) (event.green * 255) & 0xFF) << 8;
        fogColour |= (int) (event.blue * 255) & 0xFF;
    }

    private static Framebuffer blurOutputHorz = null;

    private static Matrix4f createProjectionMatrix(int width, int height) {
        Matrix4f projMatrix = new Matrix4f();
        projMatrix.setIdentity();
        projMatrix.m00 = 2.0F / (float) width;
        projMatrix.m11 = 2.0F / (float) (-height);
        projMatrix.m22 = -0.0020001999F;
        projMatrix.m33 = 1.0F;
        projMatrix.m03 = -1.0F;
        projMatrix.m13 = 1.0F;
        projMatrix.m23 = -1.0001999F;
        return projMatrix;
    }

    private static void blurBackground(OutputStuff output, float blurFactor) {
        if (!OpenGlHelper.isFramebufferEnabled() || !OpenGlHelper.areShadersSupported()) return;

        int width = Minecraft.getMinecraft().displayWidth;
        int height = Minecraft.getMinecraft().displayHeight;

        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, width, height, 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);

        if (blurOutputHorz == null) {
            blurOutputHorz = new Framebuffer(width, height, false);
            blurOutputHorz.setFramebufferFilter(GL11.GL_NEAREST);
        }
        if (blurOutputHorz == null || output == null) {
            return;
        }
        if (blurOutputHorz.framebufferWidth != width || blurOutputHorz.framebufferHeight != height) {
            blurOutputHorz.createBindFramebuffer(width, height);
            Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(false);
        }

        if (output.blurShaderHoriz == null) {
            try {
                output.blurShaderHoriz = new Shader(Minecraft.getMinecraft().getResourceManager(), "blur",
                        output.framebuffer, blurOutputHorz
                );
                output.blurShaderHoriz.getShaderManager().getShaderUniform("BlurDir").set(1, 0);
                output.blurShaderHoriz.setProjectionMatrix(createProjectionMatrix(width, height));
            } catch (Exception ignored) {
            }
        }
        if (output.blurShaderVert == null) {
            try {
                output.blurShaderVert = new Shader(Minecraft.getMinecraft().getResourceManager(), "blur",
                        blurOutputHorz, output.framebuffer
                );
                output.blurShaderVert.getShaderManager().getShaderUniform("BlurDir").set(0, 1);
                output.blurShaderVert.setProjectionMatrix(createProjectionMatrix(width, height));
            } catch (Exception ignored) {
            }
        }
        if (output.blurShaderHoriz != null && output.blurShaderVert != null) {
            if (output.blurShaderHoriz.getShaderManager().getShaderUniform("Radius") == null) {
                return;
            }

            output.blurShaderHoriz.getShaderManager().getShaderUniform("Radius").set(blurFactor);
            output.blurShaderVert.getShaderManager().getShaderUniform("Radius").set(blurFactor);

            GL11.glPushMatrix();
            GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, Minecraft.getMinecraft().getFramebuffer().framebufferObject);
            GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, output.framebuffer.framebufferObject);
            GL30.glBlitFramebuffer(0, 0, width, height,
                    0, 0, output.framebuffer.framebufferWidth, output.framebuffer.framebufferHeight,
                    GL11.GL_COLOR_BUFFER_BIT, GL11.GL_NEAREST
            );

            output.blurShaderHoriz.loadShader(0);
            output.blurShaderVert.loadShader(0);
            GlStateManager.enableDepth();
            GL11.glPopMatrix();
        }
        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(false);
    }

    public static void renderBlurredBackground(
            float blurStrength, int screenWidth, int screenHeight,
            int x, int y, int blurWidth, int blurHeight
    ) {
        renderBlurredBackground(blurStrength, screenWidth, screenHeight, x, y, blurWidth, blurHeight, false);
    }

    /**
     * [x->x+blurWidth, y->y+blurHeight]
     */
    public static void renderBlurredBackground(
            float blurStrength, int screenWidth, int screenHeight,
            int x, int y, int blurWidth, int blurHeight, boolean forcedUpdate
    ) {
        if (!OpenGlHelper.isFramebufferEnabled() || !OpenGlHelper.areShadersSupported()) return;
        if (blurStrength < 0.5) return;
        requestedBlurs.add(blurStrength);

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBlur > 300) {
            shouldBlur = true;
            if (currentTime - lastBlur > 400 && forcedUpdate) return;
        }

        if (blurOutput.isEmpty()) return;

        OutputStuff out = blurOutput.get(blurStrength);
        if (out == null) {
            out = blurOutput.values().iterator().next();
        }

        float uMin = x / (float) screenWidth;
        float uMax = (x + blurWidth) / (float) screenWidth;
        float vMin = (screenHeight - y) / (float) screenHeight;
        float vMax = (screenHeight - y - blurHeight) / (float) screenHeight;

        GlStateManager.depthMask(false);
        Gui.drawRect(x, y, x + blurWidth, y + blurHeight, fogColour);
        out.framebuffer.bindFramebufferTexture();
        GlStateManager.color(1f, 1f, 1f, 1f);
        RenderScreenUtils.drawTexturedRect(x, y, blurWidth, blurHeight, uMin, uMax, vMin, vMax);
        out.framebuffer.unbindFramebufferTexture();
        GlStateManager.depthMask(true);
    }

    public static void playHurtAnimation() {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();

        for(int i = 4000; i > 0; i--) {
            renderBlurredBackground(i, width, height, width / 2, height / 2, width, height);
        }
    }
}
