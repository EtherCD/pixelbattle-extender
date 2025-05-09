package com.pixelbattle.rewrite;

import com.pixelbattle.rewrite.algorithm.BasicExtendAlgorithm;
import com.pixelbattle.rewrite.algorithm.EmptyGenerateAlgorithm;
import com.pixelbattle.rewrite.algorithm.TagLeadersAlgorithm;
import com.pixelbattle.rewrite.primitives.Color;
import com.pixelbattle.rewrite.primitives.Size;
import com.pixelbattle.rewrite.runtime.RuntimeProperties;

import java.io.*;
import java.nio.file.Paths;

public class Main {
    public static void tagTest() {
        try {
            File inputFile = Paths.get("pixelbattle.12-04-24.1120x630.happybirthday.json").toFile();
            File outputFile = Paths.get("tags.json").toFile();

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            TagLeadersAlgorithm algorithm = new TagLeadersAlgorithm(reader, writer);
            algorithm.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void empty() {
        try {
            RuntimeProperties.EXTEND_SIZE = new Size(10, 10);
            File outputFile = Paths.get("empty.json").toFile();

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            EmptyGenerateAlgorithm algorithm = new EmptyGenerateAlgorithm(writer);
            algorithm.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void extendEmpty() {
        try {
            RuntimeProperties.EXTEND_SIZE = new Size(11, 11);
            RuntimeProperties.BASIC_FILL_COLOR = new Color("#000000");

            File inputFile = Paths.get("empty.json").toFile();
            File outputFile = Paths.get("extended.json").toFile();

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            BasicExtendAlgorithm algorithm = new BasicExtendAlgorithm(reader, writer);
            algorithm.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        extendEmpty();
    }
}
