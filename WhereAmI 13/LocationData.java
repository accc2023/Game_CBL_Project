import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Location Data.
 * 
 * Extracts the data from a JSON file, (country name, coordinates, image url,
 * difficulty) about all the locations into a class.
 * 
 * @author JOAO FREITAS
 * @id 1942395
 * @author ARHAN CHHABRA
 * @id 1940198
 */
public class LocationData {
    // Arrays with the locations divised based on difficulty
    private Location[] easyLocations = new Location[18];
    private Location[] mediumLocations = new Location[18];
    private Location[] hardLocations = new Location[14];
    // Indexes to add new elements to each of the arrays
    private int eIdx = 0;
    private int mIdx = 0;
    private int hIdx = 0;

    /**
     * Initializes the arrays with the data from the JSON file.
     */
    public void readFile() {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("Location.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            // Iterates through the array object in the JSON file
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = (JSONObject) array.get(i);

                String name = (String) object.get("name");
                long x = (long) object.get("x");
                long y = (long) object.get("y");
                String difficulty = (String) object.get("difficulty");
                String image = (String) object.get("image");

                // Creates and adds a location to its respective array
                Location toAdd = new Location(name, (int) x, (int) y, difficulty, image);
                if (difficulty.equals("easy")) {
                    easyLocations[eIdx++] = toAdd;
                } else if (difficulty.equals("medium")) {
                    mediumLocations[mIdx++] = toAdd;
                } else {
                    hardLocations[hIdx++] = toAdd;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Location[] getEasyLocations() {
        return easyLocations;
    }

    public Location[] getMediumLocations() {
        return mediumLocations;
    }

    public Location[] getHardLocations() {
        return hardLocations;
    }
}

// Class with all data about a single location
class Location {
    private String name;
    private int x;
    private int y;
    private String difficulty;
    private String image;

    /**
     * Initializes a location with its data.
     * 
     * @param name       Country of the location
     * @param x          X-Coordinate of the location within the map
     * @param y          Y-Coordinate of the location within the map
     * @param difficulty Difficulty level of location
     * @param image      Image of the location
     */
    public Location(String name, int x, int y, String difficulty, String image) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.difficulty = difficulty;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getImage() {
        return image;
    }
}
