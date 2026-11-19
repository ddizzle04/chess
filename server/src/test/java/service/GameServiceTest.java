package service;

import dataaccess.*;
import model.AuthData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {

    @Test
    public void createGameSuccess() throws DataAccessException {
        GameDAO games = new MemoryGameDAO();
        AuthDAO auths = new MemoryAuthDAO();
        GameService service = new GameService(games, auths);

        auths.createAuth(new AuthData("token123", "player1"));

        int gameID = service.createGame("token123", "My Game");

        assertNotNull(games.getGame(gameID));
        assertEquals("My Game", games.getGame(gameID).gameName());
    }

    @Test
    public void createGameUnauthorized() {
        GameService service = new GameService(
                new MemoryGameDAO(), new MemoryAuthDAO());

        assertThrows(IllegalStateException.class, () ->
                service.createGame("invalidToken", "My Game"));
    }

    @Test
    public void createGameMissingName() throws DataAccessException {
        GameDAO games = new MemoryGameDAO();
        AuthDAO auths = new MemoryAuthDAO();
        GameService service = new GameService(games, auths);

        auths.createAuth(new AuthData("token123", "player1"));

        assertThrows(IllegalArgumentException.class, () ->
                service.createGame("token123", ""));
    }
}