import numpy as np
import scipy.optimize as opt

v0 = 5.3

x_target = 2.1
y_target = -0.21

g = 9.81

def likn(p):
    a, t = p
    x = v0 * np.cos(a) * t
    y = v0 * np.sin(a) * t - 0.5 * g * t ** 2
    return (x - x_target, y - y_target)

a_0 = np.pi / 4
t_0 = 0.1

a, t = opt.fsolve(likn, (a_0, t_0))

d = np.degrees(a)

print(f"Utskytsvinkelen er {d:.2f} grader")

