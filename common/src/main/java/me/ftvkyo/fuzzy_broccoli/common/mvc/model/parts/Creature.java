package me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts;

import org.joml.Vector2f;
import org.joml.Vector3f;


public class Creature {

    private Vector3f position;

    private Vector2f view;


    public Creature(Vector3f position, Vector2f view) {
        this.position = position;
        this.view = view;
    }


    public Vector3f getPosition() {
        return position;
    }


    public Vector2f getView() {
        return view;
    }
}
