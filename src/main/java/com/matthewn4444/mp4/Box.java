package com.matthewn4444.mp4;

public abstract class Box {
    private final long mSize;
    private final long mOffset;
    private final int mId;

    static String idToString(int id) {
        byte[] b = new byte[4];
        b[0] = (byte) ((id >> 24) & 0xFF);
        b[1] = (byte) ((id >> 16) & 0xFF);
        b[2] = (byte) ((id >> 8) & 0xFF);
        b[3] = (byte) (id & 0xFF);
        return new String(b);
    }

    Box(long l, long offset, int id) {
        mSize = l;
        mOffset = offset;
        mId = id;
    }

    /**
     * Get the size of this box
     * @return size
     */
    public long getSize() {
        return mSize;
    }

    /**
     * Get the id as an int for this box
     * @return integer id
     */
    public int getId() {
        return mId;
    }

    /**
     * Get the offset of this box from the start of the file
     * @return offset from start of file
     */
    public long getOffset() {
        return mOffset;
    }

    /**
     * Get the id of this file as a 4 letter string
     * @return 4 letter string id
     */
    public String getStringId() {
        return idToString(mId);
    }

    abstract boolean hasParsed();
}
