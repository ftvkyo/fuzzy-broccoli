package me.ftvkyo.fuzzy_broccoli.client.view;


import me.ftvkyo.fuzzy_broccoli.client.graphics.opengl.ShaderProgram;
import me.ftvkyo.fuzzy_broccoli.common.model.World;


/**
 * Drawable class is a "View" part in MVC.
 */
public interface Drawable {

    void init(ShaderProgram currentShaderProgram);


    void draw(World currentWorld);


    void clear();


    enum State {
        Empty,
        Ready
    }
}
