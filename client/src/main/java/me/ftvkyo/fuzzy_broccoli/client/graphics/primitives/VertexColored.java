package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives;


import org.joml.Vector4f;

import java.nio.FloatBuffer;


/**
 * Extension of simple vertex that can store vertex color information.
 */
public class VertexColored extends VertexSimple {

    private Vector4f color;


    public VertexColored(float x, float y, float z, float red, float green, float blue, float alpha) {
        super(x, y, z);
        color = new Vector4f(red, green, blue, alpha);
    }


    /**
     * Get number of components used to store color info.
     *
     * @return number of color components.
     */
    public int componentsPerColor() {
        return 4;
    }


    public void get(FloatBuffer fb) {
        super.get(fb);
        color.get(fb);
        fb.position(fb.position() + componentsPerColor());
    }
}
