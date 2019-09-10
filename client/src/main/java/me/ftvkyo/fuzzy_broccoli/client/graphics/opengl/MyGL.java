package me.ftvkyo.fuzzy_broccoli.client.graphics.opengl;

import me.ftvkyo.fuzzy_broccoli.client.graphics.primitives.Utils;
import me.ftvkyo.fuzzy_broccoli.client.graphics.primitives.VertexColored;
import me.ftvkyo.fuzzy_broccoli.client.graphics.primitives.VertexSimple;
import me.ftvkyo.fuzzy_broccoli.client.graphics.primitives.VertexTextured;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;


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


    public static void bindVBO(int atIndex, VertexSimple[] vertices, int VAO) {
        final int positionComponents = VertexSimple.componentsPerPosition();

        float[] verticesArray = Utils.asArray(vertices);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(verticesArray.length).put(verticesArray).flip();
        bindVBO(VertexSimple.componentSize(), atIndex, positionComponents, buffer, VAO);
    }


    public static void bindVBO(int atIndex, VertexColored[] vertices, int VAO) {
        final int positionComponents = VertexColored.componentsPerPosition();
        final int colorComponents = VertexColored.componentsPerColor();

        float[] verticesArray = Utils.asArray(vertices);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(verticesArray.length).put(verticesArray).flip();
        bindVBO(VertexColored.componentSize(), atIndex, positionComponents, atIndex + 1, colorComponents, buffer, VAO);
    }


    public static void bindVBO(int atIndex, VertexTextured[] vertices, int VAO) {
        final int positionComponents = VertexTextured.componentsPerPosition();
        final int textureComponents = VertexTextured.componentsPerTexture();

        float[] verticesArray = Utils.asArray(vertices);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(verticesArray.length).put(verticesArray).flip();
        bindVBO(VertexColored.componentSize(), atIndex, positionComponents, atIndex + 1, textureComponents, buffer, VAO);
    }


    private static void bindVBO(int componentSize, int atIndex, int components, FloatBuffer buffer, int VAO) {
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


    private static void bindVBO(int componentSize, int atIndex1, int components1, int atIndex2, int components2, FloatBuffer buffer, int VAO) {
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


    public static void bindEBO(int[] indices, int VAO) {
        IntBuffer buffer = BufferUtils.createIntBuffer(indices.length).put(indices).flip();

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
}
