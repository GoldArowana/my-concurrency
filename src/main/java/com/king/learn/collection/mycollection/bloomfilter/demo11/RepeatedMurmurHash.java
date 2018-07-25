/*
 * Copyright 2010 The Greplin Bloom Filter Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.king.learn.collection.mycollection.bloomfilter.demo11;

/**
 * Used for the Bloom filter. To simulate having multiple hash functions, we just take the linear combination
 * of two runs of the MurmurHash (http://www.eecs.harvard.edu/~kirsch/pubs/bbbf/esa06.pdf says this is alright).
 * The core hashOnce fn is just a port of the C++ MurmurHash at http://code.google.com/p/smhasher/
 */

/**
 * made modification to return hashes without reduction to bitSet index
 */

public class RepeatedMurmurHash {
    private static int hashOnce(byte[] data, int seed) {
        int len = data.length;
        int m = 0x5bd1e995;
        int r = 24;

        int h = seed ^ len;
        int chunkLen = len >> 2;

        for (int i = 0; i < chunkLen; i++) {
            int iChunk = i << 2;
            int k = data[iChunk + 3];
            k = k << 8;
            k = k | (data[iChunk + 2] & 0xff);
            k = k << 8;
            k = k | (data[iChunk + 1] & 0xff);
            k = k << 8;
            k = k | (data[iChunk + 0] & 0xff);
            k *= m;
            k ^= k >>> r;
            k *= m;
            h *= m;
            h ^= k;
        }

        int lenMod = chunkLen << 2;
        int left = len - lenMod;

        if (left != 0) {
            if (left >= 3) {
                h ^= (int) data[len - 3] << 16;
            }
            if (left >= 2) {
                h ^= (int) data[len - 2] << 8;
            }
            if (left >= 1) {
                h ^= (int) data[len - 1];
            }

            h *= m;
        }

        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;

        return h;
    }

    public int[] hash(byte[] data, int hashCount) {
        int[] result = new int[hashCount];

        int hashA = hashOnce(data, 0);
        int hashB = hashOnce(data, hashA);

        for (int i = 0; i < hashCount; i++) {
            result[i] = hashA + i * hashB;
        }

        return result;
    }

}