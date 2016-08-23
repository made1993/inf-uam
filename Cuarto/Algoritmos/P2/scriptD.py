# coding=utf-8
"""to run on ipython:
import sys
sys.path.append(r"D:\Google Drive\Cursos\DAA\DAA 2015 2016\practicas\pract01")

%cd "D:\Google Drive\Cursos\DAA\DAA 2015 2016\practicas\pract02\entregas\p200"
##### ejecución generando grafos
%run "D:\Google Drive\Cursos\DAA\DAA 2015 2016\practicas\pract02\checkpr02.py"

##### ejecución sobre grafos no dirigidos y dirigido ya generados
%run checkpr02.py fNameNoD fNameD
"""
#!/usr/bin/python
# -*- coding: utf-8 -*-
import numpy as np
import sys

##### importar archivo grafos.py "bueno"
#sys.path.append("D:\Google Drive\Cursos\DAA\DAA 2015 2016\practicas\pract01")
import grafos111 as gr

##### importar archivo grafosp.py con el contenido de la práctica
#sys.path.append(".")
import grafos11 as grp

################################################################ big main
def mainPract2(args):
  '''reads a TGF file with a weighted graph, applies to it either Dijkstra form node 0 or iterated Dijkstra and FW.
      flTGF: leer  grafo en formato TGF  según archivo de práctica (1) o según archivoe de control  (0)
      flIter: aplicar sólo D desde 0 (0) o D iter y FW (1)
          In the first case prints the previous and distance tables. In the second it prints the minimum distance matrices between all pairs of nodes.'''
  """if len(args) != 5:
    print ("args: name of tgf files to check Kruskal, BP básica, BP para ramas ascendentes, OT y dist mín in single source DAGs")
    sys.exit(0)
  fNameKruskal = args[0]; fNameBPbasic = args[1]; fNameBPasc = args[2]; fNameOT = args[3]; fNameSSdistMin = args[4];"""
  if len(args) > 1 and len(args) != 3:
    print ("args: either none or name of tgf files with an undirected and directed grapsh to be reads to check Kruskal and the BP and related functions")
    sys.exit(0)
  fNameKruskal = fNameBP = ''
  if len(args) == 3:
    fNameKruskal = args[1]; fNameBP = args[2]

  ################################ generar, guardar y leer gr no Dirigido
  print("\n################################ generando/leyendo grafos ...\n")

  if fNameKruskal == '':
    nNodes=7; sparseFactor=0.7
    mG = grp.randMatrUndPosWGraph(nNodes, sparseFactor)
    dG = grp.fromAdjM2Dict(mG)
    grp.dG2TGF(dG, 'grnodrand.tgf')
    dGnoD = grp.TGF2dG('grnodrand.tgf')
  else:
    dGnoD = gr.TGF2dG(fNameKruskal)
  print("\tgrafo escrito:\n"); print(dG)
  print("\n\tgrafo leído:\n");   print(dGnoD)

  ################################ kruskal y sus tiempos
  ################ kruskal básico
  print("\n################################ Kruskal básico ...\n")

  L = grp.kruskal(dGnoD, flagCC=True)
  print(L)
  L = gr.kruskal(dGnoD, flagCC=True)
  print(L)

  ################ tiempos kruskal
  print("\n################################ timing Kruskal ...\n")

  lTp  = grp.timeKruskal(nGraphs=20, nNodesIni=10, nNodesFin=30, step=10, sparseFactor=0.75, flagCC=True)
  lT   = gr.timeKruskal(nGraphs=20, nNodesIni=10, nNodesFin=30, step=10, sparseFactor=0.75, flagCC=True)
  print "\ttiempos de kruskal:\nTp", lTp, "\nT", lT

  ################################ BP básica
  print("\n################################ BP básica ...\n")

  if fNameBP == '':
    mG = grp.randMatrPosWGraph(nNodes, sparseFactor)  ########## generar grafo dirigido primero como MdA y luego como GfA
    dG = grp.fromAdjM2Dict(mG)
  else:
    dG = gr.TGF2dG(fNameBP)
  d, f, p = grp.drBP(dG)
  print "d: ", d, "\nf: ", f, "\np: ", p

  ################ BP para detectar ramas ascendentes
  print("\n################################ BP para ramas ascendentes ...\n")

  lAsc = grp.drBPasc(dG)
  print "ramas asc:\n", lAsc

  ################################ OT y dist min en DAGs
  print("\n################################ OT y OT y dist min en DAGs ...\n")

  ################ generar DAG
  print("################################ generando DAG ...\n")

  nNodes = 10; sparseFactor = 0.2; nIntentosMax=1000
  flDAG = False; nIntentos = 0
  while not flDAG and nIntentos < nIntentosMax:
    mG = grp.randMatrPosWGraph(nNodes, sparseFactor)
    dG = grp.fromAdjM2Dict(mG)
    flDAG = grp.DAG(dG)
    nIntentos +=1
  print("num intentos hasta DAG: %d" % nIntentos)
  print "DAG:\n", dG

  ################ dist min en single source DAGs (sólo si se ha encontrado uno)
  print("\n################################ dist min en single source DAGs ...\n")

  if flDAG:
    lOT = grp.OT(dG)
    d, p = grp.distMinSingleSourceDAG(dG)
    print "\nd: ", d, "\np: ", p

################################################################ calling main
if __name__ == '__main__':
  '''llamada correcta: fNameKruskal, fNameBP; hacer ambos '' para generación de grafos '''
  mainPract2(sys.argv)