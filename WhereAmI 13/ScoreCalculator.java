/**
 * Score Calculator.
 * 
 * Calculates the score of a given guess as well as storing the total score.
 * 
 * @author JOAO FREITAS
 * @id 1942395
 * @author ARHAN CHHABRA
 * @id 1940198
 */
public class ScoreCalculator {
    private int totalScore = 0;
    // Constants used to convert a distance value into a score
    private static final double SCALING_FACTOR = 9;
    private static final double SCALING_FACTOR_LOW = 12;
    private static final double SCALING_FACTOR_HIGH = 6;
    private static final double SCALING_FACTOR_ULTRA_HIGH = 4;
    private static final int MAX_SCORE = 5000;

    /**
     * Calculates the score based on two points and updates the total score.
     * 
     * @param x1 X-Coordinate of the user guess
     * @param y1 Y-Coordinate of the user guess
     * @param x2 X-Coordinate of the actual location
     * @param y2 Y-Coordinate of the actual location
     * @return the score of that round
     */
    public int score(int x1, int y1, int x2, int y2) {
        if (x1 == 0 && y1 == 0) {
            return 0;
        }
        double distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        int score;
        if (distance > 250) {
            score = MAX_SCORE - (int) Math.round(distance * SCALING_FACTOR_LOW);
        } else if (distance < 25) {
            score = MAX_SCORE - (int) Math.round(distance * SCALING_FACTOR_HIGH);
        } else if (distance < 10) {
            score = MAX_SCORE - (int) Math.round(distance * SCALING_FACTOR_ULTRA_HIGH);
        } else {
            score = MAX_SCORE - (int) Math.round(distance * SCALING_FACTOR);
        }
        if (score < 0) {
            return 0;
        } else {
            if (score > 4985) {
                score = 5000;
            }
            totalScore += score;
            return score;
        }
    }

    public int getTotalScore() {
        return totalScore;
    }
}
