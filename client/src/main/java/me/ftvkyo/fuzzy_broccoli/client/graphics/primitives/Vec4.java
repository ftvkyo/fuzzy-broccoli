package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives;


/**
 * Four-dimensional vector.
 */
public class Vec4 extends Vec3 {

    public Vec4(float x, float y, float z, float w) {
        super(new float[]{x, y, z, w});
    }


    /**
     * Get number of components in vector.
     *
     * @return components number
     */
    public static int size() {
        return 4;
    }


    public float w() {
        return vec[3];
    }


    /**
     * When this vector is used to store color in RGBA format,
     * this method can be used to retrieve the alpha color component.
     *
     * @return alpha color component
     */
    public float a() {
        return w();
    }
}
