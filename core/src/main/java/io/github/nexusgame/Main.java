package io.github.nexusgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.nexusgame.entities.Player;
import io.github.nexusgame.world.Door;
import io.github.nexusgame.world.Room;
import io.github.nexusgame.world.WallSide;

import java.util.ArrayList;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private Player player;
    private List<Room> rooms = new ArrayList<>();
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private FitViewport viewport = new FitViewport(1280, 720);

    float worldWight = viewport.getWorldWidth();
    float worldHeight = viewport.getWorldHeight();



    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        player = new Player(640,360 +100);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, worldWight, worldHeight);
        camera.position.set(player.bounds.x, player.bounds.y, 0);
        camera.update();


        List<Door> door1 = new ArrayList<>();
        door1.add(new Door(WallSide.RIGHT, 320, 400, "room2", 20,150, false));

        List<Door> door2 = new ArrayList<>();
        //door2.add(new Door(WallSide.RIGHT, 320, 400, "room1", 20,150));
        door2.add(new Door(WallSide.BOTTOM, 600, 680, "room3", 100,20 , true));
        door2.add(new Door(WallSide.LEFT, 320, 400, "room1", 20,150, false));

        List<Door> door3 = new ArrayList<>();
        door3.add(new Door(WallSide.TOP, 600, 680, "room2", 100,20, true));

        rooms.add(new Room(340, 210, 400, 300, Color.WHITE, Color.RED, door2));
        rooms.add(new Room(140, 210, 200, 300, Color.WHITE, Color.RED, door1));

        rooms.add(new Room(540, 10, 200, 200, Color.WHITE, Color.RED, door3));

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        player.update(Gdx.graphics.getDeltaTime(), rooms);

        camera.position.set(player.bounds.x, player.bounds.y, 0);
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Room room: rooms){
            room.draw(shapeRenderer);
        }
        player.draw(shapeRenderer);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {

        shapeRenderer.dispose();
    }
}
