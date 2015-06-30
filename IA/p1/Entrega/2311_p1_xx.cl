;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO1;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; bfs-improved (end queue net)
;;;	Realiza una busqueda en anchura mejorada para no entrar en recursion infinita.
;;;	
;;;	INPUT:
;;;		end: Nodo objetivo
;;;		queue: Cola con los caminos
;;;		net: Grafo en el que operamos
;;;	OUTPUT:
;;;		Camino al nodo objetivo
;;; 
(defun bfs-improved (end queue net)
  (if (null queue)					;Caso base
      nil
		;;;Se vinculan los valores de path y de node
    (let ((path (car queue)))
      (let ((node (car path)))
	(if (eql node end)		;Caso base
	    (reverse path)
					;;;Recursion, se exploran los caminos con 
					;;;origen en el nodo con el que estamos trabajando
	  (bfs-improved end
			(append (cdr queue)
				(new-paths-improved path node net))
			net))))))

;;;	EJEMPLOS:
;;;		(setf grafo '((a d) (b d f) (c e) (d f) (e b f) (f)))
;;;		(setf grafo2'((a b c d e) (b a d e f) (d a g b h) (e a b g h) (g c d e h) (h f e d g) (c a g) (f b h)))
;;;		(setf grafo3 '((a d) (b d f) (c e) (d f) (e b f) (f d))
;;;
;;;		(bfs-improved 'f (list (list 'a)) grafo) ;-> (A D F)	; caso tipico
;;;		(bfs-improved 'c (list (list 'a)) grafo) ;-> nil	; caso especial
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; new-paths-improved (path node net)
;;;	Realiza una busqueda en anchura mejorada para no entrar en recursion infinita.
;;;	
;;;	INPUT:
;;;		path: Lista actual
;;;		node: Nodo del que sacar caminos
;;;		net: Grafo en el que operamos
;;;	OUTPUT:
;;;		Lista con caminos que salen de node
;;;
(defun new-paths-improved (path node net)
  (if (null (unique-p path))
      nil
    (mapcar #'(lambda(n)
		(cons n path))
	    (cdr (assoc node net)))))

;;;	EJEMPLOS:
;;;		(new-paths-improved '(A D) 'D grafo) ;-> ((F A D))	; caso tipico
;;;		(new-paths-improved '(A D F D) 'D grafo) ;-> NIL	; caso especial
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; shortest-path-improved (end queue net)
;;;	Realiza una llamada a la funcion de busqueda mejorada.
;;;	
;;;	INPUT:
;;;		end: Nodo objetivo
;;;		queue: Cola con los caminos
;;;		net: Grafo en el que operamos
;;;	OUTPUT:
;;;		Camino al nodo objetivo
;;;
(defun shortest-path-improved (end queue net)
  (bfs-improved end queue net))

;;;	EJEMPLOS:
;;;		(shortest-path-improved 'f (list (list 'a)) grafo) ;-> (A D F)	; caso tipico
;;;		(shortest-path-improved 'c (list (list 'a)) grafo) ;-> nil	; caso especial
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; unique-p (list)
;;;	Comprueba si hay elementos repetidos en una lista dada.
;;;	
;;;	INPUT:
;;;		list: Lista sobre la que mirar
;;;	OUTPUT:
;;;		True si hay elementos repetidos o NIL si no
;;;
(defun unique-p (list)
  (or (null list)
      (and (not (member (first list) (rest list)))
	   (unique-p (rest list))
	   )
      )
  )

