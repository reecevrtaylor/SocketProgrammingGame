package ce303;

public class Players {
    private int playerID;
    private boolean initialBall;
    private boolean isOnline;

    public Players(int playerID, boolean initialBall, boolean isOnline) {
        this.playerID = playerID;
        this.initialBall = initialBall;
        this.isOnline = isOnline;
    }

    public int getPlayerID() {
        return playerID;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean setOnline){
        isOnline = setOnline;
    }

    public boolean getHasBall() {
        return initialBall;
    }
    public void setHasBall(boolean ball) {
        initialBall = ball;
    }



}
