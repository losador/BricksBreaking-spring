package core;

public class Tile {

    private Color tileColor;
    private boolean isMarked;

    public Tile(Color color){
        this.tileColor = color;
        this.isMarked = false;
    }

    @Override
    public String toString() {
        switch(this.tileColor){
            case RED: return "r";
            case YELLOW: return "y";
            case BLUE: return "b";
            case NONE: return " ";
            default: break;
        }
        return "0";
    }

    public Color getTileColor() {
        return tileColor;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void mark() {
        isMarked = true;
    }

    public void setTileColor(Color tileColor) {
        this.tileColor = tileColor;
    }


}
