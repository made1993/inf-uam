import Queue as Queue
import random
import time
import copy
import numpy as np
import matplotlib.pyplot as plt


# TODO: REVISAR BP, TEMA DE INFINITOS, ETC.
#       ACABAR ALGUNOS DOCSTRINGS
#       TIEMPO DE EJECUCION DE KRUSKAL (GRAFICAS)
#       PREGUNTAR A DORRONSORO POR PARAMETROS DE BP
# Grafos practica 1

def randMatrPosWGraph(nNodes, sparseFactor, maxWeight=50.):
    matrix = np.empty(shape=(nNodes, nNodes))
    matrix[:] = np.inf

    for i in range(nNodes):
        for j in range(nNodes):
            if i == j:
                matrix[i, i] = 0
            else:
                if random.random() <= sparseFactor:
                    matrix[i, j] = random.randint(0, maxWeight)
    return matrix


def cuentaRamas(mG):
    i = 0
    for x in range(len(mG)):
        for y in range(len(mG[0])):
            if mG[x][y] != np.inf and x != y:
                i += 1
    return i


def fromAdjM2Dict(mG):
    dict = {}
    i = 0
    for fila in mG:
        j = 0
        list = []

        for columna in fila:
            if columna != np.inf and i != j:
                list.append((j, columna))
            j += 1
        dict.update({i: list})
        i += 1
    return dict


def fromDict2AdjM(dG):
    size = len(dG)
    mat = np.zeros(shape=(size, size))
    j = 0
    for elem in dG:
        list = []
        i = 0
        for x in dG[elem]:

            while i <= x[0]:
                if x[0] == i:
                    list.append(x[1])
                else:
                    list.append(np.inf)
                i += 1

        while (len(list) < size):
            list.append(np.inf)
        mat[j] = np.matrix(list)
        mat[j][j] = 0;
        j += 1

    return mat


def dijkstraD(dG, u):
    size = len(dG)
    v = [False] * size
    p = [None] * size
    d = [np.inf] * size

    q = Queue.PriorityQueue()
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
                # z[0] == indice vertice
                # z[1] == coste(d[w[1]], z)
                if d[z[0]] > d[w[1]] + z[1]:
                    d[z[0]] = d[w[1]] + z[1]
                    p[z[0]] = w[1]
                    q.put((d[z[0]], z[0]))

    return p, d


def dijkstraM(mG, u):
    size = len(mG)
    v = [False] * size
    p = [None] * size
    d = [np.inf] * size

    q = Queue.PriorityQueue()
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

                if d[i] > d[w[1]] + z:
                    d[i] = d[w[1]] + z
                    p[i] = w[1]
                    q.put((d[i], i))
                i += 1  # indice del vertice explorando

    return p, d


def timeDijkstraM(nGraphs, nNodesIni, nNodesFin, step, sparseFactor=0.25):
    timelist = []

    for i in range(nNodesIni, nNodesFin, step):
        time3 = 0
        for x in range(nGraphs):
            # print("permutacion" + repr(i) + " "+  repr(x))
            m = randMatrPosWGraph(i, sparseFactor)
            time1 = time.clock()
            dijkstraM(m, 0)
            time2 = time.clock()
            time3 += time2 - time1
        timelist.append(time3 / nGraphs)
    return timelist


def timeDijkstraD(nGraphs, nNodesIni, nNodesFin, step, sparseFactor=0.25):
    timelist = []

    for i in range(nNodesIni, nNodesFin, step):
        time3 = 0
        for x in range(nGraphs):
            # print("permutacion" + repr(i) + " "+  repr(x))
            d = fromAdjM2Dict(randMatrPosWGraph(i, sparseFactor))
            time1 = time.clock()
            dijkstraD(d, 0)
            time2 = time.clock()
            time3 += time2 - time1
        timelist.append(time3 / nGraphs)
    return timelist


# Metodos cutres, seria interesante contrastar su eficiencia

def dijkstraMCutre(mG, u):
    return dijkstraD(fromAdjM2Dict(mG), u)


def dijkstraDCutre(dG, u):
    return dijkstraM(fromDict2AdjM(dG), u)


