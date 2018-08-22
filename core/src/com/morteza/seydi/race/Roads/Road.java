/*
 *  In The Name Of ALLAH
 *
 *   Project Name   :   Need For Speed Hot Java (Race Simulation)
 *   Code Version   :   1.0
 *     Written by   :   Mortaza Seydi - Zanjan University, IRAN - Spring 2018
 *
 *   Source Code Available on  :   github.com/Mortaza-Seydi
 */

package com.morteza.seydi.race.Roads;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.morteza.seydi.race.Race;
import com.morteza.seydi.race.Sprites.Car;

public class Road
{
    private Car car;

    private Array<float[]> asphalt;
    private Array<float[]> dirt;
    private Array<float[]> sand;

    public Road(Car car, TiledMap map)
    {
        this.car = car;

        asphalt = new Array<float[]>(map.getLayers().get(9).getObjects().getCount());
        dirt = new Array<float[]>(map.getLayers().get(10).getObjects().getCount());
        sand = new Array<float[]>(map.getLayers().get(11).getObjects().getCount());

        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rectangle = ((RectangleMapObject)object).getRectangle();
            float[] a = new float[2];
            a[0] = rectangle.getY();
            a[1] = rectangle.getY() + rectangle.getHeight();
            asphalt.add(a);
        }

        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rectangle = ((RectangleMapObject)object).getRectangle();
            float[] a = new float[2];
            a[0] = rectangle.getY();
            a[1] = rectangle.getY() + rectangle.getHeight();
            dirt.add(a);
        }

        for (MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rectangle = ((RectangleMapObject)object).getRectangle();
            float[] a = new float[2];
            a[0] = rectangle.getY();
            a[1] = rectangle.getY() + rectangle.getHeight();
            sand.add(a);
        }
    }

    public void getRoadState ()
    {
        for (int i = 0; i < asphalt.size; i++)
        {
            if (car.getPosition() > asphalt.get(i)[0]/Race.PPM && car.getPosition() < asphalt.get(i)[1]/Race.PPM)
                car.roadType = "Asphalt";
        }

        for (int i = 0; i < dirt.size; i++)
        {
            if (car.getPosition() > dirt.get(i)[0]/Race.PPM && car.getPosition() < dirt.get(i)[1]/Race.PPM)
                car.roadType = "Dirt";
        }

        for (int i = 0; i < sand.size; i++)
        {
            if (car.getPosition() > sand.get(i)[0]/Race.PPM && car.getPosition() < sand.get(i)[1]/Race.PPM)
                car.roadType = "Sand";
        }
    }
}
