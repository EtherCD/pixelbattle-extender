package com.pixelbattle.rewrite.primitives;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // игнорировать лишние поля вроде "_id"
public class Pixel {
    public long x;
    public long y;
    public String tag;
    public String author;

    public Color color;

    // Обязательный пустой конструктор
    public Pixel() {
    }

    // Необязательный удобный конструктор
    public Pixel(long x, long y, String tag, String author, Color color) {
        this.x = x;
        this.y = y;
        this.tag = tag;
        this.author = author;
        this.color = color;
    }
}