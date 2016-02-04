package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FileTypeBox extends AbstractLeafBox {
    public static final int ID = 0x66747970;

    public FileTypeBox(long size, long offset) {
        super(size, offset, ID);
    }

    @Override
    protected void onParse(RandomAccessFile raf) throws IOException {
        // TODO we dont need this info for what we are doing
    }
}
