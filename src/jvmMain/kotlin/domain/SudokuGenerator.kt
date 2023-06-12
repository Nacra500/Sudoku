package domain

import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream

class SudokuGenerator {
    public lateinit var field: Array<Array<Int>>
    private var colLin = 0
    private var boxPerColLin = 0
    public lateinit var solution: Array<Array<Int>>
    private lateinit var possibles: Array<Array<Array<Int?>>>
    private val sp = ArrayList<String>()
    fun TimsSudokuField2(boxPerColLin: Int) {
        this.boxPerColLin = boxPerColLin
        colLin = boxPerColLin * boxPerColLin
        field = Array(colLin) { Array(colLin){0} }
        possibles = Array(colLin) { Array(colLin) { arrayOfNulls(10) } }
        for (i in 0 until colLin) {
            for (j in 0 until colLin) {
                for (j2 in 0 until colLin) {
                    possibles[i][j][j2] = 0
                }
            }
        }
        generate()
        solution = field
    }

    fun getHint(userField: Array<Array<Int>>): Array<Int?> {
        val mistake = checkForMistake(userField)
        return if (mistake.size == 0) {
            mistake
        } else {
            checkForHint(userField)
        }
    }

    /**
     *
     * @return returns only one mistake even if there might be more than one
     */
    private fun checkForMistake(userField: Array<Array<Int>>): Array<Int?> {
        for (y in 0 until colLin) {
            for (x in 0 until colLin) {
                if (userField[x][y] != solution[x][y]) {
                    return arrayOf(x, y, solution[x][y])
                }
            }
        }
        return arrayOf()
    }

    private fun checkForHint(userField: Array<Array<Int>>): Array<Int?> {
        for (i in sp.size downTo -1 + 1) {
            val s =
                sp[i].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val si = arrayOfNulls<Int>(3)
            for (j in s.indices) {
                si[j] = s[j].toInt()
            }
            if (si[2] !== userField[si[0]!!][si[1]!!]) {
                return si
            }
        }
        return arrayOf()
    }

    private fun printSolution() {
        for (i in sp.indices) {
            println(sp[i])
        }
    }

    fun randomHint(): Array<Int> {
        val xx = createRandom()
        val yy = createRandom()
        var y = yy
        while (y < colLin + yy) {
            if (y > colLin - 1) {
                y = y - colLin
            }
            var x = xx
            while (x < colLin + xx) {
                if (x > colLin - 1) {
                    x = x - colLin
                }
                if (field[x][y] == -1) {
                    val i = field[x][y]
                    return arrayOf(x, y, i)
                }
                x++
            }
            y++
        }
        return arrayOf()
    }

    fun createSolvable() {

        // generatePossibleValuesArray();
        var obv: Array<Int?>
        var x: Int
        var y: Int
        var c = 0
        var t = 0
        var r = 0
        var j: Int
        // c = anzahl der gelöschten felder, t = versuche ein feld zu löschen, r =
        // anzahl der random koordinaten, die erstellt
        analyzeField()
        while (c < 100 && t < 100 && r < 2000) {
            x = createRandom()
            y = createRandom()
            r++
            if (field[y][x] != -1) {
                j = field[y][x]
                removeNumber(x, y)
                println("c: $c t: $t r: $r")
                analyzeField()
                t++
                obv = checkForObvious(x, y, j)
                if (obv.size == 0) {
                    field[y][x] = j
                } else {
                    sp.add("$x,$y,$j")
                    c++
                    t = 0
                    r = 0
                }
            }
        }
        printSolution()
    }

    /**
     *
     * @return: Arraylist of Strings with the structure "x,y,i" x and y are the
     * coordinates of the wrong input and i the correct input
     */
    fun checkField(): ArrayList<String> {
        val r = ArrayList<String>()
        for (y in 0 until colLin) {
            for (x in 0 until colLin) {
                if (field[y][x] != -1) {
                    if (field[y][x] !== solution[y][x]) {
                        val i = solution[y][x]
                        r.add("$x,$y,$i")
                    }
                }
            }
        }
        return r
    }

    /**
     * removing a number from the field equals setting that number to -1
     *
     * @param x
     * @param y
     */
    private fun removeNumber(x: Int, y: Int) {
        field[y][x] = -1
    }

    private fun analyzeField() {
        for (y in 0 until colLin) {
            for (x in 0 until colLin) {
                for (i in 1 until colLin + 1) {
                    possibles[y][x][i] = 0
                    if (field[y][x] == -1) {
                        if (checkValidInput(x, y, i)) {
                            possibles[y][x][i] = i
                        }
                    }
                }
            }
        }
    }

