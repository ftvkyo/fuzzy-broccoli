package me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts

import org.joml.Vector2f
import org.joml.Vector3f


class Creature(val position: Vector3f, view: Vector2f) {
    var view = view
        set(value) {
            while (value.x > +180F) value.x += -360F
            while (value.x < -180F) value.x += +360F

            if (value.y > +90F) value.y = +90F
            if (value.y < -90F) value.y = -90F

            field = value
        }
}
