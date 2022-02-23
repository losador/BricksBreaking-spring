import consoleUI.ConsoleUI;
import core.Field;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Field field = new Field(6, 6);
        ConsoleUI ui = new ConsoleUI(field);
        Scanner sc = new Scanner(System.in);
        while(true){
            ui.printField();
            System.out.println("Enter tile coordinates you want to delete: ");
            int row = sc.nextInt(), column = sc.nextInt();
            field.markTiles(row, column);
            System.out.println("After mark");
            ui.printField();
            field.deleteTiles();
            System.out.println("After delete");
            ui.printField();
            field.updateField();
            System.out.println("After update");
            if(field.isSolved()){
                field.generateTiles();
            }
        }

    }
}
