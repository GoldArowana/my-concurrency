package com.king.learn.collection.jdk8.string;

import java.util.Objects;

/**
 * @since 1.8
 */
public final class StringJoiner {
    private final String prefix;
    private final String delimiter;
    private final String suffix;
    private StringBuilder value;
    private String emptyValue;

    public StringJoiner(CharSequence delimiter) {
        this(delimiter, "", "");
    }

    public StringJoiner(CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        Objects.requireNonNull(prefix, "The prefix must not be null");
        Objects.requireNonNull(delimiter, "The delimiter must not be null");
        Objects.requireNonNull(suffix, "The suffix must not be null");
        // make defensive copies of arguments
        this.prefix = prefix.toString();
        this.delimiter = delimiter.toString();
        this.suffix = suffix.toString();
        this.emptyValue = this.prefix + this.suffix;
    }

    public StringJoiner setEmptyValue(CharSequence emptyValue) {
        this.emptyValue = Objects.requireNonNull(emptyValue,
                "The empty value must not be null").toString();
        return this;
    }

    @Override
    public String toString() {
        if (value == null) {
            return emptyValue;
        } else {
            if (suffix.equals("")) {
                return value.toString();
            } else {
                int initialLength = value.length();
                String result = value.append(suffix).toString();
                // reset value to pre-append initialLength
                value.setLength(initialLength);
                return result;
            }
        }
    }

    public StringJoiner add(CharSequence newElement) {
        prepareBuilder().append(newElement);
        return this;
    }

    private StringBuilder prepareBuilder() {
        if (value != null) value.append(delimiter);
        else value = new StringBuilder().append(prefix);
        return value;
    }

    public StringJoiner merge(StringJoiner other) {
        Objects.requireNonNull(other); // 不能为null
        if (other.value != null) {
            final int length = other.value.length(); // value的长度
            StringBuilder builder = prepareBuilder();
            builder.append(other.value, other.prefix.length(), length);
        }
        return this;
    }

    public int length() {
        return (value != null ? value.length() + suffix.length() : emptyValue.length());
    }
}
