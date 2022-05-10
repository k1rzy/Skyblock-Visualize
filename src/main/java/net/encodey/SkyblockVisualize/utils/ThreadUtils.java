package net.encodey.SkyblockVisualize.utils;

import lombok.Getter;

public class ThreadUtils extends Thread {

    @Getter
    private static int SLEEP_TIME;

    public static void sleepInMemoryThread(Thread runnable, int time) {
        SLEEP_TIME = time;
        new Thread(() -> {
            System.gc();
            System.runFinalization();
            try {
                Thread.sleep(time);
                runnable.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
