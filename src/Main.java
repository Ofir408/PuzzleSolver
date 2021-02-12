import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ofir Ben Shoham.
 * Main function that executes the code.
 */
public class Main {

    public static void main(String[] args) {
        // read input from text file
        String directoryPath = args[0]; // input file name.
        List<String> inputPaths = getPaths(directoryPath);
        for (String path : inputPaths) {
            System.out.println("path= " + path);
            run(path);
        }
    }

    private static void run(String inputPath) {
        InputTextReader inputTextReader = new InputTextReader(inputPath);
        GameInitialDetails gameInitialDetails = inputTextReader.getInitialGameDetails();
        int boardSize = gameInitialDetails.getBoardSize();
        String stateToPrint = gameInitialDetails.getInitialBoardState();
        //System.out.println("stateToPrint is: " + stateToPrint + ", board size is: " + boardSize);
        //BoardPrinter.getInstance().printState(stateToPrint, boardSize);

        // Using Factory Design Pattern.
        AbstractAlgorithm AStarLate = AlgorithmFactory.generateAlgorithm(gameInitialDetails, 1);
        AbstractAlgorithm AStarEarly = AlgorithmFactory.generateAlgorithm(gameInitialDetails, 2);


        StateOnBoard solutionState = AStarLate.findSolution();
        System.out.println("---------------------A* Late: -------------------------");
        System.out.println("--------------- Solution was found! ------------------");
        System.out.println("path of solution is: " + solutionState.getPath());
        System.out.println("developed nodes number is: " + solutionState.getDevelopedNodesNum());
        System.out.println("cost: " + solutionState.getPath().length());
        System.out.println("--------------- ----------- ------------------");
        System.out.println("--------------- ----------- ------------------");

        solutionState = AStarEarly.findSolution();
        System.out.println("---------------------A* Early: -------------------------");
        System.out.println("--------------- Solution was found! ------------------");
        System.out.println("path of solution is: " + solutionState.getPath());
        System.out.println("developed nodes number is: " + solutionState.getDevelopedNodesNum());
        System.out.println("cost: " + solutionState.getPath().length());
        System.out.println("--------------- ----------- ------------------");

    }

    private static List<String> getPaths(String directoryPath) {
        List<String> results = new ArrayList<>();
        File[] files = new File(directoryPath).listFiles();
        if (files == null)
            return new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getPath());
            }
        }
        return results;
    }
}
