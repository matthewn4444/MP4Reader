package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class AbstractListLeafBox extends AbstractLeafBox {
    private int mSize;
    private long[] mValues;

    public AbstractListLeafBox(long size, long offset, int id) {
        super(size, offset, id);
    }

    /**
     * Get number of items that this box holds
     * @return number of items
     */
    public int getCount() {
        return mSize;
    }

    /**
     * Get the values of this box
     * @return list of values
     */
    public long[] getValues() {
        return mValues;
    }

    @Override
    protected void onParse(RandomAccessFile raf) throws IOException {
        mSize = raf.readInt();
        mValues = new long[mSize];

        for (int i = 0; i < mSize; i++) {
            mValues[i] = readUInt32(raf);
        }
    }
}
