package server;

import io.javalin.*;
import dataaccess.*;
import service.ClearService;
import com.google.gson.Gson;
import service.UserService;
import model.UserData;
import model.AuthData;
import service.GameService;
import model.GameData;
import java.util.Map;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        UserService userService = new UserService(userDAO, authDAO);
        GameService gameService = new GameService(gameDAO, authDAO);
        Gson gson = new Gson();

        ClearService clearService = new ClearService(userDAO, authDAO, gameDAO);

        javalin.delete("/db", ctx -> {
            clearService.clear();
            ctx.contentType("application/json");
            ctx.result("{}");
        });
        javalin.post("/user", ctx -> {
            try {
                UserData user = gson.fromJson(ctx.body(), UserData.class);

                AuthData auth = userService.register(
                        user.username(),
                        user.password(),
                        user.email()
                );

                ctx.contentType("application/json");
                ctx.result(gson.toJson(auth));

            } catch (IllegalArgumentException e) {
                ctx.status(400);
                ctx.contentType("application/json");
                ctx.result("{\"message\":\"Error: bad request\"}");

            } catch (IllegalStateException e) {
                ctx.status(403);
                ctx.contentType("application/json");
                ctx.result("{\"message\":\"Error: already taken\"}");
            }
        });
        javalin.post("/session", ctx -> {
            try {
                UserData user = gson.fromJson(ctx.body(), UserData.class);

                if (user == null) {
                    throw new IllegalArgumentException("bad request");
                }

                AuthData auth = userService.login(
                        user.username(),
                        user.password()
                );

                ctx.contentType("application/json");
                ctx.result(gson.toJson(auth));

            } catch (IllegalArgumentException e) {
                ctx.status(400);
                ctx.contentType("application/json");
                ctx.result("{\"message\":\"Error: bad request\"}");

            } catch (IllegalStateException e) {
                ctx.status(401);
                ctx.contentType("application/json");
                ctx.result("{\"message\":\"Error: unauthorized\"}");
            }
        });

        javalin.delete("/session", ctx -> {
            try {
                String authToken = ctx.header("Authorization");

                userService.logout(authToken);

                ctx.contentType("application/json");
                ctx.result("{}");

            } catch (IllegalStateException e) {
                ctx.status(401);
                ctx.contentType("application/json");
                ctx.result("{\"message\":\"Error: unauthorized\"}");
            }
        });

        javalin.get("/game", ctx -> {
            try {
                String authToken = ctx.header("Authorization");

                var games = gameService.listGames(authToken);

                var gameList = games.stream().map(game ->
                        new GameData(
                                game.gameID(),
                                game.whiteUsername(),
                                game.blackUsername(),
                                game.gameName(),
                                null
                        )
                ).toList();

                ctx.contentType("application/json");
                ctx.result(gson.toJson(Map.of("games", gameList)));

            } catch (IllegalStateException e) {
                ctx.status(401);
                ctx.contentType("application/json");
                ctx.result("{\"message\":\"Error: unauthorized\"}");
            }
        });

        javalin.post("/game", ctx -> {
            try {
                String authToken = ctx.header("Authorization");

                GameData request = gson.fromJson(ctx.body(), GameData.class);

                if (request == null) {
                    throw new IllegalArgumentException("bad request");
                }

                int gameID = gameService.createGame(
                        authToken, request.gameName());

                ctx.contentType("application/json");
                ctx.result(gson.toJson(Map.of("gameID", gameID)));

            } catch (IllegalArgumentException e) {
                ctx.status(400);
                ctx.contentType("application/json");
                ctx.result("{\"message\":\"Error: bad request\"}");

            } catch (IllegalStateException e) {
                ctx.status(401);
                ctx.contentType("application/json");
                ctx.result("{\"message\":\"Error: unauthorized\"}");
            }
        });

        javalin.put("/game", ctx -> {
            try {
                String authToken = ctx.header("Authorization");

                var request = gson.fromJson(ctx.body(), java.util.Map.class);

                if (request == null ||
                        !request.containsKey("playerColor") ||
                        !request.containsKey("gameID")) {
                    throw new IllegalArgumentException("bad request");
                }

                String playerColor = (String) request.get("playerColor");
                int gameID = ((Number) request.get("gameID")).intValue();

                if (authToken == null || authDAO.getAuth(authToken) == null) {
                    ctx.status(401);
                    ctx.contentType("application/json");
                    ctx.result("{\"message\":\"Error: unauthorized\"}");
                    return;
                }

                gameService.joinGame(authToken, playerColor, gameID);

                ctx.contentType("application/json");
                ctx.result("{}");

            } catch (IllegalArgumentException e) {
                ctx.status(400);
                ctx.contentType("application/json");
                ctx.result("{\"message\":\"Error: bad request\"}");

            } catch (IllegalStateException e) {
                ctx.status(403);
                ctx.contentType("application/json");
                ctx.result("{\"message\":\"Error: already taken\"}");
            }
        });
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
