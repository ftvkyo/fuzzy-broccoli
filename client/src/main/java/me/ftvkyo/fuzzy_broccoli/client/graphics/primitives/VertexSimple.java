package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives;

public class VertexSimple {

    private Vec4 position;


    public VertexSimple(float x, float y, float z) {
        position = new Vec4(x, y, z, 1);
    }


    public static int componentSize() {
        return Vec4.componentSize();
    }


    public static int componentsPerPosition() {
        return Vec4.size();
    }


    public float[] asArray() {
        return position.asArray();
    }
}
