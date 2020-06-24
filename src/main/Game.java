package main;

import java.util.Random;
import java.util.Scanner;

public class Game {
    public enum GameState {
        STARTED,
        RUNNING,
        LOST,
        WON;
    }

    public enum MoveDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }

    public final static int BOARD_SIZE = 4;
    public final static int EMPTY_TILE_VALUE = 0;
//    public final static int[] TILE_VALUES = {2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048};
    public final static int TARGET = 2048;

    public Tile[][] tiles;
    public GameState gameState;
    public boolean moved;
    public int greatestTile;
    public int emptyTilesLeft;
    public int score;

    public Game() {
        tiles = new Tile[BOARD_SIZE][BOARD_SIZE];
        gameState = GameState.STARTED;
        moved = false;
        score = 0;
        emptyTilesLeft = BOARD_SIZE * BOARD_SIZE;
        greatestTile = 2;

        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                tiles[x][y] = new Tile(EMPTY_TILE_VALUE);
            }
        }
    }

    // maintain a running game; stop if won or lost;
    public void run() {
        while (true) {
            if (gameState == GameState.STARTED) {
                gameState = GameState.RUNNING;
                addRandomTile(true);
                addRandomTile(true);
                drawBoard();
                move();
            } else if (gameState == GameState.LOST) {
                System.out.println("=== YOU LOST ===");
                break;
            } else if (gameState == GameState.WON) {
                System.out.println("=== YOU WON ===");
                break;
            } else {
                if (moved)
                    addRandomTile(false);
                drawBoard();
                move();
            }
        }
    }

    public void drawBoard() {
        System.out.println("(LEFT: A) (UP: W) (DOWN: S) (RIGHT: D)");
        System.out.println("SCORE: " + score);
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                System.out.print(tiles[x][y].getValue() + " ");
            }
            System.out.print("\n");
        }
    }

    // generate random tiles of 2 or 4 at random empty location
    public void addRandomTile(boolean tileValueIs2) {
        int x, y;
        do {
            x = new Random().nextInt(BOARD_SIZE);
            y = new Random().nextInt(BOARD_SIZE);
        } while (tiles[x][y].getValue() != EMPTY_TILE_VALUE);
        addTile(x, y, tileValueIs2);
    }

    public void addTile(int x, int y, boolean tileValueIs2) {
        if (tiles[x][y].getValue() != EMPTY_TILE_VALUE) {
            System.out.println("ERROR");
        } else {
            if (tileValueIs2)
                tiles[x][y].setValue(2);
            else
                tiles[x][y].setValue(new Random().nextInt() % 2 == 0 ? 2 : 4);

            emptyTilesLeft--;
            if (emptyTilesLeft == 0 && !isThereAnyAvailableMove())
                gameState = GameState.LOST;
        }
    }

    public boolean isThereAnyAvailableMove() {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (findMergeableTile(x, y, MoveDirection.UP) != -1 ||
                        findMergeableTile(x, y, MoveDirection.DOWN) != -1 ||
                        findMergeableTile(x, y, MoveDirection.LEFT) != -1 ||
                        findMergeableTile(x, y, MoveDirection.RIGHT) != -1)
                    return true;
            }
        }
        return false;
    }

    private void move() {
        moved = false;
        Scanner s = new Scanner(System.in);
        String move;
        move = s.next();
        move = move.toUpperCase();
        switch (move) {
            case "A":
                moveLeft();
                break;
            case "W":
                moveUp();
                break;
            case "S":
                moveDown();
                break;
            case "D":
                moveRight();
                break;
            default:
        }
    }

    public void moveUp() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (tiles[x][y].getValue() != EMPTY_TILE_VALUE) {
                    int i = findMergeableTile(x, y, MoveDirection.UP);
                    if (i != -1) {
                        moved = true;
                        handleMerge(x, y, i, false);
                    }
                    gravityUp(x, y);
                }
            }
        }
    }

    private void gravityUp(int x, int y) {
        while (y > 0 && tiles[x][y-1].getValue() == EMPTY_TILE_VALUE) {
            moved = true;
            tiles[x][y].moveTileValue(tiles[x][y-1]);
            y--;
        }
    }

    public void moveDown() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = BOARD_SIZE - 1; y >= 0; y--) {
                if (tiles[x][y].getValue() != EMPTY_TILE_VALUE) {
                    int i = findMergeableTile(x, y, MoveDirection.DOWN);
                    if (i != -1) {
                        moved = true;
                        handleMerge(x, y, i, false);
                    }
                    gravityDown(x, y);
                }
            }
        }
    }

    private void gravityDown(int x, int y) {
        while (y < BOARD_SIZE - 1 && tiles[x][y+1].getValue() == EMPTY_TILE_VALUE) {
            moved = true;
            tiles[x][y].moveTileValue(tiles[x][y+1]);
            y++;
        }
    }

    public void moveLeft() {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (tiles[x][y].getValue() != EMPTY_TILE_VALUE) {
                    int i = findMergeableTile(x, y, MoveDirection.LEFT);
                    if (i != -1) {
                        moved = true;
                        handleMerge(x, y, i, true);
                    }
                    gravityLeft(x, y);
                }
            }
        }
    }

    private void gravityLeft(int x, int y) {
        while (x > 0 && tiles[x-1][y].getValue() == EMPTY_TILE_VALUE) {
            moved = true;
            tiles[x][y].moveTileValue(tiles[x-1][y]);
            x--;
        }
    }

    public void moveRight() {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = BOARD_SIZE - 1; x >= 0; x--) {
                if (tiles[x][y].getValue() != EMPTY_TILE_VALUE) {
                    int i = findMergeableTile(x, y, MoveDirection.RIGHT);
                    if (i != -1) {
                        moved = true;
                        handleMerge(x, y, i, true);
                    }
                    gravityRight(x, y);
                }
            }
        }
    }

    private void gravityRight(int x, int y) {
        while (x < BOARD_SIZE - 1 && tiles[x+1][y].getValue() == EMPTY_TILE_VALUE) {
            moved = true;
            tiles[x][y].moveTileValue(tiles[x+1][y]);
            x++;
        }
    }

    private int findMergeableTile(int x, int y, MoveDirection m) {
        int tileXYValue = tiles[x][y].getValue();
        switch (m) {
            case UP:
                for (int i = y+1; i < BOARD_SIZE; i++) {
                    if (tiles[x][i].getValue() != tileXYValue && tiles[x][i].getValue() != EMPTY_TILE_VALUE)
                        break;
                    if (tiles[x][i].getValue() == tileXYValue)
                        return i;
                }
                break;
            case DOWN:
                for (int i = y-1; i >= 0; i--) {
                    if (tiles[x][i].getValue() != tileXYValue && tiles[x][i].getValue() != EMPTY_TILE_VALUE)
                        break;
                    if (tiles[x][i].getValue() == tileXYValue)
                        return i;
                }
                break;
            case LEFT:
                for (int i = x+1; i < BOARD_SIZE; i++) {
                    if (tiles[i][y].getValue() != tileXYValue && tiles[i][y].getValue() != EMPTY_TILE_VALUE)
                        break;
                    if (tiles[i][y].getValue() == tileXYValue)
                        return i;
                }
                break;
            case RIGHT:
                for (int i = x-1; i >= 0; i--) {
                    if (tiles[i][y].getValue() != tileXYValue && tiles[i][y].getValue() != EMPTY_TILE_VALUE)
                        break;
                    if (tiles[i][y].getValue() == tileXYValue)
                        return i;
                }
                break;
            default:
                System.out.println("ERROR");
        }
        return -1;
    }


    private void handleMerge(int x, int y, int i, boolean isHorizontal) {
        if (isHorizontal)
            tiles[x][y].mergeAndClearTiles(tiles[i][y]);
        else
            tiles[x][y].mergeAndClearTiles(tiles[x][i]);

        score += tiles[x][y].getValue();
        emptyTilesLeft++;

        if (tiles[x][y].getValue() == TARGET)
            gameState = GameState.WON;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}