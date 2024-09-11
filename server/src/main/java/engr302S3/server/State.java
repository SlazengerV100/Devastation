package engr302S3.server;

import engr302S3.server.players.Player;

import java.util.Map;

public record State(Map<String, Player> playerMap) {}