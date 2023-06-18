package domain

import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream

/**
 * A solver is a class which solves a given SudokuField.
 * The abstract Solver defines methods which every solver must have and implements a general backtracking algorithm.
 */
abstract class  AbstractSudokuSolver
/**
 * Instanciates a new Solver with a sudoku field
 *
 * @param field
 */
    (protected var field: SudokuField) {
    /**
     * every solver needs a method which returns if a value can generally be set on a certain position
     *
     * @param x
     * @param y
     * @param val: value which should be checked
     * @return boolean if possible
     */
    abstract fun checkValidInput(x: Int, y: Int, `val`: Int): Boolean

    /**
     * solves the Sudoku with a recursive backtracking algorithm
     *
     * @return if the field is solvable
     */
    fun solve(): Boolean {
        var entries: List<Int>
        for (i_y in 0 until field.COLLIN) { //for each line
            entries = IntStream.rangeClosed(1, field.COLLIN).boxed()
                .collect(Collectors.toList()) //this function create a list of all the allowed input numbers
            Collections.shuffle(entries)
            for (i_x in 0 until field.COLLIN) { // for each column
                if (!field.compareValue(i_x, i_y, SudokuField.Companion.EMPTY)) continue
                for (newVal in entries) { // for each general possible value
                    if (checkValidInput(i_x, i_y, newVal)) { //try if value can be set on the coorindates
                        field.setValue(i_x, i_y, newVal)
                        if (solve()) return true // check if sudoku stays solvable and return true if so
                        else field.setValue(
                            i_x,
                            i_y,
                            SudokuField.Companion.EMPTY
                        ) // else unset value and try next one of genereal possible values
                    }
                }
                if (field.compareValue(
                        i_x,
                        i_y,
                        SudokuField.Companion.EMPTY
                    )
                ) return false // return false if no value leads to a solvable sudoku
            }
        }
        return true //return true if all values are set
    }
}
