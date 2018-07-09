package com.king.learn.collection.mycollection.tree.segment_tree;

/// Leetcode 307. Range Sum Query - Mutable
/// https://leetcode.com/problems/range-sum-query-mutable/description/
public class NumArray4 {

    private SegmentTree<Integer> segTree;

    public NumArray4(int[] nums) {

        if (nums.length != 0) {
            Integer[] data = new Integer[nums.length];
            for (int i = 0; i < nums.length; i++)
                data[i] = nums[i];
            segTree = new SegmentTree<>(data, (a, b) -> a + b);
        }
    }

    public void update(int i, int val) {
        if (segTree == null)
            throw new IllegalArgumentException("Error");
        segTree.set(i, val);
    }

    public int sumRange(int i, int j) {
        if (segTree == null)
            throw new IllegalArgumentException("Error");
        return segTree.query(i, j);
    }
}
