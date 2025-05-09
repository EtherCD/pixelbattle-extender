package com.pixelbattle.rewrite.wrappers;

import com.pixelbattle.rewrite.Errors;
import com.pixelbattle.rewrite.managers.DeserializeManager;
import com.pixelbattle.rewrite.primitives.Chunk;
import com.pixelbattle.rewrite.primitives.Pixel;
import com.pixelbattle.rewrite.runtime.RuntimeProperties;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class ChunkReadWrapper {
    public Chunk chunk;
    public DeserializeManager manager;
    public int readBatchIndex;

    public ChunkReadWrapper(BufferedReader stream) throws IOException, Errors.ChunkAddPixelError {
        this.chunk = new Chunk(RuntimeProperties.CHUNK_LENGTH);
        this.manager = new DeserializeManager(stream);
        this.readBatchIndex = this.manager.deserializeBatch(this.chunk);
    }

    public boolean isThatPixel(Pixel pixel) {
        Pixel current = this.chunk.getCurrentPixel();
        return (Objects.equals(current.x, pixel.x) && Objects.equals(current.y, pixel.y));
    }

    public void readBatch() throws IOException, Errors.ChunkAddPixelError {
        this.readBatchIndex = this.manager.deserializeBatch(this.chunk);
    }

    public void skip(int y) throws IOException, Errors.ChunkAddPixelError {
        if (this.isNotEnd())
            while (true) {
                Pixel currentPixel = this.getPixel();
                if (currentPixel.y >= y) return;
                this.next();
            }
    }

    public boolean next() throws IOException, Errors.ChunkAddPixelError {
        if (!this.chunk.next()) {
            this.readBatch();
            this.chunk.toStart();
        }
        return this.chunk.index <= this.readBatchIndex;
    }

    public boolean isNotEnd() {
        return this.chunk.index < this.readBatchIndex;
    }

    public Pixel getPixel() {
        return this.chunk.getCurrentPixel();
    }
}
