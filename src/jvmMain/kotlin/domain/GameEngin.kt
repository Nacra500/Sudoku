package domain

import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream

internal class GameEngine(private val boxPerColLin: Int) {
    private val field: Array<Array<Int>>
    private val colLin: Int = boxPerColLin * boxPerColLin
    private val possibles: Array<Array<Array<Int?>>>

    init {
        field = Array(colLin) { Array(colLin){0} }
        possibles = Array(colLin) { Array(colLin) { arrayOfNulls(colLin) } }
        for (i in 0 until colLin) {
            for (j in 0 until colLin) {
                for (j2 in 0 until colLin) {
                    possibles[i][j][j2] = -1
                }
            }
        }
        generate()
    }

    fun createSolvable(): Array<Array<Int>> {
        //generatePossibleValuesArray();
        var obv: Array<Int?>
        var x: Int
        var y: Int
        var c = 0
        var t = 0
        var r = 0
        // c = anzahl der gelöschten felder, t = versuche ein feld zu löschen, r = anzahl der random koordinaten, die erstellt
        while (c < 81 && t < c + 20) {
            analyzeField()
            x = createRandom()
            y = createRandom()
            r++
            if (field[y][x] != -1) {
                val j = field[y][x]
                removeNumber(x, y)
                println("c: $c t: $t r: $r")
                analyzeField()
                c++
                t++
                obv = checkForObvious1()
                if (obv.size == 0) {
                    field[y][x] = j
                    c--
                }
            }
        }
        return field
    }

    fun checkCompletedField(): Boolean {
        var r = true
        for (y in 0 until colLin) {
            for (x in 0 until colLin) {
                r = checkValidInput(x, y, field[x][y])
                if (r == false) {
                    return r
                }
            }
        }
        return r
    }

    private fun removeNumber(x: Int, y: Int) {
        field[y][x] = -1
    }

    /*
    public void generatePossibleValuesArray() {
    for (int y = 0; y < colLin; y++) {
        for (int x = 0; x < colLin;x++) {
    //              if (field[y][x] == -1) {
                possibles[y][x].add(10);
    //              }
        }
    }
    }
    public void generatePossibleValuesArray(int x, int y) {
            if (field[y][x] == -1) {
                possibles[y]= new ArrayList<Integer>();
            }
    }
    */
    private fun analyzeField() {
        for (y in 0 until colLin) {
            for (x in 0 until colLin) {
                for (i in 1..9) {
                    if (field[y][x] == -1) {
                        if (checkValidInput(x, y, i)) {
                            possibles[y][x][i] = i
                        }
                    } else {
                        possibles[y][x][i] = 0
                    }
                }
            }
        }
    }

    /**
     *
     * @return TODO: korrekte Angabe der Koordinate und des Wertes, der auf jeden Fall einfach gefunden werden kann, es mucss glaub ich nicht immer der sein, den man entfernt hat, sonst wäre es gar nicht notwendig zu returnen, da der wert dann schon die random zahlen sind
     */
    private fun checkForObvious1(): Array<Int?> {
        var r: Array<Int?>
        r = checkForObviousColumn()
        if (r.size == 0) {
            r = checkForObviousRow()
            if (r.size == 0) {
                return r
            }
        } else {
            r = checkForObviousBox()
            if (r.size == 0) {
                return r
            }
        }
        return r
    }

    /*
    private Integer[] checkForObvious2() {
        Integer[] r = {};
        for (int x = 0; x < colLin; x++) {
            for (int y = 0; y < colLin; y++) {
                for (y2 = boxPerColLin; y2 < colLin; y2++) {
                    for (x2 = boxPerColLin; x2 < colLin; x2++) {
                        r = checkForObviousColumn(0);
                        if (r.length == 0) {
                            r = checkForObviousRow(0);
                        } else {
                            r = r;
                        }
                    }
                }
            }
        }
        return r;
    }
    */
    private fun checkForObviousColumn(): Array<Int?> {
        var tempc = false
        var c = 0
        var x: Int
        var y: Int
        for (i in 1 until colLin) {
            x = 0
            while (x < colLin) {

                //check if i is already in the COlumn
                y = 0
                while (y < colLin) {
                    if (field[y][x] == i) {
                        tempc = true
                    }
                    y++
                }
                //counts how often i is a possible Value in the row (if its exactly one, that solution can be easily found by the user
                if (tempc == false) {
                    y = 0
                    while (y < colLin) {
                        if (possibles[y][x][i] == i) {
                            c++
                        }
                        y++
                    }
                }
                //returns the first position and the value that can be inserted by the user
                if (c == 1) {
                    //TODO y ist aktuell nicht der korrekte wert der übergeben wird
                    return arrayOf(x, y, i)
                }
                x++
            }
        }
        //if there is no obvious solution the return array is empty
        return arrayOf()
    }

