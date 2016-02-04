package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TrackBox extends AbstractGroupBox {
    private static final String TAG = "TrackBox";
    private static final int[] ChildIDs = new int[] {
        TrackHeaderBox.ID, MediaBox.ID
    };

    public static final int ID = 0x7472616B;

    private TrackHeaderBox mTrackBoxHeader;
    private MediaBox mMediaBox;

    TrackBox(RandomAccessFile raf, long size, long offset) {
        super(raf, size, offset, ID, ChildIDs);
    }

    public TrackHeaderBox getTrackHeaderBox() throws IOException {
        if (parseBox(TrackHeaderBox.ID)) {
            mTrackBoxHeader.parse(mRaf);
        }
        return mTrackBoxHeader;
    }

    public MediaBox getMediaBox() throws IOException {
        parseBox(MediaBox.ID);
        return mMediaBox;
    }

    @Override
    protected void onParseBox(int id, long size, long pos)
            throws IOException {
        if (id == TrackHeaderBox.ID) {
            mTrackBoxHeader = new TrackHeaderBox(size, pos);
        } else {
            mMediaBox = new MediaBox(mRaf, size, pos);
        }
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
