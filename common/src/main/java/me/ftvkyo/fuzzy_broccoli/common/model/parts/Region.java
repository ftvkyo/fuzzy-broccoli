package me.ftvkyo.fuzzy_broccoli.common.model.parts;

public class Region {

    public static final int SIZE = 64;

    private Block[][][] data = new Block[SIZE][SIZE][SIZE];


    public Block getBlock(int x, int y, int z) throws IllegalArgumentException {
        checkArgs(x, y, z);
        return data[x][y][z];
    }


    public void setBlock(int x, int y, int z, Block block) throws IllegalArgumentException {
        checkArgs(x, y, z);
        data[x][y][z] = block;
    }


    private void checkArgs(int x, int y, int z) throws IllegalArgumentException {
        if(!(0 <= x && x < SIZE
                && 0 <= y && y < SIZE
                && 0 <= z && z < SIZE)) {
            throw new IllegalArgumentException("x, y and z must be in [0, 15] range.");
        }
    }


    public static class Position {

        private final int x, y, z;


        private Position(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }


        public static Position fromWorldCoordinates(int x, int y, int z) {
            // [-SIZE, 0) -> -1; [0, SIZE) -> 0; [SIZE, 2*SIZE) -> 1
            // => [m*SIZE, (m+1)*SIZE) -> m

            if(x >= 0) {
                x = x / SIZE;
            } else {
                x = (x + 1) / SIZE - 1;
            }

            if(y >= 0) {
                y = y / SIZE;
            } else {
                y = (y + 1) / SIZE - 1;
            }

            if(z >= 0) {
                z = z / SIZE;
            } else {
                z = (z + 1) / SIZE - 1;
            }

            return new Position(x, y, z);
        }


        public static Position fromRegionPosition(int x, int y, int z) {
            return new Position(x, y, z);
        }


        public int getX() {
            return x;
        }


        public int getY() {
            return y;
        }


        public int getZ() {
            return z;
        }


        @Override
        public String toString() {
            return getClass().getName() + "(" + x + ", " + y + ", " + z + ")";
        }


        @Override
        public boolean equals(Object obj) {
            if(getClass() != obj.getClass()) {
                return false;
            }
            Position p = (Position) obj;
            return this.x == p.x && this.y == p.y && this.z == p.z;
        }


        @Override
        public int hashCode() {
            return x + y << 8 + z << 16;
        }
    }
}
