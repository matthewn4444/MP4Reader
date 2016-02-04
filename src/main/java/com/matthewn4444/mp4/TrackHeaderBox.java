package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

public class TrackHeaderBox extends AbstractLeafBox {
    public static final int ID = 0x746B6864;

    private Date mCreationTime;
    private Date mModificationTime;
    private int mTrackId;
    private long mDuration;
    private int mLayer;
    private int mAlternateGroup;
    private float mVolume;
    private float mWidth;
    private float mHeight;

    public TrackHeaderBox(long size, long offset) {
        super(size, offset, ID);
    }

    public Date getCreationTime() {
        return mCreationTime;
    }

    public Date getModificationTime() {
        return mModificationTime;
    }

    public long getTrackId() {
        return mTrackId;
    }

    public long getDuration() {
        return mDuration;
    }

    public int getLayer() {
        return mLayer;
    }

    public int getAlternateGroup() {
        return mAlternateGroup;
    }

    public float getVolume() {
        return mVolume;
    }

    public float getWidth() {
        return mWidth;
    }

    public float getHeight() {
        return mHeight;
    }

    @Override
    protected void onParse(RandomAccessFile raf) throws IOException {
        if (getVersion() == 1) {
            mCreationTime = toDate(raf.readLong());
            mModificationTime = toDate(raf.readLong());
            mTrackId = raf.readInt();
            raf.skipBytes(4);
            mDuration = raf.readLong();
        } else {
            mCreationTime = toDate(raf.readInt() & 0xFFFFFFFFL);
            mModificationTime = toDate(raf.readInt() & 0xFFFFFFFFL);
            mTrackId = raf.readInt();
            raf.skipBytes(4);
            mDuration = raf.readInt();
        }
        raf.skipBytes(8);
        mLayer = raf.readShort();
        mAlternateGroup = raf.readShort();
        mVolume = readFixedPoint88(raf);
        raf.skipBytes(38);      // Ignore Matrix (36) + reserved (2)
        mWidth = readFixedPoint1616(raf);
        mHeight = readFixedPoint1616(raf);
    }
}
