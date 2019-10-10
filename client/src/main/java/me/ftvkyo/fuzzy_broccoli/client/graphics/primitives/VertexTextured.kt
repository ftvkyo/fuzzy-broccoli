package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives


import org.joml.Vector2f
import org.joml.Vector3f

import java.nio.FloatBuffer


/**
 * Extension of simple vertex that can store vertex texture binding.
 */
class VertexTextured : VertexSimple {

    private val texture: Vector2f


    constructor(x: Float, y: Float, z: Float, textureX: Float, textureY: Float) : super(x, y, z) {
        texture = Vector2f(textureX, textureY)
    }


    constructor(pos: Vector3f, tex: Vector2f) : super(pos) {
        texture = tex
    }


    /**
     * Get number of components used to store texture info.
     *
     * @return number of texture components.
     */
    fun componentsPerTexture(): Int {
        return 2
    }


    override fun get(fb: FloatBuffer) {
        super.get(fb)
        texture.get(fb)
        fb.position(fb.position() + componentsPerTexture())
    }
}
