package com.pixelbattle.rewrite.algorithm;

import com.pixelbattle.rewrite.Errors;
import com.pixelbattle.rewrite.primitives.Pixel;
import com.pixelbattle.rewrite.primitives.Size;
import com.pixelbattle.rewrite.runtime.RuntimeProperties;
import com.pixelbattle.rewrite.wrappers.ChunkReadWrapper;
import com.pixelbattle.rewrite.wrappers.ChunkWriteWrapper;

import java.io.BufferedWriter;
import java.io.IOException;

public class EmptyGenerateAlgorithm {
    public BufferedWriter outputStream;
    public ChunkWriteWrapper writeWrapper;

    public EmptyGenerateAlgorithm(BufferedWriter outputStream) throws IOException, Errors.ChunkAddPixelError {
        this.outputStream = outputStream;
        this.writeWrapper = new ChunkWriteWrapper(outputStream);
    }

    public void run() throws IOException, Errors.ChunkAddPixelError {
        Size extendSize = RuntimeProperties.EXTEND_SIZE;

        long startTime1 = System.currentTimeMillis();
        for (int y = 0; y < extendSize.height; y++) {
            for (int x = 0; x < extendSize.width; x++) {
                Pixel emptyPixel = new Pixel((long) x, (long) y, null, null, RuntimeProperties.BASIC_FILL_COLOR);
                this.writeWrapper.add(emptyPixel);
            }
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println(endTime1 - startTime1);

        this.writeWrapper.endFlush();
    }
}
