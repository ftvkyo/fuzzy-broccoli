package me.ftvkyo.fuzzy_broccoli.client.mvc.view


import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel


/**
 * Drawable implementors are the "View" part in MVC.
 */
internal interface View {

    /**
     * Initialize current drawable.
     */
    fun init()


    /**
     * Draw this Drawable.
     */
    fun draw(modelManager: ManagerForModel)


    /**
     * Perform cleanup after use of this Drawable.
     */
    fun clear()
}