    private fun checkForObviousColumn(x: Int): Array<Int> {
        var tempc = false
        var c = 0
        var y: Int
        for (i in 1 until colLin) {
            //check if i is already in the COlumn
            y = 0
            while (y < colLin) {
                if (field[y][x] == i) {
                    tempc = true
                }
                y++
            }
            //counts how often i is a possible Value in the row (if its exactly one, that solution can be easily found by the user
            if (tempc == false) {
                y = 0
                while (y < colLin) {
                    if (possibles[y][x][i] == i) {
                        c++
                    }
                    y++
                }
            }
            //returns the first position and the value that can be inserted by the user
            if (c == 1) {
                //TODO y ist aktuell nicht der korrekte wert der übergeben wird
                return arrayOf(x, y, i)
            }
        }
        //if there is no obvious solution the return array is empty
        return arrayOf()
    }

    private fun checkForObviousRow(): Array<Int?> {
        var tempc = false
        var c = 0
        var x: Int
        var y: Int
        for (i in 1 until colLin) {
            y = 0
            while (y < colLin) {
                tempc = false
                //check if i is already in the Row
                x = 0
                while (x < colLin) {
                    if (field[y][x] == i) {
                        tempc = true
                    }
                    x++
                }
                //counts how often i is a possible Value in the row (if its exactly one, that solution can be easily found by the user
                if (tempc == false) {
                    x = 0
                    while (x < colLin) {
                        if (possibles[y][x][i] == i) {
                            c++
                        }
                        x++
                    }
                }
                //returns the first position and the value that can be inserted by the user
                if (c == 1) {
                    //TODO x ist aktuell nicht der korrekte wert der übergeben wird
                    return arrayOf(x, y, i)
                }
                y++
            }
        }
        //if there is no obvious solution the return array is empty
        return arrayOf()
    }

    private fun checkForObviousRow(y: Int): Array<Int> {
        var tempc = false
        var c = 0
        var x: Int
        for (i in 1 until colLin) {
            tempc = false
            //check if i is already in the Row
            x = 0
            while (x < colLin) {
                if (field[y][x] == i) {
                    tempc = true
                }
                x++
            }
            //counts how often i is a possible Value in the row (if its exactly one, that solution can be easily found by the user
            if (tempc == false) {
                x = 0
                while (x < colLin) {
                    if (possibles[y][x][i] == i) {
                        c++
                    }
                    x++
                }
            }
            //returns the first position and the value that can be inserted by the user
            if (c == 1) {
                //TODO x ist aktuell nicht der korrekte wert der übergeben wird
                return arrayOf(x, y, i)
            }
        }
        //if there is no obvious solution the return array is empty
        return arrayOf()
    }

    private fun checkForObviousBox(): Array<Int?> {
        var tempc = false
        var c = 0
        var x = boxPerColLin
        var y = boxPerColLin
        var x2: Int
        var y2: Int
        for (i in 1..9) {
            y2 = boxPerColLin
            while (y2 < colLin) {
                x2 = boxPerColLin
                while (x2 < colLin) {
                    tempc = false
                    y = y2 - boxPerColLin
                    while (y < y2) {

                        //check if i is already in the Box
                        x = x2 - boxPerColLin
                        while (x < x2) {
                            if (field[y][x] == i) {
                                tempc = true
                            }
                            x++
                        }
                        y++
                    }
                    //counts how often i is a possible Value in the row (if its exactly one, that solution can be easily found by the user
                    if (tempc == false) {
                        y = y2 - boxPerColLin
                        while (y < y2) {
                            x = x2 - boxPerColLin
                            while (x < x2) {
                                if (possibles[y][x][i] == i) {
                                    c++
                                }
                                x++
                            }
                            y++
                        }
                        if (c == 1) {
                            //TODO: x and y are random variables that are returened and not the real positions (we have to check for scope whether this is necessary or not
                            return arrayOf(x, y, i)
                        }
                    }
                    x2++
                }
                y2++
            }
        }
        //if there is no obvious solution the return array is empty
        return arrayOf()
    }

