package ce303;

import java.util.*;

public class Game {

    private static final Map<Integer, Players> players = new TreeMap<>();

    // creates the players
    public void createAccount(int playerID, boolean initialBall, boolean isOnline)
    {
        Players player = new Players(playerID, initialBall, isOnline);
        player.setHasBall(initialBall);
        players.put(playerID, player);
    }

    // list of players who join
    public List<Integer> getListOfPlayers() {
        List<Integer> result = new ArrayList<>();

        for (Players player : players.values())
            if (player != players.values()) {
                result.add(player.getPlayerID());
//                System.out.println("Player: " + player.getPlayerID() + " online = " + player.isOnline());
            }

        return result;
    }

    // updated list of games once players leave etc
    public List<Integer> updateList() {
        List<Integer> result = new ArrayList<>();
        // grabs list of players based on if they are current online
        for (Players player : players.values())
            if (player.isOnline()) {
                result.add(player.getPlayerID());
//                System.out.println("Player: " + player.getPlayerID() + " online = " + player.isOnline());
            }

        return result;
    }

    // sets players online status, so you can control player status and lists much easier
    public void setIsOnline(int playerID, boolean isOnline){
        players.get(playerID).setIsOnline(isOnline);
    }

    // looks to see if a player is online
    public boolean getIsOnline(int playerID) {
        if (updateList().contains(playerID)) {
            if (players.get(playerID).isOnline()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // gets players that have the ball
    public int getPlayerBall(){

        int playerWithBall = 0;

        for (Players player : players.values()){
            if (player.getHasBall()) {
                playerWithBall = player.getPlayerID();
            }
        }
        return playerWithBall;
    }

    // method to pass the ball between players
    public int passBall(int playerID, int target){

        // to access and control all clients / threads
        synchronized (players){

            // if they have the ball
            if (players.get(playerID).getHasBall()){
//                System.out.println("Player " + playerID + " has attempted to pass to Player " + target);
                if (!players.get(target).isOnline()){
                    // if the target player is now offline
                    System.out.println("Player " + target + " is offline.\nPlayer " + playerID + " still has the ball.");
                } else if (playerID != target){
                    // if they aren't passing to themselves, set the player who passed to false, and target to true
                    players.get(playerID).setHasBall(false);
                    players.get(target).setHasBall(true);
                    System.out.println("PLayer " + playerID + " has passed the ball to Player " + target);
                } else {
                    System.out.println("Player " + playerID + " has passed to themselves.");
                }
            }

        }
        return playerID;
    }

    // disconnect method for transferring ball
    public void disconnect(int playerID){

        boolean transfer = false;
        if (playerID == getPlayerBall()){
            try {
                // grab those in the list and the first person that is online gets the ball
                while (!transfer) {
                    for (int i = 0; i <= getListOfPlayers().size(); i++) {
                        if (getIsOnline(i)) {
                            passBall(playerID, i);
                            transfer = true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // method to display passing information
    public void passing(int playerID, int target){
        if (playerID == getPlayerBall()){
            System.out.println("Player " + playerID + " passed ball to Player " + target);
            passBall(playerID, target);
        } else if (getIsOnline(target) == false){
            System.out.println("This player is not online.");
        }
    }

}
