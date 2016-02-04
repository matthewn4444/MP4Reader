package com.matthewn4444.mp4;

public class MediaDataBox extends Box {
    public static final int ID = 0x6D646174;

    MediaDataBox(long size, long offset) {
        super(size, offset, ID);
    }

    @Override
    boolean hasParsed() {
        return true;
    }
}
