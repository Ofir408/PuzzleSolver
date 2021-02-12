/**
 * 
 * @author Ofir Ben Shoham
 * This class represents the initial details that the game has. 
 * Takes the details from the input text file, and returns this class.
 */
public class GameInitialDetails {
	
	private int algorithmNumber;
	private int boardSize;
	private String initialBoardState; 

	
	public GameInitialDetails(int algorithmNumber, int boardSize, String initialBoardState) {
		this.algorithmNumber = algorithmNumber;
		this.boardSize = boardSize;
		this.initialBoardState = initialBoardState;
	}
	
	
	
	// getters.
	public int getAlgorithmNumber() {
		return algorithmNumber;
	}
	public int getBoardSize() {
		return boardSize;
	}
	public String getInitialBoardState() {
		return initialBoardState;
	}

}
