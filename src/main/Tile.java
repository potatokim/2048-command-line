package main;

public class Tile {
    public final static int EMPTY_TILE_VALUE = 0;
    private int value;

    protected Tile(int value) {
        this.value = value;
    }

    protected void setValue(int value) {
        this.value = value;
    }

    protected void clear() {
        this.value = EMPTY_TILE_VALUE;
    }

    protected void moveTileValue(Tile dest) {
        dest.value = this.value;
        clear();
    }

    public void mergeAndClearTiles(Tile cleared) {
        if (this.value != cleared.value) {
            System.out.println("ERROR");
        }
        else {
            this.value *= 2;
            cleared.clear();
        }
    }

    public int getValue() {
        return value;
    }
}
