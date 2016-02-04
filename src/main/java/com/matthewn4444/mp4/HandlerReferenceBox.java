package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;

public class HandlerReferenceBox extends AbstractLeafBox {
    public static final int ID = 0x68646c72;

    private String mComponentType;
    private String mComponentSubType;
    private String mComponentName;

    public HandlerReferenceBox(long size, long offset) {
        super(size, offset, ID);
    }

    public String getComponentType() {
        return mComponentType;
    }

    public String getComponentSubType() {
        return mComponentSubType;
    }

    public String getComponentName() {
        return mComponentName;
    }

    @Override
    protected void onParse(RandomAccessFile raf) throws IOException {
        mComponentType = idToString(raf.readInt());
        mComponentSubType = idToString(raf.readInt());
        raf.skipBytes(12);      // Reserved
        mComponentName = readString(raf);
    }

}
