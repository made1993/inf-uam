import random
import numpy.random
import string
import numpy as np
import matplotlib.pyplot as plt
import math as m
import time
import Queue as queue
import copy


def iniS():
    return []


def emptyS(S):
    return S == []


def push(elem, S):
    return S.append(elem)


def pop(S):
    if emptyS(S) == True:
        return False
    return S.pop()


def isPalyndrome(s):
    stack = iniS()
    # Introducir palabras en la pila
    for x in s:
        push(x, stack)
        # print(x)

    # Leer de vuelta
    for x in s:
        elem = pop(stack)
        if x != elem:
            return False
    return True


def randomString(long, strBase):
    str = ""
    for x in range(long):
        str += random.choice(strBase)

    return str


def randomPalyndrome(long, strBase):
    if long % 2:
        return randomPalyndromeImpar(long, strBase)
    else:
        return randomPalyndromePar(long, strBase)


def randomPalyndromePar(long, strBase):
    str = ""
    stack = iniS()

    for x in range(long / 2):
        str += random.choice(strBase)

    # AB
    for x in str:
        push(x, stack)

    # Leer de vuelta
    for x in str:
        elem = pop(stack)
        str += elem
    return str


def randomPalyndromeImpar(long, strBase):
    str = ""
    stack = iniS()

    for x in range(long / 2 + 1):
        str += random.choice(strBase)

    # AB
    for x in str:
        push(x, stack)
    # Pop extra
    pop(stack)
    # Leer de vuelta
    for x in str:
        elem = pop(stack)
        if elem == False:
            break
        str += elem
    return str


def generateRandomPalyndrList(numStr, longMax, probPalyndrome):
    list = []
    for x in range(numStr):
        if random.randint(0, 100) < probPalyndrome:
            list.append(randomPalyndrome(random.randint(1, longMax), string.ascii_letters))
        else:
            list.append(randomString(random.randint(1, longMax), string.ascii_letters))

    return list


def countPalyndromesList(l):
    count = 0
    for x in l:
        if isPalyndrome(x):
            count += 1

    return count


def list2file(l, fName):
    file = open(fName, "w")
    for x in l:
        file.write(x + '\n')
    file.close()
    return


def countPalyndromesFile(fName):
    list = []

    file = open(fName, "r")
    for line in file:
        list.append(line[:-1])

    file.close()
    return countPalyndromesList(list)


def permutacion(sizeP):
    return numpy.random.permutation(sizeP)


def checkPerms(numPerms, sizeP):
    matrix = np.zeros(shape=(numPerms, sizeP))

    for i in range(numPerms):
        matrix[i] = permutacion(sizeP)
    plt.hist(matrix)
    plt.show()
    return matrix


# QUICKSORT


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


# n = 2
# X = np.empty(shape=[0, n])
#
# for i in range(5):
#     for j  in range(2):
#         X = np.append(X, [[i, j]], axis=0)

# stack = iniS()
# print(stack)
# print(emptyS(stack))
# push(5, stack)
# print(stack)
#
# print(pop(stack))
# print(isPalyndrome("ABBA"))
#
# print(random.choice(string.ascii_letters))
#
# print(randomString(5, string.ascii_letters))

# lst = []
# lst.append("a");
# lst.append("b");
# lst.append("c");
# #print(randomPalyndrome(5, string.ascii_letters))
#
# lista = generateRandomPalyndrList(5, 10, 70)
# print(lista)
# print(countPalyndromesList(lista))
# list2file(lista, "cosa.txt")
# print(lista)
# print (countPalyndromesFile("cosa.txt"))

# print(permutacion(5))
# matrix = checkPerms(10000, 5)
# print(matrix)

# perm = permutacion(10)
# print(perm)
# qs(perm, 0, 10)
# print(perm)
#
# perm = permutacion(10)
# print(perm)
# qs2(perm, 0, 10)
# print(perm)

