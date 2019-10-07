package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives


import org.joml.Vector3f
import org.joml.Vector4f

import java.nio.FloatBuffer


/**
 * Simple three-dimensional vertex.
 */
open class VertexSimple {

    private var position: Vector4f? = null


    constructor(x: Float, y: Float, z: Float) {
        position = Vector4f(x, y, z, 1f)
    }


    constructor(xyz: Vector3f) {
        position = Vector4f(xyz, 1f)
    }


    /**
     * Get size of vector component.
     *
     * @return vector component size.
     */
    fun componentSize(): Int {
        return 4 // Size of float
    }


    /**
     * Get number of components used to store position info.
     *
     * @return number of position components.
     */
    fun componentsPerPosition(): Int {
        return 4
    }


    open operator fun get(fb: FloatBuffer) {
        position!!.get(fb)
        fb.position(fb.position() + componentsPerPosition())
    }
}
