package me.ftvkyo.fuzzy_broccoli.client.view;


import me.ftvkyo.fuzzy_broccoli.common.model.World;

import static org.lwjgl.opengl.GL33.glClearColor;


public class DrawableGame implements Drawable {

    private static final DrawableGame instance = new DrawableGame();


    private DrawableGame() {
    }


    public static DrawableGame getInstance() {
        return instance;
    }


    @Override
    public void init() {
        glClearColor(0.4f, 0.5f, 0.6f, 0.0f);
    }


    @Override
    public void draw(World currentWorld) {

    }


    @Override
    public void clear() {

    }
}
