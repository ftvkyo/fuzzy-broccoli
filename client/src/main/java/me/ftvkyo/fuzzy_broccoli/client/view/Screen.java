package me.ftvkyo.fuzzy_broccoli.client.view;

import me.ftvkyo.fuzzy_broccoli.common.model.World;
import org.jetbrains.annotations.NotNull;


public class Screen {

    private Drawable currentDrawable;


    public Screen(@NotNull Drawable drawable) {
        this.currentDrawable = drawable;
        currentDrawable.init();
    }


    public void update(World currentWorld) {
        currentDrawable.draw(currentWorld);
    }


    public void changeCurrentDrawable(@NotNull Drawable drawable) {
        currentDrawable.clear();
        this.currentDrawable = drawable;
        currentDrawable.init();
    }
}
