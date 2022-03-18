package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
        File file = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            for(int i = 0; i < ROWS; i++){
                for(int j = 0; j < COLUMNS; j++){
                    char tmp = (char) br.read();
                    if(tmp == 'r') field.getFieldArray()[i][j] = new Tile(Color.RED);
                    else if(tmp == 'y') field.getFieldArray()[i][j] = new Tile(Color.YELLOW);
                    else if(tmp == 'b') field.getFieldArray()[i][j] = new Tile(Color.BLUE);
                    else if(tmp == 'n') field.getFieldArray()[i][j] = new Tile(Color.NONE);
                    else throw new IllegalArgumentException("Wrong color in file or wrong size of field");
                }
                br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        field.updateField();
        return this.field;
    }

}