# times1 = timeSort(qs, 100, 500, 600, 10)
# print (times1)
# times2 = timeSort(qs2, 10, 5, 20, 1)


# matrix = (randMatrPosWGraph(6, 0.5, 10.))
# print(matrix)
# print(cuentaRamas(matrix))
#
# dict = fromAdjM2Dict(matrix)
# print(dict)
# print(sizeDict(dict))
# adjM = fromDict2AdjM(dict)
# print(adjM)
# print(len(dict))
# print("----dijkstraD----")
# tupla = dijkstraD(dict, 0)
# print("p = ", tupla[0])
# print("d = ", tupla[1])
# print("\n----dijkstraM----")
# tupla = dijkstraM(adjM, 0)
# print("p = ", tupla[0])
# print("d = ", tupla[1])
# print (nLognValues(1,10,1))

#
# x = nLognValues(1,10000,1)
#
# y =[]
# for z in range(1,10000,1):
#     y.append(z)
# print (y)
#
# x1=np.polyfit(x,y,3)
# f=np.poly1d(z)
#
# x_new = np.linspace(x[0],x[-1],50)
# y_new =f(x_new)
#
# plt.plot(x,y,'o',x_new,y_new)
# plt.show()

# fitPlot(qs2, 100, 200, 1)

# time1 = time.clock()
# times1= timeDijstraM(5, 100, 200, 10,0.91)
# print (times1)
# time2 = time.clock()
# print ("total time:", time2 -time1)

# time1 = time.clock()
# times2 =timeDijstraD(5, 100, 150, 1,0.91)

# print (times2)
# time2 = time.clock()
# print ("total time:", time2 -time1)




# x = []
# for z in range(100, 150, 1):
#     x.append(z)

# ydata = np.polyfit(x, times2, 2)

# print (ydata)
# f = np.poly1d(ydata)
# yfit = np.linspace(100, 150,150 - 100 / 1)

# plt.plot(yfit,f(yfit),"b-")
# plt.show()

#fitPlotDij(10, 1, 100, 1,sparseFactor =1)

# mu = randMatrPosWGraph(4, 1, maxWeight=10)
# print(mu)
#
# print(dijkstraMAllPairs(mu))
# print(floydWarshall(mu))

#print(timeDijkstraMAllPairs(10, 1, 100, 1, 0.5))
#print(timeFloydWarshall(10, 1, 100, 1, 0.5))

