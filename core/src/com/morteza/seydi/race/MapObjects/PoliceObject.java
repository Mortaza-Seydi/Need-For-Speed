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
import com.badlogic.gdx.physics.box2d.World;
import com.morteza.seydi.race.Race;
import com.morteza.seydi.race.Screens.PlayScene;
import com.morteza.seydi.race.Sprites.Car;
import com.morteza.seydi.race.Sprites.PoliceCar;

public class PoliceObject extends MapObjects
{
    private boolean[] firstTime;
    private boolean[] firstTime2;
    private boolean[] catching;
    private PoliceCar[] policeCar;

    public PoliceObject(World world, SpriteBatch sb, PlayScene playScene, Car car, TiledMap map)
    {
        super(sb, playScene, car, map, 4, 4000);

        policeCar = new PoliceCar[positions.size];
        for (int i = 0; i < positions.size; i++)
            policeCar[i] = new PoliceCar(world, "Cars/Police.png", (int) (0*Race.PPM), (int) (positions.get(i)[0]*Race.PPM), 70, 140);

        firstTime = new boolean[positions.size];
        catching  = new boolean[positions.size];

        for (int i = 0; i < positions.size; i++)
            firstTime[i] = true;

        for (int i = 0; i < positions.size; i++)
            catching[i] = false;

        firstTime2 = new boolean[positions.size];

        for (int i = 0; i < positions.size; i++)
            firstTime2[i] = true;

    }

    @Override
    public void warning()
    {
        if (showWarning())
            playScene.messages.setText("Police");

        if (state())
            playScene.policeMessages.setText("Stop The Car");
    }

    public void doYourJob(double t)
    {
        for (int i = 0; i < positions.size; i++)
        {
            if (catching[i])
            {
                if (car.getPosition() - positions.get(i)[0] < 80 && car.getPosition() - positions.get(i)[0] > 0)
                {
                    if (car.getFinalV() == 0)
                    {
                        catching[i] = false;
                        car.setWallet(car.getWallet() - 100);
                        playScene.policeMessages.setText("Pay");
                        break;
                    }
                }

                if (car.getPosition() - positions.get(i)[0] > 80)
                {
                    playScene.policeMessages.setText("Police is Coming");
                    if (firstTime2[i])
                    {
                        policeCar[i].accelerate(policeCar[i].getMaximumAcceleration());
                        firstTime2[i] = false;
                    }

                    policeCar[i].go(t, 0);
                    policeCar[i].draw(batch);

                    if (policeCar[i].getPosition() >= car.getPosition())
                    {
                        catching[i] = false;
                        car.accelerate(-10);
                        policeCar[i].accelerate(-20);
                        car.setWallet(car.getWallet() - 200);
                        playScene.policeMessages.setText("Lose");
                        break;
                    }
                }

                if (car.getPosition() - positions.get(i)[0] > 300)
                {
                    catching[i] = false;
                    policeCar[i].accelerate(-20);
                    playScene.policeMessages.setText("Go");
                    break;
                }
            }
            policeCar[i].go(t, 0);
            policeCar[i].draw(batch);
        }
    }

    private boolean state()
    {
        for (int i = 0; i < positions.size; i++)
            if (car.getPosition() > positions.get(i)[0] && car.getPosition() < positions.get(i)[1])
                if (car.getFinalV() > 50 && firstTime[i])
                {
                    firstTime[i] = false;
                    catching[i] = true;
                    return true;
                }

        return false;
    }
}
