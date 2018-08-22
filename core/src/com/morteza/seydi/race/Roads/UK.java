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

import com.morteza.seydi.race.Sprites.Car;

public class UK
{
    private Car car;

    public UK(Car car)
    {
        this.car = car;
    }

    private double getRoadUK ()
    {
        if (car.roadType.equals("Asphalt"))
        {
            return 0.4;
        }

        else if (car.roadType.equals("Dirt"))
        {
            return 0.3;
        }

        else if (car.roadType.equals("Sand"))
        {
            return 0.2;
        }

        else
            return 0;

    }

    private double getWeatherUk()
    {
        if (car.weather.equals("Dry"))
        {
            return 0.40;
        }

        else
        {
            if (car.rainOrSnow >= 0 && car.rainOrSnow < 2)
            {
                return 0.35;
            }

            if (car.rainOrSnow >= 2 && car.rainOrSnow < 4)
            {
                return 0.30;
            }

            if (car.rainOrSnow >= 4 && car.rainOrSnow < 6)
            {
                return 0.25;
            }

            if (car.rainOrSnow >= 6 && car.rainOrSnow < 8)
            {
                return 0.20;
            }

            if (car.rainOrSnow >= 8 && car.rainOrSnow <= 10)
            {
                return 0.15;
            }

            else
                return 0;
        }
    }

    private double getTireUk()
    {

        if (car.tireHealth >= 0 && car.tireHealth <= 20)
        {
            return 0.20;
        }

        else if (car.tireHealth > 20 && car.tireHealth <= 40)
        {
            return 0.18;
        }

        else if (car.tireHealth > 40 && car.tireHealth <= 60)
        {
            return 0.16;
        }

        else if (car.tireHealth > 60 && car.tireHealth <= 80)
        {
            return 0.14;
        }

        else if (car.tireHealth > 80 && car.tireHealth <= 100)
        {
            return 0.12;
        }

        else
            return 0;
    }

    public double calculateTotalUk()
    {
        return getRoadUK()+getWeatherUk()+getTireUk();
    }

}
