(setf *sensors*
'((Avalon 5) (Mallory 7) (Kentares 4)(Davion 1)(Proserpina 4) (Katril 3) (Sirtis 0)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
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
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
