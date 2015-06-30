(defun combine-elt-lst (elt lst)
	(if (null lst)
				nil
		(cons (list elt (first lst)) (combine-elt-lst elt
												(rest lst)))))
