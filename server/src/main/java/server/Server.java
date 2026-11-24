package server;

import io.javalin.*;
import dataaccess.*;
import service.ClearService;
import com.google.gson.Gson;
import service.UserService;
import model.UserData;
import model.AuthData;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        UserService userService = new UserService(userDAO, authDAO);
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

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
