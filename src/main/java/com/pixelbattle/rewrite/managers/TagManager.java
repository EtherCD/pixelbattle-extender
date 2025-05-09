package com.pixelbattle.rewrite.managers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TagManager {
    public Map<String, Integer> tags;
    public BufferedWriter writer;

    public TagManager(Map<String, Integer> tags, BufferedWriter writer) {
        this.tags = tags;
        this.writer = writer;
    }

    public List<Map.Entry<String, Integer>> sort() {
        return tags.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .toList();
    }

    public String serializeTag(String tagName, Integer pixels) {
        return "\"" + tagName + "\" : " + pixels;
    }

    public void write() throws IOException {
        this.writer.write("{\n");
        List<Map.Entry<String, Integer>> tags = this.sort();
        for (int i = 0; i < tags.size(); i++) {
            Map.Entry<String, Integer> tag = tags.get(i);
            this.writer.write("\t" + this.serializeTag(tag.getKey(), tag.getValue()) + (i == tags.size() - 1 ? "\n" : ",\n"));
        }
        this.writer.write("}");
        this.writer.flush();
    }
}
