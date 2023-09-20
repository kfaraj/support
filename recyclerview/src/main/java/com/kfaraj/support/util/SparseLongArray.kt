package com.kfaraj.support.util

import android.os.Parcel
import android.os.Parcelable
import android.util.SparseArray

/**
 * Maps integers to longs.
 */
internal class SparseLongArray : SparseArray<Long?>,
    Parcelable {

    /**
     * Constructor.
     */
    constructor()

    /**
     * Constructor.
     *
     * @param source the source.
     */
    private constructor(source: Parcel) {
        val size = source.readInt()
        for (i in 0..<size) {
            val key = source.readInt()
            val value = source.readValue(Long::class.java.classLoader) as Long?
            put(key, value)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        val size = size()
        dest.writeInt(size)
        for (i in 0..<size) {
            val key = keyAt(i)
            val value = valueAt(i)
            dest.writeInt(key)
            dest.writeValue(value)
        }
    }

    /**
     * The creator.
     */
    companion object CREATOR : Parcelable.Creator<SparseLongArray> {
        override fun createFromParcel(source: Parcel): SparseLongArray {
            return SparseLongArray(source)
        }

        override fun newArray(size: Int): Array<SparseLongArray?> {
            return arrayOfNulls(size)
        }
    }

}
