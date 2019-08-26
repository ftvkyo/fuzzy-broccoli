package me.ftvkyo.fuzzy_broccoli.client.grpahics;


import me.ftvkyo.fuzzy_broccoli.common.Float3d;


public class ColorRGB {

    private float red;

    private float green;

    private float blue;


    public ColorRGB(float r, float g, float b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }


    public ColorRGB(Float3d f) {
        this.red = f.getV1();
        this.green = f.getV2();
        this.blue = f.getV3();
    }


    public float getRed() {
        return red;
    }


    public float getGreen() {
        return green;
    }


    public float getBlue() {
        return blue;
    }
}
