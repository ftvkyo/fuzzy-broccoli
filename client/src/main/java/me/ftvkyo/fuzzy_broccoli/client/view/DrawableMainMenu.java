package me.ftvkyo.fuzzy_broccoli.client.view;


import me.ftvkyo.fuzzy_broccoli.common.model.World;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;


public class DrawableMainMenu implements Drawable {

    private static final DrawableMainMenu instance = new DrawableMainMenu();

    private int VAO;


    private DrawableMainMenu() {
    }


    public static DrawableMainMenu getInstance() {
        return instance;
    }


    @Override
    public void init() {
        final int FLOAT_BYTES = 4;

        float[] vertices = {
                -0.5f, -0.5f, 0.0f, // left
                0.5f, -0.5f, 0.0f, // right
                0.0f, 0.5f, 0.0f  // top
        };

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer localVBO = stack.mallocInt(1);
            IntBuffer localVAO = stack.mallocInt(1);

            glGenVertexArrays(localVAO);
            glGenBuffers(localVBO);

            glBindVertexArray(localVAO.get(0));
            glBindBuffer(GL_ARRAY_BUFFER, localVBO.get(0));
            glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

            glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * FLOAT_BYTES, 0);
            glEnableVertexAttribArray(0);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);

            glClearColor(0.3f, 0.2f, 0.1f, 0.0f);

            VAO = localVAO.get(0);
            glDeleteBuffers(localVBO.get(0));
        }
    }


    @Override
    public void draw(World currentWorld) {
        glBindVertexArray(VAO);
        glDrawArrays(GL_TRIANGLES, 0, 3);
        glBindVertexArray(0);
    }


    @Override
    public void clear() {
        if(VAO != 0) {
            glDeleteVertexArrays(VAO);
            VAO = 0;
        }
    }
}
