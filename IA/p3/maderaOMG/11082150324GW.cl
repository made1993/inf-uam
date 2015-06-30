;GWNPG8M4ZS
;MaderaOMG

(defun mi-f-ev (estado) 
  (if (juego-terminado-p estado)
      (if (< (get-pts (estado-lado-sgte-jugador estado)) (get-pts (lado-contrario (estado-lado-sgte-jugador estado))))
          -50
          50
          )
    (- (+(get-pts (estado-lado-sgte-jugador estado))
          (get-fichas (estado-tablero estado) (estado-lado-sgte-jugador estado) 6)
          )
       (*(get-fichas (estado-tablero estado) (estado-lado-sgte-jugador estado) 4)4)
       (*(get-fichas (estado-tablero estado) (estado-lado-sgte-jugador estado) 5)4)
       (*(get-fichas (estado-tablero estado) (lado-contrario (estado-lado-sgte-jugador estado)) 4)-2)
       (*(get-fichas (estado-tablero estado) (lado-contrario (estado-lado-sgte-jugador estado)) 5)-2)

     (+(get-pts (lado-contrario (estado-lado-sgte-jugador estado))) 
        (get-fichas (estado-tablero estado) (lado-contrario (estado-lado-sgte-jugador estado)) 6)))))

		   
		   