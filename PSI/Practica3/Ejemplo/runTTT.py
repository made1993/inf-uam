import gameSearch as gs
import TTT

debug = False
inf = float('infinity')

def playGameNegamax(game):
    print game
    while not game.isOver():
        value,move = gs.negamax(game,9,-inf, inf )
        if move == None :
            print "move is None. Stopping"
            break
        game.makeMove(move)
        print "Player",game.player,"to",move,"for value",value,
        if not debug: print
        print game
        game.changePlayer()

playGameNegamax(TTT.TTT(debug))
