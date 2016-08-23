import numpy as np
import random
import Queue as Queue


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

        dict[start].append((weight, end))

    return dict


def fromAdjM2Dict(mG):
    dict = {}
    i = 0
    for fila in mG:
        j = 0
        list = []

        for columna in fila:
            if columna != float('inf') and i != j:
                list.append((columna, j))
            j += 1
        dict.update({i: list})
        i += 1
    return dict


# Grafos no dirigidos y TAD conjunto disjunto


def randMatrUndPosWGraph(nNodes, sparseFactor, maxWeight=50.):
    matrix = np.empty(shape=(nNodes, nNodes))
    matrix[:] = float('inf')

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
def checkUndirectedM(mG):
    l = np.tril(mG, -1)
    u = np.triu(mG, 1)
    transp = np.transpose(l)
    return (u == transp).sum() == u.size
    # return np.transpose(np.tril(mG, -1)).all() == np.triu(mG,1)


# Escribir una funcion checkUndirectedD(dG) que devuelva True o False
# segun el diccionario  dG corresponda o no a un grafo no dirigido. Hacer dicha
# comprobacion sin recurrir a la representacion en matriz de adyacencia.
def checkUndirectedD(dG):
    return True


def initCD(N):
    return [-1 for i in range(N)]


def union(rep1, rep2, pS):
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
    size = len(mG)
    for i in range(size):
        for j in range(i, size):
            if mG[i, j] != 0 and mG[i, j] != float('inf'):
                Q.put((mG[i, j], (i, j)))  # Formato: (coste, (x, y))
    return Q


def insertPQ(dG, Q):
    for origen, destinos in dG.iteritems():
        for destino in destinos:
            cost = destino[0]
            tupla = (origen, destino[1])
            if origen < destino[1]:
                Q.put((cost, tupla))

    return Q


def kruskal(dG, flagCC=True):
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


# Vamos a comparar el rendimiento del algoritmo de Kruskal de acuerdo a diferentes
# variantes en su implementacion, trabajando con grafos bastante densos
# en el sentido de que se han generado con valores de sparseFactor cercanos a 1,
# de tal manera de que tengan una probabilidad alta de ser conexos.
# Escribir para ello las siguientes funciones:

# timeKruskal(nGraphs, nNodesIni, nNodesFin, step, sparseFactor,
# flagCC) que genera grafos no dirigidos aleatorios y devuelve una lista con
# los tiempos de ejecucion correspondientes a cada numero de nodos entre
# nNodesIni, nNodesFin.
def timeKruskal(nGraphs, nNodesIni, nNodesFin, step, sparseFactor):
    return


# TODO
# El tiempo de ejecucion de Kruskal esta dominado por la insercion en la cola
# de prioridad. Modificar la funcion anterior para que mida los tiempos de
# ejecucion del bucle de construccion del arbol abarcador.
# Ajustar un modelo lineal a los tiempos teoricos y reales de ejecucion de las
# funciones anteriores y dibujar las correspondientes graficas.

# BP, OT y DM's
# TODO Quitar de aqui, copiar lo de grafos11
def sizeDict(dG):
    max = -1
    for elem in dG:
        if elem > max:
            max = elem
        for list in dG[elem]:
            if list[1] > max:
                max = list[1]
    return max + 1


# TODO Previos?
def incAdy(dG):
    size = sizeDict(dG)
    inc = [0] * size
    ady = [0] * size
    prev = [0] * size
    for origen, destinos in dG.iteritems():
        for destino in destinos:
            inc[destino[1]] += 1
            ady[origen] += 1
    return inc, ady, prev


def drBP(dG):
    size = len(dG)
    p = [None] * size
    d = [0] * size
    f = [0] * size
    for i in dG:
        if d[i] == 0:
            BP(i, dG, d, f, p)
    return d, f, p


def BP(u, dG, d, f, p):
    d[u] = max(max(f), max(d)) + 1
    for v in dG[u]:
        if d[v[1]] == False:
            p[v[1]] = u
            d[v[1]] = True
            BP(v[1], dG, d, f, p)

    f[u] = max(max(f), max(d)) + 1

    return


def tRama(u, v, d, f, p):
    if p[v] == u:
        return 1  # TREE
    elif p[v] != u:
        if d[u] < d[v] and f[u] == np.inf:
            return 2  # DESCENDENTE
        elif d[v] < d[u] and f[v] == np.inf:
            return 3  # ASCENDENTE
    return 4  # CICLO


def BPasc(u, dG, d, f, p, a, n):
    n += 1
    d[u] = n

    for v in dG[u]:
        if (d[v[1]] == np.inf):
            p[v[1]] = u
            n = BPasc(v[1], dG, d, f, p, a, n)
        elif tRama(u, v[1], d, f, p) == 3:
            a.append((u, v[1]))

    n += 1
    f[u] = n

    return n


def drBPasc(dG, u=0):
    size = len(dG)
    p = [None] * size
    d = [np.inf] * size
    f = [np.inf] * size
    a = []
    n = 0
    # Primera llamada para empezar en el vertice deseado
    BPasc(u, dG, d, f, p, a, n)
    for i in dG:
        if d[i] == np.inf:
            # Siguientes llamadas para reiniciar en otros vertices
            n = BPasc(i, dG, d, f, p, a, n)
    return d, f, p, a


def DAG(dG):
    """
    Funcion que comprueba si un grafo tiene ciclos, un gafo es aciclico si no tiene ascending edges
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
        f = drBPasc(dG, 2)[1]
        for x in f:
            maxindex = f.index(max(f))
            ot.append(maxindex)
            f[maxindex] = np.NINF
    return ot

def distMinSingleSourceDAG(dG):
    """
    Compruaba que dG es un DAG con una unica fuente y computa las distancias minimas a los demas nodos
    :param dG: Grafo
    :return: Tupla con lista con las distancias minimas y tabla de previos
    """
    d = [np.inf] * len(dG)
    p = [None] * len(dG)

    if DAG(dG):
        ot = OT(dG)
        d[ot[0]] = 0.0
        # Buscar caminos segun el orden topologico
        for origen in ot:
            for destino in dG:

                # Si hay camino directo origen-destino
                for vertice in dG[origen]:
                    if vertice[1] == destino:

                        coste = vertice[0]  # Cambiar
                        # Coste llegar al destino actualmente > coste llegar al origen + coste(origen, destino)
                        if d[destino] > d[origen] + coste:
                            d[destino] = d[origen] + coste
                            p[destino] = origen


    return d, p


dG = TGF2dG("DAG.gf")
print dG
# print dG
cosa = drBPasc(dG, 2)
print "BP ASC"
print cosa
print "OT"
print (OT(dG))
print("DIST MIN SS DAG")
print(distMinSingleSourceDAG(dG))
