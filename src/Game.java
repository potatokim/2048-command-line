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

    final static int BOARD_SIZE = 4;
    final static int EMPTY_TILE_VALUE = 0;
    final static int[] TILE_VALUES = {2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048};
    final static int TARGET = 2048;

    Tile[][] tiles;
    GameState gameState;
    boolean moved; // if (moved) && (isRunning()) createTile()
    int greatestTile;
    int score;

    public Game() {
        tiles = new Tile[BOARD_SIZE][BOARD_SIZE];
        gameState = GameState.STARTED;
        moved = false;
        score = 0;
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
                addTile(true);
                addTile(true);
                drawBoard();
                waitForMove();
            } else if (gameState == GameState.LOST) {
                System.out.println("=== YOU LOST ===");
                break;
            } else if (gameState == GameState.WON) {
                System.out.println("=== YOU WON ===");
                break;
            } else {
                addTile(false);
                drawBoard();
                waitForMove();
            }
        }
    }

    private void drawBoard() {
        System.out.println("(LEFT: A) (UP: W) (DOWN: S) (RIGHT D)");
        System.out.println("SCORE: " + score);
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                System.out.print(tiles[x][y].getValue() + " ");
            }
            System.out.print("\n");
        }
    }

    // generate random tiles of 2 or 4 at random empty location
    private void addTile(boolean tileValueIs2) {
        int x, y;
        do {
            x = new Random().nextInt(BOARD_SIZE);
            y = new Random().nextInt(BOARD_SIZE);
        } while (tiles[x][y].getValue() != EMPTY_TILE_VALUE);
        if (tileValueIs2)
            tiles[x][y].setValue(2);
        else
            tiles[x][y].setValue(new Random().nextInt() % 2 == 0 ? 2 : 4);
    }

    private void waitForMove() {
        moved = false;
        Scanner s = new Scanner(System.in);
        String move;
        MoveDirection dir;
        while (!moved) {
            move = s.next();
            move = move.toUpperCase();
            switch (move) {
                case "A":
                    dir = MoveDirection.LEFT;
                    moveLeft();
                    moved = true;
                    break;
                case "W":
                    dir = MoveDirection.UP;
                    moveUp();
                    moved = true;
                    break;
                case "S":
                    dir = MoveDirection.DOWN;
                    moveDown();
                    moved = true;
                    break;
                case "D":
                    dir = MoveDirection.RIGHT;
                    moveRight();
                    moved = true;
                    break;
                default:
                    moved = false;
            }
        }
    }

    // TODO: implement merge for all move methods + optimize gravity + refactor move
    private void moveUp() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 1; y < BOARD_SIZE; y++) {
                if (tiles[x][y].getValue() != EMPTY_TILE_VALUE) {
                    gravityUp(x, y);
                }
            }
        }
    }

    private void gravityUp(int x, int y) {
        while (y > 0 && tiles[x][y-1].getValue() == EMPTY_TILE_VALUE) {
            tiles[x][y].moveTileValue(tiles[x][y-1]);
            y--;
        }
    }

    private void moveDown() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 2; y >= 0; y--) {
                if (tiles[x][y].getValue() != EMPTY_TILE_VALUE) {
                    gravityDown(x, y);
                }
            }
        }
    }

    private void gravityDown(int x, int y) {
        while (y < BOARD_SIZE - 1 && tiles[x][y+1].getValue() == EMPTY_TILE_VALUE) {
            tiles[x][y].moveTileValue(tiles[x][y+1]);
            y++;
        }
    }

    private void moveLeft() {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 1; x < BOARD_SIZE; x++) {
                if (tiles[x][y].getValue() != EMPTY_TILE_VALUE) {
                    gravityLeft(x, y);
                }
            }
        }
    }

    private void gravityLeft(int x, int y) {
        while (x > 0 && tiles[x-1][y].getValue() == EMPTY_TILE_VALUE) {
            tiles[x][y].moveTileValue(tiles[x-1][y]);
            x--;
        }
    }

    private void moveRight() {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 2; x >= 0; x--) {
                if (tiles[x][y].getValue() != EMPTY_TILE_VALUE) {
                    gravityRight(x, y);
                }
            }
        }
    }

    private void gravityRight(int x, int y) {
        while (x < BOARD_SIZE - 1 && tiles[x+1][y].getValue() == EMPTY_TILE_VALUE) {
            tiles[x][y].moveTileValue(tiles[x+1][y]);
            x++;
        }
    }

    private void mergeAndClearTiles() {

    }

//    public boolean isLost() {
//        for (int y = 0; y < BOARD_SIZE; y++) {
//            for (int x = 0; x < BOARD_SIZE; x++) {
//                if (!tiles[x][y].isTaken)
//                    return false;
//            }
//        }
//        return true;
//    }
//
//    public boolean isWon() {
//        for (int y = 0; y < BOARD_SIZE; y++) {
//            for (int x = 0; x < BOARD_SIZE; x++) {
//                if (tiles[x][y].value == TARGET)
//                    return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean isRunning() {
//        return !(isLost() || isWon());
//    }

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}
