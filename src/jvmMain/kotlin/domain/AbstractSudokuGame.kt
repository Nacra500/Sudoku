import domain.DIFFICULTIES
import domain.SIZES
import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream

/**
 * an abstract version of an SudokuGames priving a general default implementations for all SudokuGames
 */
internal abstract class AbstractSudokuGame(val size: SIZES, val difficulty: DIFFICULTIES) : SudokuGame {
    private val solver: AbstractSudokuSolver // contains the specific solver for the game
    private var generator: AbstractSudokuGameFieldGenerator? = null // contains the specific generator for the game
    private val solutionField: SudokuField = SudokuField(size.COLLIN) // sudoku field containing the solution for specifed problem

    init {
        solver = this.initiateSolver(solutionField)
    }

    /**
     * abstract method to initate the solver. Must be implemented in child classes because every SudokuGame version has its own type of solver
     *
     * @param solutionField: SudokuField which should be solved
     * @return an instace of a child tpye of an AbstractSolver
     */
    protected abstract fun initiateSolver(solutionField: SudokuField): AbstractSudokuSolver

    /**
     * abstract method to initate the generator. Must be implemented in child classes because every SudokuGame version has its own type of generator
     *
     * @param gameField: SudokuField from which a solvable game field should be generated
     * @return an instace of a child tpye of an AbstractGenerator
     */
    protected abstract fun initiateGenerator(gameField: SudokuField): AbstractSudokuGameFieldGenerator

    /**
     * creates a starting point from which a sudoku can be solved. May differ for different versions of sudoku games.
     * The default is the following method which is shuffels the list of general possible input values and inserts them in the first line.
     */
    protected fun create_start() {
        val entries = IntStream.rangeClosed(1, solutionField.COLLIN).boxed().collect(Collectors.toList())
        Collections.shuffle(entries)
        for (x in 0 until solutionField.COLLIN) {
            solutionField.setValue(x, 0, entries[x])
        }
    }

    /**
     * finds the first mistake in a given SudokuField. Helper function for the getHint method.
     *
     * @param field: which should be checked for mistakes
     * @return SudokuCell containg the coordinates of the mistake and the solution value
     */
    protected fun getMistake(field: SudokuField): SudokuCell? {
        val colLin = field.COLLIN
        for (y in 0 until colLin) {
            for (x in 0 until colLin) {
                val `val` = solutionField.getCellValue(x, y)
                if (field.getCellValue(x, y) != `val` && field.getCellValue(x, y) != SudokuField.Companion.EMPTY) {
                    return SudokuCell(x, y, `val`)
                }
            }
        }
        return null
    }

    /**
     * please find more information in SudokuGame interface
     */
    override fun generate(): SudokuField {
        create_start()
        solver.solve()
        solutionField.displayInConsole()
        val gameField = SudokuField(solutionField)
        generator = SudokuGameFieldGenerator(gameField)
        val removeCount = gameField.TOTALNUMBEROFCELLS * difficulty.percentShown / 100
        generator!!.createSolvable(removeCount)
        return gameField
    }

    override val solution: SudokuField
        /**
         * please find more information in SudokuGame interface
         */
        get() {
            solver.solve()
            return solutionField
        }

    /**
     * please find more information in SudokuGame interface
     */
    override fun checkValidInput(x: Int, y: Int, `val`: Int, field: SudokuField?): Boolean {
        return SudokuSolver.Companion.checkValidInput(x, y, field, `val`)
    }

    /**
     * please find more information in SudokuGame interface
     */
    override fun showMistakes(field: SudokuField): List<SudokuCell> {
        val differences: List<SudokuCell> = SudokuField.Companion.compareFields(field, solutionField)
        return differences.stream().filter { f: SudokuCell -> f.`val` != 0 }.collect(Collectors.toList())
    }

    /**
     * please find more information in SudokuGame interface
     */
    override fun getHint(field: SudokuField): SudokuCell? {
        val mistake = getMistake(field)
        return if (mistake != null) mistake else {
            var generated = SudokuCell(0, 0, 0)
            while (field.getCellValue(
                    generated!!.x,
                    generated!!.y
                ) != SudokuField.EMPTY && generated != null
            ) {
                generated = SudokuCell(0, 0, 0)
            }
            generated
        }
    }
}
