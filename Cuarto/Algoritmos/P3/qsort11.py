import numpy as np
import random
import time

# Parte 2: QuickSelect
"""
Vamos a implementar el metodo Quickselect para la determinaci ´ on del ele- ´
mento que ocupa la posicion´ k–esima en una tabla, primero en una versi ´ on que ´
utilice como pivote el primer elemento de una tabla y luego mediante una funcion´
de pivote que asegure un coste lineal en el caso peor.
"""

"""qselect(t, p, u, k, pivotF = pivotP) que aplique el algoritmo Quickselect
para encontrar el valor del elemento que ocupar´ıa la posicion´ k en
una ordenacion de la tabla ´ t entre los ´ındices p, u, ambos inclusive.
Se observa que si bien p <= u pueden ser valores cualesquiera, k tomara´
un valor entre 0 y u-p. Esto es si k=0, se devuelve el valor del elemento
m´ınimo; si k=u-p se devuelve el maximo, y si ´ k=(u-p)/2 se devuelve
(esencialmente) la mediana.
La funcion´ pivotP que se toma como funcion pivote por omisi ´ on devuelve ´
como pivote p, esto es, el primer elemento de la tabla.


def QSel(T, P, U, k):
if k > U-P+1 or k < 1: return error
#### general case
M = split(T, P, U);
if k == M-P+1: return M
elif k < M-P+1:
return QSel(T, P, M-1, k)
else: #k > M-P+1:
return QSel(T, M+1, U, k-(M-P+1))

"""


def pivotP(t, p, u):
    return p


def split(t, p, u, pivotF=pivotP):
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


# Ojo con los indices. Mejorar para casos fuera del rango.

def qselect(t, p, u, k, pivotF=pivotP):
    if k > u - p + 1 or k < 0:
        return -1
    M = split(t, p, u, pivotF)
    if k == M - p:
        return t[M]
    elif k < M - p:
        return qselect(t, p, M - 1, k)
    else:
        return qselect(t, M + 1, u, k - (M - p + 1))

# TODO: caso < 5
# TODO: considerar no tocar la tabla al hacer esto.
# TODO: Merge sort?
# TODO: Indices extremos. Ver que va ok +- va ahora mismo, pero no se, no estoy seguro.
def pivot5_value(t, p, u):
    """que devuelve el valor del elemento de t a usar
como pivote mediante la tecnica de “mediana de 5 elementos” explicada en ´
clase."""
    tablaMedianas = []

    if u - p < 5:
        return "partition5(list, left, right)"
    # Recorrer la tabla en steps de 5
    for i in range(p, u, 5):
        subRight = i + 5
        if subRight > u:
            subRight = u
        # Ordenar la subtabla. TODO: Cambiar a Merge sort si esto
        t[i:subRight] = sorted(t[i:subRight])
        #print(t[i:subRight])
        # Obtener la mediana de la subtabla
        median5 = t[i:subRight][len(t[i:subRight]) / 2]
        tablaMedianas.append(median5)
    tablaMedianas.sort()
    return tablaMedianas[len(tablaMedianas)/2]
    # Busca la mediana entre todas las medianas de las subtablas.
    #return qselect(t, p, p + math.ceil((p - u) / 5) - 1, p + (u - p) / 10)

"""
    Metodo de la wiki. Uso una subtabla de medianas mejor.
        # swap t[median5], t[p + floor((i - p)/5)]
        temp = t[median5]
        t[median5] = t[p + math.floor((i - p) / 5)]
        t[p + math.floor((i - p) / 5)] = temp
    # Busca la mediana entre todas las medianas de las subtablas.
    return qselect(t, p, p + math.ceil((p - u) / 5) - 1, p + (u - p) / 10)
"""

#TODO: Manejo de tipos. Cuidado.
def pivot5(t, p, u):
    """que llama a la funcion anterior y devuelve la ´ posicion´ en
t del valor devuelto por pivot5 value. Sugerencia: usar la funcion´ where
de NumPy para localizar el valor del pivote en la tabla.
"""
    pivote = pivot5_value(t, p, u)
    # si es np.array:
    if type(t) == np.ndarray:
        return np.where(t == pivote)[0][0]
    #Si es lista normal
    else:
        return t.index(pivote)
    #return t.index()

def n_time_qselect_ave(nPerms, sizeIni, sizeFin, step, pivotF=pivotP):
    """
    that works for different pivot functions and uses the function
    clock() of the time library and returns a list with the average times
    needed at each step to find the median of nPers permutations of sizes that
    increase between sizeIni, sizeFin, both included, with a step step.
    """
    timelist = []
    for i in range(sizeIni, sizeFin, step):
        time3 = 0
        for j in range(nPerms):
            t=np.random.permutation(i)
            p= min(t)
            u=max(t)
            k= random.randrange(len(t))

            time1 = time.clock()
            qselect(t, p, u, k, pivotF)
            time2 = time.clock()
            time3 += time2-time1

        timelist.append(time3 / nPerms)
    return timelist


def time_qselect_worst(nPerms, sizeIni, sizeFin, step, pivotF=pivotP):
    """
that works for different pivot functions and uses the function
clock() of the time library and returns a list with the worst times
needed at each step to find the median of nPers permutations of sizes that
increase between sizeIni, sizeFin, both included, with a step step.
    """
    timelist = []
    for i in range(sizeIni, sizeFin, step):
        time3 = 0
        timeW=0
        for j in range(nPerms):
            t=np.random.permutation(i)
            p= min(t)
            u=max(t)
            k= random.randrange(len(t))

            time1 = time.clock()
            qselect(t, p, u, k, pivotF)
            time2 = time.clock()
            time3 = time2-time1
            if (time3>timeW):
                timeW=time3

        timelist.append(timeW)
    return timelist


"""
Demás cosas:
3.Run both functions with the first element pivot function and compare the
time sets obtained with the theoretical values expected.
4. Run both functions now with the median of 5 pivot and compare the time
sets obtained with the theoretical values expected.
5. Adaptar las funciones de medida de tiempo de QuickSort de la Practica 1
para medir su tiempo de ejecucion´ en el caso peor utilizando el pivote
de “mediana de 5 elementos” y comparar el creciemiento de los tiempos
obtenidos con su valor teorico.
"""
