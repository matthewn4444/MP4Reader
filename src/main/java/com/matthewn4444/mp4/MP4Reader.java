package com.matthewn4444.mp4;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MP4Reader extends AbstractGroupBox {
    private static final String TAG = "MP4Reader";
    private static final int[] ChildIDs = new int[] {
            FileTypeBox.ID, MovieBox.ID, MediaDataBox.ID
    };

    private FileTypeBox mFileTypeBox;
    private MovieBox mMovieBox;
    private MediaDataBox mMediaDataBox;

    /**
     * Initalize reader with a filepath
     * @param  filename    path to the video
     * @throws IOException video path does not exist, not readable
     */
    public MP4Reader(String filename) throws IOException {
        this(new RandomAccessFile(filename, "r"));
    }

    /**
     * Initalize reader with a RandomAccessFile
     * @param  filename    path to the video
     * @throws IOException video path does not exist, not readable
     */
    public MP4Reader(RandomAccessFile raf) throws IOException {
        super(raf, ChildIDs);
    }

    /**
     * Close the reader
     * @throws IOException closing fails
     */
    public void close() throws IOException {
        mRaf.close();
    }

    /**
     * Determine if this is an mp4 file and can read
     * @return true if is mp4 file
     * @throws IOException   unable to read region of file
     */
    public boolean readHeader() throws IOException {
        parseBox(FileTypeBox.ID);
        return mFileTypeBox != null;
    }

    /**
     * Gets the movie box atom structure
     * @return MovieBox object if avaliable
     * @throws IOException   cannot read file
     */
    public MovieBox getMovieBox() throws IOException {
        parseBox(MovieBox.ID);
        return mMovieBox;
    }

    /**
     * Gets the media data box atom structure
     * @return MediaDataBox object if avaliable
     * @throws IOException   cannot read file
     */
    public MediaDataBox getMediaDataBox() throws IOException {
        parseBox(MediaDataBox.ID);
        return mMediaDataBox;
    }

    @Override
    protected void onParseBox(int id, long size, long pos) throws IOException {
        if (id == FileTypeBox.ID) {
            mFileTypeBox = new FileTypeBox(size, pos);
        } else if (id == MovieBox.ID) {
            mMovieBox = new MovieBox(mRaf, size, pos);
        } else {
            mMediaDataBox = new MediaDataBox(size, pos);
        }
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
