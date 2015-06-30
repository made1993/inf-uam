;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;	Search strategies
;;
(defstruct strategy
name		; Name of the search strategy
node-compare-p)	; boolean comparison
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(setf *A-star*
(make-strategy :name 'A_star  :node-compare-p 'node-g-<=))

(defun node-g-<= (node-1 node-2)
(<= (node-g node-1)
(node-g node-2)))
