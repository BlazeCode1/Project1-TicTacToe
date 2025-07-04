import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


public class Main {

    private static final char[][] board = new char[3][3];
    private static char currentPlayer = 'X';

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            StartGame(sc);
        } catch (StackOverflowError e) {
            System.out.println(e.getMessage());
        }


    }

    public static void StartGame(Scanner sc) {
        int choice = -1;
        try {
            do {
                try {
                    System.out.println("""
                            4 Modes Available:
                            1- Three Games vs Computer. (EASY)
                            2- One Game vs Computer. (EASY)
                            3- One Game vs Computer. (HARD)
                            4- Player vs Player
                            0- Exit Game.""");
                    choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            threeGames();
                            break;
                        case 2:
                            easyGameMode();
                            break;
                        case 3:
                            hardGameMode();
                            break;
                        case 4:
                            playerVsPlayer();
                            break;
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Please Input a number between (0-4)");
                    sc.nextLine();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    sc.nextLine();
                }
            } while (choice != 0);
        } catch (StackOverflowError | Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void easyGameMode() {
        initializeBoard();
        while (true) {
            printBoard();
            try {
                if (currentPlayer == 'X') {
                    playerMove();
                } else {
                    computerMoveEasy();
                }
                if (isWinner(currentPlayer)) {
                    printBoard();
                    System.out.println("Player " + currentPlayer + " wins!");
                    break;
                }
                if (isBoardFull()) {
                    printBoard();
                    System.out.println("The game is a tie!");
                    break;
                }
                switchPlayer();
            } catch (InputMismatchException e) {
                System.out.println("Input a number!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public static void hardGameMode() {
        initializeBoard();

        while (true) {

            printBoard();
            try {
                if (currentPlayer == 'X') {
                    playerMove();
                } else {
                    computerMoveHard();
                }
                if (isWinner(currentPlayer)) {
                    printBoard();
                    System.out.println("Player " + currentPlayer + " wins!");
                    break;
                }
                if (isBoardFull()) {
                    printBoard();
                    System.out.println("The game is a tie!");
                    break;
                }

                switchPlayer();
            } catch (InputMismatchException _) {
                System.out.println("Input a number!.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public static void threeGames() {
        int playerWins = 0;
        int computerWins = 0;
        while (playerWins < 3 && computerWins < 3) {

            if (playerWins == 2 && computerWins == 2)
                System.out.println("ITS ONN!!");
            initializeBoard();
            while (true) {

                printBoard();

                try {
                    if (currentPlayer == 'X') {
                        playerMove();
                    } else {
                        computerMoveEasy();
                    }
                    if (isWinner(currentPlayer)) {
                        System.out.println((currentPlayer == 'X' ? "You" : "Computer") + " Win!.");
                        if (currentPlayer == 'X') {
                            playerWins++;
                        } else {
                            computerWins++;
                        }
                        break;
                    }
                    if (isBoardFull()) {
                        printBoard();
                        System.out.println("The game is a tie!");
                        break;
                    }
                    switchPlayer();
                } catch (InputMismatchException _) {
                    System.out.println("Input a number!.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Score is : Player = " + playerWins + ", Computer = " + computerWins);
        }
        if (playerWins == 3) {
            System.out.println("You Won!");
        } else {
            System.out.println("Computer Won...");
        }


    }

    public static void playerVsPlayer() {
        initializeBoard();
        while (true) {
            printBoard();
            try {
                playerMove();
                if (isWinner(currentPlayer)) {
                    System.out.println("Player " + (currentPlayer == 'X' ? "1" : "2") + " Wins!.");
                    break;
                }
                if (isBoardFull()) {
                    System.out.println("Its A Tie!.");
                    break;
                }
                switchPlayer();
            } catch (InputMismatchException _) {
                System.out.println("Input a number!.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public static boolean isDiagWin(char player) {
        for (int i = 0; i < 3; i++) {
            if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                    (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNaturalWin(char player) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                    (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        return false;
    }

    public static int minmax(boolean isMax) {
        if (isWinner('X')) return -1;

        if (isWinner('O')) return 1;
        if (isBoardFull()) return 0;
        int bestValue;
        if (isMax) {
            bestValue = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        int v = minmax(false);
                        board[i][j] = ' ';
                        bestValue = Math.max(bestValue, v);
                    }
                }
            }

        } else {
            bestValue = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        int v = minmax(true);
                        board[i][j] = ' ';
                        bestValue = Math.min(bestValue, v);
                    }
                }
            }
        }
        return bestValue;

    }

    public static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
        currentPlayer = 'X';
    }

    public static void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    public static void playerMove() {
        Scanner scanner = new Scanner(System.in);
        int row, col;
        while (true) {
            try {
                System.out.println("Player  " + currentPlayer + " , enter your move (row and column): ");
                row = scanner.nextInt();
                col = scanner.nextInt();

                if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
                    board[row][col] = currentPlayer;
                    break;
                } else {
                    throw new Exception("This move is not valid");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input a number!.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }

        }
    }

    public static void computerMoveEasy() {
        Random random = new Random();
        int row, col;

        while (true) {
            System.out.println("Computer, Move ");
            row = random.nextInt(3);
            col = random.nextInt(3);

            if (isValidMove(row, col)) {
                board[row][col] = currentPlayer;
                break;
            }
        }
    }

    public static void computerMoveHard() {
        int bestScore = Integer.MIN_VALUE;
        int moveRow = -1;
        int moveCol = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'O';
                    int v = minmax(false);
                    board[i][j] = ' ';

                    if (v > bestScore) {
                        bestScore = v;
                        moveRow = i;
                        moveCol = j;
                    }


                }
            }
        }
        System.out.println("Computer Moves");
        board[moveRow][moveCol] = 'O';
    }

    public static boolean isValidMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ';
    }

    public static boolean isWinner(char player) {
        if (isNaturalWin(player))
            return true;

        if (isDiagWin(player))
            return true;
        return false;
    }

    public static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}