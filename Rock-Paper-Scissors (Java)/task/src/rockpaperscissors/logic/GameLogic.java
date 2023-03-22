package rockpaperscissors.logic;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private List<String> possibleOptions = List.of("rock", "paper", "scissors");

    public void setPossibleOptions(List<String> possibleOptions) {
        this.possibleOptions = possibleOptions;
    }

    public List<String> getPossibleOptions() {
        return possibleOptions;
    }

    public boolean isCorrectInput(String input) {
        return possibleOptions.contains(input);
    }

    public String getGameResult(String userTurn, String computerTurn) {
        List<String> currentComputerTurns = getCurrentTurns(userTurn);
        int resultIndex = getIndexOfTurn(computerTurn, currentComputerTurns);
        if (resultIndex == -1) {
            return "draw";
        }
        if (resultIndex >= currentComputerTurns.size() / 2) {
            return "win";
        }
        return "lose";
    }

    public void printResults(String userTurn, String computerTurn) {
        String result = getGameResult(userTurn, computerTurn);
        String resultText = switch (result) {
            case "win" -> String.format("Well done. The computer chose %s and failed", computerTurn);
            case "draw" -> String.format("There is a draw (%s)", computerTurn);
            case "lose" -> String.format("Sorry, but the computer chose %s", computerTurn);
            default -> "";
        };
        System.out.println(resultText);
    }

    private int getIndexOfTurn(String turn, List<String> currentTurns) {
        int resultValue = -1;
        for (int i = 0; i < currentTurns.size(); i++) {
            if (currentTurns.get(i).equals(turn)) {
                resultValue = i;
            }
        }
        return resultValue;
    }

    private List<String> getCurrentTurns(String turn) {
        List<String> currentTurns = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        boolean insertIntoBegin = false;
        for (String possibleName : possibleOptions) {
            if (possibleName.equals(turn)) {
                insertIntoBegin = true;
                continue;
            }
            if (!insertIntoBegin) {
                tempList.add(possibleName);
            } else {
                currentTurns.add(possibleName);
            }
        }
        currentTurns.addAll(tempList);
        return currentTurns;
    }
}
