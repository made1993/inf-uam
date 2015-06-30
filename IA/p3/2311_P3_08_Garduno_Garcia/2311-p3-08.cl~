;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	mi-f-ev (estado)
;;;	Dado un estado devuleve un valor numerico que indica la calidad del estado
;;;	para el jugador que tiene el turno.
;;;	INPUT: 
;;;		estado: Estado en el que se esta.
;;;		
;;;	OUTPUT:
;;;		Valor del estado actual.
;;;

(defun mi-f-ev (estado) 
  (if (juego-terminado-p estado)
      (if (< (get-pts (estado-lado-sgte-jugador estado)) (get-pts (lado-contrario (estado-lado-sgte-jugador estado))))
          -60		;caso en el jugador haya perdido
          60		;caso en le que el jugador haya ganado
          )
		  
		;;Obtenemos tolos valores que nos benefician de la partida le restamos loq ue nos perjudica
    (- (+(get-pts (estado-lado-sgte-jugador estado))			;obtiene las fichas del jugador con el turno
          (get-fichas (estado-tablero estado) (estado-lado-sgte-jugador estado) 6) ;obtiene las fichas del kalaha del jugador con el turno
          )
       (*(get-fichas (estado-tablero estado) (estado-lado-sgte-jugador estado) 4)4) ;<=fichasPos4*4
       (*(get-fichas (estado-tablero estado) (estado-lado-sgte-jugador estado) 5)6) ;<=fichasPos5*6
       (*(get-fichas (estado-tablero estado) (lado-contrario (estado-lado-sgte-jugador estado)) 4)-2) ;<=fichasPosRival4*-2
       (*(get-fichas (estado-tablero estado) (lado-contrario (estado-lado-sgte-jugador estado)) 5)-4) ;<=fichasPosRival4*-4

     (+(get-pts (lado-contrario (estado-lado-sgte-jugador estado))) ;obtiene las fichas del jugador que no tiene el turno
        (get-fichas (estado-tablero estado) (lado-contrario (estado-lado-sgte-jugador estado)) 6))))) ;obtiene las fichas del kalaha del jugador que no tiene el turno
		
		
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defun minimax-a-b(estado profundidad-max f-eval)
  (let* ((oldverb *verb*)
         (*verb* (if *debug-mmx* *verb* nil))
         (estado2 (minimax-a-b-1 estado 0 t profundidad-max f-eval -99999 99999))
         (*verb* oldverb))
    estado2))

	
	
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	minimax-a-b-1 (estado profundidad devolver-movimiento profundidad-max f-eval alfa beta)
;;;	Dado un estado devuleve un valor numerico que indica la calidad del estado o devuelve el movimiento
;;;	para el jugador que tiene el turno.
;;;	INPUT: 
;;;		estado: Estado en el que se esta.
;;;		profundidad: Profundiad en la que nos encontramos.
;;;		devolver-movimiento: Valor boleano que indica si se devuleve como sucesor o como valor.
;;;		profundidad-max: Profundidad a la que va a buscar el algoritmo.
;;;		f-eval: Funcion de evaluacin que se usara para comprobar el valor del estado.
;;;		alfa: Valor de alfa
;;;		beta: Valor de beta
;;;		
;;;	OUTPUT:
;;;		Devuelve una posicion o el valor de la mejor posicion encontrada.
;;;	
	
(defun minimax-a-b-1 (estado profundidad devolver-movimiento profundidad-max f-eval alfa beta)
  (cond ((>= profundidad profundidad-max)
         (unless devolver-movimiento  (funcall f-eval estado)))
        (t
         (let* ((sucesores (generar-sucesores estado))
                                                              (mejor-valor -99999)
                (mejor-sucesor nil))
           (cond ((null sucesores)
                  (unless devolver-movimiento  (funcall f-eval estado)))
                 (t
                  (loop for sucesor in sucesores do
                        (let* ((resultado-sucesor (minimax-a-b-1 sucesor (1+ profundidad)
                                                             nil profundidad-max f-eval (- beta) (- alfa)))
                           (valor-nuevo (- resultado-sucesor)))
                      ;(format t "~% Mmx-1 Prof:~A valor-nuevo ~4A de sucesor  ~A" profundidad valor-nuevo (estado-tablero sucesor))
                      (when (> valor-nuevo mejor-valor)
                        (setq mejor-valor valor-nuevo)
                        (setq mejor-sucesor  sucesor ))
                      (when t
                        (setq alfa (max alfa valor-nuevo)))
                      (if (>= alfa beta) 
                          (if  devolver-movimiento (return mejor-sucesor) (return mejor-valor)))
                      ))
                  (if  devolver-movimiento mejor-sucesor mejor-valor)))))))
				  
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defun f-j-mmx-ab (estado profundidad-max f-eval)
  (minimax-a-b estado profundidad-max f-eval))


(setf *jdr-mmx-Regular-ab* (make-jugador
                        :nombre   '|PongoLOL|
                        :f-juego  #'minimax-a-b
                        :f-eval   #'mi-f-ev))



(time (partida 0 2 (list *jdr-mmx-Regular*      *jdr-mmx-Regular-ab*)))
