import math
def FFT(c, K):
	"""c: table with 2**K coefficients"""
	f=[0]*2**K
	if K==0: 
		return c
	else:
		c_e = c[0:2**K:2]
		c_o = c[1:2**K:2] #coeffs in even and odd positions
		f_e = FFT(c_e, K-1)
		f_o = FFT(c_o, K-1)
		for j in range(0, 2**K):
			f[j] = f_e[j % 2**(K-1)] + math.exp(2*math.pi * j/2**K) * f_o[j % 2**(K-1)]
	return f

t =FFT([1,2,3,4], 2)
print t
print FFT(t, 2)

  