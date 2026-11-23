package main.SpaceInvaders_Nivel2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import space_invaders.sprites.Alien;

import static org.junit.Assert.assertEquals;

public class AlienTest {

    //Test id: InitAlienCB1
    //Camino: A – B – D – F – H – J – K
    @Test
    public void initAlienCB1(){
        try{
            int x = 100;
            int y = 100;
            Alien alien = new Alien(x,y);
            assertEquals(100, alien.getX());
            Assertions.assertEquals(100, alien.getY());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Test id: InitAlienCB2
    //Camino: A – B – D – F – H – I – K
    @Test
    public void initAlienCB2(){
        try{
            int x = -5;
            int y = -5;
            Alien alien = new Alien(x,y);
            assertEquals(0, alien.getX());
            assertEquals(0,alien.getY());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Test id: InitAlienCB3
    //Camino:A – B – D – F – G – H – J – K
    @Test
    public void initAlienCB3(){
        try{
            int x = 100;
            int y = 400;
            Alien alien = new Alien(x,y);
            assertEquals(100, alien.getX());
            assertEquals(400,alien.getY());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Test id: InitAlienCB4
    //Camino: A – B – D – E – F – H – J – K
    @Test
    public void initAlienCB4(){
        try{
            int x = -5;
            int y = 100;
            Alien alien = new Alien(x,y);
            assertEquals(-5, alien.getX());
            assertEquals(100,alien.getY());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Test id: InitAlienCB5
    //Camino: A – B – C – D – F – H – J – K
    @Test
    public void initAlienCB5(){
        try{
            int x = 400;
            int y = 100;
            Alien alien = new Alien(x,y);
            assertEquals(400, alien.getX());
            assertEquals(100,alien.getY());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
