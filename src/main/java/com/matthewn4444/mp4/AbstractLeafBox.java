package com.matthewn4444.mp4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

public abstract class AbstractLeafBox extends Box {
    private int mVersion;
    private int mFlags;
    private boolean mParsed;

    public AbstractLeafBox(long size, long offset, int id) {
        super(size, offset, id);
        mVersion = -1;
        mFlags = -1;
        mParsed = false;
    }

    /**
     * Get version to parse this box
     * @return version
     */
    public int getVersion() {
        return mVersion;
    }

    /**
     * Get the flags for this box
     * @return flags
     */
    public int getFlags() {
        return mFlags;
    }

    @Override
    boolean hasParsed() {
        return mParsed;
    }

    void parse(RandomAccessFile raf) throws IOException {
        if (!mParsed) {
            mParsed = true;
            raf.seek(getOffset() + 8);
            readVersionAndFlags(raf);
            onParse(raf);
        }
    }

    protected abstract void onParse(RandomAccessFile raf) throws IOException;

    protected void readVersionAndFlags(RandomAccessFile raf) throws IOException {
        // Version is 1 byte and 3 bytes for flags
        mVersion = raf.readByte();
        mFlags = ((raf.readByte() & 0xFF) << 16) | ((raf.readByte() & 0xFF) << 8) | (raf.readByte() & 0xFF);
    }

    protected long readUInt32(RandomAccessFile raf) throws IOException {
        return raf.readInt() & 0xFFFFFFFFL;
    }

    protected String readString(RandomAccessFile raf) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int read;
        while ((read = raf.readByte()) != 0) {
            out.write(read);
        }
        return new String(out.toByteArray(), "UTF-8");
    }

    protected float readFixedPoint1616(RandomAccessFile raf) throws IOException {
        int result = 0;
        result |= ((raf.readByte() << 24) & 0xFF000000);
        result |= ((raf.readByte() << 16) & 0xFF0000);
        result |= ((raf.readByte() << 8) & 0xFF00);
        result |= ((raf.readByte()) & 0xFF);
        return result / 65536f;
    }

    protected float readFixedPoint88(RandomAccessFile raf) throws IOException {
        short result = 0;
        result |= ((raf.readByte() << 8) & 0xFF00);
        result |= ((raf.readByte()) & 0xFF);
        return result / 256f;
    }

    protected String readIso639(RandomAccessFile raf) throws IOException {
        int bits = raf.readUnsignedShort();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int c = (bits >> (2 - i) * 5) & 0x1f;
            result.append((char) (c + 0x60));
        }
        return result.toString();
    }

    protected Date toDate(long secondsSince) {
        return new Date((secondsSince - 2082844800L) * 1000L);
    }
}
