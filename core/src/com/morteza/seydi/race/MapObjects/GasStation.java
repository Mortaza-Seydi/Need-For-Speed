/*
 *  In The Name Of ALLAH
 *
 *   Project Name   :   Need For Speed Hot Java (Race Simulation)
 *   Code Version   :   1.0
 *     Written by   :   Mortaza Seydi - Zanjan University, IRAN - Spring 2018
 *
 *   Source Code Available on  :   github.com/Mortaza-Seydi
 */

package com.morteza.seydi.race.MapObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.morteza.seydi.race.Screens.PlayScene;
import com.morteza.seydi.race.Sprites.Car;

public class GasStation extends MapObjects
{
    public Stage stage;

    private TextField  fuel;
    private TextButton fullButton;
    private TextButton okButton;

    public GasStation(SpriteBatch sb, PlayScene playScene, Car car, TiledMap map)
    {
        super(sb, playScene, car, map, 5, 4000);

        Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Skin skin = new Skin(Gdx.files.internal("metal/skin/metal-ui.json"));

        TextField.TextFieldFilter textFieldFilter = new TextField.TextFieldFilter.DigitsOnlyFilter();
        fuel = new TextField("0", skin);
        fuel.setPosition(210,200);
        fuel.setTextFieldFilter(textFieldFilter);
        stage.addActor(fuel);

        fullButton = new TextButton("Full", skin);
        fullButton.setPosition(280,150);
        stage.addActor(fullButton);

        okButton = new TextButton("OK", skin);
        okButton.setPosition(210, 150);
        stage.addActor(okButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void warning()
    {
        if (showWarning())
        {
            playScene.messages.setText("Gas");
        }
    }

    public boolean isArrived()
    {
        for (int i = 0; i < positions.size; i++)
        {
            if (car.getFinalV() == 0 && car.getPosition() > positions.get(i)[0] && car.getPosition() < positions.get(i)[1])
                return true;
        }

        return false;
    }

    public void handleInput()
    {
        if (fullButton.isPressed())
        {
            car.setFuel(car.getMaximumFuel());
            car.setWallet(car.getWallet() - 100);
            car.accelerate(1);
            stage.unfocus(fullButton);
        }

        if (okButton.isPressed())
        {
            car.setFuel(car.getFuel() + Integer.parseInt(fuel.getText()));
            car.setWallet(car.getWallet() - Integer.parseInt(fuel.getText()));
            car.accelerate(1);
            stage.unfocus(okButton);
        }

    }
}
