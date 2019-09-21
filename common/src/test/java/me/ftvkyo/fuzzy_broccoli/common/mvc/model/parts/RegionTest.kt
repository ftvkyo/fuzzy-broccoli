package me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts

import org.joml.Vector3i
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


internal class RegionTest {

    @Test
    fun positionFromCoordinatesTest() {
        assertEquals(
                Region.Position.fromRegionPosition(6, 6, 6),
                Region.Position.fromWorldCoordinates(Vector3i(100, 100, 100))
        )

        assertEquals(
                Region.Position.fromRegionPosition(4, 4, 4),
                Region.Position.fromWorldCoordinates(Vector3i(64, 64, 64))
        )

        assertEquals(
                Region.Position.fromRegionPosition(3, 3, 3),
                Region.Position.fromWorldCoordinates(Vector3i(63, 63, 63))
        )

        assertEquals(
                Region.Position.fromRegionPosition(0, 0, 0),
                Region.Position.fromWorldCoordinates(Vector3i(0, 0, 0))
        )

        assertEquals(
                Region.Position.fromRegionPosition(-1, -1, -1),
                Region.Position.fromWorldCoordinates(Vector3i(-1, -1, -1))
        )

        assertEquals(
                Region.Position.fromRegionPosition(-4, -4, -4),
                Region.Position.fromWorldCoordinates(Vector3i(-64, -64, -64))
        )

        assertEquals(
                Region.Position.fromRegionPosition(-5, -5, -5),
                Region.Position.fromWorldCoordinates(Vector3i(-65, -65, -65))
        )
    }


    @Test
    fun positionEqualsTest() {
        assertEquals(
                Region.Position.fromWorldCoordinates(Vector3i(0, 0, 0)),
                Region.Position.fromWorldCoordinates(Vector3i(1, 1, 1))
        )

        assertEquals(
                Region.Position.fromWorldCoordinates(Vector3i(-1, -1, -1)),
                Region.Position.fromWorldCoordinates(Vector3i(-16, -16, -16))
        )
    }
}
