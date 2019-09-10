package me.ftvkyo.fuzzy_broccoli.client.view;


import me.ftvkyo.fuzzy_broccoli.client.graphics.opengl.MyGL;
import me.ftvkyo.fuzzy_broccoli.client.graphics.opengl.ShaderProgram;
import me.ftvkyo.fuzzy_broccoli.client.graphics.primitives.VertexTextured;
import me.ftvkyo.fuzzy_broccoli.client.graphics.textures.Image;
import me.ftvkyo.fuzzy_broccoli.common.model.World;

import static org.lwjgl.opengl.GL33.*;


public class DrawableMainMenu implements Drawable {

    private static final DrawableMainMenu instance = new DrawableMainMenu();

    private final VertexTextured[] vertices = {
            new VertexTextured(0.0f, 0.0f, 0.0f, 0.5f, 0.5f), // 0: center
            new VertexTextured(0.5f, 0.5f, 0.0f, 1.0f, 1.0f), // 1: top-right
            new VertexTextured(-0.5f, 0.5f, 0.0f, 0.0f, 1.0f), // 2: top-left
            new VertexTextured(-0.5f, -0.5f, 0.0f, 0.0f, 0.0f), // 3: bottom-left
            new VertexTextured(0.5f, -0.5f, 0.0f, 1.0f, 0.0f), // 4: bottom-right
    };

    private final int[] verticesOrder = {
            0, 1, 2,
            0, 4, 1,
            0, 2, 3,
            0, 3, 4,
    };

    private State state;

    private int VAO;

    private int texture;


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

        try(Image i = Image.fromResource("me/ftvkyo/fuzzy_broccoli/client/textures/texture.png")) {
            texture = i.createTexture();
        }

        VAO = glGenVertexArrays();
        // Load vertices into GPU and put them into the attributes list at index 0 of given VAO
        int vertexAttributeIndex = glGetAttribLocation(currentShaderProgram.getID(), "vertex");
        assert glGetAttribLocation(currentShaderProgram.getID(), "texture") == vertexAttributeIndex + 1;
        MyGL.bindVBO(vertexAttributeIndex, vertices, VAO);
        MyGL.bindEBO(verticesOrder, VAO);

        glClearColor(0.1f, 0.3f, 0.3f, 0.0f);

        this.state = State.Ready;
    }


    @Override
    public void draw(World currentWorld) {
        if(this.state != State.Ready) {
            throw new IllegalStateException("Attempt to use uninitialized drawable.");
        }

        glBindVertexArray(VAO); // bind VAO
        glBindTexture(GL_TEXTURE_2D, texture);
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
