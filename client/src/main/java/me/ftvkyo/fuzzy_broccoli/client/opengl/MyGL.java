package me.ftvkyo.fuzzy_broccoli.client.opengl;

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


    public static void bindNewVBO(int index, float[] vertices, int VAO) {
        final int vecSize = 3;
        final int COMPONENT_BYTES_SIZE = 4;

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length).put(vertices).flip();

        /* VBO is used to store vertices. */
        int VBO = glGenBuffers();

        glBindVertexArray(VAO); // bind VAO

        glBindBuffer(GL_ARRAY_BUFFER, VBO); // bind VBO
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        MyGL.vertexAttribPointer(index, vecSize, GL_FLOAT, false, vecSize * COMPONENT_BYTES_SIZE, 0);
        glEnableVertexAttribArray(index);

        glBindVertexArray(0); // unbind VAO
        /* Unbinding VBO can be skipped because it is unbound with VAO. */
        /* VBO can be deleted: https://www.khronos.org/registry/OpenGL/specs/gl/glspec33.core.pdf D.1.2 */
        glDeleteBuffers(VBO);
    }


    public static void bindNewEBO(int[] indices, int VAO) {
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
