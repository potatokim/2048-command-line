package main;

public class MockGame extends Game {

    public void addTile(int x, int y, int value) {
        tiles[x][y].setValue(value);
    }
}
