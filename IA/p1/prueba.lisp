(defun combina-elt-lst( elet lst)
	(if (null lst)
		nil
	(mapcar #'(lambda (x) (list elet x)) lst)))
