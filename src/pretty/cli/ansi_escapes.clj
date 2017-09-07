(ns pretty.cli.ansi-escapes)

(def esc-code "Return the string of basic escape code" "\033[")
(def reset-code "Return the string code to reset all attributes" (str esc-code "0m"))

(defn cursor-to
  "Return the string code to move the cursor to the 'x, y' cell"
  ([] (str esc-code "H"))
  ([x] (str esc-code (+ x 1) "G"))
  ([x y] (str esc-code (+ y 1) ";" (+ x 1) "H")))

(defn cursor-move
  "Return the string code to move the cursor by 'x' and 'y' cells"
  [x y]
  (let [x-code (if (< x 0) (str (- x) "D") (str x "C"))
        y-code (if (< y 0) (str (- y) "A") (str y "B"))]
    (str esc-code x-code esc-code y-code)))

(defn cursor-up
  "Return the string code to move the cursor 'n' (default 1) cells up"
  ([n] (str esc-code n "A"))
  ([] (cursor-up 1)))

(defn cursor-down
  "Return the string code to move the cursor 'n' (default 1) cells down"
  ([n] (str esc-code n "B"))
  ([] (cursor-down 1)))

(defn cursor-forward
  "Return the string code to move the cursor 'n' (default 1) cells forward"
  ([n] (str esc-code n "C"))
  ([] (cursor-forward 1)))

(defn cursor-backward
  "Return the string code to move the cursor 'n' (default 1) cells backward"
  ([n] (str esc-code n "D"))
  ([] (cursor-backward 1)))

(defn cursor-left
  "Return the string code to move the cursor to the left"
  []
  (str esc-code "G"))

(defn cursor-position
  "Return the string code to retrieve the cursor position"
  []
  (str esc-code "6n"))

(defn cursor-position-save
  "Return the string code to save the cursor position"
  []
  (str esc-code "s"))

(defn cursor-position-restore
  "Return the string code to restore the cursor position"
  []
  (str esc-code "u"))

(defn cursor-next-line
  "Return the string code to move the cursor to beginning of the next line"
  []
  (str esc-code "E"))

(defn cursor-prev-line
  "Return the string code to move the cursor to beginning of the previous line"
  []
  (str esc-code "F"))

(defn cursor-hide
  "Return the string code to hide the cursor"
  []
  (str esc-code "?25l"))

(defn cursor-show
  "Return the string code to show the cursor"
  []
  (str esc-code "?25h"))

(defn erase-end-line
  "Return the string code to erase to the end of the line"
  []
  (str esc-code "K"))

(defn erase-start-line
  "Return the string code to erase to the start of the line"
  []
  (str esc-code "1K"))

(defn erase-line
  "Return the string code to erase the line"
  []
  (str esc-code "2K"))

(defn erase-down
  "Return the string code to erase down"
  []
  (str esc-code "J"))

(defn erase-up
  "Return the string code to erase up"
  []
  (str esc-code "1J"))

(defn erase-screen
  "Return the string code to erase the screen"
  []
  (str esc-code "2J"))

(defn scroll-up
  "Return the string code to scroll screen up"
  []
  (str esc-code "S"))

(defn scroll-down
  "Return the string code to scroll screen down"
  []
  (str esc-code "T"))

(defn clear-screen
  "Return the string code to clear screen"
  []
  (str "\u001Bc"))

(defn beep
  "Return the string code to emit a beep"
  []
  (str "\0007"))
