package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

public class MovieHeaderBox extends AbstractLeafBox {
    public static final int ID = 0x6D766864;

    private Date mCreationTime;
    private Date mModificationTime;
    private int mTimeScale;
    private long mDuration;
    private float mPreferredRate;
    private float mPreferredVolume;
    private int mPreviewTime;
    private int mPreviewDuration;
    private int mPosterTime;
    private int mSelectionTime;
    private int mCurrentTime;
    private int mNextTrackId;

    public MovieHeaderBox(long size, long offset) {
        super(size, offset, ID);
    }

    public Date getCreationTime() {
        return mCreationTime;
    }

    public Date getModificationTime() {
        return mModificationTime;
    }

    public int getTimescale() {
        return mTimeScale;
    }

    public long getDuration() {
        return mDuration;
    }

    public float getPreferredRate() {
        return mPreferredRate;
    }

    public float getPreferredVolume() {
        return mPreferredVolume;
    }

    public int getPreviewTime() {
        return mPreviewTime;
    }

    public int getPreviewDuration() {
        return mPreviewDuration;
    }

    public int getPosterTime() {
        return mPosterTime;
    }

    public int getSelectionTime() {
        return mSelectionTime;
    }

    public int getCurrentTime() {
        return mCurrentTime;
    }

    public int getNextTrackId() {
        return mNextTrackId;
    }

    @Override
    protected void onParse(RandomAccessFile raf) throws IOException {
        if (getVersion() == 1) {
            mCreationTime = toDate(raf.readLong());
            mModificationTime = toDate(raf.readLong());
            mTimeScale = raf.readInt();
            mDuration = raf.readLong();
        } else {
            mCreationTime = toDate(raf.readInt() & 0xFFFFFFFFL);
            mModificationTime = toDate(raf.readInt() & 0xFFFFFFFFL);
            mTimeScale = raf.readInt();
            mDuration = raf.readInt();
        }
        mPreferredRate = readFixedPoint1616(raf);
        mPreferredVolume = readFixedPoint88(raf);
        raf.skipBytes(10);      // Reserved
        raf.skipBytes(36);      // Skip matrix structure
        mPreviewTime = raf.readShort();
        mPreviewDuration = raf.readShort();
        mPosterTime = raf.readShort();
        mSelectionTime = raf.readShort();
        mCurrentTime = raf.readShort();
        mNextTrackId = raf.readShort();
    }
}
