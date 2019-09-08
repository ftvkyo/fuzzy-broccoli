package me.ftvkyo.fuzzy_broccoli.client.view;


import me.ftvkyo.fuzzy_broccoli.common.model.World;

import static org.lwjgl.opengl.GL33.glClearColor;


public class DrawablePauseMenu implements Drawable {

    private static final DrawablePauseMenu instance = new DrawablePauseMenu();


    private DrawablePauseMenu() {
    }


    public static DrawablePauseMenu getInstance() {
        return instance;
    }


    @Override
    public void init() {
        glClearColor(0.7f, 0.7f, 0.7f, 0.0f);
    }


    @Override
    public void draw(World currentWorld) {
        DrawableGame.getInstance().draw(currentWorld);
    }


    @Override
    public void clear() {

    }
}
