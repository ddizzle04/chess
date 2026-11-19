package service;

import dataaccess.*;
import model.AuthData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    public void registerSuccess() throws DataAccessException {
        UserDAO users = new MemoryUserDAO();
        AuthDAO auths = new MemoryAuthDAO();
        UserService service = new UserService(users, auths);

        AuthData result = service.register(
                "player1", "password123", "player@example.com");

        assertEquals("player1", result.username());
        assertNotNull(result.authToken());
        assertNotNull(users.getUser("player1"));
        assertNotNull(auths.getAuth(result.authToken()));
    }

    @Test
    public void registerDuplicateUsername() throws DataAccessException {
        UserDAO users = new MemoryUserDAO();
        AuthDAO auths = new MemoryAuthDAO();
        UserService service = new UserService(users, auths);

        service.register("player1", "password123", "player@example.com");

        assertThrows(IllegalStateException.class, () ->
                service.register("player1", "anotherPassword", "other@example.com"));
    }

    @Test
    public void registerMissingUsername() {
        UserService service = new UserService(
                new MemoryUserDAO(), new MemoryAuthDAO());

        assertThrows(IllegalArgumentException.class, () ->
                service.register("", "password123", "player@example.com"));
    }

    @Test
    public void loginSuccess() throws DataAccessException {
        UserDAO users = new MemoryUserDAO();
        AuthDAO auths = new MemoryAuthDAO();
        UserService service = new UserService(users, auths);

        service.register("player1", "password123", "player@example.com");

        AuthData result = service.login("player1", "password123");

        assertEquals("player1", result.username());
        assertNotNull(result.authToken());
        assertNotNull(auths.getAuth(result.authToken()));
    }

    @Test
    public void loginWrongPassword() throws DataAccessException {
        UserDAO users = new MemoryUserDAO();
        AuthDAO auths = new MemoryAuthDAO();
        UserService service = new UserService(users, auths);

        service.register("player1", "password123", "player@example.com");

        assertThrows(IllegalStateException.class, () ->
                service.login("player1", "wrongpassword"));
    }
    @Test
    public void logoutSuccess() throws DataAccessException {
        UserDAO users = new MemoryUserDAO();
        AuthDAO auths = new MemoryAuthDAO();
        UserService service = new UserService(users, auths);

        AuthData auth = service.register(
                "player1", "password123", "player@example.com");

        service.logout(auth.authToken());

        assertNull(auths.getAuth(auth.authToken()));
    }

    @Test
    public void logoutInvalidToken() {
        UserService service = new UserService(
                new MemoryUserDAO(), new MemoryAuthDAO());

        assertThrows(IllegalStateException.class, () ->
                service.logout("invalidToken"));
    }
}