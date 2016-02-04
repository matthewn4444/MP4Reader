package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;

public class SampleTableBox extends AbstractGroupBox {
    private static final String TAG = "SampleTableBox";
    private static final int[] ChildIDs = new int[] {
            DecodingTimeToSampleBox.ID,
            CompositionTimeToSampleBox.ID,
            SyncSampleBox.ID,
            SampleToChunkBox.ID,
            SampleSizeBox.ID,
            ChunkOffsetBox.ID
    };

    public static final int ID = 0x7374626C;

    private DecodingTimeToSampleBox mDecodingTimeToSampleBox;
    private CompositionTimeToSampleBox mCompositionTimeToSampleBox;
    private SyncSampleBox mSyncSampleBox;
    private SampleToChunkBox mSampleToChunkBox;
    private SampleSizeBox mSampleSizeBox;
    private ChunkOffsetBox mChunkOffsetBox;

    public SampleTableBox(RandomAccessFile raf, long size, long offset) {
        super(raf, size, offset, ID, ChildIDs);
    }

    public DecodingTimeToSampleBox getDecodingTimeToSampleBox() throws IOException {
        if (parseBox(DecodingTimeToSampleBox.ID)) {
            mDecodingTimeToSampleBox.parse(mRaf);
        }
        return mDecodingTimeToSampleBox;
    }

    public CompositionTimeToSampleBox getCompositionTimeToSampleBox() throws IOException {
        if (parseBox(CompositionTimeToSampleBox.ID)) {
            mCompositionTimeToSampleBox.parse(mRaf);
        }
        return mCompositionTimeToSampleBox;
    }

    public SyncSampleBox getSyncSampleBox() throws IOException {
        if (parseBox(SyncSampleBox.ID)) {
            mSyncSampleBox.parse(mRaf);
        }
        return mSyncSampleBox;
    }

    public SampleToChunkBox getSampleToChunkBox() throws IOException {
        if (parseBox(SampleToChunkBox.ID)) {
            mSampleToChunkBox.parse(mRaf);
        }
        return mSampleToChunkBox;
    }

    public SampleSizeBox getSampleSizeBox() throws IOException {
        if (parseBox(SampleSizeBox.ID)) {
            mSampleSizeBox.parse(mRaf);
        }
        return mSampleSizeBox;
    }

    public ChunkOffsetBox getChunkOffsetBox() throws IOException {
        if (parseBox(ChunkOffsetBox.ID)) {
            mChunkOffsetBox.parse(mRaf);
        }
        return mChunkOffsetBox;
    }

    @Override
    protected void onParseBox(int id, long size, long pos) throws IOException {
        switch (id) {
        case DecodingTimeToSampleBox.ID:
            mDecodingTimeToSampleBox = new DecodingTimeToSampleBox(size, pos);
            break;
        case CompositionTimeToSampleBox.ID:
            mCompositionTimeToSampleBox = new CompositionTimeToSampleBox(size, pos);
            break;
        case SyncSampleBox.ID:
            mSyncSampleBox = new SyncSampleBox(size, pos);
            break;
        case SampleToChunkBox.ID:
            mSampleToChunkBox = new SampleToChunkBox(size, pos);
            break;
        case SampleSizeBox.ID:
            mSampleSizeBox = new SampleSizeBox(size, pos);
            break;
        case ChunkOffsetBox.ID:
            mChunkOffsetBox = new ChunkOffsetBox(size, pos);
            break;
        }
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
