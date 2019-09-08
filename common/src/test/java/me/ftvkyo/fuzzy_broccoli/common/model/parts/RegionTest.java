package me.ftvkyo.fuzzy_broccoli.common.model.parts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RegionTest {

    @Test
    void PositionFromCoordinatesTest() {
        assertEquals(
                Region.Position.fromRegionPosition(1, 1, 1),
                Region.Position.fromWorldCoordinates(100, 100, 100)
        );

        assertEquals(
                Region.Position.fromRegionPosition(1, 1, 1),
                Region.Position.fromWorldCoordinates(64, 64, 64)
        );

        assertEquals(
                Region.Position.fromRegionPosition(0, 0, 0),
                Region.Position.fromWorldCoordinates(63, 63, 63)
        );

        assertEquals(
                Region.Position.fromRegionPosition(0, 0, 0),
                Region.Position.fromWorldCoordinates(0, 0, 0)
        );

        assertEquals(
                Region.Position.fromRegionPosition(-1, -1, -1),
                Region.Position.fromWorldCoordinates(-1, -1, -1)
        );

        assertEquals(
                Region.Position.fromRegionPosition(-1, -1, -1),
                Region.Position.fromWorldCoordinates(-64, -64, -64)
        );

        assertEquals(
                Region.Position.fromRegionPosition(-2, -2, -2),
                Region.Position.fromWorldCoordinates(-65, -65, -65)
        );
    }


    @Test
    void PositionEqualsTest() {
        assertEquals(
                Region.Position.fromWorldCoordinates(0, 0, 0),
                Region.Position.fromWorldCoordinates(1, 1, 1)
        );

        assertEquals(
                Region.Position.fromWorldCoordinates(-1, -1, -1),
                Region.Position.fromWorldCoordinates(-64, -64, -64)
        );
    }
}
