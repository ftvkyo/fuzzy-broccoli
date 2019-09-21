package me.ftvkyo.fuzzy_broccoli.client.mvc.model;


import me.ftvkyo.fuzzy_broccoli.client.graphics.primitives.VertexTextured;
import me.ftvkyo.fuzzy_broccoli.common.mvc.model.Model;
import me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts.Block;
import me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts.Creature;
import me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts.Region;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;


public class ManagerForModel implements AutoCloseable {

    private Model model;


    public ManagerForModel() {
        model = null;
    }


    public Model getModel() {
        return model;
    }


    public ManagerForModel setModel(Model model) {
        if(this.model != null) {
            this.model.close();
        }
        this.model = model;

        return this;
    }


    public void update() {
        if(this.model != null) {
            this.model.tick();
        }
    }


    @Override
    public void close() {
        if(this.model != null) {
            this.model.close();
        }
    }


    public void getScreenContents(@NotNull ArrayList<VertexTextured> vs, @NotNull ArrayList<Integer> vOrder) {
        vs.clear();
        vOrder.clear();

        if(this.model == null) {
            throw new IllegalStateException("Can not get contents of empty model.");
        }

        Creature player = model.getPlayers().get(model.getPlayerName());
        Vector3f playerPosition = player.getPosition();
        Region.Position centralRegionPosition = Region.Position.fromCreaturePosition(playerPosition);

        Region.Position[] nearestPositions = centralRegionPosition.getNearest();
        Region[] nearestRegions = new Region[nearestPositions.length];

        for(int i = 0; i < nearestPositions.length; i++) {
            nearestRegions[i] = model.getRegion(nearestPositions[i]);

            if(nearestRegions[i] == null) {
                continue;
            }

            for(int x = 0; x < Region.SIDE; x++) {
                for(int y = 0; y < Region.SIDE; y++) {
                    for(int z = 0; z < Region.SIDE; z++) {
                        Block b = nearestRegions[i].getBlock(x, y, z);

                        if(b == null) {
                            continue;
                        }

                        Vector3f bp000 = new Vector3f(
                                nearestPositions[i].getX() * Region.SIDE + x,
                                nearestPositions[i].getY() * Region.SIDE + y,
                                nearestPositions[i].getZ() * Region.SIDE + z
                        );
                        Vector3f bp001 = new Vector3f(bp000.x, bp000.y, bp000.z + 1);
                        Vector3f bp010 = new Vector3f(bp000.x, bp000.y + 1, bp000.z);
                        Vector3f bp011 = new Vector3f(bp000.x, bp000.y + 1, bp000.z + 1);
                        Vector3f bp100 = new Vector3f(bp000.x + 1, bp000.y, bp000.z);
                        Vector3f bp101 = new Vector3f(bp000.x + 1, bp000.y, bp000.z + 1);
                        Vector3f bp110 = new Vector3f(bp000.x + 1, bp000.y + 1, bp000.z);
                        Vector3f bp111 = new Vector3f(bp000.x + 1, bp000.y + 1, bp000.z + 1);

                        Vector2f tex00 = new Vector2f(0.5f, 0f);
                        Vector2f tex01 = new Vector2f(tex00.x, tex00.y + 0.5f);
                        Vector2f tex10 = new Vector2f(tex00.x + 0.5f, tex00.y);
                        Vector2f tex11 = new Vector2f(tex00.x + 0.5f, tex00.y + 0.5f);


                        int vsIndex;

                        // back plane
                        vsIndex = vs.size();
                        vs.add(new VertexTextured(bp000, tex00));
                        vs.add(new VertexTextured(bp010, tex01));
                        vs.add(new VertexTextured(bp100, tex10));
                        vs.add(new VertexTextured(bp110, tex11));
                        vOrder.add(vsIndex);
                        vOrder.add(vsIndex + 1);
                        vOrder.add(vsIndex + 2);
                        vOrder.add(vsIndex + 1);
                        vOrder.add(vsIndex + 2);
                        vOrder.add(vsIndex + 3);

                        // front plane
                        vsIndex = vs.size();
                        vs.add(new VertexTextured(bp001, tex00));
                        vs.add(new VertexTextured(bp011, tex01));
                        vs.add(new VertexTextured(bp101, tex10));
                        vs.add(new VertexTextured(bp111, tex11));
                        vOrder.add(vsIndex);
                        vOrder.add(vsIndex + 1);
                        vOrder.add(vsIndex + 2);
                        vOrder.add(vsIndex + 1);
                        vOrder.add(vsIndex + 2);
                        vOrder.add(vsIndex + 3);

                        // top plane
                        vsIndex = vs.size();
                        vs.add(new VertexTextured(bp010, tex00));
                        vs.add(new VertexTextured(bp011, tex01));
                        vs.add(new VertexTextured(bp110, tex10));
                        vs.add(new VertexTextured(bp111, tex11));
                        vOrder.add(vsIndex);
                        vOrder.add(vsIndex + 1);
                        vOrder.add(vsIndex + 2);
                        vOrder.add(vsIndex + 1);
                        vOrder.add(vsIndex + 2);
                        vOrder.add(vsIndex + 3);

                        // bottom plane
                        vsIndex = vs.size();
                        vs.add(new VertexTextured(bp000, tex00));
                        vs.add(new VertexTextured(bp001, tex01));
                        vs.add(new VertexTextured(bp100, tex10));
                        vs.add(new VertexTextured(bp101, tex11));
                        vOrder.add(vsIndex);
                        vOrder.add(vsIndex + 1);
                        vOrder.add(vsIndex + 2);
                        vOrder.add(vsIndex + 1);
                        vOrder.add(vsIndex + 2);
                        vOrder.add(vsIndex + 3);

                        // left plane
                        vsIndex = vs.size();
                        vs.add(new VertexTextured(bp000, tex00));
                        vs.add(new VertexTextured(bp001, tex01));
                        vs.add(new VertexTextured(bp010, tex10));
                        vs.add(new VertexTextured(bp011, tex11));
                        vOrder.add(vsIndex);
                        vOrder.add(vsIndex + 1);
                        vOrder.add(vsIndex + 2);
                        vOrder.add(vsIndex + 1);
                        vOrder.add(vsIndex + 2);
                        vOrder.add(vsIndex + 3);

                        // right plane
                        vsIndex = vs.size();
                        vs.add(new VertexTextured(bp100, tex00));
                        vs.add(new VertexTextured(bp101, tex01));
                        vs.add(new VertexTextured(bp110, tex10));
                        vs.add(new VertexTextured(bp111, tex11));
                        vOrder.add(vsIndex);
                        vOrder.add(vsIndex + 1);
                        vOrder.add(vsIndex + 2);
                        vOrder.add(vsIndex + 1);
                        vOrder.add(vsIndex + 2);
                        vOrder.add(vsIndex + 3);
                    }
                }
            }
        }

    }
}
