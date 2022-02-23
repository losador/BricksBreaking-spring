package consoleUI;

import core.Field;
import lombok.Data;

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
            System.out.print(i + "    ");
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

}
