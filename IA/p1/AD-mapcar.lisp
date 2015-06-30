;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; AD-mapcar (x y)
;;;	Calcula la deviacion absoluta
;;;	
;;;	INPUT:
;;;		x: Vector
;;;		y: Vextor
;;;	OUTPUT:
;;;		la desviacion absoluta acumulada
;;; 
(defun AD-mapcar (x y)
	(if (null x)		;para entrada incorrecta
		0
	(if (null y)		;para entrada incorrecta
		0
		(eval		;aplicamos la desviacion absoluta 
			(cons '+ 
				(mapcar #'(lambda (l) (abs l))
					(mapcar #'(lambda (l1 l2) (- l1 l2))
						x y)))))))
;;; 	EJEMPLOS:
;;;		(ad-mapcar '(1 2) '(-1 0)) ;-> 4;caso tipico
;;;		(ad-mapcar '() '()) ;-> 0	;caso especial
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
