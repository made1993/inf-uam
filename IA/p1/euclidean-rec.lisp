;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; euclidena-rec (x y)
;;; 	Calcula la distancia euclide a de 2 vectores
;;;
;;; 	INPUT:
;;;		x: Vector
;;;		y: Vector
;;; 	OUTPUT:
;;;		Distancia euclidea entre 2 vectores
;;;
(defun euclidean-rec (x y)
	(if (null x)	;;comprobamos la entrada
		0
	(if (null y)
		0
	(sqrt (suma-sqr x y)))))

;;;	EJEMPLOS:
;;;	(euclidean-rec '() '());-> 0 ;caso especial
;;;	(euclidean-rec '(1 1 1) '(0 0 0)); 1.7320508 ; caso tipico
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; suma-sqr (x y)
;;; 	Calcula eñ cuadrado de la distancia euclidea
;;;
;;; 	INPUT:
;;;		x: Vector
;;;		y: Vector
;;; 	OUTPUT:
;;;		Distancia euclidea al cuadrado entre 2 vectores
;;;
(defun suma-sqr (x y)
	(if (null x)	;;comprobamos la entrada
		0
	(if (null y)
		0
	(if (null (cdr x))	;caso base
		(expt (- (car x) (car y))2)
	(+(expt (- (car x) (car y)) 2) (suma-sqr(cdr x) (cdr y))))))) ;caso recursivo

;;;	EJEMPLOS:
;;;	(suma-sqr '() '());-> 0 ;caso especial
;;;	(suma-sqr '(1 1 1) '(0 0 0)); 3 ; caso tipico
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
