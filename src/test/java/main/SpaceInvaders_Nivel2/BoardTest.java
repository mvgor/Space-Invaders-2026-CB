package main.SpaceInvaders_Nivel2;

import main.Board;
import main.Commons;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class BoardTest {

    //Test id: UpdateCB1
    //Camino: A-C-FIN
    @Test
    public void updateCB1(){
        try {
            Board board = new Board();

            Method updateMethod = Board.class.getDeclaredMethod("update");
            updateMethod.setAccessible(true);

            board.setDeaths(Commons.NUMBER_OF_ALIENS_TO_DESTROY+1);

            updateMethod.invoke(board);

            String valorMessage1 = board.getMessage();

            assertNotEquals("Game won!", valorMessage1);
            assertTrue(board.isInGame());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateCB2
    //Camino: A-B-C-FIN
    @Test
    public void testUpdateBoard2(){
        try {
            Board board = new Board();

            Method updateMethod = Board.class.getDeclaredMethod("update");
            updateMethod.setAccessible(true);

            board.setDeaths(Commons.NUMBER_OF_ALIENS_TO_DESTROY);
            updateMethod.invoke(board);

            String valorMessage1 = board.getMessage();

            assertEquals("Game won!", valorMessage1);
            assertFalse(board.isInGame());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
