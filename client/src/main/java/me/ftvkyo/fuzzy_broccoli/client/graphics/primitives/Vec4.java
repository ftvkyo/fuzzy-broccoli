package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives;

public class Vec4 extends Vec3 {

    public Vec4(float x, float y, float z, float w) {
        super(new float[]{x, y, z, w});
    }


    public static int size() {
        return 4;
    }


    public float w() {
        return vec[3];
    }


    public float a() {
        return w();
    }
}
