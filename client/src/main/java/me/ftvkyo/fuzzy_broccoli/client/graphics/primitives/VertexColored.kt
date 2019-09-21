package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives


import org.joml.Vector4f

import java.nio.FloatBuffer


/**
 * Extension of simple vertex that can store vertex color information.
 */
class VertexColored(x: Float, y: Float, z: Float, red: Float, green: Float, blue: Float, alpha: Float) : VertexSimple(x, y, z) {

    private val color: Vector4f


    init {
        color = Vector4f(red, green, blue, alpha)
    }


    /**
     * Get number of components used to store color info.
     *
     * @return number of color components.
     */
    fun componentsPerColor(): Int {
        return 4
    }


    override fun get(fb: FloatBuffer) {
        super.get(fb)
        color.get(fb)
        fb.position(fb.position() + componentsPerColor())
    }
}
