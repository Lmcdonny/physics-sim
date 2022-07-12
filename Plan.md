#Physics Simulation Plan

##Initial Ideas
* Have an abstract class AstroObject
	* AstroObject would have attributes:
		* float mass
		* float density
		* float size
		* int posX
		* int posY
	* larger mass objects get higher gravity:
		* function to decide if 2 masses are close enough?
		* I think the easier AND more accurate way would be to say that a molecule is affected by the total mass nearby
		* if this is the case we shouldn't need any seperate function
* Constant GRAVITY
	* each molecule will constantly be affected by gravity
	* double GRAVITY = 6.67 x 10^-11
	* `public double getForce(AstroObject mol1, AstroObject mol2)  
		float dist = sqrt((mol1.x - mol2.x)^2 + (mol1y - mol2.y)^2);
		return (GRAVITY * mol1.mass * mol2.mass) / dist^2`
	* we draw a vector for both in their respective directions
* update the position of the point:


## Vector stuff
* need to implement a current velocity vector will probably start at 0 and then as the forces act on it, it grows
	* need more research but I think it might be something like after the current forces act on it it, it calculates the total distance and angle in which it moved and stores that as the current velocity vector
	* that vector would then be added on in addition to the other forces


## Planning the graphage
For the sake of ease of calculation we will say that a pixel on the graph is one nanometer, and we will say that the masses of the atoms are in g^13 so they will actually have an effect on eachother at this scale

* My newest idea is to make the distances in terms of AU and the masses Solar masses


