(ns pretty.cli.ansi-escapes)

(def esc-code "\033[")
;(def esc-code "debug-")

(defn cursor-to
  "Cursor to the 'x,y' position"
  ([] (str esc-code "H"))
  ([x] (str esc-code (+ x 1) "G"))
  ([x y] (str esc-code (+ y 1) ";" (+ x 1) "H")))

(defn cursor-move [x y]
  "Move the cursor by 'x' and 'y' positions"
  (let [x-code (if (< x 0) (str (- x) "D") (str x "C"))
        y-code (if (< y 0) (str (- y) "A") (str y "B"))]
    (str esc-code x-code esc-code y-code)))

(defn cursor-up
  "Move the cursor up by 'n' positions"
  ([n] (str esc-code n "A"))
  ([] (cursor-up 1)))

(defn cursor-down
  "Move the cursor down by 'n' positions"
  ([n] (str esc-code n "B"))
  ([] (cursor-down 1)))

(defn cursor-forward
  "Move the cursor forward by 'n' positions"
  ([n] (str esc-code n "C"))
  ([] (cursor-forward 1)))

(defn cursor-backward
  "Move the cursor backward by 'n' positions"
  ([n] (str esc-code n "D"))
  ([] (cursor-backward 1)))
