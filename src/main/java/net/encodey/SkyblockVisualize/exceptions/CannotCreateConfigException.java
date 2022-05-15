package net.encodey.SkyblockVisualize.exceptions;

public class CannotCreateConfigException extends ExUtil {
    public CannotCreateConfigException() {
        super("Cannot create or read main config file. \nThrowing this exception so you don't lose your save. \n" +
                "Report this bug in official discord server.");
    }
}
