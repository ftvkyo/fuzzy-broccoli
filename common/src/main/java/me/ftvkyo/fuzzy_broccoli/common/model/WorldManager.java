package me.ftvkyo.fuzzy_broccoli.common.model;


public class WorldManager implements AutoCloseable {

    private World world;


    public WorldManager() {
        world = null;
    }


    public World getWorld() {
        return world;
    }


    public WorldManager setWorld(World world) {
        if(this.world != null) {
            this.world.close();
        }
        this.world = world;
        return this;
    }


    public void update() {
        if(this.world != null) {
            this.world.tick();
        }
    }


    @Override
    public void close() {
        if(this.world != null) {
            this.world.close();
        }
    }
}
