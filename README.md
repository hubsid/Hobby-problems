# Hobby-problems
a simple 1 class hobby project to test the optimum gear shift point in a manual transmission vehicle to acheive the greatest acceleration
from 0 - 100 kmph.
it needs data pertaining to the engine torque curve, redline rpm, initial + final reduction ratio, number of gears and their corresponding 
gear ratios, weight of the vehicle + the rider,
as configuration inputs.

Assumption : full throttle is provided, and no clutch slippage during lever release after gear shift.

The configuration is present as static constants, and the default values found in the file correspond to a 
five geared honda shine motorcycle, and the torque curve is linear by default
for these values, it seems the result obtained is 13 secs when gears are shifted little after the torque peak point.