def fitPlotDij(nGraphs, nNodesIni, nNodesFin, step, sparseFactor=0.25):
    times1 = timeDijkstraM(nGraphs, nNodesIni, nNodesFin, step, sparseFactor)

    times2 = timeDijkstraD(nGraphs, nNodesIni, nNodesFin, step, sparseFactor)

    x = []
    for z in range(nNodesIni, nNodesFin, step):
        x.append(z)

    ydata1 = np.polyfit(x, times1, 2)
    f1 = np.poly1d(ydata1)
    yfit1 = np.linspace(nNodesIni, nNodesFin, nNodesFin - nNodesIni / step)

    ydata2 = np.polyfit(x, times2, 2)
    f2 = np.poly1d(ydata2)
    yfit2 = np.linspace(nNodesIni, nNodesFin, nNodesFin - nNodesIni / step)

    plt.plot(x, times1, "b.", label="valor real M")
    plt.plot(yfit1, f1(yfit1), "r-", label='matriz')
    plt.plot(x, times2, "r.", label="valor real D")
    plt.plot(yfit2, f2(yfit2), "b-", label='diccionario')
    plt.legend(bbox_to_anchor=(0., 1.02, 1., .102), loc=3, ncol=2, mode="expand", borderaxespad=0.)
    plt.show()

    return


# Dijkstra vs Floyd-Warshall

def dijkstraMAllPairs(mG):
    size = len(mG)
    mat = np.zeros(shape=(size, size))
    for x in range(size):
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
    for i in range(nNodesIni, nNodesFin, step):
        time3 = 0
        for x in range(nGraphs):
            m = randMatrPosWGraph(i, sparseFactor)
            time1 = time.clock()
            dijkstraMAllPairs(m)
            time2 = time.clock()
            time3 += time2 - time1
        timelist.append(time3 / nGraphs)

    return timelist


def timeFloydWarshall(nGraphs, nNodesIni, nNodesFin, step, sparseFactor):
    timelist = []
    for i in range(nNodesIni, nNodesFin, step):
        time3 = 0
        for x in range(nGraphs):
            m = randMatrPosWGraph(i, sparseFactor)
            time1 = time.clock()
            floydWarshall(m)
            time2 = time.clock()
            time3 += time2 - time1
        timelist.append(time3 / nGraphs)

    return timelist


def fitPlotFW(nGraphs, nNodesIni, nNodesFin, step, sparseFactor=0.25):
    times1 = timeDijkstraMAllPairs(nGraphs, nNodesIni, nNodesFin, step, sparseFactor)
    times2 = timeFloydWarshall(nGraphs, nNodesIni, nNodesFin, step, sparseFactor)

    print ("tiempios dijkstraMAllPairs")
    print (times1)
    print("tiempos FloydWarshall")
    print (times2)
    x = []
    for z in range(nNodesIni, nNodesFin, step):
        x.append(z)

    ydata1 = np.polyfit(x, times1, 2)
    print ("DJ time")
    print (ydata1)
    f1 = np.poly1d(ydata1)
    yfit1 = np.linspace(nNodesIni, nNodesFin, nNodesFin - nNodesIni / step)

    ydata2 = np.polyfit(x, times2, 3)
    print ("Fw time")
    print (ydata2)
    f2 = np.poly1d(ydata2)
    yfit2 = np.linspace(nNodesIni, nNodesFin, nNodesFin - nNodesIni / step)

    plt.plot(x, times1, "b.", label="valor real Dijkstra")
    plt.plot(yfit1, f1(yfit1), "r-", label='Dijkstra')
    plt.plot(x, times2, "r.", label="valor real FloydWarshall")
    plt.plot(yfit2, f2(yfit2), "b-", label='FloydWarshall')
    plt.legend(bbox_to_anchor=(0., 1.02, 1., .102), loc=3, ncol=2, mode="expand", borderaxespad=0.)
    plt.show()

    return


# Trivial Graph Format


def dG2TGF(dG, fName):
    file = open(fName, "w")
    edges = ""

    for x in dG:
        file.write(str(x) + '\n')
        for value in dG[x]:
            edges += str(x) + ' ' + str(value[0]) + ' ' + str(value[1]) + '\n'

    file.write('#\n' + edges)
    return


