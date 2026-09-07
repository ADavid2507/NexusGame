package io.github.nexusgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.nexusgame.world.Room;

import java.util.List;


public class Player {
    public Rectangle bounds;

    public Player(float x, float y) {
        bounds = new Rectangle(x, y, 20, 20);
    }

    public void update(float delta, List<Room> crRoom) {
        Vector2 direccion = new Vector2();
        float dx, dy;
        float maxSpeed = 150f;

        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) direccion.y += maxSpeed;
        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) direccion.y -= maxSpeed;
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) direccion.x -= maxSpeed;
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) direccion.x += maxSpeed ;

        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            maxSpeed *=2;
        }


        direccion.nor().scl(maxSpeed*delta);

        dx = direccion.x;
        dy = direccion.y;

        Rectangle nextX = new Rectangle(bounds.x + dx, bounds.y, bounds.width, bounds.height);
        if(canMoveInRooms(nextX, crRoom)) {
            bounds.x += dx;
        }
        Rectangle nextY = new Rectangle(bounds.x, bounds.y + dy, bounds.width, bounds.height);
        if(canMoveInRooms(nextY, crRoom)) {
            bounds.y += dy;
        }
    }

    public boolean canMoveInRooms(Rectangle rc, List<Room> rooms) {
        for (Room room: rooms){
            if(!room.canMove(rc)) return false;
        }
        return true;
    }


    public void draw(ShapeRenderer renderer) {
        renderer.setColor(Color.BLUE);
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
