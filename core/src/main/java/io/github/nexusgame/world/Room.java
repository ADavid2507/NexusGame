package io.github.nexusgame.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private float x, y, width, height;
    private float wallThickness = 5f;
    private List<Rectangle> wallBounds;
    private List<Door> doors;
    private Color floorColor;
    private Color wallColor;

    public Room(float x, float y, float width, float height, Color floorColor, Color wallColor, List<Door> doors) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.floorColor = floorColor;
        this.wallColor = wallColor;
        this.doors = doors;

        wallBounds = new ArrayList<>();
        buildWalls(WallSide.BOTTOM);
        buildWalls(WallSide.TOP);
        buildWalls(WallSide.LEFT);
        buildWalls(WallSide.RIGHT);

    }
    private Door findDoorOnSide(WallSide side) {
        for (Door door: doors){
            if(door.getSide() == side) return door;
        }
        return null;
    }

    private void buildWalls(WallSide side) {
        Door door = findDoorOnSide(side);
        boolean horizontal = (side == WallSide.BOTTOM || side == WallSide.TOP);

        float fixedX, fixedY, fullLength;
        if(side == WallSide.BOTTOM){
            fixedX = x;
            fixedY = y;
            fullLength = width;
        } else if(side == WallSide.TOP){
            fixedX = x;
            fixedY = y+height-wallThickness;
            fullLength = width;
        } else if(side == WallSide.LEFT){
            fixedX = x;
            fixedY = y;
            fullLength = height;
        } else {
            fixedX = x + width - wallThickness;
            fixedY = y;
            fullLength = height;
        }

        if(door == null) {
            if (horizontal) {
                wallBounds.add(new Rectangle(fixedX, fixedY, fullLength, wallThickness));
            } else {
                wallBounds.add(new Rectangle(fixedX, fixedY, wallThickness, fullLength));
            }
            return;
        }

        float gapInicio = door.getGapInicio();
        float gapFinal = door.getGapFinal();

        float origin = horizontal ? x : y;

        float belowLength = gapInicio - origin;
        float aboveLength = (origin + fullLength) - gapFinal;

        if(horizontal) {
            wallBounds.add(new Rectangle(origin, fixedY, belowLength, wallThickness));
            wallBounds.add(new Rectangle(gapFinal, fixedY, aboveLength, wallThickness));
            door.setTrigger(new Rectangle(gapInicio, fixedY, gapFinal - gapInicio, wallThickness));
        } else {
            wallBounds.add(new Rectangle(fixedX, origin, wallThickness, belowLength));
            wallBounds.add(new Rectangle(fixedX, gapFinal, wallThickness, aboveLength));
            door.setTrigger(new Rectangle(fixedX, gapInicio, wallThickness, gapFinal - gapInicio));
        }
    }


    public boolean canMove(Rectangle player) {
        for (Rectangle wall: wallBounds){
            if(player.overlaps(wall)) return false;
        }

        Door door = checkDoorTrigger(player);
        if(door != null && door.isLocked()) return false;

        return true;
    }


    public Door checkDoorTrigger(Rectangle player) {
        for (Door door: doors){
            if(player.overlaps(door.getTrigger())) return door;
        }
        return null;
    }

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(floorColor);
        renderer.rect(x, y, width, height);
        renderer.setColor(wallColor);
        for (Rectangle wall: wallBounds){
            renderer.rect(wall.x, wall.y, wall.width, wall.height);
        }
    }


}
