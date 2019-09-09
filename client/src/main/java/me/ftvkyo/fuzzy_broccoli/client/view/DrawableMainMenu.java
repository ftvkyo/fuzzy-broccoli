package me.ftvkyo.fuzzy_broccoli.client.view;


import me.ftvkyo.fuzzy_broccoli.client.opengl.MyGL;
import me.ftvkyo.fuzzy_broccoli.client.opengl.ShaderProgram;
import me.ftvkyo.fuzzy_broccoli.common.model.World;

import static org.lwjgl.opengl.GL33.*;


public class DrawableMainMenu implements Drawable {

    private static final DrawableMainMenu instance = new DrawableMainMenu();

    private final float[] verticesVec3s = {
            0.0f, 0.0f, 0.0f, // center
            0.5f, 0.5f, 0.0f, // top-right
            -0.5f, 0.5f, 0.0f, // top-left
            -0.5f, -0.5f, 0.0f, //bottom-left
            0.5f, -0.5f, 0.0f, // bottom-right
    };

    private final int[] verticesOrder = {
            0, 1, 2,
            0, 1, 4,
            0, 2, 3,
            0, 4, 3,
    };

    private State state;

    private int VAO;


    private DrawableMainMenu() {
        this.state = State.Empty;
    }


    public static DrawableMainMenu getInstance() {
        return instance;
    }


    @Override
    public void init(ShaderProgram currentShaderProgram) {
        if(this.state != State.Empty) {
            throw new IllegalStateException("Attempt to initialize Drawable twice.");
        }

        VAO = glGenVertexArrays();
        // Load vertices into GPU and put them into the attributes list at index 0 of given VAO
        MyGL.bindNewVBO(glGetAttribLocation(currentShaderProgram.getID(), "vertex"), verticesVec3s, VAO);
        MyGL.bindNewEBO(verticesOrder, VAO);

        glClearColor(0.1f, 0.3f, 0.3f, 0.0f);

        this.state = State.Ready;
    }


    @Override
    public void draw(World currentWorld) {
        if(this.state != State.Ready) {
            throw new IllegalStateException("Attempt to use uninitialized drawable.");
        }

        glBindVertexArray(VAO); // bind VAO
        glDrawElements(GL_TRIANGLES, verticesOrder.length, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0); // unbind VAO
    }


    @Override
    public void clear() {
        if(VAO != 0) {
            glDeleteVertexArrays(VAO);
            VAO = 0;
        }

        this.state = State.Empty;
    }
}
