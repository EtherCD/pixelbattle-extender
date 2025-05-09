package com.pixelbattle.rewrite.algorithm;

import com.pixelbattle.extender.util.RuntimeProperties;
import com.pixelbattle.rewrite.Errors;
import com.pixelbattle.rewrite.managers.TagManager;
import com.pixelbattle.rewrite.primitives.Pixel;
import com.pixelbattle.rewrite.wrappers.ChunkReadWrapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TagLeadersAlgorithm {
    public BufferedReader reader;
    public BufferedWriter writer;
    public ChunkReadWrapper wrapper;
    public TagManager manager;

    public TagLeadersAlgorithm(BufferedReader reader, BufferedWriter writer) throws IOException, Errors.ChunkAddPixelError {
        this.writer = writer;
        this.reader = reader;
        this.wrapper = new ChunkReadWrapper(reader);
    }

    public void run() throws IOException, Errors.ChunkAddPixelError {
        Map<String, Integer> tags = new HashMap<>();
        while (this.wrapper.isNotEnd()) {
            Pixel pixel = this.wrapper.getPixel();
            if (this.wrapper.next()) {
                String tag = pixel.tag;
                if (tag != null)
                    tags.merge(tag, 1, Integer::sum);
            }
        }

        this.manager = new TagManager(tags, writer);

        this.manager.write();
    }
}
