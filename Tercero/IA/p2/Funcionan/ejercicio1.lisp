(setf *planets-destination* '(Sirtis))

(defun f-goal-test-galaxy (state planets-destination) 
	(if(null state)
		NIL
	(equal state (find state planets-destination))))
