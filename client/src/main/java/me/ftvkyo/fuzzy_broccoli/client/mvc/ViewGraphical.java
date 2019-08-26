package me.ftvkyo.fuzzy_broccoli.client.mvc;

import me.ftvkyo.fuzzy_broccoli.client.grpahics.ColorRGB;
import me.ftvkyo.fuzzy_broccoli.common.Float3d;
import me.ftvkyo.fuzzy_broccoli.common.event.EventStop;
import me.ftvkyo.fuzzy_broccoli.common.event.IEventListener;
import me.ftvkyo.fuzzy_broccoli.common.mvc.IView;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;


public class ViewGraphical implements IView {

    private IEventListener el = null;

    private long window;

    private ColorRGB backgroundColor = new ColorRGB(0, 0, 0);


    public ViewGraphical() {
    }


    @Override
    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
        if(window == NULL) {
            glfwTerminate();
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwSetKeyCallback(window, (w, key, scancode, action, mods) -> {
            if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                if(el != null) {
                    el.listen(new EventStop());
                }
            }
        });

        try(MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }


    @Override
    public void redraw() {
        GL.createCapabilities();

        ColorRGB localBackgroundColor = backgroundColor;
        glClearColor(localBackgroundColor.getRed(),
                localBackgroundColor.getGreen(),
                localBackgroundColor.getBlue(),
                0.0f);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwSwapBuffers(window);
        glfwPollEvents();
    }


    @Override
    public void update(Float3d value) {
        this.backgroundColor = new ColorRGB(value);
    }


    @Override
    public void close() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }


    @Override
    public void setEventListener(IEventListener el) {
        this.el = el;
    }
}
