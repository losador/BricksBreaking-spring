package consoleUI;

import core.Field;
import core.GameState;
import lombok.Data;

import java.util.Scanner;

@Data
public class ConsoleUI {

    private Field field;

    public ConsoleUI(Field field){
        this.field = field;
    }

    public void printField(){
        System.out.println("Your score: " + this.field.getScore());
        for(int i = 0; i < field.getSingleDeleteCount(); i++){
            System.out.print("* ");
        }
        System.out.println();
        for(int i = 0; i < this.field.getCOLUMNS(); i++){
            if(i < 9) System.out.print(i + "    ");
            if(i >= 9) System.out.print(i + "   ");
        }
        System.out.println();

        for(int i = 0; i < this.field.getROWS(); i++){
            for(int j = 0; j < this.field.getCOLUMNS(); j++) {
                System.out.print(this.field.getFieldArray()[i][j].toString() + "    ");
            }
            System.out.print(i);
            System.out.println();
        }
    }

    public void play(){
        do{
            printField();
            handleInput();
            if(field.isSolved()){
                field.setState(GameState.SOLVED);
                field.setScore(field.getScore() + 500);
                break;
            }
        } while(field.getState() == GameState.PLAYING);
    }

    public void handleInput(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter tile coordinates of tile you want to delete: ");
        int row = sc.nextInt(), column = sc.nextInt();
        field.markTiles(row, column);
        field.deleteTiles();
        field.updateField();
    }

}
