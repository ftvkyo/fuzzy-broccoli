package me.ftvkyo.fuzzy_broccoli.client.graphics.opengl

import me.ftvkyo.fuzzy_broccoli.client.graphics.primitives.VertexColored
import me.ftvkyo.fuzzy_broccoli.client.graphics.primitives.VertexSimple
import me.ftvkyo.fuzzy_broccoli.client.graphics.primitives.VertexTextured
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL33.*
import java.nio.FloatBuffer
import java.util.*


/**
 * Wrapper for some OpenGL interactions.
 */
object MyGL {

    /**
     * Effectively call glVertexAttribPointer
     *
     *
     * Describing vertices array in current VBO that will be passed to vertex shader.
     * This description is saved in current VAO.
     *
     *
     * This method is made only for documentation purposes.
     *
     * @param index      index of attribute being configured
     * @param size       number of attribute components
     * @param type       type of attribute components
     * @param normalized is normalization needed
     * @param stride     byte offset between consecutive attributes
     * @param pointer    byte offset to the first element
     */
    fun vertexAttribPointer(index: Int,
                            size: Int,
                            type: Int,
                            normalized: Boolean,
                            stride: Int,
                            pointer: Long) {
        glVertexAttribPointer(index, size, type, normalized, stride, pointer)
    }


    /**
     * Create new Vertex Buffer Object from simple vertices and bind it to attribute positionIndex in VAO.
     *
     * @param positionIndex attribute index of position
     * @param vertices      array of vertices that should be loaded into the VBO
     * @param VAO           Vertex Arrays Object that VBO should be bind to
     */
    fun bindSimpleVBO(positionIndex: Int, vertices: ArrayList<VertexSimple>, VAO: Int) {
        require(vertices.size >= 1) { "Vertices size must be greater than 0" }

        val positionComponents = vertices[0].componentsPerPosition()


        val buffer = BufferUtils.createFloatBuffer(vertices.size * positionComponents)
        for (v in vertices) {
            v.get(buffer)
        }
        buffer.flip()

        bindVBO(vertices[0].componentSize(), positionIndex, positionComponents, buffer, VAO)
    }


    /**
     * Create new Vertex Buffer Object from colored vertices and bind it to attribute positionIndex in VAO.
     * Assumes that color attribute location is positionIndex + 1.
     * TODO: Add parameter for color attribute location.
     *
     * @param positionIndex attribute index of position
     * @param colorIndex    attribute index of color
     * @param vertices      array of vertices that should be loaded into the VBO
     * @param VAO           Vertex Arrays Object that VBO should be bind to
     */
    fun bindColoredVBO(positionIndex: Int, colorIndex: Int, vertices: ArrayList<VertexColored>, VAO: Int) {
        require(vertices.size >= 1) { "Vertices size must be greater than 0" }

        val positionComponents = vertices[0].componentsPerPosition()
        val colorComponents = vertices[0].componentsPerColor()

        val buffer = BufferUtils.createFloatBuffer(vertices.size * (positionComponents + colorComponents))
        for (v in vertices) {
            v.get(buffer)
        }
        buffer.flip()

        bindVBO(vertices[0].componentSize(), positionIndex, positionComponents, colorIndex, colorComponents, buffer, VAO)
    }


    /**
     * Create new Vertex Buffer Object from textured vertices and bind it to attribute positionIndex in VAO.
     * Assumes that texture attribute location is positionIndex + 1.
     * TODO: Add parameter for texture attribute location.
     *
     * @param positionIndex attribute index of position
     * @param textureIndex  attribute index of texture
     * @param vertices      array of vertices that should be loaded into the VBO
     * @param VAO           Vertex Arrays Object that VBO should be bind to
     */
    fun bindTexturedVBO(positionIndex: Int, textureIndex: Int, vertices: ArrayList<VertexTextured>, VAO: Int) {
        require(vertices.size >= 1) { "Vertices size must be greater than 0" }

        val positionComponents = vertices[0].componentsPerPosition()
        val textureComponents = vertices[0].componentsPerTexture()

        val buffer = BufferUtils.createFloatBuffer(vertices.size * (positionComponents + textureComponents))
        for (v in vertices) {
            v.get(buffer)
        }
        buffer.flip()

        bindVBO(vertices[0].componentSize(), positionIndex, positionComponents, textureIndex, textureComponents, buffer, VAO)
    }


