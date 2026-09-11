package io.github.nexusgame.world;

import com.badlogic.gdx.math.Rectangle;


public class Door {
    private WallSide side;
    private float gapInicio, gapFinal;
    private Rectangle trigger;
    private String roomId;
    private float spawnX, spawnY;
    private boolean locked;

    public Door(WallSide side, float gapInicio, float gapFinal, String roomId, float spawnX, float spawnY, boolean locked) {
        this.side = side;
        this.gapInicio = gapInicio;
        this.gapFinal = gapFinal;
        this.roomId = roomId;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.locked = locked;
    }

    public boolean canInteract(Rectangle playerBounds){
        if (getTrigger() == null) return false;

        Rectangle interactionZone = new Rectangle(getTrigger());
        interactionZone.x -= 20;
        interactionZone.y -= 20;
        interactionZone.width += 40;
        interactionZone.height += 40;
        if (!interactionZone.overlaps(playerBounds)) return false;
        return true;
    }

    public void unlock(){
        if (isLocked()) locked = false;
    }

    public boolean isLocked() {
        return locked;
    }

    public WallSide getSide() {
        return side;
    }

    public void setSide(WallSide side) {
        this.side = side;
    }

    public float getGapInicio() {
        return gapInicio;
    }

    public void setGapInicio(float gapInicio) {
        this.gapInicio = gapInicio;
    }

    public Rectangle getTrigger() {
        return trigger;
    }

    public void setTrigger(Rectangle trigger) {
        this.trigger = trigger;
    }

    public float getGapFinal() {
        return gapFinal;
    }

    public void setGapFinal(float gapFinal) {
        this.gapFinal = gapFinal;
    }

    public float getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(float spawnX) {
        this.spawnX = spawnX;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public float getSpawnY() {
        return spawnY;
    }

    public void setSpawnY(float spawnY) {
        this.spawnY = spawnY;
    }
}
