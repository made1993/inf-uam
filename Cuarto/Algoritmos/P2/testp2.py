#import practica2 as p
import grafos111 as g

#mG = g.randMatrPosWGraph(5, .5)
#print(mG)
#print(g.cuentaRamas(mG))
#dG = g.fromAdjM2Dict(mG)
print("start")
dG = g.TGF2dG("DAG.gf")
print(dG)
mG = g.fromDict2AdjM(dG)
print(mG)
print(g.dijkstraD(dG, 2))
print(g.dijkstraM(mG, 2))
print(g.dijkstraMAllPairs(mG))
print(g.floydWarshall(mG))
#P2
unid = g.randMatrUndPosWGraph(5, 1)
print(unid)
unidict = g.fromAdjM2Dict(unid)
#print(g.checkUndirectedM(unid))
conjdisj = g.initCD(5)
print(conjdisj)
print(unidict)
print(g.kruskal(unidict))
print(g.incAdy(dG))
print(g.OT(dG))
print("DIST MIN SS DAG")
print(g.distMinSingleSourceDAG(dG))

cosa = g.drBP(dG, 2)
print(cosa)
cosa = g.drBP(dG)
print(cosa)
#import Queue as Queue
#dG = p.randMatrUndPosWGraph(5, .6)
#dicc = g.fromAdjM2Dict(dG)
#print (dicc)
#print(dG)
##pS = p.initCD(10)
##print(pS)
#
#
##p.union(1,2,pS)
##print(pS)
##p.union(1,2,pS)
##print(pS)
##p.union(3,4,pS)
##print(pS)
##p.union(1,3,pS)
##print(pS)
#print("priority")
#Q = Queue.PriorityQueue()
#p.insertPQ(dicc, Q)
#print(Q.queue)
#LL = p.kruskal(dicc)
#print LL
#m2 = g.randMatrPosWGraph(5, .6)
#dicc2 = g.fromAdjM2Dict(m2)
#print p.incAdy(dicc2)


##Random
#import numpy as np
#import random
#def permutacion(sizeP):
#    return np.random.permutation(sizeP)
#
#def func():
#    listolists = []
#    for _ in range(10000):
#        z = permutacion(4).tolist()
#        z[random.randint(0,3)] = random.randint(0,3)
#        if z not in listolists:
#            listolists.append(z)
#    print(listolists)
#    print(len(listolists))
#
#def func2():
#    listolists = []
#    for _ in range(100000):
#        z = [1,2,3]
#        z.append(random.randint(1,3))
#        random.shuffle(z)
#        if z not in listolists:
#            listolists.append(z)
#    return listolists
#
#func()
#func2()
#[[1, 2, 3, 1], [2, 1, 3, 3], [2, 3, 1, 2], [2, 3, 1, 1], [1, 1, 2, 3], [3, 2, 3, 1], [1, 3, 2, 2], [2, 3, 1, 3], [2, 1, 3, 2], [2, 2, 1, 3], [3, 2, 1, 2], [3, 1, 3, 2], [2, 1, 2, 3], [1, 3, 3, 2], [1, 3, 2, 1], [1, 2, 2, 3], [1, 2, 1, 3], [1, 2, 3, 2], [3, 1, 2, 2], [2, 2, 3, 1], [3, 1, 2, 3], [3, 2, 1, 3], [3, 3, 2, 1], [3, 1, 1, 2], [1, 3, 1, 2], [2, 3, 2, 1], [3, 3, 1, 2], [2, 1, 1, 3], [3, 1, 2, 1], [3, 2, 1, 1], [2, 1, 3, 1], [1, 1, 3, 2], [3, 2, 2, 1], [1, 2, 3, 3], [2, 3, 3, 1], [1, 3, 2, 3]]
