package me.ftvkyo.fuzzy_broccoli.client

import me.ftvkyo.fuzzy_broccoli.client.mvc.controller.ManagerForController
import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel
import me.ftvkyo.fuzzy_broccoli.client.mvc.view.ManagerForView
import me.ftvkyo.fuzzy_broccoli.common.ArgumentProcessor


/**
 * Manager for MVC: World(s), Drawable(s) and InputProcessor(s).
 * Drawables are managed through Screen.
 * Worlds are managed through WorldManager.
 * InputProcessors are managed through InputManager.
 */
class Main private constructor() : AutoCloseable {

    private val modelManager: ManagerForModel = ManagerForModel()

    private val viewManager: ManagerForView = ManagerForView()
            .setView("main-menu")

    private val controllerManager: ManagerForController = ManagerForController(viewManager.windowGLFW, viewManager, modelManager)
            .setController("main-menu")


    private fun run() {
        try {
            while (!viewManager.shouldClose()) {
                this.modelManager.update()
                this.viewManager.redraw(modelManager)
                this.controllerManager.pollEvents()

                Thread.sleep(MSPF.toLong())
            }
        } catch (e: InterruptedException) {
            println("Thread was interrupted.")
        }

    }


    override fun close() {
        modelManager.close()
        viewManager.close()
    }

    companion object {

        private const val FPS = 60

        private const val MSPF = 1000 / FPS


        @JvmStatic
        fun main(args: Array<String>) {
            val prefix = " [client] "

            ArgumentProcessor.printAll(prefix, args)

            Main().run()

            println(prefix + "Done!")
        }
    }
}
