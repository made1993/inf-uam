;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; genera-lista-interpretaciones (lst)
;;;	Devuelve una lista de listas con las dierentes combinaciones que pueden
;;; tomar los valores que se nos pasan en listas de n, donde n es el numero
;;; de elementos que tiene la lista que se nos pasa
;;;	
;;;	
;;;	INPUT:
;;;		lst: una lista
;;;	OUTPUT:
;;;		la lista de listas
;;; 



(defun genera-lista-interpretaciones (lst)
	(if (null lst)			;comprueba la correcta entrada de los argumentos
			nil
		(combine-list-of-lsts ;combina las listas reslultantes del mapcar
		
						;;El mapcar lo que nos hace es que para cada valor de la lista
						;;nos genera una lista con 2 sublisatas en las que se agrupa el
						;; valor del elemento de la lista con T o NIL
						
			(mapcar #'(lambda (x) (list (list x 'T ) (list x 'nil))) lst))))	

;;; 	EJEMPLOS:
;;;							
;;;		(genera-lista-interpretaciones '(A B)); -> (((A T) (B T)) ((A T) (B NIL)) 
;;;												 ((A NIL) (B T)) ((A NIL) (B NIL)))
;;;												;caso tipico
;;;		(genera-lista-interpretaciones '());-> nil	;caso especial
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; combine-list-of-lsts (lstolsts)
;;;	Devuelve una lista de listas
;;;	
;;;	INPUT:
;;;		lstolsts: lista de listas
;;;	OUTPUT:
;;;		la lista de listas
;;; 


(defun combine-list-of-lsts (lstolsts)
  (if (null (cdr lstolsts))
      (mapcar #'list (first lstolsts)) 
    (mapcar #'(lambda (x) (cons (first x) (first (rest x)))) 
	    (combine-lst-lst(car lstolsts)(combine-list-of-lsts (rest lstolsts))))))

;;; 	EJEMPLOS:
;;;		(combine-list-of-lsts '((a b) (+ -) (1 2));-> ((A + 1) (A + 2) (A - 1) (A - 2) 
;;;								(B + 1) (B + 2) (B - 1) (B - 2));caso tipico
;;;		(combine-list-of-lsts '() );-> nil	;caso especial
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defun combine-lst-lst (lst1 lst2)
	(if (null lst1)
			nil
		(append ( combine-elt-lst (first lst1) lst2)
					(combine-lst-lst (rest lst1) lst2))))
												
												
(defun combine-elt-lst (elt lst)
	(if (null lst)
		nil
	(cons (list elt (first lst)) (combine-elt-lst elt
		(rest lst)))))

