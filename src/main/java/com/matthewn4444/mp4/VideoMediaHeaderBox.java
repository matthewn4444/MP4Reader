package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;

public class VideoMediaHeaderBox extends AbstractLeafBox {
    public static final int ID = 0x766D6864;

    private int mGraphicsMode;
    private int[] mOpColor;

    public VideoMediaHeaderBox(long size, long offset) {
        super(size, offset, ID);
    }

    public int getGraphicsMode() {
        return mGraphicsMode;
    }

    public int[] getOpColor() {
        return mOpColor;
    }

    @Override
    protected void onParse(RandomAccessFile raf) throws IOException {
        mGraphicsMode = raf.readUnsignedShort();
        mOpColor = new int[3];
        for (int i = 0; i < 3; i++) {
            mOpColor[i] = raf.readUnsignedShort();
        }
    }
}
