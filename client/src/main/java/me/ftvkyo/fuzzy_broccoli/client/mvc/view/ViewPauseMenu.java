package me.ftvkyo.fuzzy_broccoli.client.mvc.view;


import me.ftvkyo.fuzzy_broccoli.client.graphics.opengl.ShaderProgram;
import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel;

import static org.lwjgl.opengl.GL33.glClearColor;


class ViewPauseMenu implements View {

    private ShaderProgram shaderProgram;


    ViewPauseMenu() {
        shaderProgram = ShaderProgram.fromResources(
                ShaderProgram.default2DVertexShaderResource,
                ShaderProgram.default2DFragmentShaderResource);
    }


    @Override
    public void init() {
        shaderProgram.use();
        glClearColor(0.7f, 0.7f, 0.7f, 0.0f);
    }


    @Override
    public void draw(ManagerForModel modelManager) {
    }


    @Override
    public void clear() {
        shaderProgram.reset();
    }
}
