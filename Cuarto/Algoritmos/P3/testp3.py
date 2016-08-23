import mochila as m
import numpy as np
import matplotlib.pyplot as plt
if __name__ == '__main__':
    # print(m.genSuperCrec(10))
    # print(m.mcd(20, 8))
    # print(m.mcd(31,17))
    # print(m.multiplier(26, 1))
    # print(m.mcd(1239, 735))
    # print(m.euclidesExtendido(1239, 735))
    # print(m.euclidesExtendido(735, 1239))
    # print(m.euclidesExtendido(3, 11))
    # print(3*4%11)
    # print(m.inverse(3, 11))
    # print(m.modMultInv(m.genSuperCrec(10)))
    # l = [2, 3, 13]
    # print(m.genSucesionPublica(l, 7, 20))
    # print(m.lPub_2_lSC([14, 1, 11], 3, 20))
    # print(m.genRandomBitString(10))
    # print("Encrypt")
    # print(m.MH_encrypt([1,1,0,1,0,1], [14, 1, 11]))
    # print(m.block_decrypt(15, [2,3,13], 3, 20))
    # print(m.l_decrypt([15,25], [2,3,13], 3, 20))

    #print("QUICKSELECT:")
    #l = np.random.permutation(105)
    #print(l)
    #print(m.pivot5_value(l, 0, 105))
    #print(l)
    #print(np.matrix(l).reshape((21, 5)))
    #l = [3, 4, 10, 17, 9, 1]
    #print(l)
    #for x in range(len(l)):
    #    print(x, m.qselect(l, 0, 6, x))


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
    #qs(l,0, 105)
    #print(l)
    #print l[100:105], l[104]
    #print l[32]
    #print(m.qselect(l, 0, 105, 43, m.pivot5))
    #print(m.qselect([1,2,3,4,5,6,7,8,9,0], 0, 10, 6, m.pivot5))

    def fitPlotQselect(nReps, sizeIni, sizeFin, step):
        times1 = m.n_time_qselect_ave(nReps, sizeIni, sizeFin, step)

        times2 = m.n_time_qselect_ave(nReps, sizeIni, sizeFin, step, m.pivot5)
        x = []
        for z in range(sizeIni, sizeFin, step):
            x.append(z)

        ydata1 = np.polyfit(x, times1, 1)
        f1 = np.poly1d(ydata1)
        yfit1 = np.linspace(sizeIni, sizeFin, sizeFin - sizeIni / step)

        ydata2 = np.polyfit(x, times2, 1)
        f2 = np.poly1d(ydata2)
        yfit2 = np.linspace(sizeIni, sizeFin, sizeFin - sizeIni / step)
        
        plt.figure(figsize=(10,10))
        plt.plot(x, times1, "b.", label="valor real pivotP")
        plt.plot(yfit1, f1(yfit1), "r-", label='pivotP')
        plt.plot(x, times2, "r.", label="valor real pivot5")
        plt.plot(yfit2, f2(yfit2), "b-", label='pivot5')
        plt.legend(bbox_to_anchor=(0., 1.02, 1., .102), loc=3, ncol=2, mode="expand", borderaxespad=0.)
        
        plt.show()

        return

    fitPlotQselect(20, 100, 200, 1)
    #m.n_time_qselect_ave(10, 100, 200, 1, m.pivot5)