package me.ftvkyo.fuzzy_broccoli.client.mvc.view;


import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel;
import org.jetbrains.annotations.Nullable;


/**
 * Drawable implementors are the "View" part in MVC.
 */
interface View {

    /**
     * Initialize current drawable.
     */
    void init();


    /**
     * Draw this Drawable.
     */
    void draw(@Nullable ManagerForModel modelManager);


    /**
     * Perform cleanup after use of this Drawable.
     */
    void clear();


    enum State {
        Empty,
        Ready
    }
}
