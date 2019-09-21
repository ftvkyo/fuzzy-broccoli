package me.ftvkyo.fuzzy_broccoli.client.mvc.view;


import me.ftvkyo.fuzzy_broccoli.client.graphics.opengl.MyGL;
import me.ftvkyo.fuzzy_broccoli.client.graphics.opengl.ShaderProgram;
import me.ftvkyo.fuzzy_broccoli.client.graphics.primitives.VertexTextured;
import me.ftvkyo.fuzzy_broccoli.client.graphics.textures.Image;
import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel;
import me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts.Creature;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL33.*;


class ViewGame implements View {

    private final ArrayList<VertexTextured> vertices = new ArrayList<>();

    private final ArrayList<Integer> verticesOrder = new ArrayList<>();

    private ShaderProgram shaderProgram;

    private int VAO;

    private int vertexAttributeIndex;

    private int textureAttributeIndex;

    private int atlas;

    private double angle = 0;

    private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);


    ViewGame() {
        shaderProgram = ShaderProgram.fromResources(
                ShaderProgram.default3DVertexShaderResource,
                ShaderProgram.default3DFragmentShaderResource);
    }


    @Override
    public void init() {
        shaderProgram.use();

        try(Image i = Image.fromResource("me/ftvkyo/fuzzy_broccoli/client/graphics/textures/atlas.png")) {
            atlas = i.createTexture();
        }

        VAO = glGenVertexArrays();
        vertexAttributeIndex = glGetAttribLocation(shaderProgram.getID(), "vertex");
        textureAttributeIndex = glGetAttribLocation(shaderProgram.getID(), "texture");

        glClearColor(0.4f, 0.5f, 0.6f, 0.0f);
    }


    @Override
    public void draw(ManagerForModel modelManager) {
        // Load vertices into GPU and put them into the attributes list at index 0 of given VAO
        modelManager.getScreenContents(vertices, verticesOrder);
        MyGL.bindTexturedVBO(vertexAttributeIndex, textureAttributeIndex, vertices, VAO);
        MyGL.bindEBO(verticesOrder, VAO);

        Creature player = modelManager.getModel().getPlayers().get(modelManager.getModel().getPlayerName());
        Matrix4f mModel = new Matrix4f()
                .rotate((float) Math.toRadians(player.getView().y), new Vector3f(1f, 0f, 0f))
                .rotate((float) Math.toRadians(player.getView().x), new Vector3f(0f, 1f, 0f));
        Matrix4f mView = new Matrix4f().translate(new Vector3f(0f, 0f, 0f));
        Matrix4f mProjection = new Matrix4f().perspective(45f, 16f / 9f, 0.1f, 100f);

        int modelLoc = glGetUniformLocation(shaderProgram.getID(), "model");
        glUniformMatrix4fv(modelLoc, false, mModel.get(matrixBuffer));
        int viewLoc = glGetUniformLocation(shaderProgram.getID(), "view");
        glUniformMatrix4fv(viewLoc, false, mView.get(matrixBuffer));
        int projectionLoc = glGetUniformLocation(shaderProgram.getID(), "projection");
        glUniformMatrix4fv(projectionLoc, false, mProjection.get(matrixBuffer));

        if(vertices.size() > 0 && verticesOrder.size() > 0) {
            glBindVertexArray(VAO); // bind VAO
            glBindTexture(GL_TEXTURE_2D, atlas);
            glDrawElements(GL_TRIANGLES, verticesOrder.size(), GL_UNSIGNED_INT, 0);
            glBindVertexArray(0); // unbind VAO
        }
    }


    @Override
    public void clear() {
        if(VAO != 0) {
            glDeleteVertexArrays(VAO);
            VAO = 0;
        }

        if(atlas != 0) {
            glDeleteTextures(atlas);
            atlas = 0;
        }

        shaderProgram.reset();
    }
}
