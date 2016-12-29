package com.kfaraj.support.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

/**
 * Maps integers to longs.
 */
public class SparseLongArray extends SparseArray<Long> implements Parcelable {

    /**
     * The creator.
     */
    public static final Creator<SparseLongArray> CREATOR = new Creator<SparseLongArray>() {
        @Override
        public SparseLongArray createFromParcel(Parcel source) {
            return new SparseLongArray(source);
        }
        @Override
        public SparseLongArray[] newArray(int size) {
            return new SparseLongArray[size];
        }
    };

    /**
     * Constructor
     */
    public SparseLongArray() {
    }

    /**
     * Constructor
     *
     * @param source the source.
     */
    private SparseLongArray(Parcel source) {
        final int size = source.readInt();
        for (int i = 0; i < size; i++) {
            final int key = source.readInt();
            final Long value = (Long) source.readValue(Long.class.getClassLoader());
            put(key, value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        final int size = size();
        dest.writeInt(size);
        for (int i = 0; i < size; i++) {
            final int key = keyAt(i);
            final Long value = valueAt(i);
            dest.writeInt(key);
            dest.writeValue(value);
        }
    }

}
