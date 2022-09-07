
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

        public void Reset() {
            score = ScoreType.LOVE;
            deuce = false;
            advantage = false;
        }
        public void SetDeuce() {
            deuce = true;
        }
        public void SetAdvantage() {
            if (!deuce)
                throw new UnsupportedOperationException();
            advantage = true;
        }
        public void ResetAdvantage() {
            advantage = false;
        }

        public boolean IsAdvantage() {
            return advantage;
        }

        public ScoreType GetScoreValue() {
            return this.score;
        }
        
        public void Advance() {
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
                    SetAdvantage();
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

        public void ResetScore() {
            score.Reset();
        }

        public ScoreType GetScore() {
            return score.GetScoreValue();
        }

        public String GetName() {
            return name;
        }

        public void AdvanceScore() {
            score.Advance();
        }

        public boolean IsAdvantage() {
            return score.IsAdvantage();
        }

        public void ResetAdvantage() {
            score.ResetAdvantage();
        }

        public String GetScoreString() {
            return score.toString();
        }

        public void SetDeuce() {
            score.SetDeuce();
        }

    }

    private int m_score1 = 0;
    private int m_score2 = 0;
    private Player player1;
    private Player player2;
    // game sate caching
    private boolean isDeuce = false;

    private boolean isEnd = false;
    
    public TennisGameImpl(String player1Name, String Player2Name) {
        player1 = new Player(player1Name);
        player2 = new Player(Player2Name);
        player1.ResetScore();
        player2.ResetScore();
        isDeuce = false;
    }

    private void SetDeuce() {
        isDeuce = true;
        player1.SetDeuce();
        player2.SetDeuce();
    }

    public void Reset() {
        player1.ResetScore();
        player2.ResetScore();
        isDeuce = false;
    }

    public void wonPoint(String playerName) {
        Player playerToAdvance;
        Player otherPlayer;
        if (player1.GetName() == playerName) {
            playerToAdvance = player1;
            otherPlayer = player2;
        }
        else {
            playerToAdvance = player2;
            otherPlayer = player1;
        }
        if (isDeuce) {
            deuceWonPoint(playerToAdvance, otherPlayer);
        }
        else {
            nonDeuceWonPoint(playerToAdvance, otherPlayer);
        }
        // logic : if deuce, do deuce logic, else 

        
    }
    private void nonDeuceWonPoint(Player toAdvance, Player other) {
        toAdvance.AdvanceScore();
        if (toAdvance.GetScore() == ScoreType.FOURTY && other.GetScore() == ScoreType.FOURTY)
            SetDeuce();
        if (toAdvance.GetScore() == ScoreType.WIN)
        {
            isDeuce = false;
            isEnd = true;
        }
    }
    private void deuceWonPoint(Player toAdvance, Player other) {
        if (other.IsAdvantage())
            other.ResetAdvantage();
        else
            toAdvance.AdvanceScore();
        if (toAdvance.GetScore() == ScoreType.WIN)
        {
            isDeuce = false;
            isEnd = true;
        }
    }

    public String getLiteralScore() {
        String score = "";
        if (isEnd)
        {
            if (player1.GetScore() == ScoreType.WIN)
                return "Win for " + player1.GetName();
            else
                return "Win for " + player2.GetName();
        }
        else if (isDeuce) {
            if (player1.IsAdvantage())
                return "Advantage " + player1.GetName();
            else if (player2.IsAdvantage())
                return "Advantage " + player2.GetName();
            else
                return "Deuce";
        }
        else {
            if (player1.GetScore() == player2.GetScore())
                return player1.GetScoreString() + "-All";
            else
                return player1.GetScoreString() + "-" + player2.GetScoreString();
        }
    }
    
    public boolean isEnd() {
		return isEnd;
	}
}
