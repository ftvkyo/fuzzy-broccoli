package me.ftvkyo.fuzzy_broccoli.client.view;


import me.ftvkyo.fuzzy_broccoli.client.opengl.ShaderProgram;
import me.ftvkyo.fuzzy_broccoli.common.model.World;

import static org.lwjgl.opengl.GL33.glClearColor;


public class DrawableGame implements Drawable {

    private static final DrawableGame instance = new DrawableGame();

    private State state;


    private DrawableGame() {
        this.state = State.Empty;
    }


    public static DrawableGame getInstance() {
        return instance;
    }


    @Override
    public void init(ShaderProgram currentShaderProgram) {
        if(this.state != State.Empty) {
            throw new IllegalStateException("Attempt to initialize Drawable twice.");
        }

        glClearColor(0.4f, 0.5f, 0.6f, 0.0f);

        this.state = State.Ready;
    }


    @Override
    public void draw(World currentWorld) {
        if(this.state != State.Ready) {
            throw new IllegalStateException("Attempt to use uninitialized drawable.");
        }
    }


    @Override
    public void clear() {
        this.state = State.Empty;
    }
}
