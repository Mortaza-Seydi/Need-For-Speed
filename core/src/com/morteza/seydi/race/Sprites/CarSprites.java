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

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.morteza.seydi.race.Race;

public abstract class CarSprites extends Sprite
{
    private World world;
    public Body body;

    private double finalX = 0, initialX = 0, initialV = 0;
    protected double finalV = 0;
    private double deltaT = 0, initialT = 0;
    private double acceleration = 0;
    protected double fuel = 0;
    private double distance = 0;
    private double distance2 = 0;

    protected double maximumBreakAcceleration = 0;
    protected int maximumV = 0, maximumAcceleration = 0;
    protected int maximumFuel = 0, wallet = 0;
    protected int fuelConsumption = 0;

    private int x, y, width, height;

    public String roadType;
    public String weather;
    public Array<String> tireType;
    public String activeTireType;
    public int rainOrSnow;
    public float tireHealth = 100;

    public CarSprites(World world, String fileName, int x, int y, int width, int height)
    {
        super(new Texture(fileName));

        setBounds(x / Race.PPM, y / Race.PPM,
                  width / Race.PPM,
                  height / Race.PPM
                 );

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.world = world;

        roadType = "Asphalt";

        tireType = new Array<String>(5);
        tireType.add("Very Good");
        tireType.add("Good");
        tireType.add("Average");
        tireType.add("Weak");
        tireType.add("Very Weak");

        activeTireType = "Very Good";

        defineCar();
    }

    private void defineCar()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / Race.PPM + 20 / Race.PPM, y / Race.PPM + 20 / Race.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / Race.PPM / 2, height / Race.PPM / 2);

        fixtureDef.shape = shape;
        fixtureDef.density = 0f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("Car");

        body.setBullet(true);

        setPosition(
                body.getPosition().x - getWidth() / 2,
                body.getPosition().y - getHeight() / 2
                   );

        shape.dispose();
    }

    public void accelerate(double acc)
    {
        if (acc >= maximumBreakAcceleration && acc <= maximumAcceleration)
        {
            acceleration = acc;
            initialX = finalX;
            initialV = finalV;
            initialT += deltaT;
        }

        if (acc < maximumBreakAcceleration)
            accelerate(maximumBreakAcceleration);

        if (acc > maximumAcceleration)
            accelerate(maximumAcceleration);
    }

    protected void calculateVariablesAndUpdatePosition(double t)
    {
        deltaT = t - initialT;
        finalX = (0.5) * acceleration * deltaT * deltaT + initialV * deltaT + initialX;
        finalV = acceleration * deltaT + initialV;

        setPosition(
                body.getPosition().x - getWidth() / 2,
                body.getPosition().y - getHeight() / 2
                   );

        if (finalV > 0)
        {
            fuel -= fuelConsumption * (finalX - distance) / 300;
            tireHealth -= getTireC() * (finalX - distance2) / 1000000;
        }
    }

    private int getTireC()
    {
        if (activeTireType.equals("Very Good"))
            return 5 + getRoadTireC();

        else if (activeTireType.equals("Good"))
            return 10 + getRoadTireC();

        else if (activeTireType.equals("Average"))
            return 15 + getRoadTireC();

        else if (activeTireType.equals("Weak"))
            return 20 + getRoadTireC();

        else if (activeTireType.equals("Very Weak"))
            return 25 + getRoadTireC();

        else
            return 0;
    }

    private int getRoadTireC()
    {
        if (roadType.equals("Asphalt"))
            return 15;

        else if (roadType.equals("Dirt"))
            return 10;

        else if (roadType.equals("Sand"))
            return 5;

        else
            return 0;
    }

    protected abstract void go(double timer, int keyCode);

    public double getFinalX()
    {
        return finalX;
    }

    public float getPosition()
    {
        return body.getPosition().y;
    }

    public double getFinalV()
    {
        return finalV;
    }

    public double getAcceleration()
    {
        return acceleration;
    }

    public double getFuel()
    {
        return fuel;
    }

    public int getMaximumV()
    {
        return maximumV;
    }

    public int getMaximumAcceleration()
    {
        return maximumAcceleration;
    }

    public int getMaximumFuel()
    {
        return maximumFuel;
    }

    public int getFuelConsumption()
    {
        return fuelConsumption;
    }

    public int getWallet()
    {
        return wallet;
    }

    public void setMaximumV(int maximumV)
    {
        this.maximumV = maximumV;
    }

    public void setMaximumAcceleration(int maximumAcceleration)
    {
        this.maximumAcceleration = maximumAcceleration;
    }

    public void setMaximumFuel(int maximumFuel)
    {
        this.maximumFuel = maximumFuel;
    }

    public void setFuelConsumption(int fuelConsumption)
    {
        this.fuelConsumption = fuelConsumption;
    }

    public void setWallet(int wallet)
    {
        this.wallet = wallet;
    }

    public double getMaximumBreakAcceleration()
    {
        return maximumBreakAcceleration;
    }

    public void setFuel(double fuel)
    {
        distance = finalX;
        this.fuel = fuel;
    }
}
