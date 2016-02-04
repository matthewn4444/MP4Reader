package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MediaInformationBox extends AbstractGroupBox {
    private static final String TAG = "MediaInformationBox";
    private static final int[] ChildIDs = new int[] {
            VideoMediaHeaderBox.ID, SampleTableBox.ID,
            SoundMediaHeaderBox.ID
    };

    public static final int ID = 0x6d696E66;

    private VideoMediaHeaderBox mVideoMediaHeaderBox;
    private SoundMediaHeaderBox mSoundMediaHeaderBox;
    private SampleTableBox mSampleTableBox;

    public MediaInformationBox(RandomAccessFile raf, long size, long offset) {
        super(raf, size, offset, ID, ChildIDs);
    }

    public VideoMediaHeaderBox getVideoMediaHeaderBox() throws IOException {
        if (parseBox(VideoMediaHeaderBox.ID)) {
            mVideoMediaHeaderBox.parse(mRaf);
        }
        return mVideoMediaHeaderBox;
    }

    public SoundMediaHeaderBox getSoundMediaHeaderBox() throws IOException {
        if (parseBox(SoundMediaHeaderBox.ID)) {
            mSoundMediaHeaderBox.parse(mRaf);
        }
        return mSoundMediaHeaderBox;
    }

    public SampleTableBox getSampleTableBox() throws IOException {
        parseBox(SampleTableBox.ID);
        return mSampleTableBox;
    }

    @Override
    protected void onParseBox(int id, long size, long pos) throws IOException {
        if (id == VideoMediaHeaderBox.ID) {
            mVideoMediaHeaderBox = new VideoMediaHeaderBox(size, pos);
        } else if (id == SoundMediaHeaderBox.ID) {
            mSoundMediaHeaderBox = new SoundMediaHeaderBox(size, pos);
        } else {
            mSampleTableBox = new SampleTableBox(mRaf, size, pos);
        }
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