def TGF2dG(fName):
    dict = {}
    file = open(fName, "r")
    line = file.readline()[:-1]
    # Leer los vertices hasta encontrar '#'

    while line[0] != '#':
        dict.update({int(line): []})
        line = file.readline()[:-1]

    # Leer la parte de las aristas y los pesos
    for line in file:
        # Dividir la linea en las 3 componentes
        parts = line.split()
        start = int(parts[0])
        end = int(parts[1])
        weight = float(parts[2])

        dict[start].append((end, weight))

    return dict


# Practica 2
# Grafos no dirigidos y TAD conjunto disjunto


def randMatrUndPosWGraph(nNodes, sparseFactor, maxWeight=50.):
    """
    Genera una matriz de adyacencia de un grafo no dirigido ponderado.
    :param nNodes: Numero de nodos del grafo
    :param sparseFactor: Proporcion de ramas. (0 - 1)
    :param maxWeight: Maximo peso de una arista, por defecto, 50.
    :return: Matriz de adyacencia
    """
    matrix = np.empty(shape=(nNodes, nNodes))
    matrix[:] = np.inf

    for i in range(nNodes):
        for j in range(i, nNodes):
            if i == j:
                matrix[i, i] = 0
            else:
                if random.random() <= sparseFactor:
                    matrix[i, j] = matrix[j, i] = random.randint(0, maxWeight)
    return matrix


# Escribir una funcion checkUndirectedM(mG) que devuelva True o False
# segun la matriz  mG corresponda o no a un grafo no dirigido. Pista: esto es
# soabre too una excusa para explorar indexing en numpy asi como masked
# arrays.
# TODO: Esto explota un poco
def checkUndirectedM(mG):
    l = np.tril(mG, -1)
    u = np.triu(mG, 1)
    transp = np.transpose(l)
    return (u == transp).sum() == u.size
    # return np.transpose(np.tril(mG, -1)).all() == np.triu(mG,1)


def checkUndirectedD(dG):
    """
    Comprueba si el grafo dG es no dirigido.
    :param dG: Grafo como diccionario
    :return: True si es un grafo no dirigido, False, si es dirigido.
    """
    for origen, destinos in dG.iteritems():
        for destino in destinos:
            encontradaPareja = False
            # Buscar si la arista origen - (destino, coste) tiene una inversa desde destino hasta origen
            for arista in dG[destino[0]]:
                # Si la arista va al origen con el mismo coste, es una arista no dirigida
                if arista[0] == origen and arista[1] == destino[1]:
                    encontradaPareja = True
                    break
            # Si no se ha encontrado la arista adecuada, es grafo dirigido
            if not encontradaPareja:
                return False

    return True


def initCD(N):
    """
    Inicializa un conjunto disjunto a modo de lista de tamano n con valores -1
    :param N: Tamano del conjunto disjunto
    :return: Lista con N -1's
    """
    return [-1 for i in range(N)]


def union(rep1, rep2, pS):
    """
    Realiza la union por rangos entre rep1 y rep2 en el conjunto disjunto pS
    :param rep1: representante 1
    :param rep2: representante 2
    :param pS: conjunto disjunto
    :return: representante de la union segun union por rangos
    """
    if pS[rep2] < pS[rep1]:
        pS[rep1] = rep2  # Arbol 2 mas alto
        return rep2
    elif pS[rep2] > pS[rep1]:
        pS[rep2] = rep1  # Arbol 1 mas alto
        return rep1
    else:
        pS[rep2] = rep1
        pS[rep1] -= 1
        return rep1


def find(ind, pS, flagCC):
    """
    Realiza la operacion find para el conjunto disjunto pS
    :param ind: indece que buscar en el CD
    :param pS: Conjunto Disjunto
    :param flagCC: Valor booleano que indica si hacer compresion de caminos
    :return: Representante del indice ind en pS
    """
    z = ind
    while pS[z] >= 0:
        z = pS[z]
    if flagCC:
        while pS[ind] >= 0:
            y = pS[ind]
            pS[ind] = z
            ind = y
    return z


