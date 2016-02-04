package com.matthewn4444.mp4;

public class DecodingTimeToSampleBox extends TimeToSampleBoxBase {
    public static final int ID = 0x73747473;

    public DecodingTimeToSampleBox(long size, long offset) {
        super(size, offset, ID);
    }
}
