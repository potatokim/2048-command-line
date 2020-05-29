package main;

public class MockGame extends Game {

    public MockGame() {
        super();
        gameState = GameState.RUNNING;
    }
    public void addTile(int x, int y, int value) {
        if (tiles[x][y].getValue() != EMPTY_TILE_VALUE) {
            System.out.println("ERROR");
        } else {
            tiles[x][y].setValue(value);

            emptyTilesLeft--;
            if (emptyTilesLeft == 0 && !isThereAnyAvailableMove())
                gameState = GameState.LOST;
        }
    }
}
