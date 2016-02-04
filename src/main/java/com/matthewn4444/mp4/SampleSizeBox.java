package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;

public class SampleSizeBox extends AbstractListLeafBox {
    public static final int ID = 0x7374737A;

    private int mSampleSize;

    public SampleSizeBox(long size, long offset) {
        super(size, offset, ID);
    }

    public int getSampleSize() {
        return mSampleSize;
    }

    @Override
    protected void onParse(RandomAccessFile raf) throws IOException {
        mSampleSize = raf.readInt();
        super.onParse(raf);
    }
}
