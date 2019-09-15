package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives;


/**
 * Three-dimensional vector.
 */
public class Vec3 extends Vec2 {

    Vec3(float[] vec) {
        super(vec);
    }


    public Vec3(float x, float y, float z) {
        super(new float[]{x, y, z});
    }


    /**
     * Get number of components in vector.
     *
     * @return components number
     */
    public static int size() {
        return 3;
    }


    public float z() {
        return vec[2];
    }


    /**
     * When this vector is used to store color in RGB(A) format,
     * this method can be used to retrieve the blue color component.
     *
     * @return red color component
     */
    public float r() {
        return x();
    }


    /**
     * When this vector is used to store color in RGB(A) format,
     * this method can be used to retrieve the blue color component.
     *
     * @return green color component
     */
    public float g() {
        return y();
    }


    /**
     * When this vector is used to store color in RGB(A) format,
     * this method can be used to retrieve the blue color component.
     *
     * @return blue color component
     */
    public float b() {
        return z();
    }
}
