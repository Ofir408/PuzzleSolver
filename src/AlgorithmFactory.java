/**
 * \
 *
 * @author Ofir Ben Shoham.
 * Factory Design Pattern, returns the algorithm after getting his number.
 */
public class AlgorithmFactory {

    // return AbstractAlgorithm according the input algorithmNum.
    public static AbstractAlgorithm generateAlgorithm(GameInitialDetails gameDetails, int algorithmNum) {
		if (algorithmNum == 1) {
			return new LateAStar(gameDetails);
		}
		return new EarlyAStar(gameDetails);
	}

}
