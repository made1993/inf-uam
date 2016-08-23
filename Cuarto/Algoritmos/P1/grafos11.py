import random
import numpy.random
import string
import numpy as np
import matplotlib.pyplot as plt
import math as m
import time
import queue
import copy


# Grafos

def randMatrPosWGraph(nNodes, sparseFactor, maxWeight=50.):
    matrix = np.zeros(shape=(nNodes, nNodes))
    denisity= (nNodes-1) * sparseFactor
    for x in range(nNodes):
        for y in range(int(denisity)):
            z = random.randrange(nNodes)
            while x == z or matrix[x][z] > 0.:
                z = random.randrange(nNodes)
            matrix[x][z] = random.randint(1, maxWeight)

    return matrix


def cuentaRamas(mG):
    i = 0
    for x in range(len(mG)):
        for y in range(len(mG[0])):
            if (mG[x][y] > 0):
                i += 1
    return i


def fromAdjM2Dict(mG):
    dict = {}
    i = 0
    for fila in mG:
        j = 0
        list = []

        for columna in fila:
            if columna != 0:
                list.append((columna, j))
            j += 1
        dict.update({i: list})
        i += 1
    return dict


def sizeDict(dG):
    max = -1
    for elem in dG:
        if elem > max:
            max = elem
        for list in dG[elem]:
            if list[1] > max:
                max = list[1]
    return max + 1


def fromDict2AdjM(dG):
    size = sizeDict(dG)
    mat = np.zeros(shape=(size, size))
    j = 0
    for elem in dG:
        list = []
        i = 0
        for x in dG[elem]:

            while i <= x[1]:
                if x[1] == i:
                    list.append(x[0])
                else:
                    list.append(0)
                i += 1

        while (len(list) < size):
            list.append(0)
        mat[j] = np.matrix(list)
        j += 1


    return mat



def dijkstraD(dG, u):
    size = sizeDict(dG)
    v = []
    p = []
    d = []

    for x in range(size):
        v.append(False)
        p.append(None)
        d.append(99999)

    q = queue.PriorityQueue()
    d[u] = 0
    q.put((0, u))

    while not q.empty():
        # w[0] -> coste
        # w[1] -> indice vertice
        w = q.get()
        if v[w[1]] == False:
            v[w[1]] = True
            # por cada vertice z adyacente a w
            for z in dG[w[1]]:
                # z[0] == coste(d[w[1]], z)
                # z[1] == indice vertice
                if d[z[1]] > d[w[1]] + z[0]:
                    d[z[1]] = d[w[1]] + z[0]
                    p[z[1]] = w[1]
                    q.put(z)

    return p, d


def dijkstraM(mG, u):
    size = len(mG)
    v = []
    p = []
    d = []

    for x in range(size):
        v.append(False)
        p.append(None)
        d.append(99999)

    q = queue.PriorityQueue()
    d[u] = 0
    q.put((0, u))

    while not q.empty():
        # w[0] -> coste

        # w[1] -> indice vertice
        w = q.get()
        if v[w[1]] == False:
            v[w[1]] = True
            i = 0
            # por cada vertice z adyacente a w
            for z in mG[w[1]]:
                if z != 0 and d[i] > d[w[1]] + z:
                    d[i] = d[w[1]] + z
                    p[i] = w[1]
                    q.put((z, i))
                i += 1  # indice del vertice explorando

    return p, d


def timeDijkstraM(nGraphs, nNodesIni, nNodesFin, step,sparseFactor =0.25):
    timelist = []

    for i in range(nNodesIni, nNodesFin, step):
        time3 =0
        for x in range(nGraphs):
            
            # print("permutacion" + repr(i) + " "+  repr(x))
            m=randMatrPosWGraph(i,sparseFactor)
            time1 = time.clock()
            dijkstraM(m,0)
            time2 = time.clock()
            time3 += time2 - time1
        timelist.append(time3/ nGraphs)
    return timelist

def timeDijkstraD(nGraphs, nNodesIni, nNodesFin, step,sparseFactor =0.25):
    timelist = []

    for i in range(nNodesIni, nNodesFin, step):
        time3=0
        for x in range(nGraphs):
            # print("permutacion" + repr(i) + " "+  repr(x))
            d = fromAdjM2Dict(randMatrPosWGraph(i,sparseFactor))
            time1 = time.clock()
            dijkstraD(d,0)
            time2 = time.clock()
            time3 += time2 - time1
        timelist.append(time3/ nGraphs)
    return timelist

# Metodos cutres, seria interesante contrastar su eficiencia

def dijkstraMCutre(mG, u):
    return dijkstraD(fromAdjM2Dict(mG), u)


def dijkstraDCutre(dG, u):
    return dijkstraM(fromDict2AdjM(dG), u)

