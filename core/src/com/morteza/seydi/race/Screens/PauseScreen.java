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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.morteza.seydi.race.Race;

public class PauseScreen implements Screen
{
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private TiledMap map;

    private Stage stage;
    private Race game;

    public TextField maxV;
    public TextField maxA;
    public TextField firstA;
    public TextField maxF;
    public TextField fuelC;
    public TextField wallet;

    public TextField wetField;
    public CheckBox checkBoxDry;

    public Button okButton;
    public Button cancelButton;

    public PauseScreen(Race game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        map = new TmxMapLoader().load("PauseMap/Map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera), game.batch);

        Skin skin = new Skin(Gdx.files.internal("RobotSkin/skin/rusty-robot-ui.json"));

        TextField.TextFieldFilter textFieldFilter = new TextField.TextFieldFilter.DigitsOnlyFilter();

        maxV = new TextField("100", skin);
        maxA = new TextField("10", skin);
        firstA = new TextField("5", skin);
        maxF = new TextField("100000", skin);
        fuelC = new TextField("5", skin);
        wallet = new TextField("5000", skin);

        maxV.setTextFieldFilter(textFieldFilter);
        maxA.setTextFieldFilter(textFieldFilter);
        firstA.setTextFieldFilter(textFieldFilter);
        maxF.setTextFieldFilter(textFieldFilter);
        fuelC.setTextFieldFilter(textFieldFilter);
        wallet.setTextFieldFilter(textFieldFilter);

        maxV.setPosition(310, 430);
        maxA.setPosition(310, 380);
        firstA.setPosition(310, 330);
        maxF.setPosition(670, 415);
        fuelC.setPosition(670, 365);
        wallet.setPosition(670, 315);

        stage.addActor(maxV);
        stage.addActor(maxA);
        stage.addActor(firstA);
        stage.addActor(maxF);
        stage.addActor(fuelC);
        stage.addActor(wallet);

        wetField = new TextField("0", skin);
        checkBoxDry = new CheckBox("", skin);

        wetField.setPosition(1020, 320);
        wetField.setTextFieldFilter(textFieldFilter);
        checkBoxDry.setPosition(1020, 380);

        stage.addActor(wetField);
        stage.addActor(checkBoxDry);

        okButton = new TextButton("OK", skin);
        cancelButton = new TextButton("CANCEL", skin);

        okButton.setPosition(570, 0);
        cancelButton.setPosition(700, 0);

        stage.addActor(okButton);
        stage.addActor(cancelButton);

        stage.cancelTouchFocus();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta)
    {
        camera.update();
        tiledMapRenderer.setView(camera);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.render();

        game.batch.setProjectionMatrix(camera.combined);
        stage.draw();

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
        tiledMapRenderer.dispose();
        map.dispose();
        stage.dispose();
    }

    public void feed(int maxV, int maxA, int maxF,
                     int fuelC, int wallet, int wetField, String weather)
    {
        this.maxV.setText(String.format("%d", maxV));
        this.maxA.setText(String.format("%d", maxA));
        this.maxF.setText(String.format("%d", maxF));

        this.fuelC.setText(String.format("%d", fuelC));
        this.wallet.setText(String.format("%d", wallet));

        this.wetField.setText(String.format("%d", wetField));

        if (weather.equals("Dry"))
            this.checkBoxDry.setChecked(true);
    }
}