def insertPQMatriz(mG, Q):
    """
    Realiza la insercion de las ramas en la cola de prioridad de Kruskal
    :param mG: grafo como matriz de adyacencia
    :param Q: Cola de prioridad a la que insertar las ramas
    :return: cola de prioridad Q
    """
    size = len(mG)
    for i in range(size):
        for j in range(i, size):
            if mG[i, j] != 0 and mG[i, j] != np.inf:
                Q.put((mG[i, j], (i, j)))  # Formato: (coste, (x, y))
    return Q


def insertPQ(dG, Q):
    """
    Realiza la insercion de las ramas en la cola de prioridad de Kruskal
    :param dG: grafo como diccionario
    :param Q: Cola de prioridad a la que insertar las ramas
    :return: cola de prioridad Q
    """
    for origen, destinos in dG.iteritems():
        for destino in destinos:
            cost = destino[1]
            tupla = (origen, destino[0])
            if origen < destino[1]:
                Q.put((cost, tupla))

    return Q


def kruskal(dG, flagCC=True):
    """
    Aplica el algoritmo de Kruskal sobre el grafo dG
    :param dG: grafo como diccionario
    :param flagCC: Realizar o no compresion de caminos
    :return: Arbol abarcador minimo resultante del algoritmo
    """
    ListaAristas = []
    ConjDisj = initCD(len(dG))
    q = Queue.PriorityQueue()
    insertPQ(dG, q)

    while not q.empty():
        elem = q.get()  # Elem = (coste, (x,y))
        x = find(elem[1][0], ConjDisj, flagCC)
        y = find(elem[1][1], ConjDisj, flagCC)
        if x != y:
            # addLL()
            ListaAristas.append(elem)
            union(x, y, ConjDisj)
    return ListaAristas


def kruskal2(dG, flagCC=True):
    """
    Aplica el algoritmo de Kruskal sobre el grafo dG
    :param dG: grafo como diccionario
    :param flagCC: Realizar o no compresion de caminos
    :return: Arbol abarcador minimo resultante del algoritmo
    """
    ListaAristas = []
    ConjDisj = initCD(len(dG))
    q = Queue.PriorityQueue()
    insertPQ(dG, q)

    time1 = time.clock()
    while not q.empty():
        elem = q.get()  # Elem = (coste, (x,y))
        x = find(elem[1][0], ConjDisj, flagCC)
        y = find(elem[1][1], ConjDisj, flagCC)
        if x != y:
            # addLL()
            ListaAristas.append(elem)
            union(x, y, ConjDisj)

    time2 = time.clock()
    return ListaAristas, time2 - time1


# Vamos a comparar el rendimiento del algoritmo de Kruskal de acuerdo a diferentes
# variantes en su implementacion, trabajando con grafos bastante densos
# en el sentido de que se han generado con valores de sparseFactor cercanos a 1,
# de tal manera de que tengan una probabilidad alta de ser conexos.
# Escribir para ello las siguientes funciones:

# timeKruskal(nGraphs, nNodesIni, nNodesFin, step, sparseFactor,
# flagCC) que genera grafos no dirigidos aleatorios y devuelve una lista con
# los tiempos de ejecucion correspondientes a cada numero de nodos entre
# nNodesIni, nNodesFin.
def timeKruskal(nGraphs, nNodesIni, nNodesFin, step, sparseFactor, flagCC=True):
    timelist = []
    for i in range(nNodesIni, nNodesFin, step):
        time3 = 0
        for x in range(nGraphs):
            m = randMatrUndPosWGraph(i, sparseFactor)
            dg = fromAdjM2Dict(m)
            time1 = time.clock()
            kruskal(dg, flagCC)
            time2 = time.clock()
            time3 += time2 - time1
        timelist.append(time3 / nGraphs)

    return timelist


def timeKruskal02(nGraphs, nNodesIni, nNodesFin, step, sparseFactor, flagCC=True):
    timelist = []
    for i in range(nNodesIni, nNodesFin, step):
        time = 0
        for x in range(nGraphs):
            m = randMatrUndPosWGraph(i, sparseFactor)
            dg = fromAdjM2Dict(m)
            time += kruskal2(dg, flagCC)[1]
        timelist.append(time / nGraphs)

    return timelist