def fitPlotDij(nGraphs, nNodesIni, nNodesFin, step,sparseFactor =0.25):
    times1 =timeDijkstraM(nGraphs, nNodesIni, nNodesFin, step,sparseFactor)

    times2 =timeDijkstraD(nGraphs, nNodesIni, nNodesFin, step,sparseFactor)

    x = []
    for z in range(nNodesIni, nNodesFin, step):
        x.append(z)

    ydata1 = np.polyfit(x, times1, 2)
    f1 = np.poly1d(ydata1)
    yfit1 = np.linspace(nNodesIni, nNodesFin,nNodesFin - nNodesIni / step)

    ydata2 = np.polyfit(x, times2, 2)
    f2 = np.poly1d(ydata2)
    yfit2 = np.linspace(nNodesIni, nNodesFin,nNodesFin - nNodesIni / step)

    plt.plot(x,times1,"b.", label="valor real M")
    plt.plot(yfit1, f1(yfit1),"r-", label='matriz')
    plt.plot(x,times2, "r.", label="valor real D")
    plt.plot(yfit2,f2(yfit2),"b-", label ='diccionario')
    plt.legend(bbox_to_anchor=(0., 1.02, 1., .102), loc=3,ncol=2, mode="expand", borderaxespad=0.)
    plt.show()

    return


#Dijkstra vs Floyd-Warshall

def dijkstraMAllPairs(mG):

    size = len(mG)
    mat = np.zeros(shape=(size, size))

    for x in range (size):
        mat[x] = dijkstraM(mG, x)[1]
    return mat


def floydWarshall(mG):

    N = len(mG)
    d = copy.copy(mG)

    for k in range(N):
        for i in range(N):
            for j in range(N):
                if d[i][j] > d[i][k] + d[k][j]:
                    d[i][j] = d[i][k] + d[k][j]

    return d

def timeDijkstraMAllPairs(nGraphs, nNodesIni, nNodesFin, step, sparseFactor):

    timelist = []
    for i in range (nNodesIni, nNodesFin, step):
        time3 = 0
        for x in range(nGraphs):

            m = randMatrPosWGraph(i, sparseFactor)
            time1 = time.clock()
            dijkstraMAllPairs(m)
            time2 = time.clock()
            time3 += time2 - time1
        timelist.append(time3/nGraphs)

    return timelist

def timeFloydWarshall(nGraphs, nNodesIni, nNodesFin, step, sparseFactor):

    timelist = []
    for i in range (nNodesIni, nNodesFin, step):
        time3 = 0
        for x in range(nGraphs):

            m = randMatrPosWGraph(i, sparseFactor)
            time1 = time.clock()
            floydWarshall(m)
            time2 = time.clock()
            time3 += time2 - time1
        timelist.append(time3/nGraphs)

    return timelist

def fitPlotFW(nGraphs, nNodesIni, nNodesFin, step,sparseFactor =0.25):

    times1 = timeDijkstraMAllPairs(nGraphs, nNodesIni, nNodesFin, step, sparseFactor)
    times2 = timeFloydWarshall(nGraphs, nNodesIni, nNodesFin, step, sparseFactor)

    x = []
    for z in range(nNodesIni, nNodesFin, step):
        x.append(z)

    ydata1 = np.polyfit(x, times1, 2)
    f1 = np.poly1d(ydata1)
    yfit1 = np.linspace(nNodesIni, nNodesFin,nNodesFin - nNodesIni / step)

    ydata2 = np.polyfit(x, times2, 2)
    f2 = np.poly1d(ydata2)
    yfit2 = np.linspace(nNodesIni, nNodesFin,nNodesFin - nNodesIni / step)

    plt.plot(x,times1,"b.", label="valor real Dijkstra")
    plt.plot(yfit1, f1(yfit1),"r-", label='Dijkstra')
    plt.plot(x,times2, "r.", label="valor real FloydWarshall")
    plt.plot(yfit2,f2(yfit2),"b-", label ='FloydWarshall')
    plt.legend(bbox_to_anchor=(0., 1.02, 1., .102), loc=3,ncol=2, mode="expand", borderaxespad=0.)
    plt.show()

    return


#Trivial Graph Format


def dG2TGF(dG, fName):

    file = open(fName, "w")
    edges = ""

    for x in dG:
        file.write(str(x) + '\n')
        for value in dG[x]:
            edges += str(x) + ' ' + str(value[1]) + ' ' + str(value[0]) + '\n'

    file.write('#\n' + edges)

    #for x in dG:
    #    for value in dG[x]:
    #        file.write(str(x) + ' ' + str(value[1]) + ' ' + str(value[0]) + '\n')
    return


def TGF2dG(fName):
    dict = {}
    file = open(fName, "r")
    line = file.readline()[:-1]
    #Leer los vertices hasta encontrar '#'

    while line != '#':
        dict.update({int(line): []})
        print(line)
        line = file.readline()[:-1]

    #Leer la parte de las aristas y los pesos
    for line in file:
        #Dividir la linea en las 3 componentes
        parts = line.split()
        start = int(parts[0])
        end = int(parts[1])
        weight = float(parts[2])

        dict[start].append((weight, end))

    return dict




fName = sys.argv[1]

dG= TGF2dG(fName)
mG = fromDict2AdjM(dG)

dijk = dijkstraMAllPair(mG)
FW = floydWarshall(mG)


if len(dijk)/2 >8:
    for x in range (0,7,1):
        list = []
        for y in range (0,7,1):
            list.append(dijk[x][j])
        print list
else:
    for x in range(0,(dijk)/2,1):
        list = []
        for y in range (0,(dijk)/2,1):
            list.append(dijk[x][j])
        print list
 
if len(FW)/2 >8:
    for x in range (0,7,1):
        list = []
        for y in range (0,7,1):
            list.append(FW[x][j])
        print list
        
else:
    for x in range(0,(FW)/2,1):
        list = []
        for y in range (0,(FW)/2,1):
            list.append(FW[x][j])
        print list
        