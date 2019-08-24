package me.ftvkyo.fuzzy_broccoli.client;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.Random;

import static java.lang.Math.abs;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import me.ftvkyo.fuzzy_broccoli.common.ArgumentProcessor;


public class Client {

    private long window;


    private void run() {
        System.out.println(PREFIX + "Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }


    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(300, 300, PREFIX + "Hello World!", NULL, NULL);
        if(window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
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


    private void loop() {
        GL.createCapabilities();

        Random random = new Random();

        final int colors = 3;
        final int steps = 100;
        int currentStep = steps;

        float[] current = new float[colors];
        float[] next = new float[colors];
        float[] step = new float[colors];

        while(!glfwWindowShouldClose(window)) {
            if(currentStep >= steps)
            {
                for(int i = 0; i < colors; i++)
                {
                    next[i] = random.nextFloat();
                    step[i] = (next[i] - current[i]) / steps;
                }
                currentStep = 0;
            }

            for(int i = 0; i < colors; i++)
            {
                current[i] += step[i];
            }

            glClearColor(current[0], current[1], current[2], 0.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glfwSwapBuffers(window);
            glfwPollEvents();

            try {
                Thread.sleep(1000/60);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            currentStep += 1;
        }
    }


    private static final String PREFIX = " [client] ";


    public static void main(String[] args) {
        ArgumentProcessor.printAll(PREFIX, args);

        new Client().run();

        System.out.println(PREFIX + "Done!");
    }
}
