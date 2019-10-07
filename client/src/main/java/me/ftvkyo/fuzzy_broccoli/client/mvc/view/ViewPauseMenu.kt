package me.ftvkyo.fuzzy_broccoli.client.mvc.view


import me.ftvkyo.fuzzy_broccoli.client.graphics.opengl.ShaderProgram
import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel

import org.lwjgl.opengl.GL33.glClearColor


internal class ViewPauseMenu : View {

    private val shaderProgram: ShaderProgram = ShaderProgram.fromResources(
            ShaderProgram.default2DVertexShaderResource,
            ShaderProgram.default2DFragmentShaderResource)


    override fun init() {
        shaderProgram.use()
        glClearColor(0.7f, 0.7f, 0.7f, 0.0f)
    }


    override fun draw(modelManager: ManagerForModel?) {}


    override fun clear() {
        shaderProgram.reset()
    }
}
