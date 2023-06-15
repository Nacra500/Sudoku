import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * an abstract version of an SudokuGames priving a general default implementations for all SudokuGames
 */
abstract class AbstractSudokuGame implements SudokuGame {	
	public final GameFactory.Difficulty DIFFICULTY;
	public final GameFactory.Size SIZE;
	
	private final AbstractSudokuSolver solver; // contains the specific solver for the game
	private AbstractSudokuGameFieldGenerator generator; // contains the specific generator for the game

	private SudokuField solutionField; // sudoku field containing the solution for specifed problem

	public AbstractSudokuGame(GameFactory.Size size, GameFactory.Difficulty difficulty) {
		solutionField = new SudokuField(size.COLLLIN);
		solver = initiateSolver(solutionField);
		this.DIFFICULTY = difficulty;
		this.SIZE = size;
	}

	/**
	 * abstract method to initate the solver. Must be implemented in child classes because every SudokuGame version has its own type of solver
	 *
	 * @param solutionField: SudokuField which should be solved
	 * @return an instace of a child tpye of an AbstractSolver
	 */
	protected abstract AbstractSudokuSolver initiateSolver(SudokuField solutionField);

	/**
	 * abstract method to initate the generator. Must be implemented in child classes because every SudokuGame version has its own type of generator
	 *
	 * @param gameField: SudokuField from which a solvable game field should be generated
	 * @return an instace of a child tpye of an AbstractGenerator
	 */
	protected abstract AbstractSudokuGameFieldGenerator initiateGenerator(SudokuField gameField);

	/**
	 * creates a starting point from which a sudoku can be solved. May differ for different versions of sudoku games.
	 * The default is the following method which is shuffels the list of general possible input values and inserts them in the first line.
	 */
	 protected void create_start() {
	        List<Integer> entries = IntStream.rangeClosed(1,solutionField.COLLIN).boxed().collect(Collectors.toList());
	        Collections.shuffle(entries);
	        
	        for (int x = 0; x < solutionField.COLLIN; x++) {
	        	solutionField.setValue(x, 0, entries.get(x));
	        }
	    }

	/**
	 * finds the first mistake in a given SudokuField. Helper function for the getHint method.
	 *
	 * @param field: which should be checked for mistakes
	 * @return SudokuCell containg the coordinates of the mistake and the solution value
	 */
	protected SudokuCell getMistake(SudokuField field) {
		 int colLin = field.COLLIN;
			for (int y = 0; y < colLin; y++) {
	            for (int x = 0; x < colLin; x++) {
	            	int val = solutionField.getCellValue(x, y);
	                if (field.getCellValue(x, y) != val && field.getCellValue(x, y) != SudokuField.EMPTY) {                	
	                   return new SudokuCell(x, y, val);
	                }
	            }
	        }
			return null;
	 }


	/**
	 * please find more information in SudokuGame interface
	 */
	@Override
	public SudokuField generate() {
		create_start();
		solver.solve();
		solutionField.displayInConsole();
		SudokuField gameField = new SudokuField(solutionField);
		generator = new SudokuGameFieldGenerator(gameField);
		final int removeCount = (int) gameField.TOTALNUMBEROFCELLS * DIFFICULTY.percentShown / 100;
		generator.createSolvable(removeCount);
		System.out.println(generator.getNumberOfRemovedCells());
		return gameField;
	}

	/**
	 * please find more information in SudokuGame interface
	 */
	@Override
	public SudokuField getSolution() {
		solver.solve();
		return solutionField;
	}

	/**
	 * please find more information in SudokuGame interface
	 */
	@Override
	public boolean checkValidInput(int x, int y, Integer val, SudokuField field) {
		return SudokuSolver.checkValidInput(x, y, field, val);
	}

	/**
	 * please find more information in SudokuGame interface
	 */
	@Override
	public List<SudokuCell> showMistakes(SudokuField field) {
		List<SudokuCell> differences = SudokuField.compareFields(field, solutionField);
		return differences.stream().filter(f -> f.val != 0).collect(Collectors.toList());
	}

	/**
	 * please find more information in SudokuGame interface
	 */
	@Override
	public SudokuCell getHint(SudokuField field) {
		SudokuCell mistake = getMistake(field);
		if (mistake != null) return mistake;
		else {
			SudokuCell generated = generator.getNextSolutionStep();
			while (field.getCellValue(generated.x, generated.y) != SudokuField.EMPTY && generated != null) {
				generated = generator.getNextSolutionStep();
			}
			return generated;
		}
	}


}
