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
