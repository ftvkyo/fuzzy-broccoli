package me.ftvkyo.fuzzy_broccoli.common.mvc.model

import me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts.Block
import me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts.Creature
import me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts.Entity
import me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts.Region
import org.joml.Vector2f
import org.joml.Vector3f
import java.io.File
import java.util.*
import kotlin.math.abs


/**
 * World class is the "Model" part in MVC.
 */
class Model(private val worldFilename: File, val playerName: String) : AutoCloseable {

    private val entities: ArrayList<Entity> = ArrayList()

    private val creatures: ArrayList<Creature> = ArrayList()

    private val players: MutableMap<String, Creature> = HashMap();

    private val regions: MutableMap<Region.Position, Region> = HashMap();


    init {
        //boolean shouldInit = this.worldFilename.createNewFile();
        players[playerName] = Creature(Vector3f(0f, 0f, 0f), Vector2f(0f, 0f))
        initSomeData()
    }


    fun getPlayers(): Map<String, Creature> {
        return players
    }


    fun getRegion(pos: Region.Position): Region? {
        return regions[pos]
    }


    private fun initSomeData() {
        val positions = Region.Position.fromRegionPosition(0, 0, 0).nearest
        for (pos in positions) {
            val r = Region()

            val rand = Random()
            for (x in 0 until Region.SIDE) {
                for (y in 0 until Region.SIDE) {
                    for (z in 0 until Region.SIDE) {
                        if (abs(x) + abs(y) + abs(z) > 2 && rand.nextInt(100) < 2) {
                            r.setBlock(x, y, z, Block("dirt"))
                        }
                    }
                }
            }

            regions[pos] = r
        }
    }


    fun tick() {
    }


    override fun close() {
    }
}
