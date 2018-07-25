package com.king.learn.collection.mycollection.bloomfilter.demo7;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * Quick benchmark to evaluate the efficiency of rewriting the entire bloom filter when we have to sync changes
 * versus just seeking to the positions that changed and just writing those.
 * <p/>
 * On our test machine, seeking and writing was faster than sequential writes while there were less than ~20
 * bytes to change.
 */
public class SeekSequentialWriteBenchmark {
    private static final int SIZE = 1024 * 1024;
    private static final int TRIALS = 50;

    private static void writeChanges(int[] positions, WriteMethod method) throws IOException {
        for (int i = 0; i < TRIALS; i++) {
            RandomAccessFile file = new RandomAccessFile(File.createTempFile("test", "bench"), "rw");
            byte[] cache = new byte[SIZE];

            if (WriteMethod.SEEK == method) {
                for (int position : positions) {
                    assert position < SIZE;
                    file.seek(position);
                    file.write(1);
                    cache[position] = 1;
                }
            } else {
                assert WriteMethod.SEQUENTIAL == method;
                for (int position : positions) {
                    cache[position] = 1;
                }
                file.write(cache);
            }
            file.getFD().sync();
        }
    }

    public static void main(String[] args) throws IOException {
        for (int i = 1; i < 200; i += 1) {
            int[] positions = new int[i];
            int jump = SIZE / i;
            for (int j = 0; j < i; j++) {
                positions[j] = j * jump;
            }
            for (WriteMethod method : WriteMethod.values()) {
                long startTime = System.currentTimeMillis();
                writeChanges(positions, method);
                long endTime = System.currentTimeMillis();
                System.out.println("Writing " + i + " changes with the " + method + " method took "
                        + (endTime - startTime) + "ms");
            }
        }
    }

    private static enum WriteMethod {
        SEQUENTIAL,
        SEEK
    }
}
