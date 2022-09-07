
public class TennisGameImpl implements TennisGame {
    public static enum ScoreType {
        UNKNOWN, // Uninitialized
        LOVE, // 0,
        FIFTEEN, // 15,
        THIRTY, // 30,
        FOURTY, // 40,
        WIN, // End state
    }

    public static class Score {
        private ScoreType score = ScoreType.UNKNOWN;
        private boolean deuce = false;
        private boolean advantage = false;

        public void reset() {
            score = ScoreType.LOVE;
            deuce = false;
            advantage = false;
        }
        public void setDeuce() {
            deuce = true;
        }
        public void setAdvantage() {
            if (!deuce)
                throw new UnsupportedOperationException();
            advantage = true;
        }
        public void resetAdvantage() {
            advantage = false;
        }

        public boolean isAdvantage() {
            return advantage;
        }

        public ScoreType getScoreValue() {
            return this.score;
        }
        
        public void advance() {
            // increase score by one
            if (score == ScoreType.LOVE)
                score = ScoreType.FIFTEEN;
            else if (score == ScoreType.FIFTEEN)
                score = ScoreType.THIRTY;
            else if (score == ScoreType.THIRTY)
                score = ScoreType.FOURTY;
            else if (score == ScoreType.FOURTY) {
                // deuce and not advantage
                if (deuce && (!advantage))
                    setAdvantage();
                else
                    score = ScoreType.WIN;
            }
            else
                throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            if (score == ScoreType.LOVE)
                return "Love";
            else if (score == ScoreType.FIFTEEN)
                return "Fifteen";
            else if (score == ScoreType.THIRTY)
                return "Thirty";
            else if (score == ScoreType.FOURTY)
                return "Fourty";
            else if (score == ScoreType.WIN)
                return "Win";
            else
                return "<UNKNOWN>";
        }
        
    }

    public static class Player {
        private String name;
        private Score score = new Score();
        
        public Player(String name) {
            this.name = name;
        }

        public void resetScore() {
            score.reset();
        }

        public ScoreType getScore() {
            return score.getScoreValue();
        }

        public String getName() {
            return name;
        }

        public void advanceScore() {
            score.advance();
        }

        public boolean isAdvantage() {
            return score.isAdvantage();
        }

        public void resetAdvantage() {
            score.resetAdvantage();
        }

        public String getScoreString() {
            return score.toString();
        }

        public void setDeuce() {
            score.setDeuce();
        }
    }

    private Player player1;
    private Player player2;
    
    // game inner state variables
    private boolean isDeuce = false;
    private boolean isEnd = false;
    
    public TennisGameImpl(String player1Name, String Player2Name) {
        player1 = new Player(player1Name);
        player2 = new Player(Player2Name);
        player1.resetScore();
        player2.resetScore();
        isDeuce = false;
    }

    private void setDeuce() {
        isDeuce = true;
        player1.setDeuce();
        player2.setDeuce();
    }

    public void reset() {
        player1.resetScore();
        player2.resetScore();
        isDeuce = false;
    }

    public void wonPoint(String playerName) {
        Player playerToAdvance;
        Player otherPlayer;
        if (player1.getName() == playerName) {
            playerToAdvance = player1;
            otherPlayer = player2;
        }
        else {
            playerToAdvance = player2;
            otherPlayer = player1;
        }
        if (isDeuce()) {
            wonPointWhenDeuce(playerToAdvance, otherPlayer);
        }
        else {
            wonPointWhenNotDeuce(playerToAdvance, otherPlayer);
        }

    }
    private void wonPointWhenNotDeuce(Player toAdvance, Player other) {
        toAdvance.advanceScore();
        if (toAdvance.getScore() == ScoreType.FOURTY && other.getScore() == ScoreType.FOURTY)
            setDeuce();
        if (toAdvance.getScore() == ScoreType.WIN)
        {
            isDeuce = false;
            isEnd = true;
        }
    }
    private void wonPointWhenDeuce(Player toAdvance, Player other) {
        if (other.isAdvantage())
            other.resetAdvantage();
        else
            toAdvance.advanceScore();
        if (toAdvance.getScore() == ScoreType.WIN)
        {
            isDeuce = false;
            isEnd = true;
        }
    }

    public String getLiteralScore() {
        if (isEnd())
        {
            if (player1.getScore() == ScoreType.WIN)
                return "Win for " + player1.getName();
            else
                return "Win for " + player2.getName();
        }
        else if (isDeuce()) {
            if (player1.isAdvantage())
                return "Advantage " + player1.getName();
            else if (player2.isAdvantage())
                return "Advantage " + player2.getName();
            else
                return "Deuce";
        }
        else {
            if (player1.getScore() == player2.getScore())
                return player1.getScoreString() + "-All";
            else
                return player1.getScoreString() + "-" + player2.getScoreString();
        }
    }

    private boolean isDeuce() {
        return isDeuce;
    }
    
    public boolean isEnd() {
		return isEnd;
	}
}
