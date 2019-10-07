package me.ftvkyo.fuzzy_broccoli.client.mvc.model


import me.ftvkyo.fuzzy_broccoli.client.graphics.primitives.VertexTextured
import me.ftvkyo.fuzzy_broccoli.common.mvc.model.Model
import me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts.Region
import org.joml.Vector2f
import org.joml.Vector3f
import java.util.*


class ManagerForModel : AutoCloseable {

    private var model: Model? = null

    init {
        model = null
    }


    fun getModel(): Model? {
        return model
    }


    fun setModel(model: Model): ManagerForModel {
        if (this.model != null) {
            this.model!!.close()
        }
        this.model = model

        return this
    }


    fun update() {
        if (this.model != null) {
            this.model!!.tick()
        }
    }


    override fun close() {
        if (this.model != null) {
            this.model!!.close()
        }
    }


    fun getScreenContents(vs: ArrayList<VertexTextured>, vOrder: ArrayList<Int>) {
        vs.clear()
        vOrder.clear()

        checkNotNull(this.model) { "Can not get contents of empty model." }

        val player = model!!.getPlayers()[model!!.playerName] ?: error("")
        val playerPosition = player.position
        val centralRegionPosition = Region.Position.fromCreaturePosition(playerPosition)

        val nearestPositions = centralRegionPosition.nearest
        val nearestRegions = arrayOfNulls<Region>(nearestPositions.size)

        for (i in nearestPositions.indices) {
            nearestRegions[i] = model!!.getRegion(nearestPositions[i])

            if (nearestRegions[i] == null) {
                continue
            }

            for (x in 0 until Region.SIDE) {
                for (y in 0 until Region.SIDE) {
                    for (z in 0 until Region.SIDE) {
                        val b = nearestRegions[i]?.getBlock(x, y, z) ?: continue

                        val bp000 = Vector3f(
                                (nearestPositions[i].x * Region.SIDE + x).toFloat(),
                                (nearestPositions[i].y * Region.SIDE + y).toFloat(),
                                (nearestPositions[i].z * Region.SIDE + z).toFloat()
                        )
                        val bp001 = Vector3f(bp000.x, bp000.y, bp000.z + 1)
                        val bp010 = Vector3f(bp000.x, bp000.y + 1, bp000.z)
                        val bp011 = Vector3f(bp000.x, bp000.y + 1, bp000.z + 1)
                        val bp100 = Vector3f(bp000.x + 1, bp000.y, bp000.z)
                        val bp101 = Vector3f(bp000.x + 1, bp000.y, bp000.z + 1)
                        val bp110 = Vector3f(bp000.x + 1, bp000.y + 1, bp000.z)
                        val bp111 = Vector3f(bp000.x + 1, bp000.y + 1, bp000.z + 1)

                        val tex00 = Vector2f(0.5f, 0f)
                        val tex01 = Vector2f(tex00.x, tex00.y + 0.5f)
                        val tex10 = Vector2f(tex00.x + 0.5f, tex00.y)
                        val tex11 = Vector2f(tex00.x + 0.5f, tex00.y + 0.5f)


                        var vsIndex: Int

                        // back plane
                        vsIndex = vs.size
                        vs.add(VertexTextured(bp000, tex00))
                        vs.add(VertexTextured(bp010, tex01))
                        vs.add(VertexTextured(bp100, tex10))
                        vs.add(VertexTextured(bp110, tex11))
                        vOrder.add(vsIndex)
                        vOrder.add(vsIndex + 1)
                        vOrder.add(vsIndex + 2)
                        vOrder.add(vsIndex + 1)
                        vOrder.add(vsIndex + 2)
                        vOrder.add(vsIndex + 3)

                        // front plane
                        vsIndex = vs.size
                        vs.add(VertexTextured(bp001, tex00))
                        vs.add(VertexTextured(bp011, tex01))
                        vs.add(VertexTextured(bp101, tex10))
                        vs.add(VertexTextured(bp111, tex11))
                        vOrder.add(vsIndex)
                        vOrder.add(vsIndex + 1)
                        vOrder.add(vsIndex + 2)
                        vOrder.add(vsIndex + 1)
                        vOrder.add(vsIndex + 2)
                        vOrder.add(vsIndex + 3)

                        // top plane
                        vsIndex = vs.size
                        vs.add(VertexTextured(bp010, tex00))
                        vs.add(VertexTextured(bp011, tex01))
                        vs.add(VertexTextured(bp110, tex10))
                        vs.add(VertexTextured(bp111, tex11))
                        vOrder.add(vsIndex)
                        vOrder.add(vsIndex + 1)
                        vOrder.add(vsIndex + 2)
                        vOrder.add(vsIndex + 1)
                        vOrder.add(vsIndex + 2)
                        vOrder.add(vsIndex + 3)

                        // bottom plane
                        vsIndex = vs.size
                        vs.add(VertexTextured(bp000, tex00))
                        vs.add(VertexTextured(bp001, tex01))
                        vs.add(VertexTextured(bp100, tex10))
                        vs.add(VertexTextured(bp101, tex11))
                        vOrder.add(vsIndex)
                        vOrder.add(vsIndex + 1)
                        vOrder.add(vsIndex + 2)
                        vOrder.add(vsIndex + 1)
                        vOrder.add(vsIndex + 2)
                        vOrder.add(vsIndex + 3)

                        // left plane
                        vsIndex = vs.size
                        vs.add(VertexTextured(bp000, tex00))
                        vs.add(VertexTextured(bp001, tex01))
                        vs.add(VertexTextured(bp010, tex10))
                        vs.add(VertexTextured(bp011, tex11))
                        vOrder.add(vsIndex)
                        vOrder.add(vsIndex + 1)
                        vOrder.add(vsIndex + 2)
                        vOrder.add(vsIndex + 1)
                        vOrder.add(vsIndex + 2)
                        vOrder.add(vsIndex + 3)

                        // right plane
                        vsIndex = vs.size
                        vs.add(VertexTextured(bp100, tex00))
                        vs.add(VertexTextured(bp101, tex01))
                        vs.add(VertexTextured(bp110, tex10))
                        vs.add(VertexTextured(bp111, tex11))
                        vOrder.add(vsIndex)
                        vOrder.add(vsIndex + 1)
                        vOrder.add(vsIndex + 2)
                        vOrder.add(vsIndex + 1)
                        vOrder.add(vsIndex + 2)
                        vOrder.add(vsIndex + 3)
                    }
                }
            }
        }

    }
}
