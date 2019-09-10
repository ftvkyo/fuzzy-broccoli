package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives;

public class VertexTextured extends VertexSimple {

    private Vec2 texture;


    public VertexTextured(float x, float y, float z, float textureX, float textureY) {
        super(x, y, z);
        texture = new Vec2(textureX, textureY);
    }


    public static int componentsPerTexture() {
        return Vec2.size();
    }


    @Override
    public float[] asArray() {
        return Utils.joinArrays(super.asArray(), this.texture.asArray());
    }
}
