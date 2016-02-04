# MP4 Reader (0.1.0) [Java]
This is a library project to read metadata in MP4 files. This
project is not fully complete but perfectly usable to extract metadata such as
duration or chunk information. This only reads mp4 files and does not write them.
This project also does not have the ability to read video/audio frames.

You can read the documentation for the MP4 spec at:
[https://developer.apple.com/library/mac/documentation/QuickTime/QTFF/QTFFChap2/qtff2.html](https://developer.apple.com/library/mac/documentation/QuickTime/QTFF/QTFFChap2/qtff2.html)

## Why this library over others?

At first I used [mp4parser](https://github.com/sannies/mp4parser) and it works,
if you have the entire file. In my project I required reading mp4 files without
downloading the entire file. Even if a region of data is avaliable, mp4parser will
not read the offsets, size or data correctly, they require you to have the entire
file. This is not ideal for streaming or using while downloading files. This is
not a replacement to mp4parser in any means, it is a simplier way to read metadata
from mp4 files.

This library was built in mind for reading when needed. Therefore if you need
to get MovieBox, it will return null if it can't read it. Once you download the
MovieBox region it will return it without reading its children. Once you call
other ***get*** functions from these boxes it will parse the data when needed.

This library is lightweight and does not include any extra complexity. Most of the
code can be easily extended to read newer types of data with ease. One day I will
complete all the atom structures in this project.

## Sample Code

    // You can either present a RandomAccessFile if you have one or a string path
    MP4Reader reader = new MP4Reader("/path/to/my/mp4");

    // Check if this is a valid mp4 file
    if (!reader.readHeader()) {
        throw new RuntimeException("Reading a non-mp4 file!");
    }

    // Read the duration of the video
    MovieHeaderBox box = reader.getMovieBox().getMovieHeaderBox();
    int durationMs = box.getDuration() * 1f / box.getTimescale() * 1000;

    // Get the list of chunk offsets in the video
    ChunkOffsetBox offsetBox = mReader.getMovieBox().getTrackBox(0).getMediaBox()
                    .getMediaInformationBox().getSampleTableBox().getChunkOffsetBox();
    long[] offsets = offsetBox.getValues();

    reader.close();


## Integration with an Android Studio project

1. You can clone the project in the root of your project here:
    ``https://github.com:matthewn4444/MP4Reader.git``

    Or add it as a submodule
    ``git submodule add https://github.com:matthewn4444/MP4Reader.git``


2. In your project's **settings.gradle**:
    ``include ':mp4reader'``

3. In your app's **build.gradle** add this line to dependencies:
    ``compile project(':mp4reader')``

4. You are now ready to use this library

## Future Work/TODO

- Extract header information (type of mp4 etc)
- Ability to extract audio/video raw data
- Finish all the atom structures missed
