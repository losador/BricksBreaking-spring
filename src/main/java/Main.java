import consoleUI.ConsoleUI;
import core.Field;
import core.GameState;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        ui.initializeField();

        while(true){
            ui.play();
        }
    }
}
