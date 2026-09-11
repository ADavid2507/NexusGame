package io.github.nexusgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.nexusgame.world.Room;

import java.sql.Time;
import java.util.List;


public class Player {
    public Rectangle bounds;
    public boolean isSpeedBoosted = false;
    public boolean isOnCoolDown = false;

    public float boostTimer = 10f;
    public float maxBoostTimer = 10f;
    public float speed = 150f;
    public float topSpeed = 300f;


    public Player(float x, float y) {
        bounds = new Rectangle(x, y, 20, 20);
    }

    public void update(float delta, List<Room> crRoom) {
        Vector2 direccion = new Vector2();
        float dx, dy;


        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) direccion.y += speed;
        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) direccion.y -= speed;
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) direccion.x -= speed;
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) direccion.x += speed ;

        temporizador(delta);

        direccion.nor().scl(speed*delta);

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

    public void temporizador(float delta){
        if (isSpeedBoosted){
            boostTimer -= delta;
            if(boostTimer <= 0){
                boostTimer = 0f;
                isSpeedBoosted = false;
                isOnCoolDown = true;
                speed = 150;
                System.out.println("Boost off");
            }
        } else {
            if (!isOnCoolDown){
                boostTimer += delta;
                if(boostTimer >= maxBoostTimer){
                    boostTimer = maxBoostTimer;
                }
            } else {
                boostTimer += delta;
                if(boostTimer >= maxBoostTimer){
                    boostTimer = maxBoostTimer;
                    isOnCoolDown = false;
                    System.out.println("Cool down off.");
                }
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)){
            if (isSpeedBoosted) {
                isSpeedBoosted = false;
                speed = 150f;
                System.out.println("Boost off");
            }
            else if(!isSpeedBoosted && boostTimer > 0.5){
                isSpeedBoosted = true;
                speed = topSpeed;
                System.out.println("Boost on");
            } else {
                System.out.println("Cool down on");
            }
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
