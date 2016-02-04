package com.matthewn4444.mp4;

public class SyncSampleBox extends AbstractListLeafBox {
    public static final int ID = 0x73747373;

    public SyncSampleBox(long size, long offset) {
        super(size, offset, ID);
    }
}
