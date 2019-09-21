package me.ftvkyo.fuzzy_broccoli.common.mvc.model;

import me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts.Block;
import me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts.Creature;
import me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts.Entity;
import me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts.Region;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.abs;


/**
 * World class is the "Model" part in MVC.
 */
public class Model implements AutoCloseable {

    private final File worldFilename;


    private ArrayList<Entity> entities;

    private ArrayList<Creature> creatures;

    private Map<String, Creature> players;

    private String playerName;

    private Map<Region.Position, Region> regions;


    public Model(@NotNull File f, @NotNull String playerName) {
        this.worldFilename = f;

        //boolean shouldInit = this.worldFilename.createNewFile();

        entities = new ArrayList<>();
        creatures = new ArrayList<>();
        players = new HashMap<>();
        regions = new HashMap<>();

        this.playerName = playerName;
        players.put(playerName, new Creature(new Vector3f(0f, 0f, 3f), new Vector2f(0f, 0f)));

        initSomeData();
    }


    public ArrayList<Entity> getEntities() {
        return entities;
    }


    public ArrayList<Creature> getCreatures() {
        return creatures;
    }


    public Map<String, Creature> getPlayers() {
        return players;
    }


    public String getPlayerName() {
        return playerName;
    }


    public Region getRegion(Region.Position pos) {
        return regions.get(pos);
    }


    private void initSomeData() {
        Region.Position[] positions = Region.Position.fromRegionPosition(0, 0, 0).getNearest();
        for(Region.Position pos : positions) {
            Region r = new Region();

            Random rand = new Random();
            for(int x = 0; x < Region.SIDE; x++) {
                for(int y = 0; y < Region.SIDE; y++) {
                    for(int z = 0; z < Region.SIDE; z++) {
                        if(abs(x) + abs(y) + abs(z) > 2 &&
                                rand.nextInt(100) < 1) {
                            r.setBlock(x, y, z, new Block("dirt"));
                        }
                    }
                }
            }

            regions.put(pos, r);
        }
    }


    public void tick() {

    }


    @Override
    public void close() {

    }
}
