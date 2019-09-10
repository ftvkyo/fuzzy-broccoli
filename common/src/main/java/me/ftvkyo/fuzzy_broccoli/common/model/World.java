package me.ftvkyo.fuzzy_broccoli.common.model;

import me.ftvkyo.fuzzy_broccoli.common.model.parts.Creature;
import me.ftvkyo.fuzzy_broccoli.common.model.parts.Entity;
import me.ftvkyo.fuzzy_broccoli.common.model.parts.Region;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * World class is the "Model" part in MVC.
 */
public class World implements AutoCloseable {

    private final File worldFilename;


    private ArrayList<Entity> entities;

    private ArrayList<Creature> creatures;

    private Map<String, Creature> players;

    private Map<Region.Position, Region> chunks;


    public World(File f) {
        this.worldFilename = f;

        //boolean shouldInit = this.worldFilename.createNewFile();

        entities = new ArrayList<>();
        creatures = new ArrayList<>();
        players = new HashMap<>();
        chunks = new HashMap<>();
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


    public Map<Region.Position, Region> getChunks() {
        return chunks;
    }


    public void tick() {

    }


    @Override
    public void close() {

    }
}
