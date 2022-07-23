package ce303;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements AutoCloseable {
    final int port = 8888;

    private final Scanner reader;
    private final PrintWriter writer;

    // the client class is used to pass information from client handler to client program

    public Client() throws Exception {
        // Connecting to the server and creating objects for communications
        Socket socket = new Socket("localhost", port);
        reader = new Scanner(socket.getInputStream());

        // Automatically flushes the stream with every command
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    // create the initial accounts
    public int createAccount() {
        writer.println("create");

        String line = reader.nextLine();
        return Integer.parseInt(line);
    }

    // writes "check" in cHandler to see who has the ball
    public String getPlayerBall() {
        // Writing the command from cHandler
        writer.println("check");

        // Reading the line and returns
        String line = reader.nextLine();
        return line;
    }

    // writes "players" in cHandler to grab all players in game
    public String getPlayerList() {
        // Writing the command
        writer.println("players");

        // Reading the account balance
        String line = reader.nextLine();
        return line;
    }

    // writes "pass" in cHandler to initiate passing the ball
    public void passTheBall(int playerID, int target) {
        // Writing the command
        writer.println("pass" + " " + playerID + " " + target);

        // Reading the response
        String line = reader.nextLine();
        if (line.trim().compareToIgnoreCase("success") != 0);
    }

    // writes "ball" in cHandler for getting who has the ball
    public int returnPlayerBall(){
        writer.println("ball");

        String line = reader.nextLine();
        return Integer.parseInt(line);
    }

    @Override
    public void close() {
        reader.close();
        writer.close();
    }
}
