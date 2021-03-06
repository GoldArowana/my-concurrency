/*
 * Copyright (c) 2008 IBM Corporation
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

package com.king.learn.collection.amino.mcas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Wrapper class for using sun.misc.Unsafe.
 *
 * @author Xiao Jun Dai
 */
final class UnsafeWrapper {

    /**
     * Utility classes should not have a public or default constructor.
     */
    private UnsafeWrapper() {
    }

    /**
     * @return Unsafe object
     */
    public static Unsafe getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error  when accessing to sun.misc.Unsafe", e);
        }
    }
}
