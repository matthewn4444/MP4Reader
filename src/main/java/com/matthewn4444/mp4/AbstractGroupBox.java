package com.matthewn4444.mp4;

import android.util.Log;

import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class AbstractGroupBox extends Box {
    private final int[] mChildIds;
    protected final RandomAccessFile mRaf;

    private long mParsedPosition;

    AbstractGroupBox(RandomAccessFile raf, int[] childIds) throws IOException {
        this(raf, raf.length(), 0, 0, childIds);
        mParsedPosition = 0;
    }

    AbstractGroupBox(RandomAccessFile raf, long size, long offset, int id, int[] childIds) {
        super(size, offset, id);
        mRaf = raf;
        mChildIds = childIds;
        mParsedPosition = offset + 8;
    }

    @Override
    boolean hasParsed() {
        return mParsedPosition >= getOffset() + getSize();
    }

    boolean parseBox(int id) throws IOException {
        while (!hasParsed()) {
            mRaf.seek(mParsedPosition);
            long size = mRaf.readInt() & 0xFFFFFFFFL;
            int boxId = mRaf.readInt();
            if (size == 0 || boxId == 0) {
                return false;
            }
            boolean parsed = false;
            for (int childId : mChildIds) {
                if (childId == boxId) {
                    parsed = true;
                    onParseBox(childId, size, mParsedPosition);

                    // Return if we found what we are looking for
                    if (id > 0 && boxId == id) {
                        mParsedPosition += size;
                        return true;
                    }
                }
            }
            if (!parsed) {
                Log.w(getTag(), "Skipping unknown box: " + Box.idToString(boxId) + " [0x" + Integer.toHexString(boxId) + "]");
            }
            mParsedPosition += size;
        }
        return false;
    }

    protected void parseAllBoxes() throws IOException {
        parseBox(0);
    }

    protected abstract void onParseBox(int id, long size, long pos) throws IOException;

    protected abstract String getTag();

}
