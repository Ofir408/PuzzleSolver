import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 
 * @author Ofir Ben Shoham.
 * A star algorithm, using Manhattan distance as an heuristic function.
 *
 */
public class LateAStar extends AbstractAlgorithm {
	private int boardSize;
	private String[][] stateBoardArray;
	private int counter = 0; 

	public LateAStar(GameInitialDetails gameDetails) {
		super(gameDetails);
		boardSize = gameDetails.getBoardSize();
	}

	// Documentation in base class.
	@Override
	public StateOnBoard findSolution() {
		long totalSize = 0, totalRemovedTime = 0, totalInsertTime = 0, insertedStates = 0;

		List<StateOnBoard> closedList = new ArrayList<>();
		PriorityQueue<StateOnBoard> openList = new PriorityQueue<StateOnBoard>(10,
				new StateComparator<>());
		StateOnBoard currState = new StateOnBoard(super.gameInitialDetails.getInitialBoardState(), "", 0, 0);
		openList.add(currState);


		while (!openList.isEmpty()) {
			long start = System.currentTimeMillis();
			StateOnBoard currentState = openList.remove();
			totalRemovedTime += System.currentTimeMillis() - start;

			if (closedList.contains(currentState))
				continue;
			super.addDevelopedNodes(1);

			if (currentState.getCurrentBoardState().equals(super.goalBoardState)) {
				currentState.addCost((int) (-currentState.getCost() + g(currentState)));
				currentState.addDevelopedNodes(-currentState.getDevelopedNodesNum() + super.developedNode);
				System.out.println("Inserted States= " + insertedStates);
				System.out.println("Total Insert time= " + totalInsertTime);
				System.out.println("Total Removed time= " + totalRemovedTime);
				System.out.println("open list avg size= " + totalSize / insertedStates);
				return currentState;
			}

			super.currentBoardState = currentState;
			Queue<StateOnBoard> childes = playOneStep();
			while (!childes.isEmpty()) {
				StateOnBoard s = childes.remove();
				long startTime = System.currentTimeMillis();
				openList.add(setStateFuncValues(s));
				totalInsertTime += System.currentTimeMillis() - startTime;
				insertedStates ++;
			}
			totalSize += openList.size();
			closedList.add(currentState);
		}
		System.out.println("Inserted States= " + insertedStates);
		System.out.println("Total Insert time= " + totalInsertTime);
		System.out.println("Total Removed time= " + totalRemovedTime);
		System.out.println("open list avg size= " + totalSize / insertedStates);
		return null; // not found.
	}

	// Documentation in base class.
	@Override
	public void addCost(int currCost) {
		super.currentBoardState.addCost(currCost);
	}

	// set the values of g, h function into AStarState object, and returns him.
	private AStarState setStateFuncValues(StateOnBoard state) {
		this.counter++;
		AStarState starState = new AStarState(state.getCurrentBoardState(), state.getPath(),
				state.getDevelopedNodesNum(), state.getCost(), counter);
		double gVal =  g(starState);
		double hVal = h(starState);

		starState.setgFuncVal(gVal);
		starState.sethFunc(hVal);
		return starState;
	}

	// h function is according the calculating of Manhattan distance.
	private double h(AStarState state) {
		double sum = 0;
		int maxNum = boardSize * boardSize;
		for (int number = 1; number < maxNum; number++) { // except zero.
			PairObj<Integer, Integer> placeOfCurrNum = getPlace(state, number); // get current place of this number.
			PairObj<Integer, Integer> requiredPlaceOfCurrNum = getRequiredPlace(number); // get required place of this number.
			sum += hHelper(placeOfCurrNum, requiredPlaceOfCurrNum);
		}
		return sum;
	}

	// returns a pair of (row, column) according the required place in the board of the given input number.
	private PairObj<Integer, Integer> getRequiredPlace(int number) {

		int index = number - 1;
		int row = index / boardSize;
		int column = index % boardSize;
		return new PairObj<Integer, Integer>(row, column);
	}

	// get (row,column) - real place into the board of the given number.
	private PairObj<Integer, Integer> getPlace(StateOnBoard currState, int number) {
		String strToSearch = String.valueOf(number);
		stateBoardArray = fromStringStateToArray(currState);
		for (int r = 0; r < boardSize; r++)
			for (int c = 0; c < boardSize; c++) {
				if (stateBoardArray[r][c].equals(strToSearch)) {
					return new PairObj<>(r, c);
				}
			}
		return null; // not found.
	}

	// convert from current state to board (double array)
	private String[][] fromStringStateToArray(StateOnBoard currentState) {
		String[] s = currentState.getCurrentBoardState().split("-");
		String[][] arrayToPrint = new String[boardSize][boardSize];
		int counter = 0;

		for (int r = 0; r < boardSize; r++)
			for (int c = 0; c < boardSize; c++) {
				arrayToPrint[r][c] = s[counter];
				counter += 1;
			}
		return arrayToPrint;
	}

	// Manhattan distance.
	private double hHelper(PairObj<Integer, Integer> real, PairObj<Integer, Integer> required) {
		int x1 = real.getKey();
		int x2 = required.getKey();
		int y1 = real.getValue();
		int y2 = required.getValue();
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	// returns g value function (length of the path).
	private double g(StateOnBoard state) {
		// the length of the path this state (cost of each step is 1).
		return state.getPath().length();
	}
}
