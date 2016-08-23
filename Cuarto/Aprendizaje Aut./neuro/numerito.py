"source": [
    "import numpy as np\n",
    "import matplotlib.image as mpimg\n",
    "import matplotlib.pyplot as plt\n",
    "%matplotlib inline\n",
    "\n",
    "k = mpimg.imread(\"all.png\",True)\n",
    "\n",
    "plt.figure(figsize=(20,10))\n",
    "plt.imshow(k)"
   ]

"source": [
    "k.shape"
   ]

"source": [
    "m = k[:,:,1]\n",
    "\n",
    "plt.figure(figsize=(20,10))\n",
    "intensity_thres = 75\n",
    "plt.imshow((m>intensity_thres)[:,:], cmap=plt.cm.Greys)"
   ]


"source": [
    "plt.figure(figsize=(20,10))\n",
    "mbw=(m>intensity_thres)\n",
    "horizontal = mbw.sum(1)\n",
    "thrsH = 1410\n",
    "plt.plot(range(m.shape[0]), horizontal, range(m.shape[0]), [thrsH]*m.shape[0])\n",
    "#r = [10000,11910]\n",
    "#plt.ylim([1400,1600]);\n",
    "#plt.plot(range(r[0],r[1]),horizontal[r[0]:r[1]], range(r[0],r[1]), [thrsH]*(r[1]-r[0])) \n"
   ]



"source": [
    "plt.figure(figsize=(20,10))\n",
    "vertical = mbw.sum(0)\n",
    "thrsV = 10000\n",
    "plt.plot(range(m.shape[1]), vertical, range(m.shape[1]), [thrsV]*m.shape[1])"
   ]



"source": [
    "# Cortes\n",
    "def cortes(zonas_picos):\n",
    "    cuts = []\n",
    "    i1 = 0\n",
    "    while True:\n",
    "        if i1 >= len(zonas_picos)-1:\n",
    "            break\n",
    "        if zonas_picos[i1] == False and zonas_picos[i1+1] == True:\n",
    "            for i2 in range(i1+1, len(zonas_picos)-1):\n",
    "                if zonas_picos[i2] == True and zonas_picos[i2+1] == False:\n",
    "                    cuts.append((i1,i2))\n",
    "                    i1 = i2\n",
    "                    break\n",
    "                    \n",
    "        i1 = i1 + 1\n",
    "    print cuts\n",
    "    cuts = map(lambda (i0,i1): (i0[0]+5,i1[1]-5), zip(cuts[::2],cuts[1::2]))\n",
    "    print cuts\n",
    "\n",
    "    return cuts\n",
    "\n",
    "cutsH = cortes(horizontal>thrsH)\n",
    "cutsV = cortes(vertical>thrsV)"
   ]

 "source": [
    "print cutsH\n",
    "print cutsV\n",
    "\n",
    "\n",
    "\n",
    "class Numerito:                                                               \n",
    "    clase = -1\n",
    "    "
   ]
"source": [
    "w = max(map(lambda c:c[1]-c[0],cutsV))\n",
    "h = max(map(lambda c:c[1]-c[0],cutsH))\n",
    "print w+1 if w%2==1 else w\n",
    "print h+1 if h%2==1 else h\n",
    "#dy,dx = measurements.center_of_mass(im==False)\n",
    "#print dy\n",
    "#print dx\n",
    "#plt.imshow(im)\n",
    "#num.imagen[round(h/2-dy):round(h/2-dy)+(ch[1]-ch[0]),round(w/2-dx):round(w/2-dx)+(cv[1]-cv[0])] = im\n",
    "#cms = map(lambda n:measurements.center_of_mass(n.imagen), numeritos)\n",
    "#plt.plot(cms)"
   ]
