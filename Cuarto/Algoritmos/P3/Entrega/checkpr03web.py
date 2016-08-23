#!/usr/bin/python
# -*- coding: utf-8 -*-
"""to run on ipython:
import sys
sys.path.append(r"D:\Google Drive\Cursos\DAA\DAA 2015 2016\practicas\pract03")

%cd "D:\Google Drive\Cursos\DAA\DAA 2015 2016\practicas\pract03\entregas\p300"
%run "D:\Google Drive\Cursos\DAA\DAA 2015 2016\practicas\pract03\checkpr03.py" 

##### ejecución sobre grafos no dirigidos y dirigido ya generados
%run checkpr02.py fNameNoD fNameD    
"""

import numpy as np
import sys

##### importar archivo cifrado_mh.py 
############### old sys.path.append("D:\Google Drive\Cursos\DAA\DAA 2015 2016\practicas\pract03")
#sys.path.append(r"D:\Google Drive\Cursos\DAA\practicas\20152016")
import cifrado_mh11 as mh
import qsort11 as qs
#import cifrado_mhXX as mhp

################################################################ big main
def mainPract3(args):
    """generar sucesión supercreciente con len_sc elementos y cifrar y descifrar con ella una lista aleatoria de n_bits bits"""

        
    len_sc = 3
    n_bits = 7
    
    print("\n################################################################ generar sucesión supercreciente ...\n")   
    l_sc = mh.genSuperCrec(len_sc)
    print "lSC: ", l_sc
    
    print("\n################################################################ generar módulo, multiplicador e inverso para sucesión supercreciente dada ...\n")
    p, q, m = mh.modMultInv(l_sc)
    print ("\tmult: %d\tinv: %d\tmod: %d" % (p, q, m)) 
    
    print("\n################################################################ generar sucesión general y comprobar ...\n")
    l_pub  = mh.genSucesionPublica(l_sc, p, m)
    print "lpub:\t", l_pub
    
    print("\n################################################################ generar y cifrar mensaje binario aleatorio ... \n")
    l_bits = mh.genRandomBitString(n_bits)
    print "mensaje plano:\t", l_bits
    l_cifra = mh.MH_encrypt(l_bits, l_pub, m)
    print "mensaje cifrado:\t", l_cifra
    
    print("\n################################################################ descifrar lista de mensajes cifrados y comparar ...\n")
    l_desc = mh.l_decrypt(l_cifra, l_sc, q, m)
    
    if np.all(np.array(l_bits) == np.array(l_desc[0 : len(l_bits)] )):
        print("\tcifrado y descifrado correctos")
    else:    
        print("\terror en cifrado o descifrado  ")
    
    print("\n################################################################ comprobando qselect ...\n")

    check_qsel(n_perms=2, size_max=25, pivot_func = qs.pivot5)

################################################################ función de comprobación de qselect ..."
def check_qsel(n_perms, size_max, pivot_func = qs.pivotP):
    """Check qsel over n_perms random tables t with random size between 3 and size_max.
    Chooses randomly initial index p, final index u and position pos to search in subtable t[p : u+1].
    Checks whether qsel return val_qsel equals val_true, the value in pos of the sorted subtable."""
    for i in range(n_perms):
        size_perm = np.random.randint(max(3, size_max//2), size_max)      #3 to ensure size_perm-2 > 0
        t = np.random.permutation(size_perm)
        p = np.random.randint(0, size_perm//2) 
        u = np.random.randint(p+1, size_perm-1) 
        pos = np.random.randint(0, u-p)
        print ("tabla %d:\t" % i), t
        print "\tprimero: %d\tultimo: %d\ttamaño: %d" % (p, u, u-p+1) 
        print ("\tsubtabla %d:\t" % i), t[p : u+1]
        val_qsel = qs.qselect(t, p, u, pos, pivot_func)
        val_true = sorted(list(t[p:u+1]))[pos]
        print "\tposición entre [primero, último]: %d\tvalor qsel: %d\tvalor t ordenada: %d \t=>" % (pos, val_qsel, val_true) ,
        if val_qsel == val_true:
            print "búsqueda correcta\n"
        else:
            print "búsqueda errónea\n"

################################################################ calling main
if __name__ == '__main__':
    mainPract3(sys.argv[1 : ])    