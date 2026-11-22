package main.SpaceInvaders_Nivel2;

import main.Commons;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import space_invaders.sprites.Alien;

import static org.junit.Assert.assertEquals;

public class BombTest {

    //Test id:
    //Camino: A – B – C – F
    @Test
    public void initBombCB1(){
        try{
            int x = 0;
            int y = 0;
            Alien alien = new Alien(x, y);
            assertEquals(x, alien.getBomb().getX());
            assertEquals(y,alien.getBomb().getY());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Test id:
    //Camino: A – B – D – E – F
    @Test
    public void initBombCB2(){
        try{
            int x = 357;
            int y = 351;
            Alien alien = new Alien(x, y);

            assertEquals(Commons.BOARD_WIDTH, alien.getBomb().getX());
            assertEquals(Commons.BOARD_HEIGHT,alien.getBomb().getY());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
