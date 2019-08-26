package me.ftvkyo.fuzzy_broccoli.common;

public class Float3d {

    private final float v1;

    private final float v2;

    private final float v3;


    public Float3d() {
        this.v1 = 0;
        this.v2 = 0;
        this.v3 = 0;
    }


    public Float3d(Float3d other) {
        this.v1 = other.v1;
        this.v2 = other.v2;
        this.v3 = other.v3;
    }


    public Float3d(float n1, float n2, float n3) {
        this.v1 = n1;
        this.v2 = n2;
        this.v3 = n3;
    }


    public float getV1() {
        return v1;
    }


    public float getV2() {
        return v2;
    }


    public float getV3() {
        return v3;
    }


    public Float3d add(Float3d other) {
        return new Float3d(this.v1 + other.v1, this.v2 + other.v2, this.v3 + other.v3);
    }


    public Float3d sub(Float3d other) {
        return new Float3d(this.v1 - other.v1, this.v2 - other.v2, this.v3 - other.v3);
    }


    public Float3d div(long other) {
        return new Float3d(this.v1 / other, this.v2 / other, this.v3 / other);
    }
}
