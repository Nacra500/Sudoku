import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuGenerator{

    private Integer[][] field;
    private int colLin;
    private int boxPerColLin;

    public SudokuGenerator(int boxPerColLin){
        this.boxPerColLin = boxPerColLin;
        this.colLin = boxPerColLin * boxPerColLin;
        field = new Integer[colLin][colLin];
        //generate();
        for (Integer[] line: field){
            Arrays.fill(line, 0);
        }
        List<Integer> entries = IntStream.rangeClosed(1,colLin).boxed().collect(Collectors.toList());
        Collections.shuffle(entries);

        field[0] = entries.toArray(new Integer[0]);

        generate();
    }

    private void generate(){

        boolean solvable = false;

        while (!solvable) {
            for (Integer[] line: field){
                Arrays.fill(line, 0);
            }

            solvable = true;
            int numOfProblems = -colLin +1;

            List<Integer> entries = IntStream.rangeClosed(1,colLin).boxed().collect(Collectors.toList());
            Collections.shuffle(entries);

            field[0] = entries.toArray(new Integer[0]);

            lineFor: for (int i_y = 1; i_y < colLin; i_y++) {

                while (Arrays.asList(field[i_y]).contains((Integer) 0)) {
                    numOfProblems++;
                    if (numOfProblems > 2000) {
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
        System.out.println("tries: "+tries);
    }

    private boolean solve(){
            List<Integer> entries = Arrays.asList(1,2,3,4,5,6,7,8,9);

            for (int i_y = 1; i_y < colLin; i_y++) {
                    entries = IntStream.rangeClosed(1, colLin).boxed().collect(Collectors.toList());
                    Collections.shuffle(entries);

                    for (int i_x = 0; i_x < colLin; i_x++) {
                        if (field[i_y][i_x] != 0) continue;
                        for (int newVal : entries) {
                            if (checkValidInput(i_x, i_y, newVal)) {
                                field[i_y][i_x] = newVal;
                                //entries.remove((Integer) newVal);
                                if (solve()) return true;
                                else field[i_y][i_x] = 0;
                            }
                        }
                        if (field[i_y][i_x] == 0) return  false;
                    }
                }
            return true;
    }

    private boolean checkValidInput(int x, int y, Integer val){
        return checkBox(x,y,val) && checkLine(y, val) && checkColumn(x, val);
    }

    private boolean checkBox(int x, int y, Integer val){
        int xBox = (x/boxPerColLin) ;
        int yBox = (y/boxPerColLin) ;
        for (int i_x = xBox * boxPerColLin; i_x < (xBox+1) * boxPerColLin; i_x++){
            for (int i_y = yBox * boxPerColLin; i_y < (yBox+1) * boxPerColLin; i_y++){
                if (field[i_y][i_x].equals(val)) return false;
            }
        }
        return true;
    }

    private boolean checkLine(int y, Integer val){
        for(int i = 0; i < colLin; i++){
            if (field[y][i].equals(val)) return false;
        }
        return true;
    }

    private boolean checkColumn(int x, Integer val){
        for(int i = 0; i < colLin; i++){
            if (field[i][x].equals(val)) return false;
        }
        return true;
    }

    public void displayInConsole(){
        for (Integer l = 0; l < colLin; l++){
            Integer[] line = field[l];
            for (Integer col = 0; col < colLin; col++){
                System.out.print(line[col]+ " ");
                if ((col+1)%boxPerColLin == 0) System.out.print("|");
            }
            System.out.print("\n");
            if ( (l+1)%boxPerColLin == 0 ){
                for (int i = 0; i < colLin; i++ ){
                    System.out.print("__");
                }
                System.out.print("\n");
            }
        }
    }

}
