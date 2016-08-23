# coding=utf-8
import random
import numpy as np
import math
import time


# Cifrado Merkle-Hellman

def genSuperCrec(n_terms):
    # TODO OJO: Rango en el que generas los numeros aleatorios. Ahora 1-10
    """Vamos a generar sucesiones supercrecientes ordenadas aleatorias con un numero n terms de terminos y
con un valor inicial aleatorio; con un valor si que supere a Pi−1 0 sj en otro entero aleatorio.
Escribir una funcion genSuperCrec(n terms) que devuelva una tal sucesion"""
    randomRange = 10
    sucesion = []
    sucesion.append(random.randint(1, randomRange))

    for x in range(n_terms - 1):
        nextValue = random.randint(sum(sucesion), sum(sucesion) + randomRange)
        sucesion.append(nextValue)

    return sucesion


def multiplier(mod, mult_ini):
    # TODO: Esta es una manera un poco cutre de hacerlo, que esta de acuerdo con el enunciado. Mejorar potencialmente.
    """
    multiplier(mod, mult ini) que devuelva un entero primo relativo con el
    modulo ´ mod y sea superior al entero mult ini (para evitar multiplicadores
    demasiado pequenos). Para ello generar n ˜ umeros aleatorios en el rango ´
    en cuestion hasta encontrar uno primo relativo con ´ mod. Comprobad esto
    mediante una funcion´ mcd(x, y) que implemente el algoritmo de Euclides
    de calculo del m ´ aximo com ´ un divisor entre ´ x, y.
    """
    nIntentos = 0
    nIntentosMax = 1000
    while nIntentos < nIntentosMax:
        posiblePrimo = random.randint(mult_ini, mod)
        if mcd(mod, posiblePrimo) == 1:
            return posiblePrimo
        nIntentos += 1

    return None


def mcd(a, b):
    if b == 0:
        return a
    else:
        return mcd(b, a % b)


def euclidesExtendido(a, b):
    """
    Aplica el algoritmo de euclides y devuelve 3 valores correspondientes
    a los coeficientes de la identidad de bezout
    :param a: numero entero
    :param b: numero entero
    :return: x, y, z tal que y * a + z * b = x. y es el inverso de a mod b.
    """
    if b == 0:
        return a, 1, 0
    else:
        x2 = 1
        x1 = 0
        y2 = 0
        y1 = 1
        while b > 0:
            q = a / b
            r = a - q * b
            x = x2 - q * x1
            y = y2 - q * y1
            a = b
            b = r
            x2 = x1
            x1 = x
            y2 = y1
            y1 = y
    return a, x2, y2


def inverse(p, mod):
    # TODO: Ojo con eso, ahora que lo pienso: comprobar que el mcd es 1 previamente.
    """
    inverse(p, mod) que devuelva el inverso de p modulo ´ mod.
    :param p:
    :param mod:
    :return:
    """
    return euclidesExtendido(p, mod)[1] % mod


def modMultInv(lSC):
    # TODO: mismo problema que con genSuperCrec, randomRange arbitrario
    """
    que calcule un modulo adecuado para la sucesi ´ on su- ´
percreciente lSC, esto es, un entero aleatorio mayor que la suma de sus
terminos, y devuelva el multiplicador, su inverso y el m ´ odulo en este orden.
    :param lSC:
    :return:
    """
    randomRange = 100
    modulo = random.randint(sum(lSC), sum(lSC) + randomRange)
    mult = multiplier(modulo, randomRange)
    inv = inverse(mult, modulo)
    return mult, inv, modulo


def genSucesionPublica(lSC, p, mod):
    """
    que devuelva la solucion publica aso- ´
ciada a la la sucesion supercreciente ´ lSC, al multiplicador p y al modulo ´
mod.
    :param lSC:
    :param p:
    :param mod:
    :return:
    """
    # Operador % no funciona sobre listas por defecto, aparentemente
    return [x * p % mod for x in lSC]


def lPub_2_lSC(lpub, q, mod):
    return [x * q % mod for x in lpub]


# Cifrado de cadenas binarias y su descifrado
"""
Queremos cifrar mediante el cifrado Merkle–Hellman (o de la mochila) una
cadena aleatoria de 0 y 1 usando una reordenacion aleatoria de la sucesi ´ on p ´ ublica ´
generada con los metodos anteriores.
"""


def genRandomBitString(n_bits):
    """que devuelva una lista de n bits aleatorios."""
    # return [0 if random.random() > 0.5 else 1 for x in range(n_bits)]
    return np.random.randint(2, size=(n_bits))


def MH_encrypt(s, lPub):
    """
    TODO: SI ESO, NO MODIFICAR S
que devuelva una lista con el cifrado de cada bloque
de la lista binaria s mediante la lista publica ´ lPub. Si es necesario, anadir ˜
ceros al final de s para que su longitud sea un multiplo de la de ´ lPub.
Sugerencia: transformar s en un array de NumPy, redimensionarlo en un
array bidimensional M × N, donde N es la longitud de lPub y obtener el
cifrado de un bloque de N bits mediante un producto escalar.

    :param s:
    :param lPub:
    :return:
    """
    tamBits = len(s)
    tamClave = len(lPub)

    if tamBits % tamClave != 0:
        # Hacer padding con 0's
        s = s + [0] * (tamClave - (tamBits % tamClave))

    matrix = np.array(s)
    matrix = matrix.reshape((tamBits / tamClave, tamClave))
    return np.dot(matrix, lPub)


def block_decrypt(c, l_sc, inv, mod):
    """
    que descifre un mensaje cifrado mediante
un entero C mediante la sucesion supercreciente ´ l sc y los enteros
inverso y modulo ´ inv, mod.
    :param c:
    :param l_sc:
    :param inv:
    :param mod:
    :return:
    """
    inverso = c * inv % mod
    descifrado = []
    for elem in reversed(l_sc):
        if inverso >= elem:
            inverso = inverso - elem
            descifrado.append(1)
        else:
            descifrado.append(0)
    return descifrado[::-1]


def l_decrypt(l_cifra, l_sc, inv, mod):
    """
    que devuelva una cadena de bits
conteniendo el descifrado de los sucesivos enteros en la lista l cifra mediante
la sucesion supercreciente ´ l sc y los enteros inverso y modulo ´ inv,
mod
    :param l_cifra:
    :param l_sc:
    :param inv:
    :param mod:
    :return:
    """
    descifrado = []
    for elem in l_cifra:
        descifrado += block_decrypt(elem, l_sc, inv, mod)
    return descifrado


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
