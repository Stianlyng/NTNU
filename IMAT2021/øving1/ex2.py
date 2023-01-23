"""
Oppgave 10
Vector v in basis {[1,0],[0,1]} is given by v = [3,-17]
Find the components of v in basis {[1,3],[4,-1]}.
"""

import numpy

# Basis vektorer
b1 = numpy.array([1,3])
b2 = numpy.array([4,-1])

# Komponenter av v i basis {[1,3],[4,-1]}
v = numpy.array([3,-17])

B = numpy.array([b1,b2])
B = B.T
Binv = numpy.linalg.inv(B)

# Komponentene
vcomp = numpy.dot(Binv,v)

print(vcomp)

"""
Vector w in basis {[1,3],[4,-1]} is given by w = [-3,-3]
Find the components of w in basis {[1,0],[0,1]}.
"""

import numpy

# Basis vectors
b1 = numpy.array([1,3])
b2 = numpy.array([4,-1])

# Vector w
w = numpy.array([-3,-3])

# Matrix of basis vectors
B = numpy.array([b1,b2])

# Matrix of basis vectors in columns
B = B.T

# Matrix of basis vectors in rows

# Components of w in basis {[1,0],[0,1]}
wcomp = numpy.dot(B,w)

print(wcomp)