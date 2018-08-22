/*
 *  In The Name Of ALLAH
 *
 *   Project Name   :   Need For Speed Hot Java (Race Simulation)
 *   Code Version   :   1.0
 *     Written by   :   Mortaza Seydi - Zanjan University, IRAN - Spring 2018
 *
 *   Source Code Available on  :   github.com/Mortaza-Seydi
 */

package com.morteza.seydi.race.Sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.morteza.seydi.race.Race;

public class PoliceCar extends CarSprites
{
    public PoliceCar(World world, String fileName, int x, int y, int width, int height)
    {
        super(world, fileName, x, y, width, height);

        maximumV = 100;
        maximumAcceleration = 10;
        maximumBreakAcceleration = -20;
    }

    @Override
    public void go (double time, int keyCode)
    {
        if (body.isActive())
        {
            calculateVariablesAndUpdatePosition(time);

            if (finalV >= maximumV)
                finalV = maximumV;

            body.setLinearVelocity(new Vector2(0, (float) finalV / Race.PPM2));
        }
    }
}
