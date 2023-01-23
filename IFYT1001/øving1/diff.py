"""
from scipy.optimize import fsolve
import math

# Define the target positions
x_target = 2.1
y_target = 0.21

# Define the initial velocity and gravitational acceleration
v0 = 5.3
g = 9.81

# Define the function that we want to solve
def equations(p):
    a, t = p
    x = v0 * math.cos(a) * t
    y = v0 * math.sin(a) * t - 0.5 * g * t ** 2
    return (x - x_target, y - y_target)

# Use fsolve to find the solution
a_0 = math.pi / 4 # Initial guess for launch angle
t_0 = 0.1 # Initial guess for impact time
a, t =  fsolve(equations, (a_0, t_0))

# Print the result
print(f"The launch angle is {a} radians")

"""

from scipy.optimize import fsolve
import numpy as np

# Function to calculate the difference between the final y position and the y position calculated using the kinematic equation
def eqn(theta, x_f, v_0):
    x, y = x_f
    g = 9.81
    t = (2*y)**0.5/g
    v_ix = x/t
    v_iy = (v_0**2 - v_ix**2)**0.5
    y_eqn = v_iy*t - (1/2)*g*t**2
    return y - y_eqn

x_f = np.array([2.1, 0.21]) # Displacement vector
v_0 = 5.3 # Initial velocity
initial_guess = 0.5 # Initial guess for the launch angle as random number

theta = fsolve(eqn, initial_guess, args=(x_f, v_0))

print(f"The launch angle is {np.degrees(theta[0]):.2f} degrees")
