package main.SpaceInvaders_Nivel1;

import main.Commons;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import space_invaders.sprites.Player;
import space_invaders.sprites.Sprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void specialValueTesting_Player() {
        Player player = new Player();
        assertNotNull(player, "Player should be created succesfully");

    }

    //public initPlayer --> casos especiales (si esta en las coordenadas iniciales)
    @Test
    public void inicializarPlayer() {
        Player player = new Player();
        try{
            Method metodo = Player.class.getDeclaredMethod("initPlayer");
            metodo.setAccessible(true);
            metodo.invoke(player);

            Field valX = Sprite.class.getDeclaredField("x");
            valX.setAccessible(true);
            int valorX = (int) valX.get(player);
            Field valy = Sprite.class.getDeclaredField("y");
            valy.setAccessible(true);
            int valorY = (int) valy.get(player);

            int xInicial = Commons.BOARD_WIDTH / 2;
            int yInicial = Commons.GROUND + 10;

            assertEquals(xInicial, valorX, "La posición X no es la mitad del ancho del tablero");
            assertEquals(yInicial, valorY, "La posición Y no está 10px sobre el suelo del tablero");

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "NormalBoundaryValueTestingActPlayer.csv", numLinesToSkip = 1)
    public void normalBoundaryValueTesting_act(int x, int dx, int output) {
        try{
            Player player = new Player();
            player.setX(x);

            Field valdx = Sprite.class.getDeclaredField("dx");
            valdx.setAccessible(true);
            valdx.set(player,dx);

            //El JAR da problemas para establecer width así que le asignamos
            //manualmente el valor Commons.PLAYER_WIDTH
            Field valw = Player.class.getDeclaredField("width");
            valw.setAccessible(true);
            valw.set(player,Commons.PLAYER_WIDTH);

            player.act();
            assertEquals(output,player.getX(),"Player x should be " + output + ". Actual x is: "+ player.getX());

        }catch (NoSuchFieldException | IllegalAccessException e ) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "WeakNormalEquivalenceClassAndNormalBoundaryValueTestingActPlayer.csv", numLinesToSkip = 1)
    public void weakNormalEquivalenceClassAndNormalBoundaryValueTesting_act(int x, int dx, int output) {
        try{
            Player player = new Player();
            player.setX(x);

            Field valdx = Sprite.class.getDeclaredField("dx");
            valdx.setAccessible(true);
            valdx.set(player,dx);

            //El JAR da problemas para establecer width así que le asignamos
            //manualmente el valor Commons.PLAYER_WIDTH
            Field valw = Player.class.getDeclaredField("width");
            valw.setAccessible(true);
            valw.set(player,Commons.PLAYER_WIDTH);

            player.act();
            assertEquals(output,player.getX(),"Player x should be " + output + ". Actual x is: "+ player.getX());

        }catch (NoSuchFieldException | IllegalAccessException e ) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCajaNegraKeyPressed1(){
        Component source = new Button();
        Player p = new Player();

        try {
            int dxExpected = -2;
            KeyEvent eventIzquierda = new KeyEvent(
                    source,
                    KeyEvent.KEY_PRESSED,
                    System.currentTimeMillis(),
                    0,
                    KeyEvent.VK_LEFT,
                    KeyEvent.CHAR_UNDEFINED
            );
            p.keyPressed(eventIzquierda);

            Field valDx = Sprite.class.getDeclaredField("dx");
            valDx.setAccessible(true);
            int valorDx = (int) valDx.get(p);

            assertEquals(dxExpected, valorDx, "El esperado: " + dxExpected + " y el dado es: " + valorDx);
            System.out.println("El esperado: " + dxExpected + " y el dado es: " + valorDx);
            p.keyReleased(eventIzquierda);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testCajaNegraKeyPressed2() throws NoSuchFieldException, IllegalAccessException {
        Component source = new Button();
        Player p = new Player();

        try {
            int dxExpected = 2;
            KeyEvent eventDerecha = new KeyEvent(
                    source,
                    KeyEvent.KEY_PRESSED,
                    System.currentTimeMillis(),
                    0,
                    KeyEvent.VK_RIGHT,
                    KeyEvent.CHAR_UNDEFINED
            );
            p.keyPressed(eventDerecha);

            Field valDx = Sprite.class.getDeclaredField("dx");
            valDx.setAccessible(true);
            int valorDx = (int) valDx.get(p);

            assertEquals(dxExpected, valorDx, "El esperado: " + dxExpected + " y el dado es: " + valorDx);
            System.out.println("El esperado: " + dxExpected + " y el dado es: " + valorDx);
            p.keyReleased(eventDerecha);




        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testCajaNegraKeyPressed3(){
        Component source = new Button();
        Player p = new Player();

        try {
            Field valDx = Sprite.class.getDeclaredField("dx");
            valDx.setAccessible(true);
            int dxExpected = (int) valDx.get(p);

            KeyEvent eventKeyIncorrect = new KeyEvent(
                    source,
                    KeyEvent.KEY_PRESSED,
                    System.currentTimeMillis(),
                    0,
                    KeyEvent.VK_SPACE,
                    KeyEvent.CHAR_UNDEFINED
            );
            p.keyPressed(eventKeyIncorrect);

            int valorDx = (int) valDx.get(p);

            assertEquals(dxExpected, valorDx, "El esperado: " + dxExpected + " y el dado es: " + valorDx);
            System.out.println("El esperado: " + dxExpected + " y el dado es: " + valorDx);
            p.keyReleased(eventKeyIncorrect);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testCajaNegrakeyReleased1(){
        Component source = new Button();
        Player p = new Player();

        try {
            int dxExpected = 0;
            KeyEvent eventIzquierda = new KeyEvent(
                    source,
                    KeyEvent.KEY_RELEASED,
                    System.currentTimeMillis(),
                    0,
                    KeyEvent.VK_LEFT,
                    KeyEvent.CHAR_UNDEFINED
            );
            p.keyReleased(eventIzquierda);

            Field valDx = Sprite.class.getDeclaredField("dx");
            valDx.setAccessible(true);
            int valorDx = (int) valDx.get(p);

            assertEquals(dxExpected, valorDx, "El esperado: " + dxExpected + " y el dado es: " + valorDx);
            System.out.println("El esperado: " + dxExpected + " y el dado es: " + valorDx);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCajaNegrakeyReleased2(){
        Component source = new Button();
        Player p = new Player();

        try {
            int dxExpected = 0;
            KeyEvent eventDerecha = new KeyEvent(
                    source,
                    KeyEvent.KEY_RELEASED,
                    System.currentTimeMillis(),
                    0,
                    KeyEvent.VK_RIGHT,
                    KeyEvent.CHAR_UNDEFINED
            );
            p.keyReleased(eventDerecha);

            Field valDx = Sprite.class.getDeclaredField("dx");
            valDx.setAccessible(true);
            int valorDx = (int) valDx.get(p);

            assertEquals(dxExpected, valorDx, "El esperado: " + dxExpected + " y el dado es: " + valorDx);
            System.out.println("El esperado: " + dxExpected + " y el dado es: " + valorDx);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCajaNegrakeyReleased3(){
        Component source = new Button();
        Player p = new Player();

        try {
            Field valDx = Sprite.class.getDeclaredField("dx");
            valDx.setAccessible(true);
            int dxExpected = (int) valDx.get(p);
            KeyEvent eventKeyIncorrect = new KeyEvent(
                    source,
                    KeyEvent.KEY_RELEASED,
                    System.currentTimeMillis(),
                    0,
                    KeyEvent.VK_SPACE,
                    KeyEvent.CHAR_UNDEFINED
            );
            p.keyReleased(eventKeyIncorrect);

            int valorDx = (int) valDx.get(p);

            assertEquals(dxExpected, valorDx, "El esperado: " + dxExpected + " y el dado es: " + valorDx);
            System.out.println("El esperado: " + dxExpected + " y el dado es: " + valorDx);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
