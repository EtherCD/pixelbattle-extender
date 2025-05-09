package com.pixelbattle.rewrite.managers;

import com.pixelbattle.rewrite.primitives.Chunk;
import com.pixelbattle.rewrite.primitives.Color;
import com.pixelbattle.rewrite.primitives.Pixel;
import com.pixelbattle.rewrite.runtime.RuntimeProperties;

import java.io.BufferedWriter;
import java.io.IOException;

public class SerializeManager {
    public BufferedWriter stream;

    public SerializeManager(BufferedWriter stream) throws IOException {
        this.stream = stream;
        this.stream.write("[");
        this.stream.flush();
    }

    public String serializePixel(Pixel pixel) {
        return "{\"x\":" + pixel.x + ",\"y\":" + pixel.y +
                ",\"author\":" + (pixel.author == null ? "null" : "\"" + pixel.author + "\"") +
                ",\"tag\":" + (pixel.tag == null ? "null" : "\"" + pixel.tag + "\"") +
                ",\"color\":" + (pixel.color == null ? "null" : serializeColor(pixel.color)) + "}";
    }

    public String serializeColor(Color color) {
        if (RuntimeProperties.COLOR_AS_NUMBER)
            return "" + color.toNumber();
        else
            return "\"#"+color.toHex()+"\"";
    }

    public void flush(Chunk chunk) throws IOException {
        for (int index = 0; index < chunk.index; index++) {
            this.stream.write(serializePixel(chunk.pixels[index]));
            if (index + 1 < chunk.index)
                this.stream.write(",");
        }
        this.stream.flush();
    }

    public void mark() throws IOException {
        this.stream.write(",");
    }

    public void end() throws IOException {
        this.stream.write("]");
        this.stream.flush();
    }
}
