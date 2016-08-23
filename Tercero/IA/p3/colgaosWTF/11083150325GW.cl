;GWNPG8M4ZS
;ColgaosWTF

(defun mi-f-ev (estado) 
  (if (juego-terminado-p estado)
      (if (< (get-pts (estado-lado-sgte-jugador estado)) (get-pts (lado-contrario (estado-lado-sgte-jugador estado))))
          -80
          80
          )
    (- (+(get-pts (estado-lado-sgte-jugador estado))
          (get-fichas (estado-tablero estado) (estado-lado-sgte-jugador estado) 6)
          )
       (*(get-fichas (estado-tablero estado) (estado-lado-sgte-jugador estado) 4)4)
       (*(get-fichas (estado-tablero estado) (estado-lado-sgte-jugador estado) 5)8)
       (*(get-fichas (estado-tablero estado) (lado-contrario (estado-lado-sgte-jugador estado)) 4)-3)
       (*(get-fichas (estado-tablero estado) (lado-contrario (estado-lado-sgte-jugador estado)) 5)-6)

     (+(get-pts (lado-contrario (estado-lado-sgte-jugador estado))) 
        (get-fichas (estado-tablero estado) (lado-contrario (estado-lado-sgte-jugador estado)) 6)))))

		   
		   