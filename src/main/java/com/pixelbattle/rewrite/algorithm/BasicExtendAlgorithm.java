package com.pixelbattle.rewrite.algorithm;

import com.pixelbattle.rewrite.Errors;
import com.pixelbattle.rewrite.primitives.Pixel;
import com.pixelbattle.rewrite.primitives.Size;
import com.pixelbattle.rewrite.runtime.RuntimeProperties;
import com.pixelbattle.rewrite.wrappers.ChunkReadWrapper;
import com.pixelbattle.rewrite.wrappers.ChunkWriteWrapper;

import java.io.*;

public class BasicExtendAlgorithm {
    public BufferedReader inputStream;
    public BufferedWriter outputStream;
    public ChunkReadWrapper readWrapper;
    public ChunkWriteWrapper writeWrapper;

    public BasicExtendAlgorithm(BufferedReader inputStream, BufferedWriter outputStream) throws IOException, Errors.ChunkAddPixelError {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.readWrapper = new ChunkReadWrapper(inputStream);
        this.writeWrapper = new ChunkWriteWrapper(outputStream);

    }

    public void run() throws IOException, Errors.ChunkAddPixelError {
        Size extendSize = RuntimeProperties.EXTEND_SIZE;

        for (int y = 0; y < extendSize.height; y++) {
            this.readWrapper.skip(y);
            for (int x = 0; x < extendSize.width; x++) {
                Pixel emptyPixel = new Pixel((long) x, (long) y, null, null, RuntimeProperties.BASIC_FILL_COLOR);
                if (this.readWrapper.isNotEnd()) {
                    if (this.readWrapper.isThatPixel(emptyPixel)) {
                        this.writeWrapper.add(this.readWrapper.getPixel());
                        this.readWrapper.next();
                    } else this.writeWrapper.add(emptyPixel);
                } else {
                    this.writeWrapper.add(emptyPixel);
                }
            }
        }

        this.writeWrapper.endFlush();
    }
}
