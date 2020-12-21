package helpers.objects;

public class GameInfo {
    private final String code;
    private final int gameId;

    public GameInfo(String gameCode, String gameId) {
        this.gameId = Integer.parseInt(gameId);
        code = gameCode;
    }

    public String getCode() {
        return code;
    }

    public Integer getGameId() {
        return gameId;
    }
}
