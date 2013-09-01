# Summary
Specification of the gameplay mechanics.

# General Structure

Pong is made of a play field, 2 paddles, goal areas, and a ball. The *play field* is the rectangular area which contains the 2 paddles and the ball. A *paddle* is a thin rectangle that can move up or down and rebound the ball. *Goal area* is the part of the play field which is past each paddle, and a player scores if the ball goes into the goal area. The *ball* is a small circle which can move in a straight line and rebound off of the paddles or top and bottom sides of the play field. A good example of a working pong game can be found at http://www.xnet.se/javaTest/jPong/jPong.html.

# Rounds

A *round* is the series of events that cause a goal to be scored. A round starts with the ball placed in the center of the play field, and ends with the ball in the goal area.

# Ball Motion

At the start of each round, the ball will be placed in the center of the play field and given a fixed speed but in a random direction (either left or right and within roughly 60 degrees of horizontal). No interactions between the ball and wall or paddle will change the speed of the ball - only direction. The ball will always move in a straight line unless it collides with the wall or paddle. Collision with the wall will result in a bounce with equal angles of incidence and reflectance. Collision with a paddle will result in a bounce-back direction defined by the paddle.

# Paddle Motion And Bounce

Paddles can only move up or down, and at a fixed speed. A paddle is bounded by the upper and lower sides of the play field so it will stay inside the play field. A paddle may be controlled by a human player, computer player, or the artificial intelligence.

If the ball hits the upper half of the paddle, it will be rebounded upwards, and similarly rebounded downwards if the ball hits the lower half of the paddle. The angle of bounce will be greater if the ball hits closer to the edge of the paddle.

# Simulation

## CPU Player

First of all, no human will want to play endless rounds against the artificial intelligence for it to learn, so a *CPU player* will be created. The CPU player will be purely algorithmic and may operate in different ways depending on its play style. For example, it might merely move the paddle in the direction the ball is currently moving. Or it might move in the direction that the ball is in relation to its paddle.

## Speed

It will be desirable to run the simulation very quickly so the success of the AI will become evident quickly. This will most likely mean that the rounds themselves will not be displayed on the screen; rather, they will be simulated in the computer and it will print out the win:loss ratio as it goes.

## Repeatability

The simulation needs to be repeatable in order to track bugs and AI behavior effectively. This means that if any CPU player or AI behavior is unpredictable, a repeatable round must be recorded. If, however, all behavior can be re-simulated, then all that is necessary for a round to be repeated is the initial conditions to be recorded. Also, rounds must run based on ticks to be repeatable. A *tick* is a fixed time step with which the game can update positions and game logic.
