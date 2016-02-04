package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MediaBox extends AbstractGroupBox {
    private static final String TAG = "MediaBox";
    private static final int[] ChildIDs = new int[] {
        MediaHeaderBox.ID, HandlerReferenceBox.ID, MediaInformationBox.ID
    };

    public static final int ID = 0x6d646961;

    private MediaHeaderBox mMediaHeaderBox;
    private HandlerReferenceBox mHandlerReferenceBox;
    private MediaInformationBox mMediaInformationBox;

    public MediaBox(RandomAccessFile raf, long size, long offset) {
        super(raf, size, offset, ID, ChildIDs);
    }

    public MediaHeaderBox getMediaHeaderBox() throws IOException {
        if (parseBox(MediaHeaderBox.ID)) {
            mMediaHeaderBox.parse(mRaf);
        }
        return mMediaHeaderBox;
    }

    public HandlerReferenceBox getHandlerReferenceBox() throws IOException {
        if (parseBox(HandlerReferenceBox.ID)) {
            mHandlerReferenceBox.parse(mRaf);
        }
        return mHandlerReferenceBox;
    }

    public MediaInformationBox getMediaInformationBox() throws IOException {
        parseBox(MediaInformationBox.ID);
        return mMediaInformationBox;
    }

    @Override
    protected void onParseBox(int id, long size, long pos) throws IOException {
        if (id == MediaHeaderBox.ID) {
            mMediaHeaderBox = new MediaHeaderBox(size, pos);
        } else if (id == HandlerReferenceBox.ID) {
            mHandlerReferenceBox = new HandlerReferenceBox(size, pos);
        } else if (id == MediaInformationBox.ID) {
            mMediaInformationBox = new MediaInformationBox(mRaf, size, pos);
        }
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
