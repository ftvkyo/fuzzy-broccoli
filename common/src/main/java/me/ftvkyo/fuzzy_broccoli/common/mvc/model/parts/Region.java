package me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts;

import org.joml.Vector3f;
import org.joml.Vector3i;


public class Region {

    public static final int SIDE = 16;

    private Block[][][] data = new Block[SIDE][SIDE][SIDE];


    public Block getBlock(int x, int y, int z) throws IllegalArgumentException {
        checkArgs(x, y, z);
        return data[x][y][z];
    }


    public void setBlock(int x, int y, int z, Block block) throws IllegalArgumentException {
        checkArgs(x, y, z);
        data[x][y][z] = block;
    }


    private void checkArgs(int x, int y, int z) throws IllegalArgumentException {
        if(!(0 <= x && x < SIDE
                && 0 <= y && y < SIDE
                && 0 <= z && z < SIDE)) {
            throw new IllegalArgumentException("x, y and z must be in [0, 15] range.");
        }
    }


    public static class Position {

        private final Vector3i pos;


        private Position(int x, int y, int z) {
            pos = new Vector3i(x, y, z);
        }


        public static Position fromWorldCoordinates(Vector3i wc) {
            // [-SIZE, 0) -> -1; [0, SIZE) -> 0; [SIZE, 2*SIZE) -> 1
            // => [m*SIZE, (m+1)*SIZE) -> m

            int x, y, z;

            if(wc.x >= 0) {
                x = wc.x / SIDE;
            } else {
                x = (wc.x + 1) / SIDE - 1;
            }

            if(wc.y >= 0) {
                y = wc.y / SIDE;
            } else {
                y = (wc.y + 1) / SIDE - 1;
            }

            if(wc.z >= 0) {
                z = wc.z / SIDE;
            } else {
                z = (wc.z + 1) / SIDE - 1;
            }

            return new Position(x, y, z);
        }


        public static Position fromCreaturePosition(Vector3f cp) {
            int x, y, z;
            x = Math.round(cp.x) - (cp.x < 0 ? 1 : 0);
            y = Math.round(cp.y) - (cp.y < 0 ? 1 : 0);
            z = Math.round(cp.z) - (cp.z < 0 ? 1 : 0);
            return fromWorldCoordinates(new Vector3i(x, y, z));
        }


        public static Position fromRegionPosition(int x, int y, int z) {
            return new Position(x, y, z);
        }


        public Position[] getNearest() {
            Position[] positions = new Position[27];

            int counter = 0;
            for(int x = -1; x <= 1; x++) {
                for(int y = -1; y <= 1; y++) {
                    for(int z = -1; z <= 1; z++) {
                        positions[counter++] = new Position(pos.x + x, pos.y + y, pos.z + z);
                    }
                }
            }

            return positions;
        }


        public int getX() {
            return pos.x;
        }


        public int getY() {
            return pos.y;
        }


        public int getZ() {
            return pos.z;
        }


        @Override
        public String toString() {
            return getClass().getName() + "(" + pos + ")";
        }


        @Override
        public boolean equals(Object obj) {
            if(getClass() != obj.getClass()) {
                return false;
            }
            Position p = (Position) obj;
            return this.pos.x == p.pos.x && this.pos.y == p.pos.y && this.pos.z == p.pos.z;
        }


        @Override
        public int hashCode() {
            return pos.x + pos.y << 8 + pos.z << 16;
        }
    }
}
