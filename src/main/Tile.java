package main;

public class Tile {
    private int value;

    protected Tile(int value) {
        this.value = value;
    }

    protected void setValue(int value) {
        this.value = value;
    }

    protected void clear() {
        this.value = 0;
    }

    protected void moveTileValue(Tile dest) {
        dest.value = this.value;
        clear();
    }

    public int getValue() {
        return value;
    }
}
