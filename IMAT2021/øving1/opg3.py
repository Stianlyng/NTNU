from numpy import *
from numpy.linalg import *

"""
x + 0y + 3z = 1, 
0x + 5y + 6z = 1,
-5x + 8y + 0z = 2
"""

A = array([[1, 0, 3], [0, 5, 6], [-5, 8, 0]])

b = array([1, 1, 2])

x = solve(A, b)

I = eye(3)

B = array([[1,2,3],[4,5,6]])

C = dot(B, A)

D = dot(A, transpose(B))
