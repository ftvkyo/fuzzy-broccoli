package me.ftvkyo.fuzzy_broccoli.client.mvc.view;


import me.ftvkyo.fuzzy_broccoli.client.graphics.opengl.ShaderProgram;
import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel;

import static org.lwjgl.opengl.GL33.glClearColor;


class ViewMainMenu implements View {

    private ShaderProgram shaderProgram;


    ViewMainMenu() {
        shaderProgram = ShaderProgram.fromResources(
                ShaderProgram.default3DVertexShaderResource,
                ShaderProgram.default3DFragmentShaderResource);
    }


    @Override
    public void init() {
        shaderProgram.use();
        glClearColor(0.1f, 0.3f, 0.3f, 0.0f);
    }


    @Override
    public void draw(ManagerForModel modelManager) {
    }


    @Override
    public void clear() {
        shaderProgram.reset();
    }
}
