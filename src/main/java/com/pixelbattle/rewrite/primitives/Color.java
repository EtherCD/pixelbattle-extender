package com.pixelbattle.rewrite.primitives;

public class Color {
    private String asHexString = null;
    private int asNumber = -1;

    public Color(String hex) {
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }
        this.asHexString = hex;
    }

    public Color(int number) {
        this.asNumber = number;
    }

    public String toHex() {
        if (this.asHexString != null)
            return this.asHexString;
        return String.format("%06X", this.asNumber);
    }

    public int toNumber() {
        if (this.asNumber != -1)
            return this.asNumber;
        return Integer.parseInt(this.asHexString, 16);
    }
}
