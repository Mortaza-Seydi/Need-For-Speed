/*
 *  In The Name Of ALLAH
 *
 *   Project Name   :   Need For Speed Hot Java (Race Simulation)
 *   Code Version   :   1.0
 *     Written by   :   Mortaza Seydi - Zanjan University, IRAN - Spring 2018
 *
 *   Source Code Available on  :   github.com/Mortaza-Seydi
 */

package com.morteza.seydi.race.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.morteza.seydi.race.MapObjects.FixPlace;
import com.morteza.seydi.race.MapObjects.GasStation;
import com.morteza.seydi.race.MapObjects.PoliceObject;
import com.morteza.seydi.race.MapObjects.SpeedControllerCamera;
import com.morteza.seydi.race.Sprites.AutoCars;
import com.morteza.seydi.race.Sprites.Car;
import com.morteza.seydi.race.Roads.Road;
import com.morteza.seydi.race.Race;

public class PlayScreen implements Screen
{
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private TiledMap map;

    private World world;
    private PlayScene playScene;

    public Car car;
    private AutoCars[] autoCars = new AutoCars[4];

    private PoliceObject policeObject;
    private GasStation gasStationObject;
    private FixPlace fixPlaceObject;
    private SpeedControllerCamera speedControllerCameraObject;

    private Road road;
    private double timer = 0;
    //private Box2DDebugRenderer box2DDebugRenderer;
    private Race game;

    public PlayScreen(Race game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        camera = new OrthographicCamera(Gdx.graphics.getWidth() / Race.PPM, Gdx.graphics.getHeight() / Race.PPM);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        map = new TmxMapLoader().load("GameMap/Map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Race.PPM);

        world = new World(new Vector2(0, 0), true);

        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            Body body;
            BodyDef bodyDef = new BodyDef();
            PolygonShape shape = new PolygonShape();
            FixtureDef fixtureDef = new FixtureDef();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(
                    (rectangle.getX() + rectangle.getWidth() / 2) / Race.PPM,
                    (rectangle.getY() + rectangle.getHeight() / 2) / Race.PPM
                                );
            body = world.createBody(bodyDef);

            shape.setAsBox(rectangle.getWidth() / Race.PPM / 2, rectangle.getHeight() / Race.PPM / 2);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

        world.setContactListener(new ContactListener()
        {
            @Override
            public void beginContact(Contact contact)
            {
                Fixture fixture1 = contact.getFixtureA();
                Fixture fixture2 = contact.getFixtureB();

                if (fixture1.getUserData() == "Car" && fixture2.getUserData() == "Car")
                {
                    fixture1.getBody().setBullet(false);
                    fixture1.getBody().setLinearVelocity(0, 0);
                    fixture2.getBody().setBullet(false);
                    fixture2.getBody().setLinearVelocity(0, 0);
                }

            }

            @Override
            public void endContact(Contact contact)
            {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold)
            {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse)
            {
            }
        });

        //box2DDebugRenderer = new Box2DDebugRenderer();

        car = new Car(world, "Cars/0.png", 637, 170, 100, 200);
        road = new Road(car, map);

        autoCars[0] = new AutoCars(world, "Cars/1.png", 267, 105, 100, 200);
        autoCars[1] = new AutoCars(world, "Cars/2.png", 1002, 106, 100, 200);
        autoCars[2] = new AutoCars(world, "Cars/3.png", 845, 327, 100, 200);
        autoCars[3] = new AutoCars(world, "Cars/4.png", 425, 327, 100, 200);

        playScene = new PlayScene(game.batch, car);

        policeObject = new PoliceObject(world, game.batch, playScene, car, map);
        gasStationObject = new GasStation(game.batch, playScene, car, map);
        fixPlaceObject = new FixPlace(game.batch, playScene, car, map);
        speedControllerCameraObject = new SpeedControllerCamera(game.batch, playScene, car, map);
    }

    @Override
    public void render(float delta)
    {
        road.getRoadState();
        world.step(1 / 60f, 6, 2);
        timer += delta;

        policeObject.warning();
        gasStationObject.warning();
        fixPlaceObject.warning();
        speedControllerCameraObject.warning();
        speedControllerCameraObject.doYourJob();

        car.handleInputAndGo(timer);

        for (int i = 0; i < 4; i++)
            autoCars[i].go(timer, 0);

        playScene.update(timer);
        camera.position.y = car.getPosition() + 1;
        camera.update();
        tiledMapRenderer.setView(camera);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.render();
        //box2DDebugRenderer.render(world, camera.combined);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        car.draw(game.batch);
        policeObject.doYourJob(timer);
        for (int i = 0; i < 4; i++)
            autoCars[i].draw(game.batch);

        game.batch.end();
        game.batch.setProjectionMatrix(playScene.stage.getCamera().combined);
        playScene.stage.draw();

        if (gasStationObject.isArrived())
        {
            Gdx.input.setInputProcessor(gasStationObject.stage);
            game.batch.setProjectionMatrix(gasStationObject.stage.getCamera().combined);
            gasStationObject.stage.draw();
            gasStationObject.stage.act();
            gasStationObject.handleInput();
        }

        if (fixPlaceObject.isArrived())
        {
            Gdx.input.setInputProcessor(fixPlaceObject.stage);
            game.batch.setProjectionMatrix(fixPlaceObject.stage.getCamera().combined);
            fixPlaceObject.stage.draw();
            fixPlaceObject.stage.act();
            fixPlaceObject.handleInput();
        }

    }

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void hide()
    {
    }

    @Override
    public void dispose()
    {
        game.batch.dispose();
        map.dispose();
        tiledMapRenderer.dispose();
        playScene.stage.dispose();
        fixPlaceObject.stage.dispose();
        gasStationObject.stage.dispose();
    }

    public void setVariables(String maxV, String maxA, String firstA, String maxF,
                             String fuelC, String wallet, String wetField, boolean dry, boolean firstRun)
    {

        car.setMaximumV(Integer.parseInt(maxV));
        car.setMaximumAcceleration(Integer.parseInt(maxA));

        if (firstRun)
            car.accelerate(Integer.parseInt(firstA));

        car.setMaximumFuel(Integer.parseInt(maxF));
        car.setFuel(Integer.parseInt(maxF));
        car.setFuelConsumption(Integer.parseInt(fuelC));
        car.setWallet(Integer.parseInt(wallet));

        car.rainOrSnow = Integer.parseInt(wetField);

        if (dry)
            car.weather = "Dry";

        else
            car.weather = "Wet";

    }
}
