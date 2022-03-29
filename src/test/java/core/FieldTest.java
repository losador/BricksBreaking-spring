package core;

import sk.tuke.gamestudio.game.core.Color;
import sk.tuke.gamestudio.game.core.Field;
import sk.tuke.gamestudio.game.core.LevelLoader;
import org.junit.jupiter.api.Test;

import sk.tuke.gamestudio.game.core.GameState;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FieldTest {
    private Random generator = new Random();
    private Field field;
    private int rowCount;
    private int columnCount;

    public FieldTest() {
        rowCount = generator.nextInt(5) + 5;
        columnCount = rowCount;
        field = new Field(rowCount, columnCount);
    }

    @Test
    public void checkForThreeColors(){
        field.generateTiles();
        List<Color> colors = new ArrayList<>();
        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++){
                if(!colors.contains(field.getFieldArray()[i][j].getTileColor())) colors.add(field.getFieldArray()[i][j].getTileColor());
            }
        }
        int size = colors.size();
        assertEquals(3, size, "Different count of colors in the field");
    }

    @Test
    public void checkForRightMark(){
        field = new LevelLoader(6, 6).loadFieldFromFile("src/main/java/field.txt");
        field.markTiles(4, 2);
        boolean isRight = true;
        if(!field.getFieldArray()[4][2].isMarked()) isRight = false;
        if(!field.getFieldArray()[4][3].isMarked()) isRight = false;
        assertTrue(isRight, "Tiles are marked incorrectly");
    }

    @Test
    public void checkForRightDelete(){
        field = new LevelLoader(6, 6).loadFieldFromFile("src/main/java/field.txt");
        field.markTiles(4, 2);
        field.deleteTiles();
        boolean isRight = true;
        if (field.getFieldArray()[4][2].getTileColor() != Color.NONE) isRight = false;
        if (field.getFieldArray()[4][3].getTileColor() != Color.NONE) isRight = false;
        assertTrue(isRight, "Tiles are deleted incorrectly");
    }

    @Test
    public void isSolvedTest1() {
        field = new LevelLoader(6, 6).loadFieldFromFile("src/main/java/field.txt");
        boolean isSolved = field.isSolved();
        assertFalse(isSolved, "Method isSolved works incorrectly");
    }

    @Test
    public void isSolvedTest2() {
        field = new LevelLoader(6, 6).loadFieldFromFile("src/main/java/test1.txt");
        boolean isSolved = field.isSolved();
        assertTrue(isSolved, "Method isSolved works incorrectly");
    }

    @Test
    public void isFailedTest1(){
        field = new LevelLoader(6, 6).loadFieldFromFile("src/main/java/failed.txt");

        field.isFailed();
        boolean isFailed = field.getState() == GameState.FAILED;

        assertFalse(isFailed, "Method isFailed works incorrectly");
    }

    @Test
    public void isFailedTest2(){
        field = new LevelLoader(6, 6).loadFieldFromFile("src/main/java/failed.txt");

        field.markTiles(4, 1);
        field.deleteTiles();
        field.markTiles(4, 2);
        field.deleteTiles();
        field.markTiles(4, 3);
        field.deleteTiles();

        field.isFailed();
        boolean isFailed = field.getState() == GameState.FAILED;

        assertTrue(isFailed, "Method isFailed works incorrectly");
    }

    @Test
    public void updateTest1(){
        field = new LevelLoader(6, 6).loadFieldFromFile("src/main/java/update.txt");

        field.markTiles(5, 2);
        field.markTiles(5, 3);
        field.deleteTiles();
        field.updateField();

        boolean isRight = true;
        if(field.getFieldArray()[5][1].getTileColor() != Color.BLUE) isRight = false;
        if(field.getFieldArray()[5][2].getTileColor() != Color.BLUE) isRight = false;
        if(field.getFieldArray()[4][1].getTileColor() != Color.NONE) isRight = false;
        if(field.getFieldArray()[4][2].getTileColor() != Color.NONE) isRight = false;

        assertTrue(isRight, "Method update works incorrectly");
    }

    @Test
    public void updateTest2(){
        field = new LevelLoader(6, 6).loadFieldFromFile("src/main/java/update2.txt");

        field.markTiles(5, 2);
        field.markTiles(4, 2);
        field.deleteTiles();
        field.updateField();

        boolean isRight = true;
        if(field.getFieldArray()[5][2].getTileColor() != Color.RED) isRight = false;
        if(field.getFieldArray()[4][2].getTileColor() != Color.YELLOW) isRight = false;
        if(field.getFieldArray()[5][3].getTileColor() != Color.NONE) isRight = false;
        if(field.getFieldArray()[4][3].getTileColor() != Color.NONE) isRight = false;

        assertTrue(isRight, "Method update works incorrectly");

    }
}
