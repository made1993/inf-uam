import numpy.random
import numpy as np
import matplotlib.pyplot as plt
import math as m
import time
import random


# QUICKSORT
# PARTE 2, QUICKSELECT EN L 100


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
    plt.plot(x, times, 'r--', x, yfit)
    plt.show()
    return


# Parte 2: QuickSelect


def pivotP(t, p, u):
    """
    Funcion que devuelve el primer elemento p como pivote
    :param t: tabla
    :param p: primer elemento
    :param u: ultimo elemento
    :return: p
    """
    return p


def split(t, p, u, pivotF=pivotP):
    """
    Funcion partir de quicksort/quickselect
    :param t: tabla
    :param p: primer elemento
    :param u: ultimo elemento
    :param pivotF: funcion pivote
    :return: p
    """
    pivot = pivotF(t, p, u)

    k = t[pivot]
    # swap(t[p], t[pivot])
    temp = t[p]
    t[p] = t[pivot]
    t[pivot] = temp

    pivot = p

    for i in range(p, u + 1):
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


def qselect(t, p, u, k, pivotF=pivotP):
    """
    Algoritmo quickselect, que encuentra el elemento que ocuparia la
    posicion k si la tabla estuviese ordenada
    :param t: tabla
    :param p: primer elemento
    :param u: ultimo elemento
    :param k: indice del elemento a buscar
    :param pivotF: funcion pivote
    :return: elemento encontrado
    """
    if k > u - p + 1 or k < 0:
        return -1
    M = split(t, p, u, pivotF)
    # print "-------quickselect", p, u, k, M, "--------"
    if k == M - p:
        return t[M]
    elif k < M - p:
        return qselect(t, p, M - 1, k, pivotF)
    else:
        return qselect(t, M + 1, u, k - (M - p + 1), pivotF)


def pivot5_value(t, p, u):
    """
    Funcion pivote que devuelve el valor del elemento de t a usar
    mediante la tecnica de mediana de 5 elementos.
    :param t: tabla
    :param p: primer elemento
    :param u: ultimo elemento
    :return: valor del pivote
    """
    tablaMedianas = []

    # Caso base para tabla < 5
    if u - p < 5:
        return sorted(t[p:u + 1])[len(t[p:u + 1]) / 2]

    # Recorrer la tabla en steps de 5
    for i in range(p, u + 1, 5):
        subRight = i + 5
        if subRight > u:
            subRight = u + 1

        # Ordenar la subtabla.
        t[i:subRight] = sorted(t[i:subRight])

        # Obtener la mediana de la subtabla
        tablaMedianas.append(t[i:subRight][len(t[i:subRight]) / 2])

    # Busca la mediana entre todas las medianas de las subtablas.
    return qselect(tablaMedianas, 0, len(tablaMedianas) - 1, len(tablaMedianas) / 2, pivot5)


def pivot5(t, p, u):
    """
    Funcion pivote que devuelve la posicion del elemento de t a usar
    mediante la tecnica de mediana de 5 elementos llamando a pivot5_value
    :param t: tabla
    :param p: primer elemento
    :param u: ultimo elemento
    :return: indice del pivote
    """

    pivote = pivot5_value(t, p, u)
    # si es np.array:
    if type(t) == np.ndarray:
        return np.where(t == pivote)[0][0]
    # Si es lista normal
    else:
        return t.index(pivote)


def time_qselect_ave(nPerms, sizeIni, sizeFin, step, pivotF=pivotP):
    """
    Funcion que mide el tiempo medio de quickselect para nPerms de sizeIni a sizeFin
    con un step para diferentes funciones pivote
    :param nPerms: numero de permutaciones a realizar para cada tamano
    :param sizeIni: tamano inicial
    :param sizeFin: tamano final
    :param step: step entre cada 2 tamanos
    :param pivotF: funcion pivote a emplear
    :return lista de tiempos empleados de media en cada paso
    """
    timelist = []
    for i in range(sizeIni, sizeFin, step):
        time3 = 0
        for j in range(nPerms):
            t = np.random.permutation(i)
            p = min(t)
            u = max(t)
            k = np.median(np.array(t))

            time1 = time.clock()
            qselect(t, p, u, k, pivotF)
            time2 = time.clock()
            time3 += time2 - time1

        timelist.append(time3 / nPerms)
    return timelist


