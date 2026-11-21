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

    @Test
    public void listGamesSuccess() throws DataAccessException {
        GameDAO games = new MemoryGameDAO();
        AuthDAO auths = new MemoryAuthDAO();
        GameService service = new GameService(games, auths);

        auths.createAuth(new AuthData("token123", "player1"));

        service.createGame("token123", "Game One");
        service.createGame("token123", "Game Two");

        var result = service.listGames("token123");

        assertEquals(2, result.size());
    }

    @Test
    public void listGamesUnauthorized() {
        GameService service = new GameService(
                new MemoryGameDAO(), new MemoryAuthDAO());

        assertThrows(IllegalStateException.class, () ->
                service.listGames("invalidToken"));
    }
    @Test
    public void joinGameSuccess() throws DataAccessException {
        GameDAO games = new MemoryGameDAO();
        AuthDAO auths = new MemoryAuthDAO();
        GameService service = new GameService(games, auths);

        auths.createAuth(new AuthData("token123", "player1"));

        int gameID = service.createGame("token123", "My Game");

        service.joinGame("token123", "WHITE", gameID);

        assertEquals("player1", games.getGame(gameID).whiteUsername());
        assertNull(games.getGame(gameID).blackUsername());
    }
    @Test
    public void joinGameColorTaken() throws DataAccessException {
        GameDAO games = new MemoryGameDAO();
        AuthDAO auths = new MemoryAuthDAO();
        GameService service = new GameService(games, auths);

        auths.createAuth(new AuthData("token1", "player1"));
        auths.createAuth(new AuthData("token2", "player2"));

        int gameID = service.createGame("token1", "My Game");

        service.joinGame("token1", "WHITE", gameID);

        assertThrows(IllegalStateException.class, () ->
                service.joinGame("token2", "WHITE", gameID));

        assertEquals("player1", games.getGame(gameID).whiteUsername());
    }
    @Test
    public void joinGameNotFound() throws DataAccessException {
        GameDAO games = new MemoryGameDAO();
        AuthDAO auths = new MemoryAuthDAO();
        GameService service = new GameService(games, auths);

        auths.createAuth(new AuthData("token123", "player1"));

        assertThrows(IllegalArgumentException.class, () ->
                service.joinGame("token123", "WHITE", 999));
    }
    @Test
    public void joinGameBlackSuccess() throws DataAccessException {
        GameDAO games = new MemoryGameDAO();
        AuthDAO auths = new MemoryAuthDAO();
        GameService service = new GameService(games, auths);

        auths.createAuth(new AuthData("token123", "player1"));

        int gameID = service.createGame("token123", "My Game");

        service.joinGame("token123", "BLACK", gameID);

        assertEquals("player1", games.getGame(gameID).blackUsername());
        assertNull(games.getGame(gameID).whiteUsername());
    }
}