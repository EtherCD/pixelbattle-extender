package com.pixelbattle.extender.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.pixelbattle.extender.General;
import com.pixelbattle.extender.MessageLauncher;
import com.pixelbattle.extender.events.ProcessEventBus;
import com.pixelbattle.extender.objects.Canvas;
import com.pixelbattle.extender.objects.Chunk;
import com.pixelbattle.extender.objects.Pixel;
import com.pixelbattle.extender.util.RuntimeProperties;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class FileManager {
    public static Canvas loadCanvas(String fileName, ProcessEventBus event) throws IOException, JsonParseException {
        Path filePath = Paths.get(fileName);
        Charset charset = StandardCharsets.UTF_8;
        CanvasDeserializer deserializer = new CanvasDeserializer();
        Gson g = new GsonBuilder()
                .registerTypeAdapter(Pixel.class, deserializer)
                .create();

        long startTime = System.currentTimeMillis();
        event.setParsingStatus("Started");

        Type itemsListType = new TypeToken<List<Pixel>>() {}.getType();

        List<String> lines = Files.readAllLines(filePath, charset);
        StringBuilder jsonFileRaw = new StringBuilder();
        for (String line : lines)
            jsonFileRaw.append(line);

        List<Pixel> pixels = g.fromJson(jsonFileRaw.toString(), itemsListType);

        if (pixels == null)
            pixels = new ArrayList<>();

        Canvas canvas = new Canvas(Math.toIntExact(deserializer.width) + 1, Math.toIntExact(deserializer.height) + 1);

        canvas.setPixels(pixels);

        long endTime = System.currentTimeMillis();
        long diff = endTime - startTime;
        event.setParsingStatus("Done in " + Math.floor((double) diff /100)/10 + "s.");

        return canvas;
    }

    public static void configure(File outputCanvasFile) {
        Paths.get(RuntimeProperties.saveTo).toFile().mkdir();
//        outputCanvasFile.delete();

    }

    private static void tryWrite(BufferedWriter writer, String value) throws IOException {
            writer.write(value);
    }

    public static void writeChunk(Chunk chunk, BufferedWriter writer, int currentChunkId) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (currentChunkId == 0) builder.append("[");
        for (int i = 0; i < chunk.lastId; i++)
            builder.append(chunk.get(i).toString() + ",");
        if (currentChunkId == General.countOfChunks-1) {
            builder.deleteCharAt(builder.length()-1);
            builder.append("]");
        }

        tryWrite(writer, builder.toString());
    }
}
