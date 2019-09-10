package me.ftvkyo.fuzzy_broccoli.client.graphics.primitives;

import org.jetbrains.annotations.NotNull;


public class Utils {

    private Utils() {
    }


    public static float[] joinArrays(@NotNull float[] array1, @NotNull float[] array2) {
        float[] result = new float[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }


    public static float[] asArray(@NotNull VertexSimple[] vertices) {
        assert vertices.length >= 1;
        return asArray(vertices, VertexSimple.componentsPerPosition());

    }


    public static float[] asArray(@NotNull VertexColored[] vertices) {
        assert vertices.length >= 1;
        return asArray(vertices, VertexColored.componentsPerPosition() + VertexColored.componentsPerColor());
    }


    public static float[] asArray(@NotNull VertexTextured[] vertices) {
        assert vertices.length >= 1;
        return asArray(vertices, VertexTextured.componentsPerPosition() + VertexTextured.componentsPerTexture());
    }


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
