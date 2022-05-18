package net.encodey.SkyblockVisualize.utils;

import lombok.Getter;

public class ThreadUtils extends Thread {

    @Getter
    private static int SLEEP_TIME;

    public static void sleepInMemoryThread(Thread runnable, int sleepTime) {
        SLEEP_TIME = sleepTime;
        new Thread(() -> {
            System.runFinalization();
            try {
                Thread.sleep(sleepTime);
                runnable.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

   public static void scheduleRunnable(Runnable runnableStart, int sleepTime)  {
        new Thread(() -> {
            try {
                Thread.sleep(sleepTime);
                runnableStart.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
   }
}
