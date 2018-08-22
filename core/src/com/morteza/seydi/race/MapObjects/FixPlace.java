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
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.morteza.seydi.race.Screens.PlayScene;
import com.morteza.seydi.race.Sprites.Car;

public class FixPlace extends MapObjects
{
    public Stage stage;

    private SelectBox<String> tireSelect;
    private Slider healthSlider;
    private TextButton okButton;

    public FixPlace(SpriteBatch sb, PlayScene playScene, Car car, TiledMap map)
    {
        super(sb, playScene, car, map, 6, 4000);

        Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Skin skin = new Skin(Gdx.files.internal("RobotSkin/skin/rusty-robot-ui.json"));

        tireSelect = new SelectBox<String>(skin);
        tireSelect.setItems(car.tireType);
        tireSelect.setBounds(200, 300, 200, 50);
        stage.addActor(tireSelect);

        healthSlider = new Slider(0, 100, 1, false, skin);
        healthSlider.setBounds(200, 180, 150, 100);
        stage.addActor(healthSlider);

        okButton = new TextButton("OK", skin);
        okButton.setBounds(200, 100, 100, 100);
        stage.addActor(okButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void warning()
    {
        if (showWarning())
        {
            playScene.messages.setText("Fix");
        }
    }

    public boolean isArrived()
    {
        for (int i = 0; i < positions.size; i++)
            if (car.getFinalV() == 0 && car.getPosition() > positions.get(i)[0] && car.getPosition() < positions.get(i)[1])
                return true;

        return false;
    }

    public void handleInput()
    {
        if (okButton.isPressed())
        {
            car.tireHealth = healthSlider.getValue();
            car.activeTireType = tireSelect.getSelected();
            car.setWallet(car.getWallet() - 200);
            car.accelerate(1);
            stage.unfocus(okButton);
        }
    }
}
