package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class MovieBox extends AbstractGroupBox {
    private static final String TAG = "MovieBox";
    private static final int[] ChildIDs = new int[] {
        MovieHeaderBox.ID, TrackBox.ID
    };

    public static final int ID = 0x6d6f6F76;

    private final List<TrackBox> mTrackBoxes;
    private MovieHeaderBox mMovieHeaderBox;

    MovieBox(RandomAccessFile raf, long size, long offset) {
        super(raf, size, offset, ID, ChildIDs);
        mTrackBoxes = new ArrayList<TrackBox>();
    }

    public MovieHeaderBox getMovieHeaderBox() throws IOException {
        if (parseBox(MovieHeaderBox.ID)) {
            mMovieHeaderBox.parse(mRaf);
        }
        return mMovieHeaderBox;
    }

    public TrackBox getTrackBox(int index) throws IOException {
        parseAllBoxes();
        return mTrackBoxes.get(index);
    }

    public int getTrackBoxCount() throws IOException {
        parseAllBoxes();
        return mTrackBoxes.size();
    }

    @Override
    protected void onParseBox(int id, long size, long pos) throws IOException {
        if (id == MovieHeaderBox.ID) {
            mMovieHeaderBox = new MovieHeaderBox(size, pos);
        } else {
            TrackBox trackBox = new TrackBox(mRaf, size, pos);
            mTrackBoxes.add(trackBox);
        }
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}


