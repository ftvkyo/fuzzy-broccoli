package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives;


/**
 * Extension of simple vertex that can store vertex color information.
 */
public class VertexColored extends VertexSimple {

    private Vec4 color;


    public VertexColored(float x, float y, float z, float red, float green, float blue, float alpha) {
        super(x, y, z);
        color = new Vec4(red, green, blue, alpha);
    }


    /**
     * Get number of components used to store color info.
     *
     * @return number of color components.
     */
    public static int componentsPerColor() {
        return Vec4.size();
    }


    @Override
    public float[] asArray() {
        return Utils.joinArrays(super.asArray(), this.color.asArray());
    }
}
