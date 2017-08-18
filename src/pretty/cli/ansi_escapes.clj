(ns pretty.cli.ansi-escapes)

(def esc-code "Escape code" "\033[")

(defn cursor-to
  "Move the cursor to the 'x, y' cell"
  ([] (str esc-code "H"))
  ([x] (str esc-code (+ x 1) "G"))
  ([x y] (str esc-code (+ y 1) ";" (+ x 1) "H")))

(defn cursor-move
  "Move the cursor by 'x' and 'y' cells"
  [x y]
  (let [x-code (if (< x 0) (str (- x) "D") (str x "C"))
        y-code (if (< y 0) (str (- y) "A") (str y "B"))]
    (str esc-code x-code esc-code y-code)))

(defn cursor-up
  "Moves the cursor 'n' (default 1) cells up"
  ([n] (str esc-code n "A"))
  ([] (cursor-up 1)))

(defn cursor-down
  "Moves the cursor 'n' (default 1) cells down"
  ([n] (str esc-code n "B"))
  ([] (cursor-down 1)))

(defn cursor-forward
  "Moves the cursor 'n' (default 1) cells forward"
  ([n] (str esc-code n "C"))
  ([] (cursor-forward 1)))

(defn cursor-backward
  "Moves the cursor 'n' (default 1) cells backward"
  ([n] (str esc-code n "D"))
  ([] (cursor-backward 1)))

(defn cursor-left
  "Moves the cursor to the left"
  []
  (str esc-code "G"))

(defn cursor-position
  "Retrieve the cursor position"
  []
  (str esc-code "6n"))

(defn cursor-position-save
  "Save the cursor position"
  []
  (str esc-code "s"))

(defn cursor-position-restore
  "Restore the cursor position"
  []
  (str esc-code "u"))

(defn cursor-next-line
  "Moves the cursor to beginning of the next line"
  []
  (str esc-code "E"))

(defn cursor-prev-line
  "Moves the cursor to beginning of the previous line"
  []
  (str esc-code "F"))

(defn cursor-hide
  "Hide the cursor"
  []
  (str esc-code "?25l"))

(defn cursor-show
  "Show the cursor"
  []
  (str esc-code "?25h"))

(defn erase-end-line
  "Erase to the end of the line"
  []
  (str esc-code "K"))

(defn erase-start-line
  "Erase to the start of the line"
  []
  (str esc-code "1K"))

(defn erase-line
  "Erase the line"
  []
  (str esc-code "2K"))

(defn erase-down
  "Erase down"
  []
  (str esc-code "J"))

(defn erase-up
  "Erase up"
  []
  (str esc-code "1J"))

(defn erase-screen
  "Erase the screen"
  []
  (str esc-code "2J"))

(defn scroll-up
  "Scroll screen up"
  []
  (str esc-code "S"))

(defn scroll-down
  "Scroll screen down"
  []
  (str esc-code "T"))

(defn clear-screen
  "Clear screen"
  []
  (str "\u001Bc"))

(defn beep
  "Emit a beep"
  []
  (str "\u0007"))
