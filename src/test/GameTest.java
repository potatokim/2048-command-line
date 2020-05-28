package test;

import main.MockGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    private MockGame game;
    private int prevScore;

    @BeforeEach
    public void runBefore() {
        game = new MockGame();
        prevScore = 0;
    }

    @Test
    public void testBasicMoves() {
        game.addTile(1, 1,2);
        game.addTile(1, 2, 2);

        game.moveUp();
        assertEquals(4, game.tiles[1][0].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[1][1].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[1][2].getValue());
        assertEquals(4, game.score - prevScore);

        prevScore = game.score;
        game.addTile(1, 1, 2);

        game.moveDown();
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[1][0].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[1][1].getValue());
        assertEquals(4, game.tiles[1][2].getValue());
        assertEquals(2, game.tiles[1][3].getValue());
        assertEquals(0, game.score - prevScore);

        prevScore = game.score;
        game.addTile(3, 3, 2);

        game.drawBoard();
        game.moveLeft();
        assertEquals(4, game.tiles[0][2].getValue());
        assertEquals(4, game.tiles[0][3].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[1][2].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[1][3].getValue());
        assertEquals(4, game.score - prevScore);

        prevScore = game.score;
        game.addTile(3, 3, 4);

        game.moveRight();
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[0][2].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[0][3].getValue());
        assertEquals(4, game.tiles[3][2].getValue());
        assertEquals(8, game.tiles[3][3].getValue());
        assertEquals(8, game.score - prevScore);
    }

    @Test
    public void testComplexMoves() {
        game.addTile(0, 0, 4);
        game.addTile(0, 1, 8);
        game.addTile(1, 0, 4);
        game.addTile(1, 1, 8);
        game.addTile(1, 2, 32);
        game.addTile(1, 3, 2);
        game.addTile(2, 0 ,2);
        game.addTile(2, 1, 32);
        game.addTile(3, 0, 4);
        game.addTile(3, 1, 2);

        game.moveUp();
        assertEquals(0, game.score - prevScore);

        prevScore = game.score;

        game.moveDown();
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[0][0].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[0][1].getValue());
        assertEquals(4, game.tiles[0][2].getValue());
        assertEquals(8, game.tiles[0][3].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[2][0].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[2][1].getValue());
        assertEquals(2, game.tiles[2][2].getValue());
        assertEquals(32, game.tiles[2][3].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[3][0].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[3][1].getValue());
        assertEquals(4, game.tiles[3][2].getValue());
        assertEquals(2, game.tiles[3][3].getValue());
        assertEquals(0, game.score - prevScore);

        game.addTile(2, 0, 2);
        prevScore = game.score;

        game.moveLeft();
        assertEquals(4, game.tiles[0][0].getValue());
        assertEquals(8, game.tiles[0][1].getValue());
        assertEquals(2, game.tiles[1][0].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[1][1].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[2][0].getValue());
        assertEquals(0, game.score - prevScore);

        game.addTile(3, 0, 2);
        prevScore = game.score;

        game.moveRight();
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[0][0].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[0][1].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[1][0].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[1][1].getValue());
        assertEquals(4, game.tiles[2][0].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[2][1].getValue());
        assertEquals(4, game.tiles[3][0].getValue());
        assertEquals(8, game.tiles[3][1].getValue());
        assertEquals(4, game.score - prevScore);
    }

    @Test
    public void testIntegrationMoveAndMerge() {
        game.addTile(0, 0, 16);
        game.addTile(0, 1, 16);
        game.addTile(0, 2, 16);
        game.addTile(0, 3, 2);
        game.addTile(1, 0, 4);
        game.addTile(1, 1, 4);
        game.addTile(1, 2, 4);
        game.addTile(1, 3, 4);
        game.addTile(2, 1, 16);
        game.addTile(2, 2, 64);
        game.addTile(2, 3, 4);
        game.addTile(3, 2, 4);
        game.addTile(3, 3, 2);

        game.moveUp();
        assertEquals(32, game.tiles[0][0].getValue());
        assertEquals(16, game.tiles[0][1].getValue());
        assertEquals(2, game.tiles[0][2].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[0][3].getValue());
        assertEquals(8, game.tiles[1][0].getValue());
        assertEquals(8, game.tiles[1][1].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[1][2].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[1][3].getValue());
        assertEquals(16, game.tiles[2][0].getValue());
        assertEquals(64, game.tiles[2][1].getValue());
        assertEquals(4, game.tiles[2][2].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[2][3].getValue());
        assertEquals(4, game.tiles[3][0].getValue());
        assertEquals(2, game.tiles[3][1].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[3][2].getValue());
        assertEquals(game.EMPTY_TILE_VALUE, game.tiles[3][3].getValue());
        assertEquals(48, game.score - prevScore);
    }

    @Test
    public void testIsLostActuallyNotLost() {
        // full board, but with one singular available move
    }

    @Test
    public void testIsLostActuallyLost() {

    }

    @Test
    public void testIsWon() {
        // full board, but has 2048
    }
}
