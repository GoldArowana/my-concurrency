package com.king.learn.collection.myconcurrent.spinl;

import com.king.learn.collection.myconcurrent.spinl.barrier.barriers;
import com.king.learn.collection.myconcurrent.spinl.barrier.implementation;
import com.king.learn.collection.myconcurrent.spinl.locks.Lock;

public class Test3 {

    private static final String TTASLock = "TTASLock";
    public static int THREAD_COUNT;

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException {
        if (args.length >= 1)
            THREAD_COUNT = Integer.parseInt(args[0]);
        else
            THREAD_COUNT = 8;
        if (args.length >= 2)
            implementation.select = Integer.parseInt(args[1]);
        barriers.THREAD_COUNT = THREAD_COUNT;
        implementation.THREAD_COUNT = THREAD_COUNT;
        final barriers bar = new barriers(0, (Lock) Class.forName("locks." + TTASLock).newInstance());

        final implementation[] threads = new implementation[THREAD_COUNT];
        for (int t = 0; t < THREAD_COUNT; t++) {
            threads[t] = new implementation(bar);
        }

        for (int t = 0; t < THREAD_COUNT; t++) {
            threads[t].start();
        }

        long totalTime = 0;
        for (int t = 0; t < THREAD_COUNT; t++) {
            totalTime += threads[t].getElapsedTime();
            threads[t].join();
        }

        System.out.println("Average waiting time per thread is " + totalTime / THREAD_COUNT + "ms");
    }
}
