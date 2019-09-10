package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives;


/**
 * Two-dimensional vector.
 */
public class Vec2 {

    float[] vec;


    Vec2(float[] vec) {
        this.vec = vec;
    }


    public Vec2(float x, float y) {
        this.vec = new float[]{x, y};
    }


    /**
     * Get size of vector component.
     *
     * @return vector component size.
     */
    public static int componentSize() {
        return 4;
    }


    /**
     * Get number of components in vector.
     *
     * @return components number
     */
    public static int size() {
        return 2;
    }


    /**
     * Get a copy of internal array.
     *
     * @return copy of vector data.
     */
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