    private fun checkForObvious(x: Int, y: Int, i: Int): Array<Int?> {
        var r: Array<Int?>
        r = checkForOneSolution(x, y)
        if (r.size == 0) {
            r = checkForObviousColumn(x, i)
            if (r.size == 0) {
                r = checkForObviousRow(y, i)
                if (r.size == 0) {
                    r = checkForObviousBox(x, y, i)
                    if (r.size == 0) {
                        return r
                    }
                }
            }
        }
        return r
    }

    private fun checkForObviousColumn(x: Int, ii: Int): Array<Int?> {
        var tempc = false
        var c = 0
        var y: Int
        for (i in 1 until colLin + 1) {
            // check if i is already in the COlumn
            y = 0
            while (y < colLin) {
                if (field[y][x] == i) {
                    tempc = true
                }
                y++
            }
            // counts how often i is a possible Value in the row (if its exactly one, that
            // solution can be easily found by the user
            if (tempc == false) {
                y = 0
                while (y < colLin) {
                    if (possibles[y][x][i] == i) {
                        c++
                    }
                    y++
                }
            }
            // returns the first position and the value that can be inserted by the user
            if (c == 1 && i == ii) {
                // TODO y ist aktuell nicht der korrekte wert der übergeben wird
                return arrayOf(x, y, i)
            }
        }
        // if there is no obvious solution the return array is empty
        return arrayOf()
    }

    private fun checkForObviousRow(y: Int, ii: Int): Array<Int?> {
        var tempc = false
        var c = 0
        var x: Int
        for (i in 1 until colLin + 1) {
            tempc = false
            // check if i is already in the Row
            x = 0
            while (x < colLin) {
                if (field[y][x] == i) {
                    tempc = true
                }
                x++
            }
            // counts how often i is a possible Value in the row (if its exactly one, that
            // solution can be easily found by the user
            if (tempc == false) {
                x = 0
                while (x < colLin) {
                    if (possibles[y][x][i] == i) {
                        c++
                    }
                    x++
                }
            }

            // returns the first position and the value that can be inserted by the user
            if (c == 1 && i == ii) {
                // TODO x ist aktuell nicht der korrekte wert der übergeben wird
                return arrayOf(x, y, i)
            }
        }
        // if there is no obvious solution the return array is empty
        return arrayOf()
    }

    private fun checkForOneSolution(x: Int, y: Int): Array<Int?> {
        var d = 0
        var j: Int
        j = 1
        while (j < colLin + 1) {
            if (possibles[y][x][j] == 0) {
                d++
            }
            j++
        }
        return if (d == colLin - 1) {
            arrayOf(x, y)
        } else arrayOf()
    }

    private fun checkForObviousBox(xx: Int, yy: Int, ii: Int): Array<Int?> {
        var tempc = false
        var c = 0
        var x2: Int
        var y2: Int
        val x = xx - xx % boxPerColLin
        val y = yy - yy % boxPerColLin
        for (i in 1 until colLin + 1) {
            tempc = false
            y2 = y
            while (y2 < y + boxPerColLin) {

                // check if i is already in the Box
                x2 = x
                while (x2 < x + boxPerColLin) {
                    if (field[y2][x2] == i) {
                        tempc = true
                    }
                    x2++
                }
                y2++
            }
            // counts how often i is a possible Value in the row (if its exactly one, that
            // solution can be easily found by the user
            if (tempc == false) {
                y2 = y
                while (y2 < y + boxPerColLin) {
                    x2 = x
                    while (x2 < x + boxPerColLin) {
                        if (possibles[y2][x2][i] == i) {
                            c++
                        }
                        x2++
                    }
                    y2++
                }
                if (c == 1 && i == ii) {
                    // TODO: x and y are random variables that are returened and not the real
                    // positions (we have to check for scope whether this is necessary or not
                    return arrayOf(x, y, i)
                }
            }
        }
        return arrayOf()
    }

    private fun createRandom(): Int {
        val rand = Random()
        return rand.nextInt(colLin)
    }

    private fun findZ(x: Int, y: Int): Int {
        var z = 0
        var z1 = x / boxPerColLin
        if (x % boxPerColLin != 0) {
            z1++
        }
        var z2 = y / boxPerColLin
        if (y % boxPerColLin != 0) {
            z2++
        }
        z = z1 + boxPerColLin * z2
        return z
    }

    // ab hier ist es von TIm
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
