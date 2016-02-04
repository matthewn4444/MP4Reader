package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class TimeToSampleBoxBase extends AbstractLeafBox {
    public static class Entry {
        public final long count;
        public final long duration;

        Entry(long c, long d) {
            count = c;
            duration = d;
        }
    }

    private int mSize;
    private Entry[] mEntries;

    public TimeToSampleBoxBase(long size, long offset, int id) {
        super(size, offset, id);
    }

    public int getCount() {
        return mSize;
    }

    public Entry[] getEntries() {
        return mEntries;
    }

    @Override
    protected void onParse(RandomAccessFile raf) throws IOException {
        mSize = raf.readInt();
        mEntries = new Entry[mSize];

        for (int i = 0; i < mSize; i++) {
            mEntries[i] = new Entry(readUInt32(raf), readUInt32(raf));
        }
    }
}
