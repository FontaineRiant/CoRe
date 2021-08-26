![CoRe screenshot](https://i.imgur.com/h0WbhXq.png)

# CoRe
CoRe is a school project that implements the design pattern **C**hain **o**f **Re**sponsiblity and does its best to illustrate it.

It's a standard space side scroller with a small twist. You need to shoot and dodge an increasing amount of colored asteroids. You have 3 keys to shoot: J, K and L, each of them shoots a different color, and asteroids only explode when they get hit with the right color. You'll need to mix colors to hit purple, green and orange asteroids (by pressing multiple keys at the same time).

When asteroids explode, they hit their nearest neighbour, which explodes if it has the same color. Ice (white) rocks clear the entire screen, but can only be hit indirectly. Satellites have more hit points, can only be hit with black shots (J+K+L), and bounce a random color to the nearest neighbour.
