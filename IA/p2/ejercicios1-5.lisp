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
;;;
;;;
;;;
;;;
;;;

(defun f-goal-test-galaxy (state planets-destination) 
	(if(null state)
		NIL
	(equal state (find state planets-destination))))

;;;
;;;
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO2;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
;;;
(defun f-h-galaxy (state sensors)
	(if(null sensors)
			NIL
	(if (null state)
			NIL
		(second(find-if #'(lambda (x) (equal state (first x)))  
			sensors   )))))

;;;
;;;
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO3;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
;;;
(defun navigate-white-hole (state white-holes)
	(if (null state)
		NIL
	(if (null white-holes)
		NIL
	(mapcar #'(lambda (x) (make-action :name 'navigate-white-holes :origin state  :final (second x) :cost (third x)))
		(remove-if-not #'(lambda (x) (equal state (first x))) white-holes)))))

;;;
;;;
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
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
;;;
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO4;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
;;;
(setf *A-star*
(make-strategy :name 'A_star  :node-compare-p 'node-f-<=))
;;;
;;;
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
;;;
(defun node-f-<= (node-1 node-2)
(<= (node-f node-1)
(node-f node-2)))
;;;
;;;
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;EJEMPLO;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(setf *uniform-cost*
	(make-strategy
		:name 'uniform-cost
		:node-compare-p 'node-g-<=))


(defun node-g-<= (node-1 node-2)
(<= (node-g node-1)
(node-g node-2)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO5;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
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
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO6;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(setf node-00
(make-node :state 'Proserpina :depth 12 :g 10 :f 20) )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
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
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(print
(setf lst-nodes-00
(expand-node node-00 *galaxy-M35*)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO7;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(setf node-01
(make-node :state 'Avalon :depth 0 :g 0 :f 0) )
(setf node-02
(make-node :state 'Kentares :depth 2 :g 50 :f 50) )
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
;;;
(defun insert-nodes-strategy (nodes lst-nodes strategy)
	(sort (append nodes lst-nodes) (strategy-node-compare-p strategy)))
;;;
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO8;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
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
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
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
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO9;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
;;;
	(defun a-star-search (problem)
		(graph-search problem *a-star* )) 

;;;
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO10;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
;;;
(defun tree-path (node)
	(tree-path-aux node '())
	)

;;;
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
;;;
(defun tree-path-aux (node states)
	(if (null node)
		states
	(tree-path-aux (node-parent node) (cons (node-state node) states))
	))

;;;
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO11;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
;;;
(defun action-sequence (node)
	(action-sequence-aux node '())
	)

;;;
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
;;;
(defun action-sequence-aux (node actions)
	(if (null node)
		actions
	(if (null (node-action node))
		actions
	(action-sequence-aux (node-parent node) (cons (node-action node) actions))
	)))

;;;
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO12;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
;;;
(setf *depth-first*
	(make-strategy
		:name 'depth-first
		:node-compare-p 'depth-first-node-compare-p))
		
(defun depth-first-node-compare-p (node-1 node-2)
	(if (or (null node-1) (null node-2))
		NIL
	(> (node-depth node-1) (node-depth node-2))))

;;;
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;;
;;;
;;;
;;;
(setf *breadth-first*
	(make-strategy
		:name 'breadth-first
		:node-compare-p 'breadth-first-node-compare-p))
		
(defun breadth-first-node-compare-p (node-1 node-2)
	(if (or (null node-1) (null node-2))
		NIL
	(< (node-depth node-1) (node-depth node-2))))

;;;
;;;
;;;
;;;
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
