package ce303;

import java.util.Scanner;

public class ClientProgram {
    public static int playerID;
    public static void main(String[] args) {

        try {
            Scanner in = new Scanner(System.in);

            // init client
            try (Client client = new Client()) {
                System.out.println("Logged in successfully.");
                playerID = client.createAccount();
                System.out.println("\nPlayer " + playerID);

                while (true) {
                    int choice;

                    // if the client opens, and they have the ball, they will have a message saying so
                    if (playerID == client.returnPlayerBall()){
                        System.out.println("\nChoose between the following options:\n(You have the ball)");
                    } else {
                        System.out.println("\nChoose between the following options:");
                    }
                    System.out.println(
                            """
                                    [1] Who has the ball
                                    [2] Check players
                                    [3] Pass the ball
                                    [4] Update game status
                                    [5] Exit game""");

                    choice = Integer.parseInt(in.nextLine());

                    if (choice == 1) {
                        // check who has the ball
                        System.out.println(client.getPlayerBall());
                    } else if (choice == 2){
                        // see who's in game
                        System.out.println("The current players in the game are: " + client.getPlayerList());
                    } else if (choice == 3){
                        // pass the ball
                        // if they do not have the ball, don't let them pass anything (obviously)
                        if (playerID != client.returnPlayerBall()){
                            System.out.println("You do not have the ball.");
                        } else if (playerID == client.returnPlayerBall()){
                            System.out.println("Who do you want to pass to?\nCurrent players: " + client.getPlayerList());
                            int passChoice = Integer.parseInt(in.nextLine());
                            client.passTheBall(playerID, passChoice);
//                            System.out.println("You have successfully passed the ball to Player " + passChoice);
                        }
                    } else if (choice == 4) {
                        // used to refresh, mainly if you have the ball or not
                        System.out.println("Updating...");
                    } else if (choice == 5){
                        // exits out loop to quit game
                        System.out.println("Thank you for playing.");
                        break;
                    }  else {
                        System.out.println("Please input the correct command.");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}