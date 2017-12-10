package com.kfaraj.support.util;

import android.os.Parcel;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SparseLongArrayTest {

    private SparseLongArray mSparseArray;

    @Before
    public void setUp() {
        mSparseArray = new SparseLongArray();
    }

    @Test
    public void testParcelable() {
        mSparseArray.put(0, null);
        mSparseArray.put(1, Long.MIN_VALUE);
        mSparseArray.put(2, Long.MAX_VALUE);
        Parcel parcel = Parcel.obtain();
        parcel.writeParcelable(mSparseArray, 0);
        parcel.setDataPosition(0);
        SparseLongArray sparseArray = parcel.readParcelable(SparseLongArray.class.getClassLoader());
        assertEquals(mSparseArray.size(), sparseArray.size());
        for (int i = 0; i < mSparseArray.size(); i++) {
            assertEquals(mSparseArray.keyAt(i), sparseArray.keyAt(i));
            assertEquals(mSparseArray.valueAt(i), sparseArray.valueAt(i));
        }
    }

}
