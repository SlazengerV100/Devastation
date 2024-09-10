package engr302S3.server;

import engr302S3.server.players.Player;

import java.io.*;
import java.util.Map;

public class GameBoardLoader {
    public static Tile[][] loadGameBoard(Map<String, Player> playerMap, Map<String, Station> stationMap) {
        String csvData = readCSV("config/gameMap.csv");
        String[] rows = csvData.split("\n");
        Tile[][] map = new Tile[rows.length][];

        for (int row = 0; row < rows.length; row++) {
            String[] columns = rows[row].split(",");
            map[row] = new Tile[columns.length];

            for (int col = 0; col < columns.length; col++) {
                String[] parts = columns[col].replaceAll("[\\[\\]]", "").split("-");

                // First part is the Room enum
                Tile.Room room = getRoomFromChar(parts[0]);

                // Second part is for Station, if any
                Station station = parts[1].equals("X") ? null : getStationFromString(parts[1], stationMap);

                // Third part is for Player, if any
                Player player = parts[2].equals("X") ? null : getPlayerFromString(parts[2], playerMap);

                if (player != null) {
                    player.setX(col);
                    player.setY(row);
                }

                // Create the Tile
                map[row][col] = new Tile(room, col, row);

                // Add station and player if they exist
                map[row][col].setStation(station);
                map[row][col].setPlayer(player);
            }
        }

        return map;
    }

    private static Tile.Room getRoomFromChar(String roomChar) {
        return switch (roomChar) {
            case "W" -> Tile.Room.WALL;
            case "P" -> Tile.Room.PLANNING;
            case "D" -> Tile.Room.DEVELOPMENT;
            case "T" -> Tile.Room.TESTING;
            case "E" -> Tile.Room.EMPTY;
            default -> throw new IllegalArgumentException("Unknown room type: " + roomChar);
        };
    }

    private static Station getStationFromString(String stationStr, Map<String, Station> stationMap) {
        return stationMap.get(stationStr);
    }

    private static Player getPlayerFromString(String playerStr, Map<String, Player> playerMap) {
        return playerMap.get(playerStr);
    }

    public static String readCSV(String filePath) {
        StringBuilder csvData = new StringBuilder();
        String line;

        // Use the class loader to load the file from resources
        try (InputStream inputStream = GameBoardLoader.class.getClassLoader().getResourceAsStream(filePath)) {
            assert inputStream != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

                while ((line = br.readLine()) != null) {
                    csvData.append(line).append("\n"); // Append each line to the StringBuilder
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvData.toString();
    }
}
