package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives;

import org.jetbrains.annotations.NotNull;


/**
 * Utilities class for operations on primitive types.
 */
public class Utils {

    private Utils() {
    }


    /**
     * Concatenate two arrays.
     *
     * @param array1 first array
     * @param array2 second array
     * @return result of concatenation of array1 and array2
     */
    public static float[] joinArrays(@NotNull float[] array1, @NotNull float[] array2) {
        float[] result = new float[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }


    /**
     * Convert array of simple vertices into consecutive array of vertex components.
     *
     * @param vertices array of vertices to be converted
     * @return array of vertex components
     */
    public static float[] asArray(@NotNull VertexSimple[] vertices) {
        return asArray(vertices, VertexSimple.componentsPerPosition());

    }


    /**
     * Convert array of colored vertices into consecutive array of vertex components.
     *
     * @param vertices array of vertices to be converted
     * @return array of vertex components
     */
    public static float[] asArray(@NotNull VertexColored[] vertices) {
        return asArray(vertices, VertexColored.componentsPerPosition() + VertexColored.componentsPerColor());
    }


    /**
     * Convert array of textured vertices into consecutive array of vertex components.
     *
     * @param vertices array of vertices to be converted
     * @return array of vertex components
     */
    public static float[] asArray(@NotNull VertexTextured[] vertices) {
        return asArray(vertices, VertexTextured.componentsPerPosition() + VertexTextured.componentsPerTexture());
    }


    /**
     * Convert array of simple vertices into consecutive array of vertex components.
     *
     * @param vertices        array of vertices to be converted
     * @param totalComponents amount of components per one vertex
     * @return array of vertex components
     */
    private static float[] asArray(@NotNull VertexSimple[] vertices, int totalComponents) {
        final float[] result = new float[vertices.length * totalComponents];
        int counter = 0;
        for(VertexSimple v : vertices) {
            float[] tmp = v.asArray();
            System.arraycopy(tmp, 0, result, counter, tmp.length);
            counter += tmp.length;
        }

        return result;
    }
}
