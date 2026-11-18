package main.SpaceInvaders_Nivel1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import space_invaders.sprites.Alien;
import space_invaders.sprites.Sprite;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AlienTest {

    private final Alien alien = new Alien(0,0);

    @Test
    public void testCajaNegraAlienCreado(){
        int x = 2;
        int y = 7;
        Alien alien = new Alien(x,y);
        assertNotNull(alien, "Alien should be created succesfully");
        assertEquals(x, alien.getX(), "Alien x should be "+x);
        assertEquals(y, alien.getY(), "Alien y should be "+y);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "TestCajaNegraAlienInicializado.csv", numLinesToSkip = 1)
    public void testCajaNegraAlienInicializado(int inX, int inY, int outX, int outY){
        try {
            Method method = Alien.class.getDeclaredMethod("initAlien", int.class, int.class);
            method.setAccessible(true);
            method.invoke(alien, inX,inY);

            Field valx = Sprite.class.getDeclaredField("x");
            valx.setAccessible(true);
            int x = ((Integer) valx.get(alien));
            Field valy = Sprite.class.getDeclaredField("y");
            valy.setAccessible(true);
            int y = (int) valy.get(alien);
            //CORREGIDO
            assertEquals(outX, x, "Alien x should be " + outX + ". Actual y is: "+ y);
            assertEquals(outY, y, "Alien y should be " + outY + ". Actual x is: "+ x);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Tests id: AlienCN16 - AlienCN25
    @ParameterizedTest
    @CsvFileSource(resources = "NormalBoundaryValueAndSpecialTestingActAlien.csv", numLinesToSkip = 1)
    public void normalBoundaryValueAndSpecialTesting_actAlien (int inX, int inDirection, int outX){
        Alien alien = new Alien(inX, 50);
        alien.act(inDirection);
        assertEquals(outX, alien.getX(), "Alien x should be "+outX);
    }
}
