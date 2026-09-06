package io.github.nexusgame.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private float x, y, width, height;
    private float wallThickness = 5f;
    private List<Rectangle> wallBounds;
    private Color floorColor = Color.WHITE;
    private Color wallColor = Color.GRAY;

    public Room(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        wallBounds = new ArrayList<>();
        wallBounds.add(new Rectangle((int)x, (int)y, (int)width, (int)wallThickness));
        wallBounds.add(new Rectangle((int)x, (int)y,(int) wallThickness, (int)height));
        wallBounds.add(new Rectangle((int)x, (int)(y + height),(int)width, (int)wallThickness));
        wallBounds.add(new Rectangle((int)(x + width), (int)y, (int)wallThickness, (int)(height+wallThickness)));
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
