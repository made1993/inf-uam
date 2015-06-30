class TTT:

    def __init__(self,debug=False):
        self.debug = debug
        self.board =  [' ']*9
        self.player = 'X'
        if False:
            self.board = ['X','X',' ', 'X','O','O',' ',' ',' ']
            self.player = 'O'
        self.playerLookAHead = self.player

    def locations(self,c):
        return filter(lambda i: self.board[i] == c, range(len(self.board)))

    def getMoves(self):
        moves = self.locations(' ')
        return moves
        
    def getUtility(self):
        whereX = self.locations('X')
        whereO = self.locations('O')
        wins = [[0,1,2],[3,4,5],[6,7,8],
                [0,3,6],[1,4,7],[2,5,8],
                [0,4,8],[2,4,6]]
        isXWon = any([all([wi in whereX for wi in w]) for w in wins])
        isOWon = any([all([wi in whereO for wi in w]) for w in wins])
        if isXWon:
            return 1 if self.playerLookAHead is 'X' else -1
        elif isOWon:
            return 1 if self.playerLookAHead is 'O' else -1
        elif ' ' not in self.board:
            return 0
        else:
            return None
            
    def isOver(self):
        return self.getUtility() != None

    def makeMove(self,move):
        self.board[move] = self.playerLookAHead
        self.playerLookAHead = 'X' if self.playerLookAHead=='O' else 'O'

    def changePlayer(self):
        self.player = 'X' if self.player=='O' else 'O'
        self.playerLookAHead = self.player

    def unmakeMove(self,move):
        self.board[move] = ' '
        self.playerLookAHead = 'X' if self.playerLookAHead=='O' else 'O'

    def __str__(self):
        if self.debug:
            s = "|%c%c%c|%c%c%c|%c%c%c|" % tuple(self.board)
        else:
            s = "%c|%c|%c\n-----\n%c|%c|%c\n-----\n%c|%c|%c" % tuple(self.board)
        return s