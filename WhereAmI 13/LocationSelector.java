import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Location Selector.
 * 
 * Selects 10 random locations weighted by the difficulty level.
 * Easy -> 7 easy, 2 medium, 1 hard. (18 easy locations)
 * Medium -> 3 easy, 5 medium, 2 hard. (18 medium locations)
 * Hard -> 1 easy, 3 medium, 6 hard. (14 hard locations)
 * 
 * @author JOAO FREITAS
 * @id 1942395
 * @author ARHAN CHHABRA
 * @id 1940198
 */
class LocationSelector {
    private static final int N_EASY_LOCATIONS = 18;
    private static final int N_MEDIUM_LOCATIONS = 18;
    private static final int N_HARD_LOCATIONS = 14;

    // Indexes of the randomly chosen numbers that will be used
    // as an index to access the location arrays
    private List<Integer> eIdxs = new ArrayList<>();
    private List<Integer> mIdxs = new ArrayList<>();
    private List<Integer> hIdxs = new ArrayList<>();

    private Location[] easyLocations;
    private Location[] mediumLocations;
    private Location[] hardLocations;

    private Location[] locations = new Location[10];
    private LocationData locationData = new LocationData();

    private String difficulty;

    /**
     * Reads the information from the file and generates the locations for the game.
     * 
     * @param inDifficulty Used to weight the distribution of locations
     */
    public LocationSelector(String inDifficulty) {
        difficulty = inDifficulty;
        locationData.readFile();

        easyLocations = locationData.getEasyLocations();
        mediumLocations = locationData.getMediumLocations();
        hardLocations = locationData.getHardLocations();

        indexSelector();
        fillLocations();
    }

    // Returns n random numbers in an interval.
    private List<Integer> random(int n, int interval) {
        Random random = new Random();
        Set<Integer> uniqueNumbers = new HashSet<>();

        // Finds non-repeated random numbers in the interval
        while (uniqueNumbers.size() < n) {
            int randomValue = random.nextInt(interval);
            uniqueNumbers.add(randomValue);
        }

        // Convert the set to List<Integer>
        List<Integer> toReturn = new ArrayList<>(uniqueNumbers);

        return toReturn;
    }

    // Returns the distribution of locations based on difficulties.
    private int[] distribution() {
        if (difficulty.equals("easy")) {
            return new int[] { 7, 2, 1 };
        } else if (difficulty.equals("medium")) {
            return new int[] { 3, 5, 2 };
        } else {
            return new int[] { 1, 3, 6 };
        }
    }

    // Returns the index of the selected locations based on difficulties.
    private void indexSelector() {
        int[] distribution = distribution();
        eIdxs = random(distribution[0], N_EASY_LOCATIONS);
        mIdxs = random(distribution[1], N_MEDIUM_LOCATIONS);
        hIdxs = random(distribution[2], N_HARD_LOCATIONS);
    }

    // Fills the array with the selected locations
    private void fillLocations() {
        for (int i = 0; i < eIdxs.size(); i++) {
            locations[i] = easyLocations[eIdxs.get(i)];
        }

        for (int i = 0; i < mIdxs.size(); i++) {
            locations[i + eIdxs.size()] = mediumLocations[mIdxs.get(i)];
        }

        for (int i = 0; i < hIdxs.size(); i++) {
            locations[i + eIdxs.size() + mIdxs.size()] = hardLocations[hIdxs.get(i)];
        }
    }

    public Location[] getLocations() {
        return locations;
    }
}
