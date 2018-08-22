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
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.morteza.seydi.race.Race;
import com.morteza.seydi.race.Screens.PlayScene;
import com.morteza.seydi.race.Sprites.Car;

abstract class MapObjects
{
    protected Array<float[]> positions;
    private double distance;
    protected Car car;
    protected SpriteBatch batch;
    protected PlayScene playScene;
    private boolean[] firstTime;

    public MapObjects(SpriteBatch sb, PlayScene playScene, Car car, TiledMap map, int index, int distance)
    {
        this.batch = sb;
        this.playScene = playScene;
        this.car = car;
        this.distance = distance/Race.PPM;
        positions = new Array<float[]>(map.getLayers().get(index).getObjects().getCount());

        firstTime = new boolean[map.getLayers().get(index).getObjects().getCount()];
        for (int i = 0; i < map.getLayers().get(index).getObjects().getCount(); i++)
            firstTime[i] = true;

        for (MapObject object : map.getLayers().get(index).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            float[] points = new float[2];
            points[0] = rectangle.getY() / Race.PPM;
            points[1] = (rectangle.getY() + rectangle.getHeight()) / Race.PPM;

            positions.add(points);
        }
    }

    protected boolean showWarning()
    {
        for (int i = 0; i < positions.size; i++)
        {
            if (((positions.get(i)[0] - car.getPosition()) < distance) && firstTime[i])
                return true;

            if (car.getPosition() > positions.get(i)[0])
            {
                firstTime[i] = false;
                return false;
            }
        }
        return false;
    }

    public abstract void warning();
}