fitPlotFW(5, 1, 150, 2, 0.5)
#dijkstr = [5.7299999999993465e-05, 9.069999999999911e-05, 0.00021939999999999736, 0.0003475000000000006, 0.0008041999999999882, 0.0009221999999999952, 0.001493299999999992, 0.0018461000000000115, 0.0027379999999999904, 0.003272800000000009, 0.004563899999999977, 0.005085500000000009, 0.006472199999999995, 0.007216400000000023, 0.009005099999999988, 0.009736999999999996, 0.012226599999999976, 0.013121099999999974, 0.015086099999999969, 0.017309599999999925, 0.01973389999999999, 0.021813400000000073, 0.025114800000000014, 0.027312399999999924, 0.02908490000000006, 0.03325910000000003, 0.03660670000000001, 0.03952790000000004, 0.04391150000000019, 0.046460599999999894, 0.05111599999999985, 0.054913499999999796, 0.06021489999999981, 0.06393990000000001, 0.07041340000000007, 0.07305240000000009, 0.07969490000000015, 0.08438459999999992, 0.09210599999999988, 0.09491099999999975, 0.1049331000000004, 0.10736260000000027, 0.11643340000000002, 0.12231599999999929, 0.13021600000000041, 0.13865420000000092, 0.14565289999999997, 0.15438839999999984, 0.16240849999999973, 0.17142850000000037, 0.17704799999999993, 0.1886736999999993, 0.19815559999999904, 0.20680279999999912, 0.2184452999999998, 0.22567460000000067, 0.23822060000000037, 0.24672300000000078, 0.2635046999999993, 0.26996770000000014, 0.2813137999999995, 0.2954796000000002, 0.3065353999999999, 0.31914859999999906, 0.33467629999999887, 0.34415939999999806, 0.36670800000000126, 0.37457100000000165, 0.389738100000001, 0.40440289999999807, 0.41680879999999976, 0.433600899999999, 0.4509164999999996, 0.46612820000000144, 0.48290880000000413, 0.4970659000000026, 0.5162141000000006, 0.5330849000000001, 0.5560897000000011, 0.5695071999999982, 0.585989600000002, 0.6083301000000034, 0.6248486999999983, 0.6454284999999942, 0.6657004000000001, 0.6828725999999989, 0.7109253999999993, 0.7265239999999977, 0.7474649999999997, 0.7668834999999973, 0.7945808000000028, 0.8113750000000067, 0.8392246, 0.8603478999999993, 0.8844471999999968, 0.9152553999999867, 0.9405877000000032, 0.9656922999999893, 0.9830517000000043]
#fw = [9.299999993572783e-06, 2.229999999485699e-05, 4.8500000002604796e-05, 9.249999999383363e-05, 0.000194099999998798, 0.00029599999999732065, 0.00043289999999274187, 0.0006355999999925644, 0.0009133999999960451, 0.0012245999999890955, 0.0016103000000043722, 0.0021588000000008377, 0.002550500000000966, 0.003189500000001999, 0.004008199999981343, 0.004751300000003766, 0.005621700000006058, 0.006744999999995116, 0.007927000000006502, 0.009060899999991535, 0.010555000000005066, 0.012110400000000254, 0.013666899999992665, 0.015575000000012551, 0.017563899999998966, 0.019302399999992302, 0.02215280000001485, 0.02496350000000689, 0.02781219999998825, 0.030276200000002973, 0.03333750000000464, 0.03690040000000181, 0.040021999999999024, 0.043786499999987426, 0.046924999999998815, 0.05312210000000732, 0.05854129999999032, 0.062019100000003394, 0.06672209999999837, 0.07437879999999382, 0.07771629999999163, 0.08201590000000465, 0.08787900000000377, 0.09367680000000292, 0.10411049999999591, 0.1111282000000017, 0.11537719999999467, 0.12084849999999961, 0.12990819999999417, 0.13709539999999834, 0.14562109999999961, 0.1553374999999903, 0.16458410000000184, 0.17260400000000117, 0.18351910000000657, 0.19321180000000027, 0.20331759999999122, 0.2135624000000121, 0.22494709999999712, 0.23607179999998493, 0.24833489999999755, 0.2611173000000065, 0.27344280000000937, 0.2866806000000054, 0.2995531999999912, 0.3153770999999949, 0.3290551999999991, 0.34483869999999683, 0.3588771000000065, 0.37566260000000395, 0.39125629999999206, 0.40834240000000366, 0.426399600000002, 0.4409573999999907, 0.461340400000006, 0.4793427000000065, 0.49683479999999347, 0.5159945999999991, 0.5419436000000019, 0.5585599999999886, 0.5799503000000016, 0.6000355999999897, 0.6219549999999969, 0.6445676999999932, 0.6671556999999894, 0.6918998000000045, 0.7156791999999939, 0.7413416999999924, 0.7684294000000136, 0.7985018000000025, 0.8202377000000126, 0.8448548999999957, 0.8744423999999981, 0.9031112999999948, 0.9330477000000087, 0.9615316000000007, 0.9934898999999973, 0.9891869999999698, 0.9684230999999954]

matrix = (randMatrPosWGraph(6, 0.5, 10.))
dG = fromAdjM2Dict(matrix)
print(dG)
dG2TGF(dG, "grafo.txt")
dG2 = TGF2dG("grafo.txt")
print(dG2)