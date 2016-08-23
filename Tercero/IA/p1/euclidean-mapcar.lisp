;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; euclidena-mapcar (x y)
;;; 	Calcula la distancia euclide a de 2 vectores
;;;
;;; 	INPUT:
;;;		x: Vector
;;;		y: Vector
;;; 	OUTPUT:
;;;		Distancia euclidea entre 2 vectores
;;;
(defun euclidean-mapcar (x y)
	(if (null x)		;para entrada incorrecta
		nil
	(if (null y)		;para entrada incorrecta
		nil
	(sqrt 			;aplicamaos la distancia euclidea
		(eval 
			(cons '+ 
				(mapcar #'(lambda (l) (* l l))
					(mapcar #'(lambda (l1 l2) (- l1 l2))
						x y))))))))

;;;	EJEMPLOS:
;;;	(euclidean-mapcar '() '());-> NIL ;caso especial
;;;	(euclidean-mapcar '(1 1 1) '(0 0 0)); 1.7320508 ; caso tipico
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

