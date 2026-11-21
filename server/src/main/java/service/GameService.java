package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.GameData;
import java.util.Collection;

public class GameService {
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public GameService(GameDAO gameDAO, AuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public int createGame(String authToken, String gameName)
            throws DataAccessException {

        if (authDAO.getAuth(authToken) == null) {
            throw new IllegalStateException("unauthorized");
        }

        if (gameName == null || gameName.isBlank()) {
            throw new IllegalArgumentException("bad request");
        }

        GameData game = new GameData(
                0, null, null, gameName, new ChessGame());

        return gameDAO.createGame(game);
    }

    public Collection<GameData> listGames(String authToken)
            throws DataAccessException {

        if (authDAO.getAuth(authToken) == null) {
            throw new IllegalStateException("unauthorized");
        }

        return gameDAO.listGames();
    }
    public void joinGame(String authToken, String playerColor, int gameID)
            throws DataAccessException {

        var auth = authDAO.getAuth(authToken);

        if (auth == null) {
            throw new IllegalStateException("unauthorized");
        }

        GameData game = gameDAO.getGame(gameID);

        if (game == null) {
            throw new IllegalArgumentException("bad request");
        }

        if (!"WHITE".equals(playerColor) && !"BLACK".equals(playerColor)) {
            throw new IllegalArgumentException("bad request");
        }

        String white = game.whiteUsername();
        String black = game.blackUsername();

        if (playerColor.equals("WHITE")) {
            if (white != null) {
                throw new IllegalStateException("already taken");
            }
            white = auth.username();
        } else {
            if (black != null) {
                throw new IllegalStateException("already taken");
            }
            black = auth.username();
        }

        GameData updatedGame = new GameData(
                game.gameID(),
                white,
                black,
                game.gameName(),
                game.game()
        );

        gameDAO.updateGame(updatedGame);
    }
}