
import random
import numpy.random
import string
import numpy as np
import matplotlib.pyplot as plt
import math as m
import time

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


# TIMESORT
# We want to time the execution of QS repeating it over nPerms permutations of
# sizes between sizeIni and sizeFin with steps of size step. To do so
# returns a list with the average times needed at each step.
def timeSort(sortM, nPerms, sizeIni, sizeFin, step):
    timelist = []

    for i in range(sizeIni, sizeFin, step):
        time1 = time.clock()
        for x in range(nPerms):
            # print("permutacion" + repr(i) + " "+  repr(x))
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
    yfit = np.linspace(f(nNodesIni), f(nNodesFin), (nNodesFin - nNodesIni) / step - 1)

    plt.plot(x, times, 'r--', yfit)
    plt.show()
    return


fitPlot(qs2,0,100,1)