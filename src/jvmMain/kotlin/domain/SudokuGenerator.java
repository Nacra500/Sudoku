import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class SudokuGenerator {
    private Integer[][] field;
    private int colLin;
    private int boxPerColLin;
    private Integer[][][] possibles;
    public SudokuGenerator(int boxPerColLin) {
        this.boxPerColLin = boxPerColLin;
        this.colLin = boxPerColLin * boxPerColLin;
        field = new Integer[colLin][colLin];
        possibles = new Integer[colLin][colLin][10];
        for (int i = 0; i < colLin; i++) {
            for (int j = 0; j < colLin; j++) {
                for (int j2 = 0; j2 < colLin; j2++) {
                    possibles[i][j][j2]= -1;
                }
            }
        }
        generate();
    }









    public void createSolvable() {

        //generatePossibleValuesArray();
        Integer[] obv;
        int x;
        int y;
        int c = 0;
        int t = 0;
        int r = 0;
        // c = anzahl der gelöschten felder, t = versuche ein feld zu löschen, r = anzahl der random koordinaten, die erstellt
        while (c<81 && t<c+20) {
            analyzeField();
            x = createRandom();
            y = createRandom();
            r++;
            if (field[y][x]!=-1) {
                int j = field[y][x];
                removeNumber(x,y);
                System.out.println("c: "+c+ " t: "+t+ " r: "+r);
                analyzeField();
                c++;
                t++;
                obv =checkForObvious1();
                if (obv.length == 0) {
                    field[y][x]=j;
                    c--;
                }

            }
        }
    }

    public Boolean checkCompletedField() {
        boolean r = true;
        for (int y = 0; y < colLin; y++) {
            for (int x = 0; x < colLin;x++) {
                r = checkValidInput(x,y,field[x][y]);
                if (r == false) {
                    return r;
                }
            }
        }
        return r;
    }

    private void removeNumber(int x, int y) {
        field[y][x] = -1;
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

    private void analyzeField() {
        for (int y = 0; y < colLin; y++) {
            for (int x = 0; x < colLin;x++) {
                for (int i = 1; i < 10; i++) {
                    if (field[y][x] == -1) {
                        if (checkValidInput(x,y,i)) {
                            possibles[y][x][i]=i;
                        }
                    } else {
                        possibles[y][x][i]=0;
                    }
                }
            }
        }
    }
    /**
     *
     * @return TODO: korrekte Angabe der Koordinate und des Wertes, der auf jeden Fall einfach gefunden werden kann, es mucss glaub ich nicht immer der sein, den man entfernt hat, sonst wäre es gar nicht notwendig zu returnen, da der wert dann schon die random zahlen sind
     */
    private Integer[] checkForObvious1() {
        Integer[] r;
        r = checkForObviousColumn();
        if (r.length == 0) {
            r = checkForObviousRow();
            if (r.length == 0) {
                return r;
            }
        } else {
            r = checkForObviousBox();
            if (r.length == 0) {
                return r;
            }
        }
        return r;
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
    private Integer[] checkForObviousColumn() {
        Boolean tempc = false;
        int c = 0;
        int x;
        int y;
        for (int i = 1; i<colLin; i++) {
            for (x = 0; x < colLin; x++) {
                //check if i is already in the COlumn
                for (y = 0; y < colLin; y++) {
                    if (field[y][x] == i) {
                        tempc = true;
                    }
                }
                //counts how often i is a possible Value in the row (if its exactly one, that solution can be easily found by the user
                if (tempc == false) {
                    for (y = 0; y < colLin; y++) {
                        if (possibles[y][x][i]== i) {
                            c++;
                        }
                    }
                }
                //returns the first position and the value that can be inserted by the user
                if (c == 1) {
                    //TODO y ist aktuell nicht der korrekte wert der übergeben wird
                    Integer[] r = {x,y,i};
                    return r;
                }
            }
        }
        //if there is no obvious solution the return array is empty
        Integer[] r = {};
        return r;
    }

    private Integer[] checkForObviousColumn(int x) {
        Boolean tempc = false;
        int c = 0;
        int y;
        for (int i = 1; i<colLin; i++) {
            //check if i is already in the COlumn
            for (y = 0; y < colLin; y++) {
                if (field[y][x] == i) {
                    tempc = true;
                }
            }
            //counts how often i is a possible Value in the row (if its exactly one, that solution can be easily found by the user
            if (tempc == false) {
                for (y = 0; y < colLin; y++) {
                    if (possibles[y][x][i]== i) {
                        c++;
                    }
                }
            }
            //returns the first position and the value that can be inserted by the user
            if (c == 1) {
                //TODO y ist aktuell nicht der korrekte wert der übergeben wird
                Integer[] r = {x,y,i};
                return r;
            }
        }
        //if there is no obvious solution the return array is empty
        Integer[] r = {};
        return r;
    }

    private Integer[] checkForObviousRow() {
        Boolean tempc = false;
        int c = 0;
        int x;
        int y;
        for (int i = 1; i<colLin; i++) {
            for (y = 0; y < colLin; y++) {
                tempc = false;
                //check if i is already in the Row
                for (x = 0; x < colLin; x++) {
                    if (field[y][x] == i) {
                        tempc = true;
                    }
                }
                //counts how often i is a possible Value in the row (if its exactly one, that solution can be easily found by the user
                if (tempc == false) {
                    for (x = 0; x < colLin; x++) {
                        if (possibles[y][x][i]==i) {
                            c++;
                        }
                    }
                }
                //returns the first position and the value that can be inserted by the user
                if (c == 1) {
                    //TODO x ist aktuell nicht der korrekte wert der übergeben wird
                    Integer[] r = {x,y,i};
                    return r;
                }
            }
        }
        //if there is no obvious solution the return array is empty
        Integer[] r = {};
        return r;
    }


    private Integer[] checkForObviousRow(int y) {
        Boolean tempc = false;
        int c = 0;
        int x;
        for (int i = 1; i<colLin; i++) {
            tempc = false;
            //check if i is already in the Row
            for (x = 0; x < colLin; x++) {
                if (field[y][x] == i) {
                    tempc = true;
                }
            }
            //counts how often i is a possible Value in the row (if its exactly one, that solution can be easily found by the user
            if (tempc == false) {
                for (x = 0; x < colLin; x++) {
                    if (possibles[y][x][i]==i) {
                        c++;
                    }
                }
            }
            //returns the first position and the value that can be inserted by the user
            if (c == 1) {
                //TODO x ist aktuell nicht der korrekte wert der übergeben wird
                Integer[] r = {x,y,i};
                return r;
            }
        }
        //if there is no obvious solution the return array is empty
        Integer[] r = {};
        return r;
    }


    private Integer[] checkForObviousBox() {
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
            }
        }
        //if there is no obvious solution the return array is empty
        Integer[] r = {};
        return r;
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


    private Integer createRandom() {
        Random rand = new Random();
        int n = rand.nextInt(colLin);
        return n;
    }

    //ab hier ist es von TIm

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