package com.pixelbattle.extender.wrappers;

import com.pixelbattle.extender.Errors;
import com.pixelbattle.extender.managers.DeserializeManager;
import com.pixelbattle.extender.primitives.Chunk;
import com.pixelbattle.extender.primitives.Pixel;
import com.pixelbattle.extender.primitives.Position;
import com.pixelbattle.extender.primitives.Size;
import com.pixelbattle.extender.runtime.RuntimeProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class ChunkReadWrapper {
    public Chunk chunk;
    public DeserializeManager manager;
    public boolean isThatPixel(Pixel pixel) {
        Pixel current = this.chunk.getCurrentPixel();
        Position cPos = current.getPosition();
        if (cPos.x > RuntimeProperties.OLD_SIZE.width || cPos.y > RuntimeProperties.OLD_SIZE.height) return false;
        Position oPos = pixel.getPosition();
        return (Objects.equals(cPos.x, oPos.x) && Objects.equals(cPos.y, oPos.y));
    }

    public int readBatchIndex;

    public ChunkReadWrapper(BufferedReader stream) throws IOException, Errors.ChunkAddPixelError {
        this.chunk = new Chunk(RuntimeProperties.CHUNK_LENGTH);
        this.manager = new DeserializeManager(stream);
        this.readBatchIndex = this.manager.deserializeBatch(this.chunk);
    }

    public boolean inBounds(Position pos, Position offset, Size size) {
        int relX = pos.x - offset.x;
        int relY = pos.y - offset.y;
        return relX >= 0 && relY >= 0 && relX < size.width && relY < size.height;
    }

    public void readBatch() throws IOException, Errors.ChunkAddPixelError {
        this.readBatchIndex = this.manager.deserializeBatch(this.chunk);
    }

    public void skip(int y) throws IOException, Errors.ChunkAddPixelError {
        if (this.isNotEnd())
            while (true) {
                Pixel currentPixel = this.getPixel();
                Position position = currentPixel.getPosition();
                if (position.y >= y) return;
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
