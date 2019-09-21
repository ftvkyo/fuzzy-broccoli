package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives;


import org.joml.Vector2f;
import org.joml.Vector3f;

import java.nio.FloatBuffer;


/**
 * Extension of simple vertex that can store vertex texture binding.
 */
public class VertexTextured extends VertexSimple {

    private Vector2f texture;


    public VertexTextured(float x, float y, float z, float textureX, float textureY) {
        super(x, y, z);
        texture = new Vector2f(textureX, textureY);
    }


    public VertexTextured(Vector3f pos, Vector2f tex) {
        super(pos);
        texture = tex;
    }


    /**
     * Get number of components used to store texture info.
     *
     * @return number of texture components.
     */
    public int componentsPerTexture() {
        return 2;
    }


    public void get(FloatBuffer fb) {
        super.get(fb);
        texture.get(fb);
        fb.position(fb.position() + componentsPerTexture());
    }
}
