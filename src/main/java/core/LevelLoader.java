package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LevelLoader {
    private final int ROWS;
    private final int COLUMNS;
    private final Field field;

    public LevelLoader(int rows, int columns){
        ROWS = rows;
        COLUMNS = columns;
        field = new Field(ROWS, COLUMNS);
    }

    /**
     * Parse .txt file and generate field with given data
     * @param path path of file
     * @return generated field
     */
    public Field loadFieldFromFile(String path){
        checkSize(path);
        File file = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            for(int i = 0; i < ROWS; i++){
                for(int j = 0; j < COLUMNS; j++){
                    char tmp = (char) br.read();
                    if(tmp == 'r') field.getFieldArray()[i][j] = new Tile(Color.RED);
                    else if(tmp == 'y') field.getFieldArray()[i][j] = new Tile(Color.YELLOW);
                    else if(tmp == 'b') field.getFieldArray()[i][j] = new Tile(Color.BLUE);
                    else if(tmp == 'n') field.getFieldArray()[i][j] = new Tile(Color.NONE);
                    else throw new IllegalArgumentException("Wrong color in fil");
                }
                br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        field.updateField();
        return this.field;
    }

    private void checkSize(String path){
        File file = new File(path);
        int row = 0, column;
        try (Scanner scanner = new Scanner(file)){
            while(scanner.hasNextLine()){
                String tmp = scanner.nextLine();
                column = tmp.length();
                if(column != COLUMNS) throw new IllegalArgumentException("Wrong size of field");
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(row != ROWS) throw new IllegalArgumentException("Wrong size of field");
    }

}