    /*
    private Integer[] checkForObviousBox(int z) {
        Boolean tempc = false;
        int c = 0;
        int x = boxPerColLin;
        int y = boxPerColLin;
        int x2;
        int y2;
        for (int i = 1; i<10; i++) {
            for (y2 = boxPerColLin; y2 < colLin; y2++) {
                for (x2 = boxPerColLin; x2 < colLin; x2++) {
                    tempc = false;
                    for (y = y2-boxPerColLin; y < y2; y++) {
                        //check if i is already in the Box
                        for (x = x2-boxPerColLin; x < x2; x++) {
                            if (field[y][x] == i) {
                                tempc = true;
                            }
                        }
                    }
                    //counts how often i is a possible Value in the row (if its exactly one, that solution can be easily found by the user
                    if (tempc == false) {
                        for (y = y2-boxPerColLin; y < y2; y++) {
                            for (x = x2-boxPerColLin; x < x2; x++) {
                                if (possibles[y][x][i]==i) {
                                    c++;
                                }
                            }
                        }
                        if (c == 1) {
                            //TODO: x and y are random variables that are returened and not the real positions (we have to check for scope whether this is necessary or not
                            Integer[] r = {x,y,i};
                            return r;
                        }
                    }
                }
                //if there is no obvious solution the return array is empty
                Integer[] r = {};
                return r;
            }
        }
        }
    */
    private fun createRandom(): Int {
        val rand = Random()
        return rand.nextInt(colLin)
    }

    //ab hier ist es von TIm
    private fun generate() {
        var solvable = false
        var tries = 0
        while (!solvable) {
            for (line in field) {
                Arrays.fill(line, 0)
            }
            solvable = true
            var numOfProblems = -colLin + 1
            var entries = IntStream.rangeClosed(1, colLin).boxed().collect(Collectors.toList())
            Collections.shuffle(entries)
            field[0] = entries.toTypedArray<Int>()
            lineFor@ for (i_y in 1 until colLin) {
                while (Arrays.asList<Int>(*field[i_y]).contains(0)) {
                    numOfProblems++
                    if (numOfProblems > 2000) {
                        tries++
                        solvable = false
                        break@lineFor
                    }
                    entries = IntStream.rangeClosed(1, colLin).boxed().collect(Collectors.toList())
                    Collections.shuffle(entries)
                    for (i_x in 0 until colLin) {
                        for (newVal in entries) {
                            if (checkValidInput(i_x, i_y, newVal)) {
                                field[i_y][i_x] = newVal
                                entries.remove(newVal as Int)
                                break
                            }
                        }
                    }
                }
            }
        }
        println("tries: $tries")
    }

    private fun checkValidInput(x: Int, y: Int, `val`: Int): Boolean {
        return checkBox(x, y, `val`) && checkLine(y, `val`) && checkColumn(x, `val`)
    }

    private fun checkBox(x: Int, y: Int, `val`: Int): Boolean {
        val xBox = x / boxPerColLin
        val yBox = y / boxPerColLin
        for (i_x in xBox * boxPerColLin until (xBox + 1) * boxPerColLin) {
            for (i_y in yBox * boxPerColLin until (yBox + 1) * boxPerColLin) {
                if (field[i_y][i_x] == `val`) return false
            }
        }
        return true
    }

    private fun checkLine(y: Int, `val`: Int): Boolean {
        for (i in 0 until colLin) {
            if (field[y][i] == `val`) return false
        }
        return true
    }

    private fun checkColumn(x: Int, `val`: Int): Boolean {
        for (i in 0 until colLin) {
            if (field[i][x] == `val`) return false
        }
        return true
    }

    fun displayInConsole() {
        for (l in 0 until colLin) {
            val line = field[l]
            for (col in 0 until colLin) {
                print(line[col].toString() + " ")
                if ((col + 1) % boxPerColLin == 0) print("|")
            }
            print("\n")
            if ((l + 1) % boxPerColLin == 0) {
                for (i in 0 until colLin) {
                    print("__")
                }
                print("\n")
            }
        }
    }
}