    /**
     * Create new Vertex Buffer Object, load the buffer into it and bind it to the VAO.
     *
     * @param componentSize size in bytes for one component of vertex
     * @param atIndex       attribute index that the VBO should be bind to
     * @param components    amount of components per one vertex
     * @param buffer        raw consecutive vertices components that should be loaded into the VBO
     * @param VAO           Vertex Arrays Object that VBO should be bind to
     */
    private fun bindVBO(componentSize: Int, atIndex: Int, components: Int, buffer: FloatBuffer, VAO: Int) {
        val attributeStride = componentSize * components

        /* VBO is used to store vertices. */
        val VBO = glGenBuffers()
        glBindVertexArray(VAO) // bind VAO
        glBindBuffer(GL_ARRAY_BUFFER, VBO) // bind VBO

        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
        vertexAttribPointer(atIndex, components, GL_FLOAT, false, attributeStride, 0)
        glEnableVertexAttribArray(atIndex)

        glBindVertexArray(0) // unbind VAO
        /* Unbinding VBO can be skipped because it is unbound with VAO. */
        /* VBO can be deleted: https://www.khronos.org/registry/OpenGL/specs/gl/glspec33.core.pdf D.1.2 */
        glDeleteBuffers(VBO)
    }


    /**
     * Create new Vertex Buffer Object, load the buffer into it and bind it to the VAO.
     *
     * @param componentSize size in bytes for one component of vertex
     * @param atIndex1      attribute index that the VBO should be bind to
     * @param components1   amount of components per one vertex
     * @param atIndex2      attribute index that the VBO should be bind to
     * @param components2   amount of components per one vertex
     * @param buffer        raw consecutive vertices components that should be loaded into the VBO
     * @param VAO           Vertex Arrays Object that VBO should be bind to
     */
    private fun bindVBO(componentSize: Int, atIndex1: Int, components1: Int, atIndex2: Int, components2: Int, buffer: FloatBuffer, VAO: Int) {
        val attributeStride = componentSize * (components1 + components2)

        /* VBO is used to store vertices. */
        val VBO = glGenBuffers()
        glBindVertexArray(VAO) // bind VAO
        glBindBuffer(GL_ARRAY_BUFFER, VBO) // bind VBO

        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
        vertexAttribPointer(atIndex1, components1, GL_FLOAT, false, attributeStride, 0)
        vertexAttribPointer(atIndex2, components2, GL_FLOAT, false, attributeStride, (componentSize * components1).toLong())
        glEnableVertexAttribArray(atIndex1)
        glEnableVertexAttribArray(atIndex2)

        glBindVertexArray(0) // unbind VAO
        /* Unbinding VBO can be skipped because it is unbound with VAO. */
        /* VBO can be deleted: https://www.khronos.org/registry/OpenGL/specs/gl/glspec33.core.pdf D.1.2 */
        glDeleteBuffers(VBO)
    }


    /**
     * Create new Element Buffer Object and store it in the VAO.
     *
     * @param indices indices that should be loaded into the EBO
     * @param VAO     Vertex Arrays Object that EBO should be stored in
     */
    fun bindEBO(indices: ArrayList<Int>, VAO: Int) {
        val buffer = BufferUtils.createIntBuffer(indices.size)
        for (i in indices) {
            buffer.put(i)
        }
        buffer.flip()

        /* EBO is used to store indices. */
        val EBO = glGenBuffers()

        glBindVertexArray(VAO) // bind VAO
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO) // bind EBO
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)

        /* EBO should not be unbound here to store it in VAO. */
        glBindVertexArray(0) // unbind VAO

        /* Unbinding EBO can be skipped because it is unbound with VAO. */
        /* EBO can be deleted: https://www.khronos.org/registry/OpenGL/specs/gl/glspec33.core.pdf D.1.2 */
        glDeleteBuffers(EBO)
    }


    fun checkError() {
        val err = glGetError()
        if (err != GL_NO_ERROR) {
            throw RuntimeException("GL Error: $err")
        }
    }
}
