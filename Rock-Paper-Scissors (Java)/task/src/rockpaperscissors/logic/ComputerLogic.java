package rockpaperscissors.logic;

import java.util.List;
import java.util.Random;

public class ComputerLogic {
    public String generateComputerTurn(GameLogic gameLogic) {
        List<String> possibleValues = gameLogic.getPossibleOptions();
        int computerTurn = new Random().nextInt(possibleValues.size());
        return possibleValues.get(computerTurn);
    }
}
