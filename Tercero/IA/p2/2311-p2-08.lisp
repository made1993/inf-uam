;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Problem definition

(defstruct problem
	states		; List of states
	initial-state	; Initial state
	fn-goal-test	; Function (in fn format) that determines
			; whether a state fulfills the goal
	fn-h		; Function (in fn format) that evaluates
			; to the value of the heuristic of a state
	operators)	; list of operators (in fn format)
			; to generate successors
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;Node in search tree
;;
(defstruct node
	state		; state label
	parent		; parent node
	action		; action that generated the current node from its parent
	(depth 0)	; depth in the search tree
	(g 0)		; cost of the path from the initial state to this node
	(h 0)		; value of the heuristic
	(f 0))		; g + h
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;Actions
;;
(defstruct action
	name		; Name of the operator that generated the action
	origin		; State on which the action is applied
	final		; State that results from the application of the action
	cost )		; Cost of the action
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;Search strategies
;;
(defstruct strategy
	name		; Name of the search strategy
	node-compare-p)	; boolean comparison
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;fn format for functions
;;
(defstruct fn
	name		; Function name
	lst-args)	; List of additional arguments
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;DATOS;;;;;;;;;;

(setf *white-holes*
	'((Avalon Mallory 2) (Avalon Proserpina 2)
	 (Mallory Katril 6) (Mallory Prosperina 7)
	 (Katril Davion 2) (Katril Mallory 6)
	 (Davion Sirtis 1) (Davion Proserpina 4)
	 (Sirtis Davion 1) (Sirtis Proserpina 10)
	 (Proserpina Sirtis 10) (Proserpina Davion 4) (Proserpina Mallory 7) (Proserpina Avalon 2)
	 (Kentares Avalon 3) (Kentares Proserpina 2) (Kentares Katril 2)))
	 
	 
(setf *worm-holes*
      '((Avalon Kentares 4) 
      	(Avalon Mallory 7) 
	(Mallory Katril 5) 
	(Mallory Proserpina 6) 
	(Kentares Proserpina 1) 
	(Proserpina Sirtis 7)
	(Katril Davion 1) 
	(Katril Sirtis 10) 
	(Davion Sirtis 8)))
	
	
