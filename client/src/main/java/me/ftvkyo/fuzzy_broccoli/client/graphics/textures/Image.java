package me.ftvkyo.fuzzy_broccoli.client.graphics.textures;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.*;


/**
 * Image class for loading a texture into OpenGL.
 * TODO: review and update.
 */
public class Image implements AutoCloseable {

    private final ByteBuffer image;

    private final int w;

    private final int h;

    private final int comp;


    private Image(ByteBuffer image, int w, int h, int comp) {
        this.image = image;
        this.w = w;
        this.h = h;
        this.comp = comp;
    }


    /**
     * Load image from resource with given path.
     *
     * @param resource path to resource.
     * @return Image
     */
    static public Image fromResource(@NotNull String resource) {
        final int initialBufferSize = 4;

        ByteBuffer buffer;

        try(InputStream source = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
            ReadableByteChannel rbc = Channels.newChannel(source)) {
            buffer = createByteBuffer(initialBufferSize);

            while(true) {
                int bytes = rbc.read(buffer);
                if(bytes == -1) {
                    break;
                }
                if(buffer.remaining() == 0) {
                    ByteBuffer newBuffer = BufferUtils.createByteBuffer(buffer.capacity() * 3 / 2);
                    buffer.flip();
                    newBuffer.put(buffer);
                    buffer = newBuffer;
                }
            }
        } catch(IOException e) {
            throw new RuntimeException("Failed to read resource.", e);
        }

        buffer.flip();

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            if(!stbi_info_from_memory(buffer, w, h, comp)) {
                throw new RuntimeException("Failed to read image information: " + stbi_failure_reason());
            }

            ByteBuffer imageTmp = stbi_load_from_memory(buffer, w, h, comp, 0);
            if(imageTmp == null) {
                throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
            }

            return new Image(imageTmp, w.get(0), h.get(0), comp.get(0));
        }
    }


    /**
     * Create and return OpenGL texture generated from this image.
     *
     * @return OpenGL texture id
     */
    public int createTexture() {
        int texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        int format;
        if(comp == 3) {
            format = GL_RGB;
        } else {
            format = GL_RGBA;
        }

        glTexImage2D(GL_TEXTURE_2D, 0, format, w, h, 0, format, GL_UNSIGNED_BYTE, image);
        glGenerateMipmap(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, 0);

        return texID;
    }


    @Override
    public void close() {
        stbi_image_free(image);
    }
}
