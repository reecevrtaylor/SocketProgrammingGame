package ce303;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public record cHandler(Socket socket, Game game) implements Runnable {

    @Override
    public void run() {
        // init
        int playerID = 0;
        int current;
        int target;
        try (
                Scanner scanner = new Scanner(socket.getInputStream());
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
        ) {
            try {
                // init players, if no one is in the game, the first player wll be player one and join with the ball
                if (game.getListOfPlayers().size() == 0) {
                    playerID = 1;
                    game.createAccount(playerID, true, true);
                    // if everyone has left, the player id will still increase as users need to be unique, will have the ball
                } else if (game.updateList().size() == 0) {
                    playerID = game.getListOfPlayers().size() + 1;
                    game.createAccount(playerID, true, true);
                    // if people are in the game, add new player where they don't have the ball
                } else if (game.getListOfPlayers().size() != 0) {
                    playerID = game.getListOfPlayers().size() + 1;
                    game.createAccount(playerID, false, true);
                }
//                game.initPlayers(playerID);

                System.out.println("Player " + playerID + " has joined.");

                writer.println(playerID);

                while (true) {

                    String line = scanner.nextLine();
                    String[] split = line.split(" ");

                    // switch cases for client control
                    switch (split[0].toLowerCase()) {

                        // get current list of players
                        case "players":
                            writer.println(game.updateList());
                            break;

                        // pass the ball to desired player online
                        case "pass":
                            // split the parsed ints, assign to variables of desired players
                            current = Integer.parseInt(split[1]);
                            target = Integer.parseInt(split[2]);
                            writer.println(game.passBall(current, target));
                            break;

                        // blank case to init players
                        case "create":
                            break;

                        // get the player who currently has the ball
                        case "ball":
                            writer.println(game.getPlayerBall());
                            break;

                        // tells you if you have the ball or not
                        case "check":
                            if (playerID != game.getPlayerBall()) {
                                writer.println("Player " + game.getPlayerBall() + " has the ball.");
                            } else {
                                writer.println("You have the ball");
//                                writer.println(game.getPlayerBall());
                            }
                            break;

                        default:
                            throw new Exception("Unknown command: " + split[0]);
                    }
                }
            } catch (Exception e) {
                writer.println("ERROR " + e.getMessage());
                socket.close();
            }
        } catch (Exception e) {
        } finally {
            // state player has disconnected in server
            System.out.println("Player " + playerID + ", has disconnected");
            // set player to offline
            game.setIsOnline(playerID, false);
            // disconnect them from game and transfer ball to new player
            game.disconnect(playerID);

        }
    }
}
