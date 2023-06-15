import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

abstract class AbstractSudokuGame implements SudokuGame {	
	public final GameFactory.Difficulty DIFFICULTY;
	public final GameFactory.Size SIZE;
	
	private final AbstractSudokuSolver solver;
	private AbstractSudokuGameFieldGenerator generator;

	private SudokuField solutionField;
	private SudokuField gameField;
	
	public AbstractSudokuGame(GameFactory.Size size, GameFactory.Difficulty difficulty) {
		solutionField = new SudokuField(size.COLLLIN);
		solver = initiateSolver(solutionField);
		this.DIFFICULTY = difficulty;
		this.SIZE = size;
	}
	
	protected abstract AbstractSudokuSolver initiateSolver(SudokuField solutionField);
	
	protected abstract AbstractSudokuGameFieldGenerator initiateGenerator(SudokuField gameField);

	 protected void create_start() {
	        List<Integer> entries = IntStream.rangeClosed(1,solutionField.COLLIN).boxed().collect(Collectors.toList());
	        Collections.shuffle(entries);
	        
	        for (int x = 0; x < solutionField.COLLIN; x++) {
	        	solutionField.setValue(x, 0, entries.get(x));
	        }
	    }
	 
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
	
	@Override
	public SudokuField generate() {
		create_start();
		solver.solve();
		solutionField.displayInConsole();
		gameField = new SudokuField(solutionField);
		generator = new SudokuGameFieldGenerator(gameField);
		final int removeCount = (int) gameField.TOTALNUMBEROFCELLS * DIFFICULTY.percentShown / 100;
		generator.createSolvable(removeCount);
		System.out.println(generator.getNumberOfRemovedCells());
		return gameField;
	}

	@Override
	public SudokuField getSolution() {
		solver.solve();
		return solutionField;
	}

	@Override
	public boolean checkValidInput(int x, int y, Integer val, SudokuField field) {
		return SudokuSolver.checkValidInput(x, y, field, val);
	}

	@Override
	public List<SudokuCell> showMistakes(SudokuField field) {
		List<SudokuCell> differences = SudokuField.compareFields(field, solutionField);
		return differences.stream().filter(f -> f.val != 0).collect(Collectors.toList());
	}

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
