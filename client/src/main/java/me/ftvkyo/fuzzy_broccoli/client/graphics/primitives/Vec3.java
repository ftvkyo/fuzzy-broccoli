package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives;


public class Vec3 extends Vec2 {

    Vec3(float[] vec) {
        super(vec);
    }


    public Vec3(float x, float y, float z) {
        super(new float[]{x, y, z});
    }


    public static int size() {
        return 3;
    }


    public float z() {
        return vec[2];
    }


    public float r() {
        return x();
    }


    public float g() {
        return y();
    }


    public float b() {
        return z();
    }
}
