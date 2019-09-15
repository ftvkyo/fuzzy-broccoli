package me.ftvkyo.fuzzy_broccoli.client.view;


import me.ftvkyo.fuzzy_broccoli.client.graphics.opengl.ShaderProgram;
import me.ftvkyo.fuzzy_broccoli.common.model.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Drawable implementors are the "View" part in MVC.
 */
public interface Drawable {

    /**
     * Initialize current drawable
     * TODO: maybe use and set shader programs in Drawables rather than in Screen.
     *
     * @param currentShaderProgram id of shader program that will be used through Drawable's draw calls.
     */
    void init(@NotNull ShaderProgram currentShaderProgram);


    /**
     * Draw this Drawable.
     *
     * @param currentWorld world to draw (if any).
     */
    void draw(@Nullable World currentWorld);


    /**
     * Perform cleanup after use of this Drawable.
     */
    void clear();


    enum State {
        Empty,
        Ready
    }
}
