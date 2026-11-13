package main.SpaceInvaders_Nivel2;

import main.Board;
import main.Commons;
import org.junit.Test;
import space_invaders.sprites.Alien;
import space_invaders.sprites.Player;
import space_invaders.sprites.Sprite;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

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
    public void updateCB2(){
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

    //Test id: UpdateBombCB1
    //Camino: A-B-FIN
    @Test
    public void updateBombCB1(){
        try {
            Board board = new Board();
            board.getAliens().clear();

            Method updateBombMethod = Board.class.getDeclaredMethod("update_bomb");
            updateBombMethod.setAccessible(true);
            updateBombMethod.invoke(board);

            assertEquals(0,board.getAliens().size());

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateBombCB2
    //Camino: A-[B-C-D-F-G-J]*-FIN
    //Cambiar int rand en update_bomb a -1
    @Test
    public void updateBombCB2(){
        try {
            Board board = new Board();
            List<Alien> aliens = board.getAliens();
            for (Alien alien : aliens){
                Alien.Bomb bomb = alien.getBomb();
                bomb.setDestroyed(true);
            }

            Method updateBombMethod = Board.class.getDeclaredMethod("update_bomb");
            updateBombMethod.setAccessible(true);
            updateBombMethod.invoke(board);

            for (Alien alien : aliens){
                assertTrue(alien.getBomb().isDestroyed());
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateBombCB3
    //A-[B-C-D-F-G-J-K-L]*-FIN
    @Test
    public void updateBombCB3(){
        try {
            Board board = new Board();

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            List<Alien> aliens = board.getAliens();
            for (Alien alien : aliens){
                Alien.Bomb bomb = alien.getBomb();
                bomb.setDestroyed(false);
                bomb.setY(0);
            }

            Player player = board.getPlayer();
            campoVisible.set(player,false);

            Method updateBombMethod = Board.class.getDeclaredMethod("update_bomb");
            updateBombMethod.setAccessible(true);

            updateBombMethod.invoke(board);

            for (Alien alien : aliens){
                assertFalse(alien.getBomb().isDestroyed());
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateBombCB4
    //A-[B-C-D-F-G-J-K-L-M]*-FIN
    @Test
    public void updateBombCB4(){
        try {
            Board board = new Board();

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            List<Alien> aliens = board.getAliens();
            for (Alien alien : aliens){
                Alien.Bomb bomb = alien.getBomb();
                bomb.setDestroyed(false);
                bomb.setY(Commons.GROUND-Commons.BOMB_HEIGHT);
            }

            Player player = board.getPlayer();
            campoVisible.set(player,false);

            Method updateBombMethod = Board.class.getDeclaredMethod("update_bomb");
            updateBombMethod.setAccessible(true);

            updateBombMethod.invoke(board);

            for (Alien alien : aliens){
                assertTrue(alien.getBomb().isDestroyed());
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateBombCB5
    //A-[B-C-D-F-G-H-J-K-L]*-FIN
    @Test
    public void updateBombCB5(){
        try {
            Board board = new Board();

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            Player player = board.getPlayer();
            player.setX(Commons.BOARD_WIDTH/2);
            player.setY(Commons.GROUND-20);
            campoVisible.set(player,true);

            List<Alien> aliens = board.getAliens();
            int i = 0;
            for (Alien alien : aliens){
                campoVisible.set(alien,false);
                Alien.Bomb bomb = alien.getBomb();
                bomb.setDestroyed(false);
                if(i == 0){
                    bomb.setX(player.getX()-1);
                    bomb.setY(player.getY()-1);
                }else if(i==1){
                    bomb.setX(player.getX()+Commons.PLAYER_WIDTH+1);
                    bomb.setY(player.getY()-1);
                }else if(i==2){
                    bomb.setX(player.getX());
                    bomb.setY(player.getY()-1);
                }else{
                    bomb.setX(player.getX());
                    bomb.setY(player.getY()+Commons.PLAYER_HEIGHT+1);
                }
                i++;
                if(i==4)i=0;
            }

            Method updateBombMethod = Board.class.getDeclaredMethod("update_bomb");
            updateBombMethod.setAccessible(true);
            updateBombMethod.invoke(board);

            for (Alien alien : aliens){
                assertFalse(alien.getBomb().isDestroyed());
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateBombCB6
    //A-[B-C-D-F-G-H-I-J]*-FIN
    @Test
    public void updateBombCB6(){
        try {
            Board board = new Board();

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            Player player = board.getPlayer();
            player.setX(Commons.BOARD_WIDTH/2);
            player.setY(Commons.GROUND-10);
            campoVisible.set(player,true);

            List<Alien> aliens = board.getAliens();
            for (Alien alien : aliens){
                Alien.Bomb bomb = alien.getBomb();
                bomb.setDestroyed(false);
                bomb.setX(player.getX());
                bomb.setY(player.getY());
            }

            Method updateBombMethod = Board.class.getDeclaredMethod("update_bomb");
            updateBombMethod.setAccessible(true);
            updateBombMethod.invoke(board);

            for (Alien alien : aliens){
                assertTrue(alien.getBomb().isDestroyed());
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateBombCB7
    //Camino: A-[B-C-D-E-F-G-J-K-L]*-FIN
    //Cambiar int rand en update_bomb a Commons.CHANCE (5)
    @Test
    public void updateBombCB7(){
        try {
            Board board = new Board();

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            List<Alien> aliens = board.getAliens();
            for (Alien alien : aliens){
                campoVisible.set(alien,true);
                Alien.Bomb bomb = alien.getBomb();
                bomb.setDestroyed(true);
            }

            Player player = board.getPlayer();
            campoVisible.set(player,false);

            Method updateBombMethod = Board.class.getDeclaredMethod("update_bomb");
            updateBombMethod.setAccessible(true);

            updateBombMethod.invoke(board);

            for (Alien alien : aliens){
                assertFalse(alien.getBomb().isDestroyed());
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
