package com.king.learn.collection.mycollection.skiplist.inst1;

import com.king.learn.collection.mycollection.random.RandomNumber;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class SkipList<T> {
    private static final RandomNumber random = new RandomNumber(333, 100); // 随机数生成器
    private static final int MAX_LEVEL = 1 << 6;
    private Node<T> top = null;
    private int level = 0;

    public SkipList() {
        this(4);
    }

    public SkipList(int level) {
        Node<T> down = null;
        for (int i = this.level = level; i > 0; i--)
            top = down = new Node<>(null, Double.MIN_VALUE, null, down);
    }

    private int getRandomLevel() {
        int lev = 1;
        while (random.isTrue()) lev++;
        return lev > MAX_LEVEL ? MAX_LEVEL : lev;
    }

    public T get(double score) {
        for (Node<T> t = top; t != null; )
            if (t.score == score) return t.val;
            else if (t.next == null || t.next.score > score) t = t.down;
            else t = t.next;
        return null;
    }

    public void delete(double score) {
        for (Node<T> t = top; t != null; )
            if (t.next == null || (t.next.score == score && t.deleteNext()) || t.next.score > score)
                t = t.down;
            else
                t = t.next;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node<T> t = top; t != null; sb.append('\n'), t = t.down)
            for (Node<T> next = t; next != null; next = next.next)
                sb.append(next.score).append(" ");
        return sb.toString();
    }

    public void put(double score, T val) {
        List<Node<T>> path = new ArrayList<>();
        Node<T> cur = null;
        for (Node<T> t = top; t != null && cur == null; )
            if (t.score == score) {
                cur = t;
            } else if (t.next == null || t.next.score > score) {
                path.add(t); // 需要向下查找，先记录该节点
                if ((t = t.down) == null) break;
            } else {
                t = t.next;
            }

        if (cur != null) {
            for (; cur != null; cur = cur.down)
                cur.val = val;
            return;
        }

        int lev = getRandomLevel();
        for (Node<T> prev = top; level < lev; level++)
            path.add(0, top = prev = new Node<>(null, Double.MIN_VALUE, null, prev));

        Node<T> downTemp = null;
        for (int i = level - 1; i >= level - lev; i--) {
            Node<T> prev = path.get(i);
            downTemp = prev.next = new Node<>(val, score, prev.next, downTemp);
        }
    }

    @AllArgsConstructor
    private static class Node<E> {
        E val;
        double score;
        Node<E> next, down;

        private boolean deleteNext() {
            this.next = this.next.next;
            return true;
        }
    }
}
