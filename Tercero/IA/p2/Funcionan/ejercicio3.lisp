;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;Actions
;;
(defstruct action
	name	; Name of the operator that generated the action
	origin	; State on which the action is applied
	final	; State that results from the application of the action
	cost )	; Cost of the action
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


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

(setf *white-holes*
	'((Avalon Mallory 2) (Avalon Proserpina 2)
	 (Mallory Katril 6) (Mallory Prosperina 7)
	 (Katril Davion 2)
	 (Daivon Sirtis 1) (Daivon Proserpina 4)
	 (Sirtis Daivon 1) (Sirtis Proserpina 10)
	 (Proserpina Sirtis 10) (Proserpina Daivon 4) (Proserpina Mallory 7) (Proserpina Avalon 2)
	 (Kentares Avalon 3) (Kentares Proserpina 2) (Kentares Katril 2)))
	  		
	  	
(setf *planets* '(Avalon Davion Katril Kentares Mallory Proserpina Sirtis))	  	
	  	
	  	
(defun navigate-white-hole (state white-holes)
	(if (null state)
		NIL
	(if (null white-holes)
		NIL
	(mapcar #'(lambda (x) (make-action :name 'white-holes :origin state  :final (second x) :cost (third x)))
		(remove-if-not #'(lambda (x) (equal state (first x))) white-holes)))))

(defun navigate-worm-hole (state worm-holes)
	(if (null state)
		NIL
	(if (null worm-holes)
		NIL
	(mapcar #'(lambda (x) (make-action :name 'white-holes :origin state  :final (if (equal state (second x)) (first x) (second x))
	:cost (third x)))
	(append(remove-if-not #'(lambda (x) (equal state (first x))) worm-holes)
	(remove-if-not #'(lambda (x) (equal state (second x))) worm-holes))
	))))
	
	
	