;;;	EJEMPLOS:
;;;		(unique-p '(A D F D)) ;-> T	; caso tipico
;;;		(unique-p '(A B C)) ;-> nil	; caso especial
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO2;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; euclidean-mapcar (x y)
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
      (sqrt 			;aplicamos la distancia euclidea
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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; euclidean-rec (x y)
;;; 	Calcula la distancia euclidea de 2 vectores
;;;
;;; 	INPUT:
;;;		x: Vector
;;;		y: Vector
;;; 	OUTPUT:
;;;		Distancia euclidea entre 2 vectores
;;;
(defun euclidean-rec (x y)
  (if (null x)	;;comprobamos las entradas
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
;;; 	Calcula el cuadrado de la distancia euclidea
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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; AD-recur(x y)
;;;	Calcula la desviacion absoluta entre 2 vectores
;;;
;;;	INPUT:
;;;		x: vector
;;;		y: vector
;;;	OUTPUT:
;;;		Desviacion absoluta
;;;
(defun AD-recur (x y)
  (if (null x)
      0
    (if (null y)
	0
      (if( null (cdr x))
	  (abs (- (car x) (car y)))
	(+ (abs (- (car x) (car y))) (AD-recur(cdr x) (cdr y)))))))

;;;	EJEMPLOS:
;;;		(AD-recur '() '()); -> 0	;caso especial
;;;		(AD-recur '(1 2) '(1)); -> 0	;caso especial
;;;		(AD-recur '(1 -1 1) '(0 0 0)); -> 3 ;caso tipico 
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO3;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; combine-elt-lst (elt lst)
;;;	Combina un elemento con los elementos de una lista
;;;
;;;	INPUT:
;;;		elt: Elemento a combinar
;;;		lst: Lista a combinar
;;;	OUTPUT:
;;;		Lista con listas combinadas
;;;
(defun combine-elt-lst (elt lst)
  (if (null lst)
      nil
    (cons (list elt (first lst)) (combine-elt-lst elt
						  (rest lst)))))
						  
;;;	EJEMPLOS:
;;;		(combine-elt-lst 'a '(1 2 3)); -> ((a 1) (a 2) (a 3)) ;caso tipico
;;;		(combine-elt-lst '() '(); -> nil	;caso especial 
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

			  
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; combine-lst-lst (lst1 lst2)
;;;	Calcula el producto cartesiano de dos listas
;;;
;;;	INPUT:
;;;		lst1: lista
;;;		lst2: lista
;;;	OUTPUT:
;;;		Producto cartesiano de las dos listas
;;;
(defun combine-lst-lst (lst1 lst2)
  (if (null lst1)
      nil
    (append ( combine-elt-lst (first lst1) lst2)
	    (combine-lst-lst (rest lst1) lst2))))

;;;	EJEMPLOS:
;;;		(combine-lst-lst '(a b c) '(1 2)) ; -> ((A 1) (A 2) (B 1) (B 2) (C 1) (C 2))	;caso tipico
;;;		(combine-lst-lst '(a b c) '()) ; -> NIL	;caso especial
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
;;;		(combine-list-of-lsts '((a b) (+ -) (1 2)) ;-> ((A + 1) (A + 2) (A - 1) (A - 2) (B + 1) (B + 2) (B - 1) (B - 2)) ;caso tipico
;;;		(combine-list-of-lsts '() );-> nil	;caso especial
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;EJERCICIO4;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



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
;;;			Lista con los simbolos atomicos de la FBF
;;;
(defun extrae-simbolos (expr)
  (if (null expr)
      nil
    (remove-duplicates			;Se quitan los duplicados 
     (remove-if #'connector-p 	;Se quita todo lo que no sean simbolos atomicos		
		(aplana expr) ) 		;Hacemos una llamada a aplana que nos da todos los elementos de la lista	
     :test #'equal)))		

;;;	EJEMPLOS:
;;;	(extrae-simbolos '()) ;-> NIL	;caso especial
;;;	(extrae-simbolos '(a => (v a b))) ;-> (a b)	; caso tipico
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; aplana (expr)
;;; 	Dada una lista devuelve todos los atomos que hay en ella
;;;
;;; 	INPUT:
;;;			expr: lista
;;; 	OUTPUT:
;;;			Lista con todos los elementos
;;;
(defun aplana (expr)
  (cond
   ((null expr) NIL)		;si la lista es nula devuleve nil
   ((atom (first expr))	;caso recursivo
    					;;Si car es un elemento y no una lista, hacemos una llamada recursiva
    						;;con el cdr de la lista y lo juntamos el resultado con el car 
    (cons(first expr)(aplana (rest expr))))
   (t (append					;caso recursivo
       (aplana (first expr))		;en este caso tenemos que el car es una lista,por tanto volvemos a llamar a apalana con su car
       (aplana (rest expr))))))

;;;	EJEMPLOS:
;;;	(aplana '());-> NIL ;caso especial
;;;	(aplana '(a b (a b)));-> (a b a b); caso tipico
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

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
    (combine-list-of-lsts 		;combina las listas reslultantes del mapcar
     ;;El mapcar lo que hace es que para cada valor de la lista
     ;;genera una lista con 2 sublistas en las que se agrupa el
     ;; valor del elemento de la lista con T o NIL
     (mapcar #'(lambda (x) (list (list x 'T ) (list x 'nil))) lst))))	

;;; 	EJEMPLOS:				
;;;		(genera-lista-interpretaciones '(A B)); -> (((A T) (B T)) ((A T) (B NIL)) ((A NIL) (B T)) ((A NIL) (B NIL))) ;caso tipico
;;;		(genera-lista-interpretaciones '()) ;-> nil	;caso especial
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