def fitPlotKruskal(nGraphs, nNodesIni, nNodesFin, step, sparseFactor, flagCC=True):
    timesK1 = timeKruskal(nGraphs, nNodesIni, nNodesFin, step, sparseFactor, flagCC=True)
    timesK2 = timeKruskal02(nGraphs, nNodesIni, nNodesFin, step, sparseFactor, flagCC=True)

    x = []
    for z in range(nNodesIni, nNodesFin, step):
        x.append(z)

    ydata1 = np.polyfit(x, timesK1, 3)
    print ("kruskal time")
    print (ydata1)
    f1 = np.poly1d(ydata1)
    yfit1 = np.linspace(nNodesIni, nNodesFin, nNodesFin - nNodesIni / step)

    ydata2 = np.polyfit(x, timesK2, 3)
    print ("kruskal time")
    print (ydata2)
    f2 = np.poly1d(ydata2)
    yfit2 = np.linspace(nNodesIni, nNodesFin, nNodesFin - nNodesIni / step)

    plt.plot(x, timesK1, "b.", label="valor real kruskal")
    plt.plot(yfit1, f1(yfit1), "r-", label='kruskal')
    plt.plot(x, timesK2, "r.", label="valor real kruskal2")
    plt.plot(yfit2, f2(yfit2), "b-", label='kruskal2')
    plt.legend(bbox_to_anchor=(0., 1.02, 1., .102), loc=3, ncol=2, mode="expand", borderaxespad=0.)
    plt.show()

    return


# fitPlotKruskal(5, 2,300,2,0.25)

# TODO
# El tiempo de ejecucion de Kruskal esta dominado por la insercion en la cola
# de prioridad. Modificar la funcion anterior para que mida los tiempos de
# ejecucion del bucle de construccion del arbol abarcador.
# Ajustar un modelo lineal a los tiempos teoricos y reales de ejecucion de las
# funciones anteriores y dibujar las correspondientes graficas.

# BP, OT y DM's

def incAdy(dG):
    """
    Calcula las tablas de incidencia y adyacencia para los nodos de dG
    :param dG: Grafo como diccionario
    :return: ([tabla de incidencia],[tabla de adyacencia])
    """
    size = len(dG)
    inc = [0] * size
    ady = [0] * size
    for origen, destinos in dG.iteritems():
        for destino in destinos:
            inc[destino[0]] += 1
            ady[origen] += 1
    return inc, ady


def drBP(dG, u = 0):
    """
    Driver que ejecuta busqueda en profundidad
    :param dG: Grafo como diccionario
    :param u: Nodo por el que comenzar la busqueda. 0 por defecto.
    :return: ([descubrimiento][finalizacion][previos])
    """
    size = len(dG)
    p = [None] * size
    d = [np.inf] * size
    f = [np.inf] * size
    n = 0
    # Primera llamada para empezar en el vertice deseado
    n = BP(u, dG, d, f, p, n)
    for i in dG:
        if d[i] == np.inf:
            # Siguientes llamadas para reiniciar en otros vertices
            n = BP(i, dG, d, f, p, n)
    return d, f, p


def BP(u, dG, d, f, p, n):
    """
    Algoritmo de busqueda en profundidad llamado desde el driver
    :param u: Nodo actual
    :param dG: Grafo como diccionario
    :param d: tabla de descubrimientos
    :param f: tabla de finalizaciones
    :param p: tabla de previos
    :param n: contador con el paso actual para calcular d y f
    :return: n con el paso actual
    """
    n += 1
    d[u] = n

    for v in dG[u]:
        if (d[v[0]] == np.inf):
            p[v[0]] = u
            n = BP(v[0], dG, d, f, p, n)
    n += 1
    f[u] = n

    return n


def tRama(u, v, d, f, p):
    """
    Funcion que comprueba el tipo de rama que es u-v. Se ejecuta durante busqueda en profundidad
    con los valores de d, f, p correspondientes
    :param u: vertice 1
    :param v: vertice 2
    :param d: tabla descubrimiento
    :param f: tabla finalizacion
    :param p: tabla previos
    :return: 1: TREE 2: DESCENDENTE 3:ASCENDENTE 4:CICLO
    """
    if p[v] == u:
        return 1  # TREE
    elif p[v] != u:
        if d[u] < d[v] and f[u] == np.inf:
            return 2  # DESCENDENTE
        elif d[v] < d[u] and f[v] == np.inf:
            return 3  # ASCENDENTE
    return 4  # CICLO/CRUCE


