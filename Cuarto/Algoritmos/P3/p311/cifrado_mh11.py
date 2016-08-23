import random
import copy

import numpy as np


# Cifrado Merkle-Hellman

def genSuperCrec(n_terms):
    """
    Genera una sucesion supercreciente aleatoria, con un rango de incremento 10
    :param n_terms: numero de terminos de la sucesion
    :return: Lista con la sucesion supercreciente generada
    """

    randomRange = 10
    sucesion = []
    sucesion.append(random.randint(1, randomRange))

    for x in range(n_terms - 1):
        nextValue = random.randint(sum(sucesion) + 1, sum(sucesion) + randomRange)
        sucesion.append(nextValue)

    return sucesion


def multiplier(mod, mult_ini):
    """
    Encuentra un entero coprimo con el modulo mod superior a mult_ini, comprobando
    si es coprimo con el mcd.
    :param mod: modulo con el que comparar
    :param mult_ini: multiplicador minimo aceptable
    :return: numero coprimo con mod y mayor que mult_ini
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
    """
    Calcula el mcd de a y b de forma recursiva
    :param a: entero
    :param b: entero
    :return: mcd(a, b)
    """
    if b == 0:
        return a
    else:
        return mcd(b, a % b)


def euclidesExtendido(a, b):
    """
    Aplica el algoritmo de euclides extendido y devuelve 3 valores correspondientes
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
    """
    Calcula el inverso multiplicativo de p mod mod.
    :param p: numero entero
    :param mod: numero entero, actua de modulo
    :return: numero q tal que p * q = 1 mod mod
    """
    x, y, z = euclidesExtendido(p, mod)
    if x == 1 or x == -1:
        return y % mod
    else:
        print("------ERROR en inverse. ", p, " NO tiene inverso mod ", mod)
        return None


def modMultInv(lSC):
    """
    Calcula un modulo adecuado para la sucesion SC lSC y obtiene
    los parametros necesarios para el cifrado Merkle Hellman
    :param lSC: lista super creciente
    :return: multiplicador, inverso, modulo
    """
    # TODO: mismo problema que con genSuperCrec, randomRange arbitrario

    randomRange = 100
    modulo = random.randint(sum(lSC), sum(lSC) + randomRange)
    mult = multiplier(modulo, modulo / 2)
    inv = inverse(mult, modulo)
    return mult, inv, modulo


def genSucesionPublica(lSC, p, mod):
    """
    Calcula la lista publica asociada a la lista SC lSC, con multiplicador p
    y modulo mod.
    :param lSC: sucesion supercreciente
    :param p: multiplicador
    :param mod: modulo
    :return: lista de clave publica para cifrado MH
    """
    # Operador % no funciona sobre listas por defecto, aparentemente
    return [x * p % mod for x in lSC]


def lPub_2_lSC(lpub, q, mod):
    """
    Calcula la lista privada asociada a la clave publica lpub a partir
    del inverso q y el modulo mod
    :param lpub: lista a modo de clave publica
    :param q: inverso multiplicativo
    :param mod: modulo
    :return: lista supercreciente que funciona de clave privada.
    """
    return [x * q % mod for x in lpub]


# Cifrado de cadenas binarias y su descifrado


def genRandomBitString(n_bits):
    """
    Devuelve una lista de n bits aleatorios
    :param n_bits: tamano de la lista en bits
    :return: lista con la serie de bits
    """
    return np.random.randint(2, size=(n_bits))


def MH_encrypt(s, lPub, mod):
    """
    Encripta el bloque de bits s mediante la lista publcia lPub segun el
    algoritmo Merkle Hellman. Anade 0's como padding si la longitud no es
    multiplo de la longitud de lPub.
    :param s: lista con la cadena de bits que cifrar
    :param lPub: lista con la clave publica
    :return: lista con el cifrado de cada subloque de bits de tamano lPub
    """
    # Duplicar s para preservarlo
    s2 = copy.copy(s)
    s2 = np.array(s2)
    tamBits = len(s2)
    tamClave = len(lPub)
    shape = (tamBits / tamClave, tamClave)

    if tamBits % tamClave != 0:
        # Hacer padding con 0's
        tamExtra = (tamClave - (tamBits % tamClave))
        for x in range(tamExtra):
            s2 = np.append(s2, 0)
        #s2 = s2 + [0] * tamExtra
        shape = ((tamBits + tamExtra) / tamClave, tamClave)

    matrix = s2.reshape(shape)
    return np.dot(matrix, lPub)


def block_decrypt(c, l_sc, inv, mod):
    """
    Descifra un numero cifrado con MH mediante un entero C, la sucesion
    supercreciente l_sc, el inverso y el modulo.
    Este es el algoritmo de descifrado MH segun la clave privada l_sc
    :param c: texto cifrado, a modo numero entero
    :param l_sc: sucesion supercreciente, clave privada
    :param inv: inverso
    :param mod: modulo
    :return: cadena de bits correspondiente al descifrado de c
    """
    inverso = c * inv % mod
    descifrado = []
    # Esencialmente, resolver el problema de la suma para inverso con la l_sc
    for elem in reversed(l_sc):
        if inverso >= elem:
            inverso = inverso - elem
            descifrado.append(1)
        else:
            descifrado.append(0)
    # Devolver la lista invertida para que quede en el orden correcto.
    return descifrado[::-1]


def l_decrypt(l_cifra, l_sc, inv, mod):
    """
    Descifra la lista de numeros l_cifra con el algoritmo MH de descifrado
    con clave privada l_sc
    :param l_cifra: lista de numeros cifrados con MH
    :param l_sc: lista supercreciente, clave privada
    :param inv: inverso
    :param mod: modulo
    :return: cadena de bits correspondiente al descifrado de l_cifra
    """
    descifrado = []
    for elem in l_cifra:
        descifrado += block_decrypt(elem, l_sc, inv, mod)
    return descifrado
