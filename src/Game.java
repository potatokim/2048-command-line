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
                tiles[x][y] = new Tile(0);
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
            } else if (gameState == GameState.LOST) {
                System.out.println("=== YOU LOST ===");
                break;
            } else if (gameState == GameState.WON) {
                System.out.println("=== YOU WON ===");
                break;
            } else {
                addTile(false);
            }
            drawBoard();
            waitForMove();
        }
    }

    private void drawBoard() {
        System.out.println("(LEFT: A) (UP: W) (DOWN: S) (RIGHT D)");
        System.out.println("SCORE: " + score);
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (tiles[x][y].value == 0)
                    System.out.print("  ");
                else {
                    System.out.println(tiles[x][y] + " ");
                }
            }
            System.out.print("\n");
        }
    }

    // generate random tiles of 2 or 4 at random empty location
    private void addTile(boolean tileValueIs2) {
        int x = new Random().nextInt(BOARD_SIZE);
        int y = new Random().nextInt(BOARD_SIZE);
        new Tile(new Random().nextInt(TILE_VALUES.length));
        // stub
    }

    private void waitForMove() {
        Scanner s = new Scanner(System.in);
        String move;
        MoveDirection dir;
        do {
            move = s.next();
            switch (move) {
                case "A":
                    dir = MoveDirection.LEFT;
                    moveLeft();
                    moved = true;
                case "W":
                    dir = MoveDirection.UP;
                    moveUp();
                    moved = true;
                case "S":
                    dir = MoveDirection.DOWN;
                    moveDown();
                    moved = true;
                case "D":
                    dir = MoveDirection.RIGHT;
                    moveRight();
                    moved = true;
                default:
                    moved = false;
            }

        } while (!moved);
    }

    private void moveUp() {
        return; // stub
    }

    private void moveDown() {
        return; // stub
    }

    private void moveLeft() {
        return; // stub
    }

    private void moveRight() {
        return; // stub
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
