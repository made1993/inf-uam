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

