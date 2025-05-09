package com.pixelbattle.extender.objects;

import com.pixelbattle.extender.util.RuntimeProperties;

public class Color {
    private int numberValue = -1;
    private String stringValue;

    public Color(int numberValue) {
        this.numberValue = numberValue;
    }

    public Color(String stringValue) {
        this.stringValue = stringValue;
    }

    private int fromString(String value) {
        if (value.startsWith("#")) {
            value = value.substring(1);
        }
        return Integer.parseInt(value, 16);
    }

    private String fromInt(int value) {
        return String.format("%06X", value);
    }


    public int asInt() {
        if (this.numberValue != -1)
            return this.numberValue;
        else return this.fromString(this.stringValue);
    }

    public String asString() {
        if (this.stringValue != null)
            return this.stringValue;
        else return this.fromInt(this.numberValue);
    }

    public javafx.scene.paint.Color asPaint() {
        int color = this.asInt();

        int r = ((color >> 16) & 0xFF) / 255;
        int g = ((color >> 8) & 0xFF) /255;
        int b = (color & 0xFF) / 255;

        return new javafx.scene.paint.Color(r,g,b,1);
    }

    @Override
    public String toString() {
        if (RuntimeProperties.colorsAsNumber)
            return ""+this.asInt();
        else {
            String asString = this.asString();
            if (!asString.startsWith("#")) asString = "#" + asString;
            return "\"" + asString + "\"";
        }
    }
}
