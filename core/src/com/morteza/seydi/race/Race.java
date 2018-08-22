/*
 *  In The Name Of ALLAH
 *
 *   Project Name   :   Need For Speed Hot Java (Race Simulation)
 *   Code Version   :   1.0
 *     Written by   :   Mortaza Seydi - Zanjan University, IRAN - Spring 2018
 *
 *   Source Code Available on  :   github.com/Mortaza-Seydi
 */

package com.morteza.seydi.race;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.morteza.seydi.race.Screens.PauseScreen;
import com.morteza.seydi.race.Screens.PlayScreen;

public class Race extends Game
{
	public static final float PPM = 100;
	public static final float PPM2 = 5;

	public SpriteBatch batch;
	private boolean firstRun = true;

	private PlayScreen  playScreen;
	private PauseScreen pauseScreen;

	@Override
	public void create ()
	{
		batch = new SpriteBatch(); // Create a batch

		pauseScreen = new PauseScreen (this); // Create New Play Screen Implements Screen
		playScreen  = new PlayScreen  (this); // Create New Pause Screen Implements Screen

		setScreen(pauseScreen); // Set Screen To The New Pause Screen
	}

	@Override
	public void render ()
	{
		super.render();

		if (pauseScreen.okButton.isPressed() && screen == pauseScreen) // Ok Button Pressed
		{
			if (firstRun) // Just For First Run
			{
				setScreen(playScreen); // Set Screen To The New Play Screen

				// Method to Set The First Variables
				playScreen.setVariables(pauseScreen.maxV.getText(),
						pauseScreen.maxA.getText(),
						pauseScreen.firstA.getText(),
						pauseScreen.maxF.getText(),
						pauseScreen.fuelC.getText(),
						pauseScreen.wallet.getText(),
						pauseScreen.wetField.getText(),
						pauseScreen.checkBoxDry.isChecked(),
						firstRun);

				firstRun = false;
			}

			else // In Other Pauses
			{
				screen = playScreen; // Change The Screen (is Different From setScreen() Method)

				// Method to Set The First Variables
				playScreen.setVariables(pauseScreen.maxV.getText(),
						pauseScreen.maxA.getText(),
						pauseScreen.firstA.getText(),
						pauseScreen.maxF.getText(),
						pauseScreen.fuelC.getText(),
						pauseScreen.wallet.getText(),
						pauseScreen.wetField.getText(),
						pauseScreen.checkBoxDry.isChecked(),
						firstRun);
			}

		}

		if (pauseScreen.cancelButton.isPressed()) // Cancel Button Pressed
		{
			screen = playScreen; // Change The Screen (is Different From setScreen() Method)

			if (firstRun)
				Gdx.app.exit(); // Exit In First Run When User Click Cancel Button
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && screen == playScreen)
		{
			setScreen(pauseScreen); // Set Screen To The New Pause Screen

			// A Method To Feed The Pause Screen Text Fields With Current Variables
			pauseScreen.feed(playScreen.car.getMaximumV(), playScreen.car.getMaximumAcceleration(),
					playScreen.car.getMaximumFuel(), playScreen.car.getFuelConsumption(),
					playScreen.car.getWallet(), playScreen.car.rainOrSnow, playScreen.car.weather);
		}
	}

	@Override
	public void dispose ()
	{
		batch.dispose(); // Dispose The Batch
	}
}
