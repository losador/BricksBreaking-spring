import consoleUI.ConsoleUI;
import core.Field;
import core.GameState;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to BricksBreaking game");
        System.out.println("Enter size of field (y,x)");
        int y = sc.nextInt(), x = sc.nextInt();
        sc = new Scanner(System.in);
        System.out.println("If you want to generate field randomly type 'g', if you want to load field from file type 'l'");
        String mode = sc.nextLine();

        StringBuilder path = new StringBuilder("src/main/java/");
        Field field;
        if(mode.equals("l")){
            System.out.println("Put your file in src/main/java and enter name of file");
            path.append(sc.nextLine());
            field = new Field(y, x, path.toString());
        } else{
            field = new Field(y, x);
        }
        ConsoleUI ui = new ConsoleUI(field);

        while(true){
            ui.printField();
            System.out.println("Enter tile coordinates of tile you want to delete: ");
            int row = sc.nextInt(), column = sc.nextInt();
            field.markTiles(row, column);
//            System.out.println("After mark");
//            ui.printField();
            field.deleteTiles();
//            System.out.println("After delete");
//            ui.printField();
            field.updateField();
//            System.out.println("After update");
            if(field.isSolved()){
                field.setState(GameState.SOLVED);
                field.setScore(field.getScore() + 500);
                field.generateTiles();
            }
        }

    }
}
