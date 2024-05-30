/**
 * The CellRecovery class provides methods for managing the recovery process of
 * diseased cells in a simulation environment. It evaluates the conditions under
 * which a cell can recover from a diseased state and implements the recovery
 * process. Recovery is contingent upon specific conditions such as the cell's
 * ability to contract diseases, its current health state, the presence of
 * mutualistic relationships, and environmental factors like temperature.
 * 
 * @author Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */
public class CellRecovery {

	/**
	 * Method to recover a cell if it meets certain conditions.
	 *
	 * @param cell  The cell to be potentially recovered.
	 * @param field The field in which the cell exists, to check environmental
	 *              conditions.
	 */
	public static void attemptRecovery(Cell cell, Field field) {
		if (canRecover(cell, field)) {
			performRecovery(cell);
		}
	}

	/**
	 * Checks if a cell can recover based on certain conditions.
	 *
	 * @param cell  The cell to check.
	 * @param field The field for checking the temperature.
	 * @return true if the cell can recover, false otherwise.
	 */
	private static boolean canRecover(Cell cell, Field field) {
		return cell.getDiseasable() && cell.getDiseased() && cell.getMutualism()
				&& field.getTemperatureCondition().equals("Warm");
	}

	/**
	 * Performs the recovery action on the cell.
	 *
	 * @param cell The cell to recover.
	 */
	private static void performRecovery(Cell cell) {
		cell.setNextState(true); // Cell recovers and will be alive in the next state.
		double recoveryAgeFactor = 0.8; // Reduce age by 20% to simulate recovery.
		cell.setAge(cell.getAge() * recoveryAgeFactor);
		cell.setDiseased(false); // The cell is no longer diseased after recovery.
	}
}