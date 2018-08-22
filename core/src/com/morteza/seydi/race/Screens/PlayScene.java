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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.morteza.seydi.race.Sprites.Car;

public class PlayScene
{
    public Stage stage;

    private Label v, d, t, a;
    private Label maxV, maxA, maxF;
    private ProgressBar fProgressBar, tireProgress;
    private Label weather, fuelC, wallet;
    public Label messages;
    public Label policeMessages;
    private Car car;

    public PlayScene (SpriteBatch sb, Car car)
    {
        this.car = car;

        Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Skin skin = new Skin(Gdx.files.internal("GdxHoloSkin/skin/uiskin.json"));
        Skin skin2 = new Skin(Gdx.files.internal("RobotSkin/skin/rusty-robot-ui.json"));


        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Label tLabel = new Label("T : ", skin2,"bg");
        Label vLabel = new Label("V : ", skin2,"bg");
        Label aLabel = new Label("A : ", skin2,"bg");
        table.add(tLabel).expandX();
        table.add(vLabel).expandX();
        table.add(aLabel).expandX();
        table.row();


        t = new Label("0", skin2);
        v = new Label("0", skin2);
        a = new Label("0", skin2);
        table.add(t).expandX();
        table.add(v).expandX();
        table.add(a).expandX();
        table.row();

        Label dLabel = new Label("D : ", skin2,"bg");
        Label maxVLAbel = new Label("Max V : ", skin2,"bg");
        Label maxALabel = new Label("Max A : ", skin2,"bg");
        table.add(dLabel).expandX();
        table.add(maxVLAbel).expandX();
        table.add(maxALabel).expandX();
        table.row();

        d = new Label("0", skin2);
        maxV = new Label("0", skin2);
        maxA = new Label("0", skin2);
        table.add(d).expandX();
        table.add(maxV).expandX();
        table.add(maxA).expandX();
        table.row();

        Label maxFLabel = new Label("MAF F : ", skin2,"bg");
        Label fuelCLabel = new Label("Max Break A : ", skin2,"bg");
        Label weatherLabel = new Label("Weather", skin2, "bg");
        table.add(maxFLabel).expandX();
        table.add(fuelCLabel).expandX();
        table.add(weatherLabel).expandX();
        table.row();

        maxF = new Label("0", skin2);
        fuelC = new Label("0", skin2);
        weather = new Label(" ", skin2);
        table.add(maxF).expandX();
        table.add(fuelC).expandX();
        table.add(weather).expandX();
        table.row();

        Label fLabel = new Label("Fuel : ", skin2, "bg");
        Label tireHLabel = new Label("Tire H : ", skin2, "bg");
        Label walletLabel = new Label("Wallet", skin2, "bg");
        table.add(fLabel).expandX();
        table.add(tireHLabel).expandX();
        table.add(walletLabel).expandX();
        table.row();

        fProgressBar = new ProgressBar(0, 100000, 1, false, skin);
        tireProgress = new ProgressBar(0, 100, 1, false, skin);
        wallet = new Label("0", skin2);
        table.add(fProgressBar).expandX();
        table.add(tireProgress).expandX();
        table.add(wallet).expandX();
        table.row();

        messages = new Label(" ", skin);
        table.add();
        table.add(messages);
        table.row();

        policeMessages = new Label(" ", skin);
        table.add();
        table.add(policeMessages);
        table.row();

        stage.addActor(table);
    }

    public void update (double time)
    {
        t.setText(String.format("%f", time));
        v.setText(String.format("%f", car.getFinalV()));
        a.setText(String.format("%f", car.getAcceleration()));

        d.setText(String.format("%f", car.getFinalX()));
        maxV.setText(String.format("%d", car.getMaximumV()));
        maxA.setText(String.format("%d", car.getMaximumAcceleration()));

        maxF.setText(String.format("%d", car.getMaximumFuel()));
        fuelC.setText(String.format("%f", car.getMaximumBreakAcceleration()));
        weather.setText(car.weather);

        fProgressBar.setValue((float) car.getFuel());
        tireProgress.setValue(car.tireHealth);
        wallet.setText(String.format("%d", car.getWallet()));
    }
}
