
import numpy.random
import numpy as np
import matplotlib.pyplot as plt
import math as m
import time
import sys

# QUICKSORT


def permutacion(sizeP):
    return numpy.random.permutation(sizeP)

def firstP(t, p, u):
    return p


def partir(t, p, u, pivotF=firstP):
    pivot = pivotF(t, p, u)

    k = t[pivot]

    # swap(t[p], t[pivot])
    temp = t[p]
    t[p] = t[pivot]
    t[pivot] = temp

    pivot = p

    for i in range(p, u):
        if t[i] < k:
            pivot += 1

            # swap(t[i], t[pivot])
            temp = t[i]
            t[i] = t[pivot]
            t[pivot] = temp

    # swap(t[i], t[pivot])
    temp = t[p]
    t[p] = t[pivot]
    t[pivot] = temp
    return pivot


def qs(t, p, u, pivotF=firstP):
    if p < u:
        split = partir(t, p, u, pivotF)
        qs(t, p, split, pivotF)
        qs(t, split + 1, u, pivotF)
    else:
        return


def qs2(t, p, u, pivotF=firstP):
    while p < u:
        split = partir(t, p, u, pivotF)
        qs2(t, p, split, pivotF)
        p = split + 1
    return


def timeSort(sortM, nPerms, sizeIni, sizeFin, step):
    timelist = []

    for i in range(sizeIni, sizeFin, step):
        time1 = time.clock()
        for x in range(nPerms):
            sortM(permutacion(i), 1, i)
        time2 = time.clock()
        timelist.append((time2 - time1) / nPerms)
    return timelist


def nLognValues(sizeIni, sizeFin, steps):
    list = []
    for i in range(sizeIni, sizeFin, steps):
        list.append(i * m.log10(i))
    return list


def fitPlot(func2fit, nNodesIni, nNodesFin, step):
    times = timeSort(func2fit, 10, nNodesIni, nNodesFin, step)

    x = []
    for z in range(nNodesIni, nNodesFin, step):
        x.append(z)

    ydata = np.polyfit(x, times, 1)
    f = np.poly1d(ydata)
    yfit = np.linspace(f(nNodesIni), f(nNodesFin), (nNodesFin - nNodesIni) / step)
    plt.plot(x, times, 'r--',x, yfit)
    plt.show()
    return

#Entrega
if len(sys.argv) != 9:
    print("Ejecución: python qsort11.py -nPerms 200 -sizeI 1000 -sizeF 10000 -step 250")
    exit()
nPerms=int(sys.argv[2])
sizeI= int(sys.argv[4])
sizeF= int(sys.argv[6])
step= int(sys.argv[8])
times =timeSort(qs, nPerms, sizeI, sizeF, step)
i=0
for z in range(sizeI, sizeF, step):
    print ("size:",z," t medio:",times[i])
    i+=1

      