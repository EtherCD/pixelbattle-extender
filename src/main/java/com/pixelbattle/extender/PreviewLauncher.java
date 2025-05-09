package com.pixelbattle.extender;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pixelbattle.extender.logic.CanvasDeserializer;
import com.pixelbattle.extender.objects.Color;
import com.pixelbattle.extender.objects.Pixel;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PreviewLauncher {
    public static void launch(String fileName) {
        int windowWidth = 600;
        int windowHeight = 600;

        Canvas canvas = new Canvas(windowWidth, windowHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        try {
            Path file = Paths.get(fileName);

            CanvasDeserializer deserializer = new CanvasDeserializer();
            Gson g = new GsonBuilder()
                    .registerTypeAdapter(Pixel.class, deserializer)
                    .create();

            Type itemsListType = new TypeToken<List<Pixel>>() {}.getType();

            Charset charset = StandardCharsets.UTF_8;
            List<String> lines = Files.readAllLines(file, charset);
            StringBuilder jsonFileRaw = new StringBuilder();
            for (String line : lines)
                jsonFileRaw.append(line);

            List<Pixel> pixels = g.fromJson(jsonFileRaw.toString(), itemsListType);

            int width = Math.toIntExact(deserializer.width);
            int height = Math.toIntExact(deserializer.height);

            for (int y = 0; y < height; y++) {
                int lastX = 0;
                Color lastColor = pixels.get(y * width).color;

                for (int x = 0; x < width; x++) {
                    Pixel pixel = pixels.get(x + y * width);
                    if (!pixel.color.equals(lastColor)) {
                        drawLine(gc, lastX, y, x - lastX, lastColor);
                        lastX = x;
                        lastColor = pixel.color;
                    }
                }

                drawLine(gc, lastX, y, width - lastX, lastColor);
            }
        } catch (Exception e) {
            MessageLauncher.launch(e.getMessage());
        }

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, windowWidth, windowHeight);

        Stage stage = new Stage();
        stage.setTitle("Canvas Window");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private static void drawLine(GraphicsContext gc, int x, int y, int width, Color color) {
        gc.setFill(color.asPaint());
        gc.fillRect(x,y,width,1);
    }
}
