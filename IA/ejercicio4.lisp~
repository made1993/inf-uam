(defconstant +bicond+ '<=>)
(defconstant +cond+ '=>)
(defconstant +and+ '^)
(defconstant +or+ 'v)
(defconstant +not+'¬)

(defun truth-value-p (x)
	(or (eql x T) (eql x NIL)))
	
(defun unary-connector-p (x)
	(eql x +not+))
	
(defun binary-connector-p (x)
	(or (eql x +bicond+)
		(eql x +cond+)))
		
(defun n-ary-connector-p (x)
	(or (eql x +and+)
		(eql x +or+)))
		
(defun connector-p (x)
	(or (unary-connector-p x)
		(binary-connector-p x)
		(n-ary-connector-p
		x)))
		
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; extrae-simbolos (expr)
;;; 	Extrae los simbolos de una FBF
;;;
;;; 	INPUT:
;;;			expr:lista con la FBF
;;; 	OUTPUT:
;;;			Los simbolos de la FBF
;;;
(defun extrae-simbolos (expr)
	(if (null expr)
		nil
	(remove-duplicates				;Se quitan los duplicados 
		(remove-if #'connector-p 	;Se quita todo lo que no sean simbolos
									;;Hacemos una llamada a aplana que nos
									;;da todos los elementos de la lista			
			(aplana expr) )
	 :test #'equal)))		

;;;	EJEMPLOS:
;;;	(extrae-simbolos '());-> NIL ;caso especial
;;;	(extrae-simbolos '(a => (v a b));-> (a b); caso tipico
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; aplana (expr)
;;; 	Dada una lista devuelve todos los atomos que hay en ella
;;;
;;; 	INPUT:
;;;			expr: lista
;;; 	OUTPUT:
;;;			Los atmos
;;;
(defun aplana (x)
	(cond
		((null x) NIL)				;si la lista es nula devuleve nil
		((atom (first x))			;caso recursivo
						;;Comprobamos que el car es un atomo
						;;si lo es, hacemos una llamada recursiva
						;;con el cdr de la lista y lo juntamos
						;;el resultado con el car 
			(cons(first x)(aplana (rest x))))
		(t (append					;caso recursivo
					;;en este caso tenemos que el car es una lista
					;;por tanto volvemos a llamar a apalana con su car
			(aplana (first x))
			(aplana (rest x))))))

;;;	EJEMPLOS:
;;;	(aplana '());-> NIL ;caso especial
;;;	(aplana '(a b (a b);-> (a b a b); caso tipico
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