def BPasc(u, dG, d, f, p, a, n):
    """
    Algoritmo de busqueda en profundidad con deteccion de ascending edges llamado desde el driver
    :param u: Nodo actual
    :param dG: Grafo como diccionario
    :param d: tabla de descubrimientos
    :param f: tabla de finalizaciones
    :param p: tabla de previos
    :param a: tabla de ascending edges
    :param n: contador con el paso actual para calcular d y f
    :return: n con el paso actual
    """
    n += 1
    d[u] = n

    for v in dG[u]:
        if (d[v[0]] == np.inf):
            p[v[0]] = u
            n = BPasc(v[0], dG, d, f, p, a, n)
        elif tRama(u, v[0], d, f, p) == 3:
            a.append((u, v[0]))

    n += 1
    f[u] = n

    return n


def drBPasc(dG, u = 0):
    """
    Driver para comenzar BP con deteccion de ascending edges.
    :param dG: Grafo como diccionario
    :param u: Nodo por el que comenzar la busqueda. 0 por defecto.
    :return: ([descubrimiento][finalizacion][previos][ascending edges])
    """
    size = len(dG)
    p = [None] * size
    d = [np.inf] * size
    f = [np.inf] * size
    a = []
    n = 0
    # Primera llamada para empezar en el vertice deseado
    n = BPasc(u, dG, d, f, p, a, n)
    for i in dG:
        if d[i] == np.inf:
            # Siguientes llamadas para reiniciar en otros vertices
            n = BPasc(i, dG, d, f, p, a, n)
    return d, f, p, a


def DAG(dG):
    """
    Funcion que comprueba si un grafo tiene ciclos, un grafo es aciclico si no tiene ascending edges
    :param dG:grafo
    :return:True si es DAG(No tiene ciclos), False si no es DAG, no tiene ciclos.
    """
    return drBPasc(dG)[3] == []


def OT(dG):
    """
    Funcion que devuelve la ordenacion por orden topologico de dG
    :param dG: grafo
    :return: Lista con el orden topologico del grafo o lista vacia si no es DAG
    """
    ot = []
    if DAG(dG):
        listaInc = incAdy(dG)[0]
        for inc in listaInc:
            if inc == 0:
                # Realiza busqueda en profundidad sobre el primer nodo con incidencia 0
                f = drBPasc(dG, listaInc.index(inc))[1]

                for x in f:
                    # Inserta los indices de mayor a menor finalizacion en ot
                    maxindex = f.index(max(f))
                    ot.append(maxindex)
                    f[maxindex] = np.NINF
                break
    return ot


def distMinSingleSourceDAG(dG):
    """
    Compruaba que dG es un DAG con una unica fuente y computa las distancias minimas a los demas nodos
    :param dG: Grafo
    :return: Tupla con lista con las distancias minimas y tabla de previos.
             Si tiene mas de una fuente o no es DAG, devuelve ([np.inf] * n, [None] * n)
    """
    d = [np.inf] * len(dG)
    p = [None] * len(dG)
    sources = 0
    if DAG(dG):

        # Si tiene mas de 2 fuentes, esto es, 2 vertices con incidencia 0, return
        listaInc = incAdy(dG)[0]
        for inc in listaInc:
            if inc == 0:
                sources += 1
        if sources == 1:
            ot = OT(dG)
            d[ot[0]] = 0.0

            # Buscar caminos segun el orden topologico
            for origen in ot:
                for destino in dG:

                    # Si hay camino directo origen-destino
                    for vertice in dG[origen]:
                        if vertice[0] == destino:

                            coste = vertice[1]  # Cambiar
                            # Coste llegar al destino actualmente > coste llegar al origen + coste(origen, destino)
                            if d[destino] > d[origen] + coste:
                                d[destino] = d[origen] + coste
                                p[destino] = origen

    return d, p

