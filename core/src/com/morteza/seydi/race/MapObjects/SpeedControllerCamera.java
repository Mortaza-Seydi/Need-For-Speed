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

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.morteza.seydi.race.Screens.PlayScene;
import com.morteza.seydi.race.Sprites.Car;

public class SpeedControllerCamera extends MapObjects
{
    public SpeedControllerCamera(SpriteBatch sb, PlayScene playScene, Car car, TiledMap map)
    {
        super(sb, playScene, car, map, 7, 3000);
    }

    @Override
    public void warning()
    {
        if (showWarning())
            playScene.messages.setText("Camera");
    }

    public void doYourJob()
    {
        if (isArrived())
        {
            playScene.policeMessages.setText("PAyyyyyyyyyyy");
            car.setWallet(car.getWallet() - 50);
        }
    }

    private boolean isArrived()
    {
        for (int i = 0; i < positions.size; i++)
        {
            if (car.getPosition() > positions.get(i)[0] && car.getPosition() < positions.get(i)[1])
                return true;
        }

        return false;
    }
}
