package me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts

import org.joml.Vector3f
import org.joml.Vector3i
import kotlin.math.roundToInt


class Region {

    private val data = Array(SIDE) { Array<Array<Block?>>(SIDE) { arrayOfNulls(SIDE) } }


    @Throws(IllegalArgumentException::class)
    fun getBlock(x: Int, y: Int, z: Int): Block? {
        checkArgs(x, y, z)
        return data[x][y][z]
    }


    @Throws(IllegalArgumentException::class)
    fun setBlock(x: Int, y: Int, z: Int, block: Block) {
        checkArgs(x, y, z)
        data[x][y][z] = block
    }


    @Throws(IllegalArgumentException::class)
    private fun checkArgs(x: Int, y: Int, z: Int) {
        require(x in 0 until SIDE
                && y in 0 until SIDE
                && z in 0 until SIDE) { "x, y and z must be in [0, 15] range." }
    }


    class Position private constructor(x: Int, y: Int, z: Int) {

        private val pos: Vector3i = Vector3i(x, y, z)


        val nearest: Array<Position>
            get() {
                val positions = arrayOfNulls<Position>(27)

                var counter = 0
                for (x in -1..1) {
                    for (y in -1..1) {
                        for (z in -1..1) {
                            positions[counter++] = Position(pos.x + x, pos.y + y, pos.z + z)
                        }
                    }
                }

                return positions.requireNoNulls()
            }


        val x: Int
            get() = pos.x


        val y: Int
            get() = pos.y


        val z: Int
            get() = pos.z


        override fun toString(): String {
            return javaClass.name + "(" + pos + ")"
        }


        override fun equals(other: Any?): Boolean {
            if (javaClass != other!!.javaClass) {
                return false
            }
            val p = other as Position?
            return this.pos.x == p!!.pos.x && this.pos.y == p.pos.y && this.pos.z == p.pos.z
        }


        override fun hashCode(): Int {
            return pos.x + pos.y shl 8 + pos.z shl 16
        }

        companion object {


            fun fromWorldCoordinates(wc: Vector3i): Position {
                // [-SIZE, 0) -> -1; [0, SIZE) -> 0; [SIZE, 2*SIZE) -> 1
                // => [m*SIZE, (m+1)*SIZE) -> m

                val x: Int = if (wc.x >= 0) {
                    wc.x / SIDE
                } else {
                    (wc.x + 1) / SIDE - 1
                }
                val y: Int = if (wc.y >= 0) {
                    wc.y / SIDE
                } else {
                    (wc.y + 1) / SIDE - 1
                }
                val z: Int = if (wc.z >= 0) {
                    wc.z / SIDE
                } else {
                    (wc.z + 1) / SIDE - 1
                }

                return Position(x, y, z)
            }


            fun fromCreaturePosition(cp: Vector3f): Position {
                val x: Int = cp.x.roundToInt() - if (cp.x < 0) 1 else 0
                val y: Int = cp.y.roundToInt() - if (cp.y < 0) 1 else 0
                val z: Int = cp.z.roundToInt() - if (cp.z < 0) 1 else 0
                return fromWorldCoordinates(Vector3i(x, y, z))
            }


            fun fromRegionPosition(x: Int, y: Int, z: Int): Position {
                return Position(x, y, z)
            }
        }
    }

    companion object {
        const val SIDE = 16
    }
}
