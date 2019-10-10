package me.ftvkyo.fuzzy_broccoli.client.mvc.view


import me.ftvkyo.fuzzy_broccoli.client.graphics.opengl.MyGL
import me.ftvkyo.fuzzy_broccoli.client.graphics.opengl.ShaderProgram
import me.ftvkyo.fuzzy_broccoli.client.graphics.primitives.VertexTextured
import me.ftvkyo.fuzzy_broccoli.client.graphics.textures.Image
import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL33.*
import java.util.*


internal class ViewGame : View {

    private val vertices = ArrayList<VertexTextured>()

    private val verticesOrder = ArrayList<Int>()

    private val shaderProgram: ShaderProgram = ShaderProgram.fromResources(
            ShaderProgram.default3DVertexShaderResource,
            ShaderProgram.default3DFragmentShaderResource)

    private var VAO: Int = 0

    private var vertexAttributeIndex: Int = 0

    private var textureAttributeIndex: Int = 0

    private var atlas: Int = 0

    private val angle = 0.0

    private val matrixBuffer = BufferUtils.createFloatBuffer(16)


    override fun init() {
        shaderProgram.use()

        Image.Companion.fromResource("me/ftvkyo/fuzzy_broccoli/client/graphics/textures/atlas.png").use { i -> atlas = i.createTexture() }

        VAO = glGenVertexArrays()
        vertexAttributeIndex = glGetAttribLocation(shaderProgram.id, "vertex")
        textureAttributeIndex = glGetAttribLocation(shaderProgram.id, "texture")

        glClearColor(0.4f, 0.5f, 0.6f, 0.0f)
    }


    override fun draw(modelManager: ManagerForModel) {
        // Load vertices into GPU and put them into the attributes list at index 0 of given VAO
        modelManager.getScreenContents(vertices, verticesOrder)
        MyGL.bindTexturedVBO(vertexAttributeIndex, textureAttributeIndex, vertices, VAO)
        MyGL.bindEBO(verticesOrder, VAO)

        val model = modelManager.getModel()
                ?: return
        val player = model.getPlayers()[model.playerName]
                ?: throw RuntimeException("There is no such player: ${model.playerName}")

        val mModel = Matrix4f()
                .rotate(Math.toRadians(player.view.y.toDouble()).toFloat(), Vector3f(1f, 0f, 0f))
                .rotate(Math.toRadians(player.view.x.toDouble()).toFloat(), Vector3f(0f, 1f, 0f))
        val mView = Matrix4f().translate(Vector3f(0f, 0f, 0f))
        val mProjection = Matrix4f().perspective(45f, 16f / 9f, 0.1f, 100f)

        val modelLoc = glGetUniformLocation(shaderProgram.id, "model")
        glUniformMatrix4fv(modelLoc, false, mModel.get(matrixBuffer))
        val viewLoc = glGetUniformLocation(shaderProgram.id, "view")
        glUniformMatrix4fv(viewLoc, false, mView.get(matrixBuffer))
        val projectionLoc = glGetUniformLocation(shaderProgram.id, "projection")
        glUniformMatrix4fv(projectionLoc, false, mProjection.get(matrixBuffer))

        if (vertices.size > 0 && verticesOrder.size > 0) {
            glBindVertexArray(VAO) // bind VAO
            glBindTexture(GL_TEXTURE_2D, atlas)
            glDrawElements(GL_TRIANGLES, verticesOrder.size, GL_UNSIGNED_INT, 0)
            glBindVertexArray(0) // unbind VAO
        }
    }


    override fun clear() {
        if (VAO != 0) {
            glDeleteVertexArrays(VAO)
            VAO = 0
        }

        if (atlas != 0) {
            glDeleteTextures(atlas)
            atlas = 0
        }

        shaderProgram.reset()
    }
}
