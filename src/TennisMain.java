import java.util.Random;

public class TennisMain {

	public static void main(String[] args) {
		
		String player1 = "leek";
		String player2 = "lily";
		
		TennisGameImpl game = new TennisGameImpl(player1, player2);
		playGame(game);
		
	}

    public static void playGame(TennisGame tennisGame) {

    	Random random = new Random(42);
    
    	while(!tennisGame.isEnd()) {
    	    if(random.nextBoolean()) {
    	    	tennisGame.wonPoint("leek");
    	    } else {
    	    	tennisGame.wonPoint("lily");
    	    }
    	    System.out.println(tennisGame.getLiteralScore());
    	}
	}
}
