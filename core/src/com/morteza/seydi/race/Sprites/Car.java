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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.morteza.seydi.race.Race;
import com.morteza.seydi.race.Roads.UK;

public class Car extends CarSprites
{
    private UK uk;
    private boolean firstTime = true, firstTime2 = true;

    public Car (World world, String fileName, int x, int y, int width, int height)
    {
        super(world, fileName, x, y, width, height);
        uk = new UK(this);
    }

    @Override
    protected void go (double time, int keyCode)
    {
        maximumBreakAcceleration = -uk.calculateTotalUk() * 9.8;

        if (body.isActive() && body.isBullet())
        {
            calculateVariablesAndUpdatePosition(time);

            if (finalV > maximumV)
                finalV = maximumV;

            if (keyCode == Input.Keys.LEFT)
                body.setLinearVelocity(new Vector2(-5, (float) (finalV / Race.PPM2)));

            else if (keyCode == Input.Keys.RIGHT)
                body.setLinearVelocity(new Vector2(5, (float) (finalV / Race.PPM2)));

            else
            {
                if (finalV <= 0)
                {
                    body.setAwake(false);
                    finalV = 0;
                    accelerate(0);
                }

                else
                    body.setLinearVelocity(new Vector2(0, (float) (finalV / Race.PPM2)));
            }

            if (fuel <= 0 && firstTime)
            {
                accelerate(-10);
                firstTime = false;
            }

            if (wallet <= 0 && firstTime2)
            {
                accelerate(-10);
                firstTime2 = false;
            }
        }
    }

    public void handleInputAndGo (double timer)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && firstTime && firstTime2)
            go(timer, Input.Keys.RIGHT);

        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && firstTime && firstTime2)
            go(timer, Input.Keys.LEFT);

        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && firstTime && firstTime2)
            accelerate(getAcceleration() - 1);

        else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && firstTime && firstTime2)
            accelerate(getAcceleration() + 1);

        else
            go(timer, 0);
    }
}
