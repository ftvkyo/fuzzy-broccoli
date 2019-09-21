package me.ftvkyo.fuzzy_broccoli.client.graphics.opengl;

import me.ftvkyo.fuzzy_broccoli.client.graphics.primitives.VertexColored;
import me.ftvkyo.fuzzy_broccoli.client.graphics.primitives.VertexSimple;
import me.ftvkyo.fuzzy_broccoli.client.graphics.primitives.VertexTextured;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL33.*;


/**
 * Wrapper for some OpenGL interactions.
 */
public class MyGL {

    /**
     * Effectively call glVertexAttribPointer
     * <p>
     * Describing vertices array in current VBO that will be passed to vertex shader.
     * This description is saved in current VAO.
     * <p>
     * This method is made only for documentation purposes.
     *
     * @param index      index of attribute being configured
     * @param size       number of attribute components
     * @param type       type of attribute components
     * @param normalized is normalization needed
     * @param stride     byte offset between consecutive attributes
     * @param pointer    byte offset to the first element
     */
    public static void vertexAttribPointer(int index,
                                           int size,
                                           int type,
                                           boolean normalized,
                                           int stride,
                                           long pointer) {
        glVertexAttribPointer(index, size, type, normalized, stride, pointer);
    }


    /**
     * Create new Vertex Buffer Object from simple vertices and bind it to attribute positionIndex in VAO.
     *
     * @param positionIndex attribute index of position
     * @param vertices      array of vertices that should be loaded into the VBO
     * @param VAO           Vertex Arrays Object that VBO should be bind to
     */
    public static void bindSimpleVBO(int positionIndex, @NotNull ArrayList<VertexSimple> vertices, int VAO) {
        if(vertices.size() < 1) {
            throw new IllegalArgumentException("Vertices size must be greater than 0");
        }

        final int positionComponents = vertices.get(0).componentsPerPosition();


        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.size() * positionComponents);
        for(VertexSimple v : vertices) {
            v.get(buffer);
        }
        buffer.flip();

        bindVBO(vertices.get(0).componentSize(), positionIndex, positionComponents, buffer, VAO);
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
    public static void bindColoredVBO(int positionIndex, int colorIndex, @NotNull ArrayList<VertexColored> vertices, int VAO) {
        if(vertices.size() < 1) {
            throw new IllegalArgumentException("Vertices size must be greater than 0");
        }

        final int positionComponents = vertices.get(0).componentsPerPosition();
        final int colorComponents = vertices.get(0).componentsPerColor();

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.size() * (positionComponents + colorComponents));
        for(VertexColored v : vertices) {
            v.get(buffer);
        }
        buffer.flip();

        bindVBO(vertices.get(0).componentSize(), positionIndex, positionComponents, colorIndex, colorComponents, buffer, VAO);
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
    public static void bindTexturedVBO(int positionIndex, int textureIndex, @NotNull ArrayList<VertexTextured> vertices, int VAO) {
        if(vertices.size() < 1) {
            throw new IllegalArgumentException("Vertices size must be greater than 0");
        }

        final int positionComponents = vertices.get(0).componentsPerPosition();
        final int textureComponents = vertices.get(0).componentsPerTexture();

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.size() * (positionComponents + textureComponents));
        for(VertexTextured v : vertices) {
            v.get(buffer);
        }
        buffer.flip();

        bindVBO(vertices.get(0).componentSize(), positionIndex, positionComponents, textureIndex, textureComponents, buffer, VAO);
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
    private static void bindVBO(int componentSize, int atIndex, int components, @NotNull FloatBuffer buffer, int VAO) {
        final int attributeStride = componentSize * components;

        /* VBO is used to store vertices. */
        int VBO = glGenBuffers();
        glBindVertexArray(VAO); // bind VAO
        glBindBuffer(GL_ARRAY_BUFFER, VBO); // bind VBO

        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        MyGL.vertexAttribPointer(atIndex, components, GL_FLOAT, false, attributeStride, 0);
        glEnableVertexAttribArray(atIndex);

        glBindVertexArray(0); // unbind VAO
        /* Unbinding VBO can be skipped because it is unbound with VAO. */
        /* VBO can be deleted: https://www.khronos.org/registry/OpenGL/specs/gl/glspec33.core.pdf D.1.2 */
        glDeleteBuffers(VBO);
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
    private static void bindVBO(int componentSize, int atIndex1, int components1, int atIndex2, int components2, @NotNull FloatBuffer buffer, int VAO) {
        final int attributeStride = componentSize * (components1 + components2);

        /* VBO is used to store vertices. */
        int VBO = glGenBuffers();
        glBindVertexArray(VAO); // bind VAO
        glBindBuffer(GL_ARRAY_BUFFER, VBO); // bind VBO

        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        MyGL.vertexAttribPointer(atIndex1, components1, GL_FLOAT, false, attributeStride, 0);
        MyGL.vertexAttribPointer(atIndex2, components2, GL_FLOAT, false, attributeStride, componentSize * components1);
        glEnableVertexAttribArray(atIndex1);
        glEnableVertexAttribArray(atIndex2);

        glBindVertexArray(0); // unbind VAO
        /* Unbinding VBO can be skipped because it is unbound with VAO. */
        /* VBO can be deleted: https://www.khronos.org/registry/OpenGL/specs/gl/glspec33.core.pdf D.1.2 */
        glDeleteBuffers(VBO);
    }


    /**
     * Create new Element Buffer Object and store it in the VAO.
     *
     * @param indices indices that should be loaded into the EBO
     * @param VAO     Vertex Arrays Object that EBO should be stored in
     */
    public static void bindEBO(@NotNull ArrayList<Integer> indices, int VAO) {
        IntBuffer buffer = BufferUtils.createIntBuffer(indices.size());
        for(int i : indices) {
            buffer.put(i);
        }
        buffer.flip();

        /* EBO is used to store indices. */
        int EBO = glGenBuffers();

        glBindVertexArray(VAO); // bind VAO
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO); // bind EBO
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        /* EBO should not be unbound here to store it in VAO. */
        glBindVertexArray(0); // unbind VAO

        /* Unbinding EBO can be skipped because it is unbound with VAO. */
        /* EBO can be deleted: https://www.khronos.org/registry/OpenGL/specs/gl/glspec33.core.pdf D.1.2 */
        glDeleteBuffers(EBO);
    }


    public static void checkError() {
        int err = glGetError();
        if(err != GL_NO_ERROR) {
            throw new RuntimeException("GL Error: " + err);
        }
    }
}
