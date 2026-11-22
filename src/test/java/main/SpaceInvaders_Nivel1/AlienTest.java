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

    private final Alien alien = new Alien(0, 0);

    //Test id: AlienCN1
    @Test
    public void specialValueTesting_Alien() {
        int x = 150;
        int y = 5;
        Alien alien = new Alien(x, y);
        assertNotNull(alien, "Alien should be created succesfully");
        assertEquals(x, alien.getX(), "Alien x should be " + x);
        assertEquals(y, alien.getY(), "Alien y should be " + y);
    }

    //Test ids: InitAlienCN1 - InitAlienCN13
    @ParameterizedTest
    @CsvFileSource(resources = "RobustBoundaryValueTesting_initAlien.csv", numLinesToSkip = 1)
    public void robustBoundaryValueTesting_initAlien(int inX, int inY, int outX, int outY) {
        try {
            Method method = Alien.class.getDeclaredMethod("initAlien", int.class, int.class);
            method.setAccessible(true);
            method.invoke(alien, inX, inY);

            Field valx = Sprite.class.getDeclaredField("x");
            valx.setAccessible(true);
            int x = ((Integer) valx.get(alien));
            Field valy = Sprite.class.getDeclaredField("y");
            valy.setAccessible(true);
            int y = (int) valy.get(alien);
            //CORREGIDO
            assertEquals(outX, x, "Alien x should be " + outX + ". Actual y is: " + y);
            assertEquals(outY, y, "Alien y should be " + outY + ". Actual x is: " + x);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Test id: AlienActCN1
    @Test
    public void strongNormalEquivalenceTesting_actAlien1() {
        Alien alien = new Alien(150, 50);
        alien.act(-1);
        assertEquals(149, alien.getX(), "Alien x should be " + 149);
    }

    // Test id: AlienActCN2
    @Test
    public void strongNormalEquivalenceTesting_actAlien2() {
        Alien alien = new Alien(150, 50);
        alien.act(1);
        assertEquals(151, alien.getX(), "Alien x should be " + 151);
    }
}
