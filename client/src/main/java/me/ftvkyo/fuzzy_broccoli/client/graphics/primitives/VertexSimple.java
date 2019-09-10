package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives;


/**
 * Simple three-dimensional vertex.
 */
public class VertexSimple {

    private Vec4 position;


    public VertexSimple(float x, float y, float z) {
        position = new Vec4(x, y, z, 1);
    }


    /**
     * Get size of vector component.
     *
     * @return vector component size.
     */
    public static int componentSize() {
        return Vec4.componentSize();
    }


    /**
     * Get number of components used to store position info.
     *
     * @return number of position components.
     */
    public static int componentsPerPosition() {
        return Vec4.size();
    }


    /**
     * Get vertex as raw consecutive array of components.
     *
     * @return array of components.
     */
    public float[] asArray() {
        return position.asArray();
    }
}
