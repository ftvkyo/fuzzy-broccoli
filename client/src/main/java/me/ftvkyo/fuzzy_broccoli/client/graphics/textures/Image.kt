package me.ftvkyo.fuzzy_broccoli.client.graphics.textures

import org.lwjgl.BufferUtils
import org.lwjgl.BufferUtils.createByteBuffer
import org.lwjgl.opengl.GL33.*
import org.lwjgl.stb.STBImage.*
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer
import java.nio.channels.Channels


/**
 * Image class for loading a texture into OpenGL.
 * TODO: review and update.
 */
class Image private constructor(private val image: ByteBuffer, private val w: Int, private val h: Int, private val comp: Int) : AutoCloseable {


    /**
     * Create and return OpenGL texture generated from this image.
     *
     * @return OpenGL texture id
     */
    fun createTexture(): Int {
        val texID = glGenTextures()
        glBindTexture(GL_TEXTURE_2D, texID)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)

        val format: Int = if (comp == 3) {
            GL_RGB
        } else {
            GL_RGBA
        }

        glTexImage2D(GL_TEXTURE_2D, 0, format, w, h, 0, format, GL_UNSIGNED_BYTE, image)
        glGenerateMipmap(GL_TEXTURE_2D)
        glBindTexture(GL_TEXTURE_2D, 0)

        return texID
    }


    override fun close() {
        stbi_image_free(image)
    }

    companion object {

        /**
         * Load image from resource with given path.
         *
         * @param resource path to resource.
         * @return Image
         */
        fun fromResource(resource: String): Image {
            val initialBufferSize = 4
            var buffer: ByteBuffer = createByteBuffer(initialBufferSize)

            Thread.currentThread().contextClassLoader.getResourceAsStream(resource)!!.use { source ->
                Channels.newChannel(source).use { rbc ->
                    while (true) {
                        val bytes = rbc.read(buffer)
                        if (bytes == -1) {
                            break
                        }
                        if (buffer.remaining() == 0) {
                            val newBuffer = BufferUtils.createByteBuffer(buffer.capacity() * 3 / 2)
                            buffer.flip()
                            newBuffer.put(buffer)
                            buffer = newBuffer
                        }
                    }

                    buffer.flip()
                }
            }

            MemoryStack.stackPush().use { stack ->
                val w = stack.mallocInt(1)
                val h = stack.mallocInt(1)
                val comp = stack.mallocInt(1)

                if (!stbi_info_from_memory(buffer, w, h, comp)) {
                    throw RuntimeException("Failed to read image information: " + stbi_failure_reason()!!)
                }

                val imageTmp = stbi_load_from_memory(buffer, w, h, comp, 0)
                        ?: throw RuntimeException("Failed to load image: " + stbi_failure_reason()!!)

                return Image(imageTmp, w.get(0), h.get(0), comp.get(0))
            }
        }
    }
}
