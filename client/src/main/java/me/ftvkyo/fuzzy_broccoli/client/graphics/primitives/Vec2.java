package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives;

public class Vec2 {

    float[] vec;


    Vec2(float[] vec) {
        this.vec = vec;
    }


    public Vec2(float x, float y) {
        this.vec = new float[]{x, y};
    }


    public static int componentSize() {
        return 4;
    }


    public static int size() {
        return 2;
    }


    public float[] asArray() {
        return vec.clone();
    }


    public float x() {
        return vec[0];
    }


    public float y() {
        return vec[1];
    }
}
