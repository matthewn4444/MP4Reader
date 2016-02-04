package com.matthewn4444.mp4;

public class ChunkOffsetBox extends AbstractListLeafBox {
    public static final int ID = 0x7374636F;

    public ChunkOffsetBox(long size, long offset) {
        super(size, offset, ID);
    }
}
