import consoleUI.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        ui.initializeField();

        while(true){
            ui.play();
        }
    }
}
