import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuGenerator {
	private Integer[][] field;
	private int colLin;
	private int boxPerColLin;
	private final Integer[][] solution;
	private Integer[][][] possibles;
	private ArrayList<String> sp = new ArrayList<String>();
	public TimsSudokuField2(int boxPerColLin) {
		this.boxPerColLin = boxPerColLin;
		this.colLin = boxPerColLin * boxPerColLin;
		field = new Integer[colLin][colLin];
		possibles = new Integer[colLin][colLin][10];
		for (int i = 0; i < colLin; i++) {
			for (int j = 0; j < colLin; j++) {
				for (int j2 = 0; j2 < colLin; j2++) {
					possibles[i][j][j2]= 0;
				}
			}
		}
		generate();
		solution = field;
	}

	
	
	public Integer[] getHint(Integer[][] userField) {
		Integer[] mistake = checkForMistake(userField);
		if (mistake.length==0) {
			return mistake;
		} else {
			Integer[] hint = checkForHint(userField);
			return hint;
		}
	}
	
	/**
	 * 
	 * @return returns only one mistake even if there might be more than one
	 */
	private Integer[] checkForMistake(Integer[][] userField) {
		for (int y = 0; y < colLin; y++) {
			for (int x = 0; x < colLin; x++) {
				if (!userField[x][y].equals(solution[x][y])) {
					Integer[] r = {x,y,solution[x][y]};
					return r;
				}
			}
		}
		Integer[] r = {};
		return r;
	}
	
	private Integer[] checkForHint(Integer [][] userField) {
		for (int i = sp.size(); i > -1; i--) {
			String[] s =  sp.get(i).split(",");
			Integer[] si = new Integer [3];
			for (int j = 0; j < s.length; j++) {
				si[j]=Integer.parseInt(s[j]);
			}
			if (si[2] != userField[si[0]][si[1]]) {
				return si;
			}
			
		}
		Integer[] r = {};
		return r;
	}
	
	
	
	private void printSolution() {
		for (int i = 0; i < sp.size(); i++) {
			System.out.println(sp.get(i));
		}
	}

	public Integer[] randomHint() {
		int xx = createRandom();
		int yy = createRandom();
		for (int y = yy; y < colLin + yy; y++) {
			if (y > colLin - 1) {
				y = y - colLin;
			}
			for (int x = xx; x < colLin + xx; x++) {
				if (x > colLin - 1) {
					x = x - colLin;
				}
				if (field[x][y].equals(-1)) {
					int i = field[x][y];
					Integer[] r = { x, y, i };
					return r;
				}
			}
		}
		Integer[] r = {};
		return r;
	}

	public void createSolvable() {

		// generatePossibleValuesArray();
		Integer[] obv;
		int x;
		int y;
		int c = 0;
		int t = 0;
		int r = 0;
		int j;
		// c = anzahl der gelöschten felder, t = versuche ein feld zu löschen, r =
		// anzahl der random koordinaten, die erstellt
		analyzeField();
		while (c < 100 && t < 100 && r < 2000) {
			x = createRandom();
			y = createRandom();
			r++;
			if (field[y][x] != -1) {
				j = field[y][x];
				removeNumber(x, y);
				System.out.println("c: " + c + " t: " + t + " r: " + r);
				analyzeField();
				t++;
				obv = checkForObvious(x, y, j);
				if (obv.length == 0) {
					field[y][x] = j;
				} else {
					sp.add("" + x + "," + y + "," + j);
					c++;
					t = 0;
					r = 0;
				}

			}
		}
		printSolution();
	}

	/**
	 * 
	 * @return: Arraylist of Strings with the structure "x,y,i" x and y are the
	 *          coordinates of the wrong input and i the correct input
	 */
	public ArrayList<String> checkField() {
		ArrayList<String> r = new ArrayList<String>();
		for (int y = 0; y < colLin; y++) {
			for (int x = 0; x < colLin; x++) {
				if (field[y][x] != -1) {
					if (field[y][x] != solution[y][x]) {
						int i = solution[y][x];
						r.add("" + x + "," + y + "," + i);
					}
				}
			}
		}
		return r;
	}

	/**
	 * removing a number from the field equals setting that number to -1
	 * 
	 * @param x
	 * @param y
	 */
	private void removeNumber(int x, int y) {
		field[y][x] = -1;
	}

	private void analyzeField() {
		for (int y = 0; y < colLin; y++) {
			for (int x = 0; x < colLin; x++) {
				for (int i = 1; i < colLin + 1; i++) {
					possibles[y][x][i] = 0;
					if (field[y][x] == -1) {
						if (checkValidInput(x, y, i)) {
							possibles[y][x][i] = i;
						}
					}
				}
			}
		}
	}

	private Integer[] checkForObvious(int x, int y, int i) {
		Integer[] r;
		r = checkForOneSolution(x, y);
		if (r.length == 0) {
			r = checkForObviousColumn(x, i);
			if (r.length == 0) {
				r = checkForObviousRow(y, i);
				if (r.length == 0) {
					r = checkForObviousBox(x, y, i);
					if (r.length == 0) {
						return r;
					}
				}
			}
		}
		return r;
	}

	private Integer[] checkForObviousColumn(int x, int ii) {
		Boolean tempc = false;
		int c = 0;
		int y;
		for (int i = 1; i < colLin + 1; i++) {
			// check if i is already in the COlumn
			for (y = 0; y < colLin; y++) {
				if (field[y][x] == i) {
					tempc = true;
				}
			}
			// counts how often i is a possible Value in the row (if its exactly one, that
			// solution can be easily found by the user
			if (tempc == false) {
				for (y = 0; y < colLin; y++) {
					if (possibles[y][x][i] == i) {
						c++;
					}
				}
			}
			// returns the first position and the value that can be inserted by the user
			if (c == 1 && i == ii) {
				// TODO y ist aktuell nicht der korrekte wert der übergeben wird
				Integer[] r = { x, y, i };
				return r;
			}
		}
		// if there is no obvious solution the return array is empty
		Integer[] r = {};
		return r;
	}

	private Integer[] checkForObviousRow(int y, int ii) {
		Boolean tempc = false;
		int c = 0;
		int x;
		for (int i = 1; i < colLin + 1; i++) {
			tempc = false;
			// check if i is already in the Row
			for (x = 0; x < colLin; x++) {
				if (field[y][x] == i) {
					tempc = true;
				}
			}
			// counts how often i is a possible Value in the row (if its exactly one, that
			// solution can be easily found by the user
			if (tempc == false) {
				for (x = 0; x < colLin; x++) {
					if (possibles[y][x][i] == i) {
						c++;
					}
				}
			}

			// returns the first position and the value that can be inserted by the user
			if (c == 1 && i == ii) {
				// TODO x ist aktuell nicht der korrekte wert der übergeben wird
				Integer[] r = { x, y, i };
				return r;
			}
		}
		// if there is no obvious solution the return array is empty
		Integer[] r = {};
		return r;
	}

	private Integer[] checkForOneSolution(int x, int y) {
		int d = 0;
		int j;
		for (j = 1; j < colLin + 1; j++) {
			if (possibles[y][x][j] == 0) {
				d++;
			}
		}
		if (d == colLin - 1) {
			Integer[] r = { x, y };
			return r;
		}
		Integer[] r = {};
		return r;
	}

	private Integer[] checkForObviousBox(int xx, int yy, int ii) {
		Boolean tempc = false;
		int c = 0;

		int x2;
		int y2;
		int x = xx - (xx % boxPerColLin);
		int y = yy - (yy % boxPerColLin);

		for (int i = 1; i < colLin + 1; i++) {
			tempc = false;
			for (y2 = y; y2 < y + boxPerColLin; y2++) {
				// check if i is already in the Box
				for (x2 = x; x2 < x + boxPerColLin; x2++) {
					if (field[y2][x2] == i) {
						tempc = true;
					}
				}
			}
			// counts how often i is a possible Value in the row (if its exactly one, that
			// solution can be easily found by the user
			if (tempc == false) {
				for (y2 = y; y2 < y + boxPerColLin; y2++) {
					for (x2 = x; x2 < x + boxPerColLin; x2++) {
						if (possibles[y2][x2][i] == i) {
							c++;
						}

					}
				}
				if (c == 1 && i == ii) {
					// TODO: x and y are random variables that are returened and not the real
					// positions (we have to check for scope whether this is necessary or not
					Integer[] r = { x, y, i };
					return r;
				}
			}
		}
		Integer[] r = {};
		return r;
	}

	private Integer createRandom() {
		Random rand = new Random();
		int n = rand.nextInt(colLin);
		return n;
	}

	private Integer findZ(int x, int y) {

		int z = 0;
		int z1 = x / boxPerColLin;
		if (x % boxPerColLin != 0) {
			z1++;
		}
		int z2 = y / boxPerColLin;
		if (y % boxPerColLin != 0) {
			z2++;
		}
		z = z1 + boxPerColLin * z2;
		return z;
	}

	// ab hier ist es von TIm

	private void generate() {
		boolean solvable = false;
		int tries = 0;
		while (!solvable) {
			for (Integer[] line : field) {
				Arrays.fill(line, 0);
			}
			solvable = true;
			int numOfProblems = -colLin + 1;
			List<Integer> entries = IntStream.rangeClosed(1, colLin).boxed().collect(Collectors.toList());
			Collections.shuffle(entries);
			field[0] = entries.toArray(new Integer[0]);
			lineFor: for (int i_y = 1; i_y < colLin; i_y++) {
				while (Arrays.asList(field[i_y]).contains((Integer) 0)) {
					numOfProblems++;
					if (numOfProblems > 2000) {
						tries++;
						solvable = false;
						break lineFor;
					}
					entries = IntStream.rangeClosed(1, colLin).boxed().collect(Collectors.toList());
					Collections.shuffle(entries);
					for (int i_x = 0; i_x < colLin; i_x++) {
						for (int newVal : entries) {
							if (checkValidInput(i_x, i_y, newVal)) {
								field[i_y][i_x] = newVal;
								entries.remove((Integer) newVal);
								break;
							}
						}
					}
				}
			}
		}
		System.out.println("tries: " + tries);
	}

	private boolean checkValidInput(int x, int y, Integer val) {
		return checkBox(x, y, val) && checkLine(y, val) && checkColumn(x, val);
	}

	private boolean checkBox(int x, int y, Integer val) {
		int xBox = (x / boxPerColLin);
		int yBox = (y / boxPerColLin);
		for (int i_x = xBox * boxPerColLin; i_x < (xBox + 1) * boxPerColLin; i_x++) {
			for (int i_y = yBox * boxPerColLin; i_y < (yBox + 1) * boxPerColLin; i_y++) {
				if (field[i_y][i_x].equals(val))
					return false;
			}
		}
		return true;
	}

	private boolean checkLine(int y, Integer val) {
		for (int i = 0; i < colLin; i++) {
			if (field[y][i].equals(val))
				return false;
		}
		return true;
	}

	private boolean checkColumn(int x, Integer val) {
		for (int i = 0; i < colLin; i++) {
			if (field[i][x].equals(val))
				return false;
		}
		return true;
	}

	public void displayInConsole() {
		for (Integer l = 0; l < colLin; l++) {
			Integer[] line = field[l];
			for (Integer col = 0; col < colLin; col++) {
				System.out.print(line[col] + " ");
				if ((col + 1) % boxPerColLin == 0)
					System.out.print("|");
			}
			System.out.print("\n");
			if ((l + 1) % boxPerColLin == 0) {
				for (int i = 0; i < colLin; i++) {
					System.out.print("__");
				}
				System.out.print("\n");
			}
		}
	}
}