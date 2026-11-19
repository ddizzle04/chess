package service;

import dataaccess.*;
import model.UserData;
import model.AuthData;
import model.GameData;
import chess.ChessGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {

    @Test
    public void clearRemovesAllData() throws DataAccessException {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        userDAO.createUser(new UserData(
                "player1", "password", "player@example.com"));

        authDAO.createAuth(new AuthData("token123", "player1"));

        int gameID = gameDAO.createGame(new GameData(
                0, null, null, "Test Game", new ChessGame()));

        ClearService service = new ClearService(
                userDAO, authDAO, gameDAO);

        service.clear();

        assertNull(userDAO.getUser("player1"));
        assertNull(authDAO.getAuth("token123"));
        assertNull(gameDAO.getGame(gameID));
        assertTrue(gameDAO.listGames().isEmpty());
    }
}