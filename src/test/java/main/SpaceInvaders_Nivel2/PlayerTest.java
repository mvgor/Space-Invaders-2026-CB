package main.SpaceInvaders_Nivel2;

import org.junit.Test;
import space_invaders.sprites.Player;
import space_invaders.sprites.Sprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import static org.junit.Assert.*;

public class PlayerTest {

    //Test id: ActPlayerCB1
    //Camino: A-B-D-FIN
    @Test
    public void actPlayerCB1(){
        Player player = new Player();
        player.setX(10);

        try {
            Field valdx = Sprite.class.getDeclaredField("dx");
            valdx.setAccessible(true);
            int dx = (int) valdx.get(player);

            player.act();

            assertEquals(10+dx, player.getX());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Test id: ActPlayerCB2
    //Camino: A-B-C-D-FIN
    @Test
    public void actPlayerCB2(){
        Player player = new Player();
        player.setX(1);

        player.act();

        assertEquals(2, player.getX());
    }

    //Test id: ActPlayerCB3
    //Camino: A-B-D-E-FIN
    @Test
    public void actPlayerCB3(){
        Player player = new Player();
        player.setX(10740);

        player.act();

        assertEquals(328, player.getX());
    }

    //Test id: KeyPressedCB1
    //Camino: A-B-D-FIN
    @Test
    public void keyPressedCB1(){
        Component source = new Button();
        Player p = new Player();

        try {
            Field valDx = Sprite.class.getDeclaredField("dx");
            valDx.setAccessible(true);
            int dxExpected = (int) valDx.get(p);

            KeyEvent keyEvent = new KeyEvent(
                    source,
                    KeyEvent.KEY_PRESSED,
                    System.currentTimeMillis(),
                    0,
                    KeyEvent.VK_SPACE,
                    KeyEvent.CHAR_UNDEFINED
            );
            p.keyPressed(keyEvent);

            int valorDx = (int) valDx.get(p);

            assertEquals(dxExpected, valorDx);
            p.keyReleased(keyEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Test id: KeyPressedCB2
    //Camino: A-B-C-D-FIN
    @Test
    public void keyPressedCB2(){
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

            assertEquals(dxExpected, valorDx);
            p.keyReleased(eventIzquierda);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Test id: KeyPressedCB3
    //Camino: A-B-D-E-FIN
    @Test
    public void keyPressedCB3(){
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

            assertEquals(dxExpected, valorDx);
            p.keyReleased(eventDerecha);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Test id: KeyReleasedCB1
    //Camino: A-B-D-FIN
    @Test
    public void keyReleasedCB1(){
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

            assertEquals(dxExpected, valorDx);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Test id: KeyReleasedCB2
    //Camino: A-B-C-D-FIN
    @Test
    public void keyReleasedCB2(){
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

            assertEquals(dxExpected, valorDx);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Test id: KeyReleasedCB3
    //Camino: A-B-D-E-FIN
    @Test
    public void keyReleasedCB3(){
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

            assertEquals(dxExpected, valorDx);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
