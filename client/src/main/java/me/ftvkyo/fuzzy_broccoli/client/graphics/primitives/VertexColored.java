package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives;

public class VertexColored extends VertexSimple {

    private Vec4 color;


    public VertexColored(float x, float y, float z, float r, float g, float b, float a) {
        super(x, y, z);
        color = new Vec4(r, g, b, a);
    }


    public static int componentsPerColor() {
        return Vec4.size();
    }


    @Override
    public float[] asArray() {
        return Utils.joinArrays(super.asArray(), this.color.asArray());
    }
}
