package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

public class MediaHeaderBox extends AbstractLeafBox {
    public static final int ID = 0x6d646864;

    private Date mCreationTime;
    private Date mModificationTime;
    private long mTimescale;
    private long mDuration;
    private String mLanguage;

    public MediaHeaderBox(long size, long offset) {
        super(size, offset, ID);
    }

    public Date getCreationTime() {
        return mCreationTime;
    }

    public Date getModificationTime() {
        return mModificationTime;
    }

    public long getTimescale() {
        return mTimescale;
    }

    public long getDuration() {
        return mDuration;
    }

    public String getLanguage() {
        return mLanguage;
    }

    @Override
    protected void onParse(RandomAccessFile raf) throws IOException {
        if (getVersion() == 1) {
            mCreationTime = toDate(raf.readLong());
            mModificationTime = toDate(raf.readLong());
            mTimescale = raf.readInt();
            mDuration = raf.readLong();
        } else {
            mCreationTime = toDate(raf.readInt() & 0xFFFFFFFFL);
            mModificationTime = toDate(raf.readInt() & 0xFFFFFFFFL);
            mTimescale = raf.readInt();
            mDuration = raf.readInt();
        }
        mLanguage = readIso639(raf);
    }
}
