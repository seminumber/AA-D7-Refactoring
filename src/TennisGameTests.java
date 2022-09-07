import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TennisGameTests {
    private final String PLAYER1_NAME = "player1";
    private final String PLAYER2_NAME = "player2";
    private TennisGame game;
    
    @BeforeEach
    void setup() {
        // Change the line below with "game = new TennisGame1();" and see if things works as expected
        game = new TennisGameImpl(PLAYER1_NAME, PLAYER2_NAME);
    }
    
    @Test
    void testTennisGameWhenLoveAll() {
        String actual = game.getLiteralScore();
        String expected = "Love-All";
        assertEquals(actual, expected);
        assertFalse(game.isEnd());
    }

    @Test
    void testTennisGameWhenFifteenLove() {
        game.wonPoint(PLAYER1_NAME);

        String actual = game.getLiteralScore();
        String expected = "Fifteen-Love";
        assertEquals(actual, expected);
        assertFalse(game.isEnd());
    }

    @Test
    void testTennisGameWhenLoveThirty() {
        game.wonPoint(PLAYER2_NAME);
        game.wonPoint(PLAYER2_NAME);

        String actual = game.getLiteralScore();
        String expected = "Love-Thirty";
        assertEquals(actual, expected);
        assertFalse(game.isEnd());
    }

    @Test
    void testTennisGameWhenThirtyAll() {
        game.wonPoint(PLAYER2_NAME);
        game.wonPoint(PLAYER1_NAME);
        game.wonPoint(PLAYER1_NAME);
        game.wonPoint(PLAYER2_NAME);

        String actual = game.getLiteralScore();
        String expected = "Thirty-All";
        assertEquals(actual, expected);
        assertFalse(game.isEnd());
    }

    @Test
    void testTennisGameWhenDeuce() {
        for (int i=0; i<10; ++i)
        {
            game.wonPoint(PLAYER2_NAME);
            game.wonPoint(PLAYER1_NAME);
            game.wonPoint(PLAYER1_NAME);
            game.wonPoint(PLAYER2_NAME);
        }

        String actual = game.getLiteralScore();
        String expected = "Deuce";
        assertEquals(actual, expected);
        assertFalse(game.isEnd());
    }

    @Test
    void testTennisGameWhenWinWithoutDeuce() {
        // 15, 30, 40, Win
        for(int i=0; i<4; ++i)
            game.wonPoint(PLAYER2_NAME);

        String actual = game.getLiteralScore();
        String expected = "Win for " + PLAYER2_NAME;
        assertEquals(actual, expected);
        assertTrue(game.isEnd());
    }
    
    @Test
    void testTennisGameWhenAdvantage() {
        for(int i=0; i<3; ++i)
            game.wonPoint(PLAYER2_NAME);
        for(int i=0; i<4; ++i)
            game.wonPoint(PLAYER1_NAME);
        
        String actual = game.getLiteralScore();
        String expected = "Advantage " + PLAYER1_NAME;
        
        assertEquals(actual, expected);
        assertFalse(game.isEnd());
    }
    
    @Test
    void testTennisGameWhenDeuceAfterAdvantage() {
        for(int i=0; i<3; ++i)
            game.wonPoint(PLAYER2_NAME);
        for(int i=0; i<4; ++i)
            game.wonPoint(PLAYER1_NAME);
        game.wonPoint(PLAYER2_NAME);
        
        String actual = game.getLiteralScore();
        String expected = "Deuce";
        
        assertEquals(actual, expected);
        assertFalse(game.isEnd());
    }

    @Test
    void testTennisGameWhenWinAfterAdvantage() {
        for(int i=0; i<3; ++i)
            game.wonPoint(PLAYER2_NAME);
        for(int i=0; i<4; ++i)
            game.wonPoint(PLAYER1_NAME);
        for(int i=0; i<2; ++i)
            game.wonPoint(PLAYER2_NAME);
        for(int i=0; i<3; ++i)
            game.wonPoint(PLAYER1_NAME);
        
        String actual = game.getLiteralScore();
        String expected = "Win for " + PLAYER1_NAME;
        
        assertEquals(actual, expected);
        assertTrue(game.isEnd());
    }


}
