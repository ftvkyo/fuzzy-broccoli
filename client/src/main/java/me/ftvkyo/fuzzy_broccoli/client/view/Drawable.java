package me.ftvkyo.fuzzy_broccoli.client.view;


import me.ftvkyo.fuzzy_broccoli.common.model.World;


/**
 * Drawable class is a "View" part in MVC.
 */
public interface Drawable {

    void init();


    void draw(World currentWorld);


    void clear();
}
