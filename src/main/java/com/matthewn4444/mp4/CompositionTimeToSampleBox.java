package com.matthewn4444.mp4;

public class CompositionTimeToSampleBox extends TimeToSampleBoxBase {
    public static final int ID = 0x63747473;

    public CompositionTimeToSampleBox(long size, long offset) {
        super(size, offset, ID);
    }
}
