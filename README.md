# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2AMQALADMABwATG4gMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YwjUwFazsXJT145NQ03PnB2MbqttQu0WyzWYyOJzOQLGVzYnG4sHuN1E9SgmWyYEoAAoMlkcpQMgBHVI5ACU12qojulVk8iUKnU9XsKDAAFUBhi3h8UKTqYplGpVJSjDpagAxJCcGCsyg8mA6SwwDmzMQ6FHAADWkoGME2SDA8QVA05MGACFVHHlKAAHmiNDzafy7gjySp6lKoDyySIVI7KjdnjAFKaUMBze11egAKKWlTYAgFT23Ur3YrmeqBJzBYbjObqYCMhbLCNQbx1A1TJXGoMh+XyNXoKFmTiYO189Q+qpelD1NA+BAIBMU+4tumqWogVXot3sgY87nae1t+7GWoKDgcTXS7QD71D+et0fj4PohQ+PUY4Cn+Kz5t7keC5er9cnvUexE7+4wp6l7FovFqXtYJ+cLtn6pavIaSpLPU+wgheertBAdZoFByyXAmlDtimGD1OEThOFmEwQZ8MDQcCyxwfECFISh+xXOgHCmF4vgBNA7CMjEIpwBG0hwAoMAADIQFkhRYcwTrUP6zRtF0vQGOo+RoARiqfIs3y-P8gJjA2oGCkB-rgeWKlaSC6k7HsDZ6fCvrOl2MAIEJ4oYoJwkEkSYCktuhi7jS+4MkyU7KVyN4+XeS7CjAYoSm6MpymW7xKsFvKheJVBIhu7pbpgKrBhqDRujAaAQMwABmvgStAMCmQCMDiiA0AouAnSeSB1T+sy0yXtASAAF4oBwUYxnGhQ6UmlSiWmTgAIwETmqh5vMixFiW9Q+B1epdb1ux0U2zXeUl-L1IecgoM+8Tnpe17Dg6YX1I+AYXVuV2LilaWnR6u0fo8cL1M54oZKoAGYJZLUSWBhGGQtxnkZeVH1qpVwjcmyCpjAuH4aM4PxUZ3wUbDyHw42DGeN4fj+F4KDoDEcSJOTlPOb4WCiYKoH1A00gRvxEbtBG3Q9HJqgKcMuOIeg6FWQ8sL+sLSFA19GEvS6dlCQz53wSLaAeW+XlUreB0wIyYCnarlHq3OIXXZUy4ReKT4PfIsrytLotPQKo0dqlitvZl2Xqo0p0wJASEFUVMClatHBNVrIM1K6a3xBtfUDSgsYKWLmHI9hMDptNGOzfNexLdAK1xwnW2E6YUd7Quo5GCg3DHpext42b+2Lpb4UZDMEA0PdL6Pbrz3WZ29QA-uFc2e2lk-crp7-YDwNuyzMAjAjrXwkjJRgDheFZttRNMaTKLrv42Dihq-FojAADiSoaEzKWSVfXO8-YSpCzD6tp59kulk7hQL0PD2tlkA5BvjmJuptx6dkFC7PyhtG5-xbtXe84VIq2z7vbWKf9ErIMXjZW6dtgCmB9rlf2gdKaFRKmVCOH1AFtRLlAHqidozJyGl-DeKNs4zX5PnRaxYi4KgYUwsu9EoFAJgQPGuICwBgLUBiJB+4UH1DQZWBA18lQehdtHNKDRZE8iarQiWX4fpolkaoOeCBAJy3XnQsGr8cwLAaC4ZxnRV6g3Tpvbe6Nxj2LUI45xLhXHl2JsxfwHAADsbgnAoCcDECMwQ4BcQAGzwAnIYWRMAigZzErY1mrQOgvzfnHPGBFfEADlIIExGpUKexoP5ITWD4pUFTsbQmsczfBMAjrolkRiOAaTZGuTUO5MRg4dbm3pPrJkRtEE4MUTda2EovaYMdvU52kjtGe0IcQ1UpDLwB3VsHKh4dI4TzwaDWOFFS5JxTvGRGY1skTRztmHh+Y+HLUEVcxhm0GyiN2uM1uNdukoF6b4y6GyFl3T0f3CZrsFa2WhfIUZ74anWPqP0o8IKlQWKsT-aO+kxi+IAJLSHMm4xMHDM5o1KUqElZLgkHwCJYOu9lNhUyQAkMAzK+wQDZQAKQgOKdRcwYjJFAGqLJm8OkXMaE0ZkMkei+PfmrJCBFsAIGAMyqAcAID2SgOZEExLSVVLXrpNFdSVXoEaT8DVWqdV6oNcsI19KAHuzSgAK0FWgXpArxSDJQISYZmszkAurnAmZayNZzNCu3ZRNte5Xm0A7C1JsZZaPOUAghGCiFZV2X7fZ5C0BHNDtQ050CM30K+cIm5bD7nwEeVnKa3DcxvLGIXUsq0q0-L3si7WMhJFwNBbS6QCiY1CjjRKRFwBk1GujddeF9Qp07Jyo0DJlCS0nMMUvAAQiGIZOQa2pzreNRt3ixh51be2+oeh1wokDTkX5O1K6ALSiAeIKAQAaiQMVLpuqKrACoOCXQ3Bl2+waPa-9VZzQB3DGgMt4iK2lkDGaSwYYkKHruaat2J70yZlzq8yGV7KwoZg9RHt-z+2wpWtgLQPSlQYjBdoUdFtx2Sho8dYVKBNEQoXTAUeI5e2T3Nb6712L-yWNlnixDLxyXy0pVvVGO9RjkcZWTKAmr2Wcq8BpxAwZYDAGwOqwgeQCiZPvrkxo7NObc15sYL+qKf7IgDW5STX5pWZq6dwPA8jBNV18p5vTPn02xpgJ3buhgTRqOitoNYyzgBrCXem3j-H+S+e-sY+temcWueAtJ5esmbH1s8Yp7xPagA
