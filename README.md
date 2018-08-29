# Need For Speed Hot Java (Racing Simulation)
#### NFS with _LibGDX_, _BOX2D_ And _Tiled Map_

## Features 
* Developed with **LibGDX** game engine, **Box2D** physics engine and **TiledMap**
* All vehicle motion variables are calculated according to the laws of physics.
* Brake acceleration is calculated by the formula ` uk * g `, and ` g = 10 M/s^2 `, uk depends on the type of road(asphalt, dirt and sand), weather(dry and wet(0 to 10)) and tire health(0 to 100).
* Fuel is low due to fuel consumption and tire's health according to its status
* There is wallet to pay

## Build
1. Connect to Internet to download Gradle files
2. Set **Working Directory** on ` core/assets `
3. Run ` desktop/src/.../DesktopLauncher.java `

## Previews

### Setting Screen
* Here you can set some variables like ` max velocity, acceleration and fuel `, ` first acceleration `, ` wallet ` and ` fuel consumption `
* Select **dry** weather or enter a number from 0 to 10 for **wet** weather ` (0:snow - 10:rain) `
* Click **OK** to go 

![Setting Screen](./previews/1.jpg)
##





### Main Screen
* In this Screen you see some details like ` time `, ` distance `, ` max and current velocity `, ` max and current acceleration `, ` max and current fuel `, ` tire health `, ` wallet `, ` weather ` and ` max break acceleration ` 
* Use _Arrow-Up_ to **accelerate** and _Arrow-Down_ to **break**
* Use _Arrow-Right_ to go **right** and _Arrow-Left_ to go **left**
* Press _space_ button to **pause**

![Main Screen](./previews/2.jpg)
##





### Police
* If your Speed is upper than **(`MaxV / 2`)** you get a **warning** to **stop** the car, if you did not stop the car, police starts to **follow** you, if he can **catch** you, you have to **pay** but you can **run away**

![Police](./previews/3.jpg)
##





### Gas Station
* In the text field **enter** any number or click on **full** button

![Gas Station](./previews/4.jpg)
##





### Change Tire
* Select tire state from **combo box**
* Change the **scroll** to set tire health from 0 to 100

![Change Tire](./previews/5.jpg)
##





### Speed Controller Camera
* If your **Speed** is upper than **(`MaxV / 2`)** you have to pay

![Speed Controller Camera](./previews/6.jpg)
