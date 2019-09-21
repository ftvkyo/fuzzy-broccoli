package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives;


import org.joml.Vector3f;
import org.joml.Vector4f;

import java.nio.FloatBuffer;


/**
 * Simple three-dimensional vertex.
 */
public class VertexSimple {

    private Vector4f position;


    public VertexSimple(float x, float y, float z) {
        position = new Vector4f(x, y, z, 1);
    }


    public VertexSimple(Vector3f xyz) {
        position = new Vector4f(xyz, 1);
    }


    /**
     * Get size of vector component.
     *
     * @return vector component size.
     */
    public int componentSize() {
        return 4; // Size of float
    }


    /**
     * Get number of components used to store position info.
     *
     * @return number of position components.
     */
    public int componentsPerPosition() {
        return 4;
    }


    public void get(FloatBuffer fb) {
        position.get(fb);
        fb.position(fb.position() + componentsPerPosition());
    }
}
