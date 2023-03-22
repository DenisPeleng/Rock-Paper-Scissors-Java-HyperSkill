package rockpaperscissors.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MenuLogic {
    private final Scanner scannerSystemIn = new Scanner(System.in);
    private final ComputerLogic computerLogic = new ComputerLogic();
    private final GameLogic gameLogic = new GameLogic();
    private final HashMap<String, Integer> rating = new HashMap<>();
    private String userName;

    public void startMenu() {

        setPlayerName();
        getRatingFromFile();
        getOptionsOfGame();
        while (true) {
            String userInput = scannerSystemIn.nextLine();
            if (userInput.equals("!exit")) {
                System.out.println("Bye!");
                return;
            }
            if (userInput.equals("!rating")) {
                printPlayerRating();
                continue;
            }
            if (!gameLogic.isCorrectInput(userInput)) {
                System.out.println("Invalid input");
            } else {
                String computerAnswer = computerLogic.generateComputerTurn(gameLogic);
                String gameResult = gameLogic.getGameResult(userInput, computerAnswer);
                gameLogic.printResults(userInput, computerAnswer);
                changeRatingAfterGame(gameResult);
            }
        }
    }

    private void getRatingFromFile() {
        String pathToRatingFile = "rating.txt";
        File ratingFile = new File(pathToRatingFile);
        try {
            if (ratingFile.isFile()) {
                Scanner scannerFile = new Scanner(new File(pathToRatingFile));
                while (scannerFile.hasNext()) {
                    String[] inputStr = scannerFile.nextLine().split(" ");
                    if (inputStr.length == 2) {
                        rating.put(inputStr[0], Integer.parseInt(inputStr[1]));
                    }
                }

            }

        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void setPlayerName() {
        System.out.println("Enter your name:");
        userName = scannerSystemIn.nextLine();
        System.out.printf("Hello, %s\n", userName);
    }

    private int getPlayerRating() {
        return rating.getOrDefault(userName, 0);
    }

    private void printPlayerRating() {
        System.out.printf("Your rating: %d", getPlayerRating());
    }

    private void changeRatingAfterGame(String gameResult) {
        if (!rating.containsKey(userName)) {
            rating.put(userName, 0);
        }
        int currentRating = rating.get(userName);
        int changeOfRating = switch (gameResult) {
            case "win" -> 100;
            case "draw" -> 50;
            default -> 0;
        };
        rating.put(userName, currentRating + changeOfRating);
    }

    private void getOptionsOfGame() {
        String[] options = scannerSystemIn.nextLine().split(",");
        if (options.length > 1) {
            gameLogic.setPossibleOptions(List.of(options));
        }
        System.out.println("Okay, let's start");
    }
}