(setf *sensors*
'((Avalon 5) (Mallory 7) (Kentares 4)(Davion 1)(Proserpina 4) (Katril 3) (Sirtis 0)))


(setf *planets* '(Avalon Davion Katril Kentares Mallory Proserpina Sirtis))

(setf *planets-destination* '(Sirtis))

(setf *planet-origin* 'Kentares)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIOS;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO1;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	f-goal-test-galaxy (state planets-destination)
;;;	Comprueba si se ha alcanzado el objetivo.
;;;
;;;	INPUT: 
;;;		state: Estado que se quiere comprobar si es el objetivo
;;;		planets-destination: Lista que contiene los nombres de los planetas destino
;;;	OUTPUT:
;;;		TRUE si el estado es el objetivo o NIL si no
;;;
(defun f-goal-test-galaxy (state planets-destination) 
	(if(null state)
		NIL
	(equal state (find state planets-destination))))

;;;
;;; EJEMPLOS:
;;;		(f-goal-test-galaxy 'Sirtis *planets-destination*) ;-> T	;Caso tipico
;;;		(f-goal-test-galaxy 'Urano *planets-destination*) ;-> NIL	;Caso especial
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO2;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	f-h-galaxy (state sensors)
;;;	Calcula la el valor de la heuristica en el estado que recibe.
;;;
;;;	INPUT: 
;;;		state: Estado del que calcular el valor heuristico
;;;		sensors: Lista de pares planeta-valor heuristico
;;;	OUTPUT:
;;;		Valor de la heuristica o NIL si no se ha podido calcular
;;;
(defun f-h-galaxy (state sensors)
	(if(null sensors)
			NIL
	(if (null state)
			NIL
		(second(find-if #'(lambda (x) (equal state (first x)))  
			sensors   )))))

;;;
;;;	EJEMPLOS:	
;;;		(f-h-galaxy 'Mallory *sensors*) ;-> 7	;Caso tipico
;;;		(f-h-galaxy 'Sirtis *sensors*) ;-> 0	;Caso tipico
;;;		(f-h-galaxy 'Tierra *sensors*) ;-> NIL	;Caso especial
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO3;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	navigate-white-hole (state white-holes)
;;;	Busca las acciones posibles desde el estado actual a traves de agujeros blancos.
;;;
;;;	INPUT: 
;;;		state: Estado en el que se esta
;;;		white-holes: Lista que contiene los caminos entre agujeros blancos y el coste de los mismos
;;;	OUTPUT:
;;;		Acciones posibles desde el estado actual o NIL si no hay acciones posibles
;;;
(defun navigate-white-hole (state white-holes)
	(if (null state)
		NIL
	(if (null white-holes)
		NIL
	(mapcar #'(lambda (x) (make-action :name 'navigate-white-holes :origin state  :final (second x) :cost (third x)))
		(remove-if-not #'(lambda (x) (equal state (first x))) white-holes)))))

;;;
;;;	EJEMPLOS:
;;;		navigate-worm-hole 'Davion *worm-holes*)	;Caso tipico	;->
;;;			(#S(ACTION :NAME NAVIGATE-WHITE-HOLES :ORIGIN DAVION :FINAL SIRTIS :COST 1)
;;;			 #S(ACTION :NAME NAVIGATE-WHITE-HOLES :ORIGIN DAVION :FINAL PROSERPINA
;;;				:COST 4))
;;;		(navigate-white-hole 'Tierra *white-holes*) ;-> NIL	;Caso especial
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	navigate-worm-hole (state worm-holes)
;;;	Busca las acciones posibles desde el estado actual a traves de agujeros de gusano.
;;;
;;;	INPUT: 
;;;		state: Estado en el que se esta
;;;		white-holes: Lista que contiene los caminos entre agujeros de gusano y el coste de los mismos
;;;	OUTPUT:
;;;		Acciones posibles desde el estado actual o NIL si no hay acciones posibles
;;;
(defun navigate-worm-hole (state worm-holes)
	(if (null state)
		NIL
	(if (null worm-holes)
		NIL
	(mapcar #'(lambda (x) (make-action :name 'navigate-worm-holes :origin state  :final (if (equal state (second x)) (first x) (second x))
	:cost (third x)))
	(append(remove-if-not #'(lambda (x) (equal state (first x))) worm-holes)
	(remove-if-not #'(lambda (x) (equal state (second x))) worm-holes))
	))))
;;;
;;;	EJEMPLOS:
;;;		navigate-worm-hole 'Davion *worm-holes*)	;Caso tipico	;->
;;;			(#S(ACTION :NAME NAVIGATE-WORM-HOLES :ORIGIN DAVION :FINAL SIRTIS :COST 8)
;;;			 #S(ACTION :NAME NAVIGATE-WORM-HOLES :ORIGIN DAVION :FINAL KATRIL :COST 1)))
;;;		(navigate-white-hole 'Tierra *white-holes*) ;-> NIL	;Caso especial
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO4;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	(setf *A-star* (make-strategy ...))
;;;	Inicializa una variable cuyo valor es la estrategia para realizar la busqueda A*
;;;
(setf *A-star*
(make-strategy 
		:name 'A_star 
		:node-compare-p 'node-f-<=
	))
;;;
;;;	EJEMPLO: Estrategia para busqueda de coste uniforme:
;;;		
(setf *uniform-cost*
	(make-strategy
		:name 'uniform-cost
		:node-compare-p 'node-g-<=))


(defun node-g-<= (node-1 node-2)
(<= (node-g node-1)
(node-g node-2)))
	
(defun node-f-<= (node-1 node-2)
(<= (node-f node-1)
(node-f node-2)))
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO5;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	(setf *galaxy-M35* (make-problem ...))
;;;	Inicializa una estructura llamada *galaxy-M35* que representa el problema bajo estudio.
;;;
(setf *galaxy-M35* (make-problem 
						:states *planets*
						:initial-state *planet-origin*
						:fn-goal-test (make-fn 
										:name 'f-goal-test-galaxy
								 		:lst-args *planets-destination*)
						:fn-h 		(make-fn 
										:name 'f-h-galaxy
								 		:lst-args *sensors*)
						:operators 	(list (make-fn 
												:name 'navigate-white-hole
								 				:lst-args *white-holes*)
								 		  (make-fn 
								 		  		:name 'navigate-worm-hole
								 				:lst-args *worm-holes*))))	
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO6;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(setf node-00
(make-node :state 'Proserpina :depth 12 :g 10 :f 20))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	(defun expand-node (node problem)
;;;	Expande el nodo en funcion del problema a tratar, devolviendo todos los nodos a los que se puede ir.
;;;
;;;	INPUT: 
;;;		node: Nodo a expandir
;;;		problem: Problema bajo estudio segun el cual realizar la expansion
;;;	OUTPUT:
;;;		Nodos a los que se puede ir desde el nodo expandido o NIL
;;;
(defun expand-node (node problem)
	(if (null node)
			NIL
	(if (null problem)
			NIL
	 (mapcar #'(lambda (x) ( make-node 
	 		:state (action-final x)
	 		:parent node
	 		:action x
	 		:depth (+ 1 (if (node-depth node) (node-depth node) 0))
	 		:g (+ (if (node-g node) (node-g node) 0 ) (if(action-cost x) (action-cost x) 0 ))
	 		:h (funcall (fn-name (problem-fn-h problem)) (action-final x) (fn-lst-args (problem-fn-h problem)))
	 		:f (+ (+ (if (node-g node) (node-g node) 0 ) (if(action-cost x) (action-cost x) 0 )) (if (null (funcall (fn-name (problem-fn-h problem)) (action-final x) (fn-lst-args (problem-fn-h problem)))) 0 (funcall (fn-name (problem-fn-h problem)) (action-final x) (fn-lst-args (problem-fn-h problem))) )) 
	 
	 )) (append(funcall(fn-name (first (problem-operators problem ))) (node-state node) (fn-lst-args (first (problem-operators problem ))))  (funcall (fn-name (second (problem-operators problem ))) (node-state node) (fn-lst-args (second (problem-operators problem )))))
		))))
		
;;;
;;;	EJEMPLOS:
;;;		(expand-node node-00 *galaxy-M35*)	;->Caso tipico	;->
;;;		(setf node-08 (make-node :state 'Tierra :depth 1 :g 1 :f 2))
;;;			(expand-node node-08 *galaxy-M35*)	;->NIL	;->Caso especial
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(setf lst-nodes-00
(expand-node node-00 *galaxy-M35*))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO7;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(setf node-01
(make-node :state 'Avalon :depth 0 :g 0 :f 0))
(setf node-02
(make-node :state 'Kentares :depth 2 :g 50 :f 50))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	defun insert-nodes-strategy (nodes lst-nodes strategy)
;;;	Inserta una lista de nodos en otra lista de acuerdo a una estrategia.
;;;
;;;	INPUT: 
;;;		nodes: Lista de nodos a insertar
;;;		lst-nodes: Lista de nodos en la que insertar
;;;		strategy: Estrategia que seguir para la insercion
;;;	OUTPUT:
;;;		Lista resultado de la insercion
;;;
(defun insert-nodes-strategy (nodes lst-nodes strategy)
	(sort (append nodes lst-nodes) (strategy-node-compare-p strategy)))
;;;
;;;	EJEMPLOS:
;;;		(insert-nodes-strategy (list node-00 node-01 node-02)
;;;								lst-nodes-00
;;;								*uniform-cost*))	;->Caso tipico
;;;		(insert-nodes-strategy '( ) '( ) *uniform-cost*))	;-> NIL	;->Caso especial
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO8;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	graph-search (problem strategy)
;;;	Llama a la funcion que realiza busqueda en grafo inicializando previamente las listas de nodos vacios y nodos cerrados.
;;;
;;;	INPUT: 
;;;		problem: Problema sobre el que realizar la busqueda
;;;		strategy: Estrategia que seguir para la busqueda
;;;	OUTPUT:
;;;		Resulta de la busqueda realizada por la funcion graph-search-aux
;;;
(defun graph-search (problem strategy)
	(graph-search-aux
		problem
		strategy 
		(list (make-node 
			:state (problem-initial-state problem)
			:depth 0
			:g 0
			:f 0))
		(list )))
;;;
;;;	EJEMPLOS:
;;;		(graph-search *galaxy-M35* *A-star*)	;->Caso tipico	;->
;;;	  #S(NODE :STATE SIRTIS
;;;   :PARENT
;;;   #S(NODE :STATE DAVION
;;;      :PARENT
;;;      #S(NODE :STATE KATRIL
;;;         :PARENT
;;;         #S(NODE :STATE KENTARES :PARENT NIL :ACTION NIL :DEPTH 0 :G 0 :H 0
;;;            :F 0)
;;;         :ACTION
;;;         #S(ACTION :NAME NAVIGATE-WHITE-HOLES :ORIGIN KENTARES :FINAL KATRIL
;;;            :COST 2)
;;;         :DEPTH 1 :G 2 :H 3 :F 5)
;;;      :ACTION
;;;      #S(ACTION :NAME NAVIGATE-WORM-HOLES :ORIGIN KATRIL :FINAL DAVION :COST 1)
;;;      :DEPTH 2 :G 3 :H 1 :F 4)
;;;   :ACTION
;;;   #S(ACTION :NAME NAVIGATE-WHITE-HOLES :ORIGIN DAVION :FINAL SIRTIS :COST 1)
;;;   :DEPTH 3 :G 4 :H 0 :F 4)
;;;
;;;	
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	graph-search-aux (problem strategy open-nodes closed-nodes)
;;;	Realiza la busqueda en grafo sobre un problema dado siguiendo una estrategia.
;;;
;;;	INPUT: 
;;;		problem: Problema sobre el que realizar la busqueda
;;;		strategy: Estrategia que seguir para la busqueda
;;;		open-nodes: Lista de nodos abiertos
;;;		closed-nodes: Lista de nodos cerrados
;;;	OUTPUT:
;;;		Resultado de la busqueda o NIL si no se ha podido realizar
;;;
(defun graph-search-aux (problem strategy open-nodes closed-nodes)
	(if (null open-nodes)
		NIL
	(if (funcall(fn-name (problem-fn-goal-test problem)) (node-state (first open-nodes)) (fn-lst-args (problem-fn-goal-test problem)))
		(first open-nodes)
	(if (or (not (member (first open-nodes) closed-nodes))
			(equal (first open-nodes) 
				(first (insert-nodes-strategy (first open-nodes) closed-nodes strategy))))
					(graph-search-aux 
								problem 
								strategy 
								(insert-nodes-strategy (expand-node (first open-nodes) problem) (rest open-nodes) strategy)
								(append (list (first open-nodes)) closed-nodes))

					(graph-search-aux problem strategy (rest open-nodes)  closed-nodes)
				))))
				
;;;
;;;	EJEMPLOS:
;;;		El caso tipico es el del ejercicio anterior.
;;;		Caso especial sería en el que una de las dos listas que recibe fueran nulas.
;;;	
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO9;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	a-star-search (problem)
;;;	Realiza la busqueda A* para el problema dado
;;;
;;;	INPUT: 
;;;		problem: Problema sobre el que realizar la busqueda
;;;	OUTPUT:
;;;		Resultado de la busqueda o NIL si no se ha podido realizar
;;;
	(defun a-star-search (problem)
		(graph-search problem *a-star* )) 

;;;
;;;	EJEMPLOS:
;;;		(a-star-search *galaxy-M35*)	;->Caso tipico	;->
;;;	  #S(NODE :STATE SIRTIS
;;;   :PARENT
;;;   #S(NODE :STATE DAVION
;;;      :PARENT
;;;      #S(NODE :STATE KATRIL
;;;         :PARENT
;;;         #S(NODE :STATE KENTARES :PARENT NIL :ACTION NIL :DEPTH 0 :G 0 :H 0
;;;            :F 0)
;;;         :ACTION
;;;         #S(ACTION :NAME NAVIGATE-WHITE-HOLES :ORIGIN KENTARES :FINAL KATRIL
;;;            :COST 2)
;;;         :DEPTH 1 :G 2 :H 3 :F 5)
;;;      :ACTION
;;;      #S(ACTION :NAME NAVIGATE-WORM-HOLES :ORIGIN KATRIL :FINAL DAVION :COST 1)
;;;      :DEPTH 2 :G 3 :H 1 :F 4)
;;;   :ACTION
;;;   #S(ACTION :NAME NAVIGATE-WHITE-HOLES :ORIGIN DAVION :FINAL SIRTIS :COST 1)
;;;   :DEPTH 3 :G 4 :H 0 :F 4)
;;;
;;;		Caso especial si el problema no tiene solucion.
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO10;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	defun tree-path (node)
;;;	Llama a la funcion que muestra el camino seguido para llegar a un nodo.
;;;
;;;	INPUT: 
;;;		node: Nodo al que se ha llegado
;;;	OUTPUT:
;;;		Lista con los nombres de los planetas por los que se ha pasado para llegar al nodo o NIL.
;;;
(defun tree-path (node)
	(tree-path-aux node '())
	)

;;;
;;;	EJEMPLOS:
;;;		(tree-path (a-star-search *galaxy-m35*))	;-> (KENTARES KATRIL DAVION SIRTIS)	;-> Caso tipico
;;;		
;;;		(tree-path NIL)	;-> NIL	;-> Caso especial
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	defun tree-path-aux (node)
;;;	Llama a la funcion que muestra el camino seguido para llegar a un nodo.
;;;
;;;	INPUT: 
;;;		node: Nodo al que se ha llegado
;;;		states: Lista donde guardar los nombres de los planetas por los que se ha pasado para llegar al nodo
;;;	OUTPUT:
;;;		Lista con los nombres de los planetas por los que se ha pasado para llegar al nodo o NIL.
;;;
(defun tree-path-aux (node states)
	(if (null node)
		states
	(tree-path-aux (node-parent node) (cons (node-state node) states))
	))

;;;
;;;	EJEMPLOS:
;;;		(tree-path-aux #S(NODE :STATE SIRTIS
;;;   :PARENT
;;;   #S(NODE :STATE DAVION
;;;      :PARENT
;;;     	NIL
;;;      :ACTION
;;;      #S(ACTION :NAME NAVIGATE-WORM-HOLES :ORIGIN KATRIL :FINAL DAVION :COST 1)
;;;      :DEPTH 2 :G 3 :H 1 :F 4)
;;;   :ACTION
;;;   #S(ACTION :NAME NAVIGATE-WHITE-HOLES :ORIGIN DAVION :FINAL SIRTIS :COST 1)
;;;   :DEPTH 3 :G 4 :H 0 :F 4) '())	;-> (DAVION SIRTIS)	;-> Caso tipico
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO11;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	defun action-sequence (node)
;;;	Llama a la funcion que muestra la secuencia de acciones para llefar a un nodo.
;;;
;;;	INPUT: 
;;;		node: Nodo al que se ha llegado
;;;	OUTPUT:
;;;		Lista con las acciones que se han realizado para llegar al  nodo o NIL.
;;;
(defun action-sequence (node)
	(action-sequence-aux node '())
	)

;;;
;;;	EJEMPLOS:
;;;		(action-sequence (a-star-search *galaxy-M35*))	;-> Caso tipico	;->
;;;		(#S(ACTION :NAME NAVIGATE-WHITE-HOLES :ORIGIN KENTARES :FINAL KATRIL :COST 2)
;;;		 #S(ACTION :NAME NAVIGATE-WORM-HOLES :ORIGIN KATRIL :FINAL DAVION :COST 1)
;;;		 #S(ACTION :NAME NAVIGATE-WHITE-HOLES :ORIGIN DAVION :FINAL SIRTIS :COST 1))
;;;	
;;;		(action-sequence nil)	;->NIL	;->Caso especial
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	defun action-sequence-aux (node actions)
;;;	Llama a la funcion que muestra la secuencia de acciones para llefar a un nodo.
;;;
;;;	INPUT: 
;;;		node: Nodo al que se ha llegado
;;;		actions: Lista en la que guardar las acciones realizadas para llegar al nodo.
;;;	OUTPUT:
;;;		Lista con las acciones que se han realizado para llegar al  nodo o NIL.
;;;
(defun action-sequence-aux (node actions)
	(if (null node)
		actions
	(if (null (node-action node))
		actions
	(action-sequence-aux (node-parent node) (cons (node-action node) actions))
	)))

;;;
;;;	EJEMPLOS:
;;;		(action-sequence-aux (a-star-search *galaxy-M35*) '())	;-> Caso tipico	;->
;;;		(#S(ACTION :NAME NAVIGATE-WHITE-HOLES :ORIGIN KENTARES :FINAL KATRIL :COST 2)
;;;		 #S(ACTION :NAME NAVIGATE-WORM-HOLES :ORIGIN KATRIL :FINAL DAVION :COST 1)
;;;		 #S(ACTION :NAME NAVIGATE-WHITE-HOLES :ORIGIN DAVION :FINAL SIRTIS :COST 1))
;;;	
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO12;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(setf *depth-first*
	(make-strategy
		:name 'depth-first
		:node-compare-p 'depth-first-node-compare-p))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	depth-first-node-compare-p (node-1 node-2)
;;;	Realiza una busqueda en profundidad
;;;
;;;	INPUT: 
;;;		node-1: Nodo a comparar
;;;		node-2: Nodo a comparar
;;;	OUTPUT:
;;;		Lista con los nombres de los planetas de los nodos expandidos hasta encontrar la solucion.
;;;	
(defun depth-first-node-compare-p (node-1 node-2)
	(if (or (null node-1) (null node-2))
		NIL
	(> (node-depth node-1) (node-depth node-2))))

;;;
;;;	EJEMPLOS:
;;;		(tree-path (graph-search *galaxy-M35* *depth-first*))	
;;;				;-> (KENTARES AVALON MALLORY KATRIL DAVION SIRTIS)	;-> Caso tipico
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(setf *breadth-first*
	(make-strategy
		:name 'breadth-first
		:node-compare-p 'breadth-first-node-compare-p))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;	breadth-first-node-compare-p (node-1 node-2)
;;;	Realiza una busqueda en anchura
;;;
;;;	INPUT: 
;;;		node-1: Nodo a comparar
;;;		node-2: Nodo a comparar
;;;	OUTPUT:
;;;		Lista con los nombres de los planetas de los nodos que recorre hasta encontrar la solucion.
;;;		
(defun breadth-first-node-compare-p (node-1 node-2)
	(if (or (null node-1) (null node-2))
		NIL
	(< (node-depth node-1) (node-depth node-2))))

;;;
;;;	EJEMPLOS:
;;;		(tree-path (graph-search *galaxy-M35* *breadth-first*))	
;;;				;-> (KENTARES PROSERPINA SIRTIS)	;-> Caso tipico
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
