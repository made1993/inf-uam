
(setf node-00
(make-node :state 'Proserpina :depth 12 :g 10 :f 20) )

(setf *galaxy-M35* (make-problem 
						:states *planets*
						:initial-state *planet-origin*
						:fn-goal-test (make-fn 
										name: 'f-goal-test-galaxy
								 		lst-args: (list *planets-destination*))
						:fn-h 		(make-fn 
										name: 'f-h-galaxy
								 		lst-args: (list *planets-destination*))
						:operators 	(list (make-fn 
												name: 'navigate-white-hole
								 				lst-args: *white-holes*)
								 		  (make-fn 
								 		  		name: 'navigate-worm-hole
								 				lst-args: *worm-holes*))))


(defun expand-node (node problem)
	(if(null node)
			NIL
	(if(null problem)
			NIL
		(
		




