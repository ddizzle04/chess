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
}