"source": [
    "import scipy as sc\n",
    "import scipy.ndimage.measurements as measurements\n",
    "\n",
    "def imageCrop(img):\n",
    "    print np.where(img.sum(0))\n",
    "\n",
    "plt.figure(figsize=(20,50))\n",
    "i = 1\n",
    "\n",
    "w = max(map(lambda c:c[1]-c[0],cutsV))\n",
    "h = max(map(lambda c:c[1]-c[0],cutsH))\n",
    "w = w+1 if w%2==1 else w\n",
    "h = h+1 if h%2==1 else h\n",
    "ww = 400.\n",
    "hh = 600.\n",
    "\n",
    "numeritos = []\n",
    "for ch in cutsH:\n",
    "    for cv in cutsV:\n",
    "        num = Numerito()\n",
    "        num.imagen = np.full((hh,ww),True)\n",
    "        im = k[ch[0]:ch[1],cv[0]:cv[1],1]>intensity_thres\n",
    "        dy,dx = measurements.center_of_mass(im==False)\n",
    "        num.imagen[round(hh/2-dy):round(hh/2-dy)+(ch[1]-ch[0]),\n",
    "                   round(ww/2-dx):round(ww/2-dx)+(cv[1]-cv[0])] = im\n",
    "        num.imagen = num.imagen[hh/2-h/2:hh/2-h/2+h,ww/2-w/2:ww/2-w/2+w]\n",
    "        num.clase = (i-1) % 10\n",
    "        numeritos.append(num)\n",
    "        plt.subplot(len(cutsH),len(cutsV),i)\n",
    "        #plt.subplot(50,10,i)\n",
    "        plt.title(str((i-1)%10)+\"_\"+str(i))\n",
    "        plt.imshow(num.imagen)\n",
    "        sc.misc.toimage(num.imagen).save('i%05d.png' % (i))\n",
    "        i += 1\n",
    "        \n",
    "imageCrop(num.imagen)"
   ]

"source": [
    "def extrae_caracs(numerito):\n",
    "    filas, columnas = numerito.imagen.shape\n",
    "    paso_fil = filas / 20.\n",
    "    paso_col = columnas / 12.\n",
    "    caracteristicas = []\n",
    "    ff = np.arange(0, filas,    paso_fil)\n",
    "    ff = np.append(ff, filas)\n",
    "    cc = np.arange(0, columnas, paso_col)\n",
    "    cc = np.append(cc, columnas)\n",
    "    for f in range(0,len(ff)-2):\n",
    "        for c in range(0,len(cc)-2):\n",
    "            caracteristicas.append(numerito.imagen[ff[f]:ff[f+2],cc[c]:cc[c+2]].sum())\n",
    "\n",
    "    return caracteristicas\n",
    "\n",
    "c = extrae_caracs(num)\n",
    "print c"
   ]

"source": [
    "data   = np.array(map(extrae_caracs, numeritos))\n",
    "target = np.array(map(lambda n: n.clase, numeritos))\n",
    "np.savetxt(\"numeros.csv\", \n",
    "           np.concatenate((data, target.reshape([len(target),1])), axis=1), \n",
    "           delimiter=\",\")\n",
    "\n",
    "dd = np.loadtxt(open(\"numeros.csv\",\"rb\"),delimiter=\",\")\n",
    "data   = dd[:,:-1]\n",
    "target = dd[:,-1]\n",
    "#data = data[np.bitwise_or(target==9, target==4),:]\n",
    "#target = target[np.bitwise_or(target==9, target==4)]\n",
    "#print data.shape\n",
    "#print target.shape\n",
    "#plt.figure(figsize=(20,10))\n",
    "#for i in (1,8):\n",
    "#    plt.plot(data[target==i].mean(0))\n"
   ]

"source": [
    "from sklearn import tree\n",
    "from sklearn.ensemble import RandomForestClassifier\n",
    "from sklearn import cross_validation\n",
    "from sklearn.metrics import confusion_matrix\n",
    "\n",
    "indexFolds = cross_validation.ShuffleSplit(len(target), n_iter=100, test_size=0.3)\n",
    "\n",
    "ll = np.unique(target)\n",
    "\n",
    "# Run partitions\n",
    "errors = np.array([0.0] * len(indexFolds))\n",
    "i = 0\n",
    "for idxTr, idxTs in indexFolds:\n",
    "    # Train model\n",
    "    #clf = tree.DecisionTreeClassifier()\n",
    "    clf = RandomForestClassifier(n_estimators=250)\n",
    "    clf.fit(data[idxTr],target[idxTr])\n",
    "    # Validate model\n",
    "    predTarget = clf.predict(data[idxTs])\n",
    "    errors[i] = float(sum(predTarget!=target[idxTs]))/len(predTarget)\n",
    "    if i==0:\n",
    "        cm = confusion_matrix(target[idxTs],predTarget, labels=ll)\n",
    "    else:\n",
    "        cm = cm + confusion_matrix(target[idxTs],predTarget, labels=ll)\n",
    "    i = i + 1\n",
    "\n",
    "print \"%g\" % (errors.mean()) + \" +- %g\" % (errors.std())\n",
    "print \"Matriz de confusión: \"\n",
    "plt.imshow(cm)\n",
    "cm"
   ]