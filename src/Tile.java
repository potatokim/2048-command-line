public class Tile {
    private int value;

    Tile(int value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void clear() {
        this.value = 0;
    }

    public void moveTileValue(Tile dest) {
        dest.value = this.value;
        clear();
    }

    public int getValue() {
        return value;
    }
}
