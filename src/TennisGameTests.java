import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TennisGameTests {
    private final String PLAYER1_NAME = "player1";
    private final String PLAYER2_NAME = "player2";
    
    @Test
    void TestSimpleGame() {
        TennisGame game = new TennisGameImpl(PLAYER1_NAME, PLAYER2_NAME);
        String output = game.getLiteralScore();
        assertEquals(output, "Love-All");
        
    }
}