def time_qselect_worst(nPerms, sizeIni, sizeFin, step, pivotF=pivotP):
    """
    Funcion que mide el tiempo peor de quickselect para nPerms de sizeIni a sizeFin
    con un step para diferentes funciones pivote
    :param nPerms: numero de permutaciones a realizar para cada tamano
    :param sizeIni: tamano inicial
    :param sizeFin: tamano final
    :param step: step entre cada 2 tamanos
    :param pivotF: funcion pivote a emplear
    :return lista de tiempos empleados en el caso peor para cada paso
    """
    timelist = []
    for i in range(sizeIni, sizeFin, step):
        timeW = 0
        for j in range(nPerms):
            t = np.random.permutation(i)
            p = min(t)
            u = max(t)
            k = np.median(np.array(t))

            time1 = time.clock()
            qselect(t, p, u, k, pivotF)
            time2 = time.clock()
            timeW = max(time2 - time1, timeW)

        timelist.append(timeW)
    return timelist

def timeSort_worst(sortM, nPerms, sizeIni, sizeFin, step, pivot_f=pivotP):
    """
    Mide los tiempos de ejecucion del algoritmo de ordenacion indicado,
    obteniendo una lista con los peores tiempos en cada paso
    :param sortM: puntero a funcion de ordenacion
    :param nPerms: numero de permutaciones a realizar para cada tamano
    :param sizeIni: tamano inicial
    :param sizeFin: tamano final
    :param step: step entre cada 2 tamanos
    :param pivot_f: funcion pivote a emplear
    :return: lista de tiempos empleados en el caso peor
    """
    timelist = []

    for i in range(sizeIni, sizeFin, step):
        timeW = 0
        for j in range(nPerms):
            t = permutacion(i)
            time1 = time.clock()
            sortM(t, sizeIni, sizeFin, pivot_f)
            time2 = time.clock()
            timeW = max(time2 - time1, timeW)

        timelist.append(timeW)
    return timelist


def fitPlotQselect(nPerms, sizeIni, sizeFin, step):
    """
    Funcion realiza las graficas y compara el uso de pivotP con pivot5
    :param nPerms: numero de permutaciones a realizar para cada tamano
    :param sizeIni: tamano inicial
    :param sizeFin: tamano final
    :param step: step entre cada 2 tamanos
    :return lista de tiempos empleados de media en cada paso
    """
    times1 = time_qselect_ave(nPerms, sizeIni, sizeFin, step)
    times2 = time_qselect_ave(nPerms, sizeIni, sizeFin, step, pivot5)

    x = []
    for z in range(sizeIni, sizeFin, step):
        x.append(z)

    ydata1 = np.polyfit(x, times1, 1)
    f1 = np.poly1d(ydata1)
    yfit1 = np.linspace(sizeIni, sizeFin, sizeFin - sizeIni / step)

    ydata2 = np.polyfit(x, times2, 1)
    f2 = np.poly1d(ydata2)
    yfit2 = np.linspace(sizeIni, sizeFin, sizeFin - sizeIni / step)

    plt.figure(figsize=(10, 10))
    plt.plot(x, times1, "b.", label="valor real pivotP")
    plt.plot(yfit1, f1(yfit1), "r-", label='pivotP')
    plt.plot(x, times2, "r.", label="valor real pivot5")
    plt.plot(yfit2, f2(yfit2), "b-", label='pivot5')
    plt.legend(bbox_to_anchor=(0., 1.02, 1., .102), loc=3, ncol=2, mode="expand", borderaxespad=0.)

    plt.show()

    return
