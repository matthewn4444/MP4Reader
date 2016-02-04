package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;

public class SampleToChunkBox extends AbstractLeafBox {
    public static final int ID = 0x73747363;

    public static class Entry {
        public final long firstChunk;
        public final long samplesPerChunk;
        public final long sampleDescriptionIndex;

        Entry(long a, long b, long c) {
            firstChunk = a;
            samplesPerChunk = b;
            sampleDescriptionIndex = c;
        }
    }

    private int mSize;
    private Entry[] mEntries;

    public SampleToChunkBox(long size, long offset) {
        super(size, offset, ID);
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
            mEntries[i] = new Entry(readUInt32(raf), readUInt32(raf), readUInt32(raf));
        }
    }
}
