#! /usr/bin/python

import random
import numpy.random
import string
import numpy as np
import matplotlib.pyplot as plt
import math as m
import time
import Queue
import copy
import sys


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

def countLinesFile(fname):
    list = []

    file = open(fName, "r")
    for line in file:
        list.append(line[:-1])

    file.close()
    return len(list)    

    
def permutacion(sizeP):
    return numpy.random.permutation(sizeP)


def checkPerms(numPerms, sizeP):
    matrix = np.zeros(shape=(numPerms, sizeP))

    for i in range(numPerms):
        matrix[i] = permutacion(sizeP)
    plt.hist(matrix)
    plt.show()
    return matrix

#Ejecucion:


fName = sys.argv[1]
numLines = countLinesFile(fName)
numPalyndr = countPalyndromesFile(fName)

print ("fName %s\tnum sequence: %d\tnum palyndromes: %d "% (fName, numLines , numPalyndr))
