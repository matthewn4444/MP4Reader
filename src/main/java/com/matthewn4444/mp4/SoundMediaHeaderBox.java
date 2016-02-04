package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;

public class SoundMediaHeaderBox extends AbstractLeafBox {
    public static final int ID = 0x736D6864;

    private float mBalance;

    public SoundMediaHeaderBox(long size, long offset) {
        super(size, offset, ID);
    }

    public float getBalance() {
        return mBalance;
    }

    @Override
    protected void onParse(RandomAccessFile raf) throws IOException {
        mBalance = readFixedPoint88(raf);
    }
}
