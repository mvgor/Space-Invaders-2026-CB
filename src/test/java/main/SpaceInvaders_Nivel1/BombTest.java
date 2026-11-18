package main.SpaceInvaders_Nivel1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import space_invaders.sprites.Alien;
import space_invaders.sprites.Sprite;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class BombTest {

    private final Alien alien = new Alien(0,0);
    private final Alien.Bomb bomb = alien.new Bomb(0,0);

    @Test
    public void testCajaNegraBombaCreada(){
        int x = 2;
        int y = 7;
        Alien alien = new Alien(x,y);
        Alien.Bomb bomb = alien.new Bomb(x,y);
        assertNotNull(bomb, "Bomb should be created succesfully");
        assertEquals(x, bomb.getX(), "Bomb x should be "+x);
        assertEquals(y, bomb.getY(), "Bomb y should be "+y);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "RobustBoundaryValueTestingInitBomb.csv", numLinesToSkip = 1)
    public void robustBoundaryValueTesting_initBomb(int inX, int inY, int outX, int outY){
        try{
            Alien alien = new Alien(0,0);
            Alien.Bomb bomb = alien.new Bomb(0,0);

            Method method = Alien.Bomb.class.getDeclaredMethod("initBomb", int.class, int.class);
            method.setAccessible(true);
            method.invoke(bomb, inX,inY);

            Field valx = Sprite.class.getDeclaredField("x");
            valx.setAccessible(true);
            int valorx = (Integer) valx.get(bomb);
            Field valy = Sprite.class.getDeclaredField("y");
            valy.setAccessible(true);
            int valory = (int) valy.get(bomb);

            assertNotNull(bomb.getImage());
            assertEquals(outX, valorx);
            assertEquals(outY, valory);

        }catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e){
            throw new RuntimeException(e);
        }

    }
}
