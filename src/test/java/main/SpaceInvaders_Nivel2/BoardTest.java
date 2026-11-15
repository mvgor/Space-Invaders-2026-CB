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
    public void updateCB1() {
        try {
            Board board = new Board();

            Method updateMethod = Board.class.getDeclaredMethod("update");
            updateMethod.setAccessible(true);

            board.setDeaths(Commons.NUMBER_OF_ALIENS_TO_DESTROY + 1);

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
    public void updateCB2() {
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

    //Test id: UpdateAlienCB1
    //Camino: A - B - L - M
    @Test
    public void update_aliensCB1() {
        try {
            Board board = new Board();
            Method updateAliensMethod = Board.class.getDeclaredMethod("update_aliens");
            updateAliensMethod.setAccessible(true);

            Field aliensField = Board.class.getDeclaredField("aliens");
            aliensField.setAccessible(true);

            List<Alien> aliens = (List<Alien>) aliensField.get(board);
            aliens.clear();

            updateAliensMethod.invoke(board);

            assertTrue(aliens.isEmpty());
            assertTrue(board.isInGame());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Test id:UpdateAlienCB2
    //Camino: A - B - C - D - [H - B - L - M]
    //CAMINO IMPOSIBLE
    @Test
    public void update_aliensCB2() {
        try {
            Board board = new Board();
            Method updateAliensMethod = Board.class.getDeclaredMethod("update_aliens");
            updateAliensMethod.setAccessible(true);

            Field aliensField = Board.class.getDeclaredField("aliens");
            aliensField.setAccessible(true);

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            List<Alien> aliens = (List<Alien>) aliensField.get(board);
            aliens.clear();

            // Alien en posición central (D=falso, H=falso) y no visible (O=falso)
            Alien alien = new Alien(100, 100);
            campoVisible.set(alien, false);

            aliens.add(alien);

            Field directionField = Board.class.getDeclaredField("direction");
            directionField.setAccessible(true);
            directionField.setInt(board, 1);
            updateAliensMethod.invoke(board);
            assertEquals(1, directionField.getInt(board));

            assertEquals(100, alien.getY());
            assertFalse(alien.isVisible());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //Test id: UpdateAlienCB3
    //Camino:  A - B - C - D - E - F - [H - B - L - M]
    @Test
    public void update_aliensCB3() {
        try {
            Board board = new Board();
            Method updateAliensMethod = Board.class.getDeclaredMethod("update_aliens");
            updateAliensMethod.setAccessible(true);

            Field aliensField = Board.class.getDeclaredField("aliens");
            aliensField.setAccessible(true);

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            List<Alien> aliens = (List<Alien>) aliensField.get(board);
            aliens.clear();

            int xDerecha = Commons.BOARD_WIDTH - Commons.BORDER_RIGHT;
            Alien alien = new Alien(xDerecha, 100);
            campoVisible.set(alien, false);
            aliens.add(alien);

            Field directionField = Board.class.getDeclaredField("direction");
            directionField.setAccessible(true);
            directionField.setInt(board, 1); // direction != -1 -&gt; D evalúa true

            updateAliensMethod.invoke(board);
            // Cambia la dirección a -1 en el nodo E
            assertEquals(-1, directionField.getInt(board));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //Test id: UpdateAlienCB4
    //Camino: A - B - C - D - E - F - G - F - [H - B - L - M]
    //Camino Imposible
    @Test
    public void update_aliensCB4() {
        try {
            Board board = new Board();
            Method updateAliensMethod = Board.class.getDeclaredMethod("update_aliens");
            updateAliensMethod.setAccessible(true);

            Field aliensField = Board.class.getDeclaredField("aliens");
            aliensField.setAccessible(true);

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            List<Alien> aliens = (List<Alien>) aliensField.get(board);
            aliens.clear();

            int xDerecha = Commons.BOARD_WIDTH - Commons.BORDER_RIGHT;
            Alien alien1 = new Alien(xDerecha, 100);
            Alien alien2 = new Alien(150, 100);
            campoVisible.set(alien1, false);
            campoVisible.set(alien2, false);
            aliens.add(alien1);
            aliens.add(alien2);

            Field directionField = Board.class.getDeclaredField("direction");
            directionField.setAccessible(true);
            directionField.setInt(board, 1);

            updateAliensMethod.invoke(board); // Dirección cambia a -1 y los aliens bajan en G (y + GO\_DOWN)
            assertEquals(-1, directionField.getInt(board));
            assertEquals(100 + Commons.GO_DOWN, alien1.getY());
            assertEquals(100 + Commons.GO_DOWN, alien2.getY());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //Test id: UpdateAliebCB5
    //Camino: A - B - C - D - H - I - J - B - L - M
    //Camino Imposible


    //Test id: UpdateAlienCB6
    //Camino: A - B - C - D - H - I - J - K - J - B - L - M
    @Test
    public void update_aliensCB6() {
        try {
            Board board = new Board();
            Method updateAliensMethod = Board.class.getDeclaredMethod("update_aliens");
            updateAliensMethod.setAccessible(true);

            Field aliensField = Board.class.getDeclaredField("aliens");
            aliensField.setAccessible(true);

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            List<Alien> aliens = (List<Alien>) aliensField.get(board);
            aliens.clear();

            int xIzquierda = Commons.BORDER_LEFT + 50;
            Alien alien1 = new Alien(xIzquierda, 50);
            campoVisible.set(alien1, false);
            aliens.add(alien1);

            Field directionField = Board.class.getDeclaredField("direction");
            directionField.setAccessible(true);
            directionField.setInt(board, -1);
            updateAliensMethod.invoke(board); // Dirección cambia a 1 y el alien baja en K (y + GO\_DOWN)
            assertEquals(1, directionField.getInt(board));
            assertEquals(50 + Commons.GO_DOWN, alien1.getY());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateAlienCB7
    //Camino: A - B - L - M - N - O - M
    @Test
    public void update_aliensCB7() {
        try {
            Board board = new Board();
            Method updateAliensMethod = Board.class.getDeclaredMethod("update_aliens");
            updateAliensMethod.setAccessible(true);

            Field aliensField = Board.class.getDeclaredField("aliens");
            aliensField.setAccessible(true);

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            List<Alien> aliens = (List<Alien>) aliensField.get(board);
            aliens.clear();

            Alien alien = new Alien(150, 150);
            campoVisible.set(alien, false); // O evalúa falso
            aliens.add(alien);

            Field directionField = Board.class.getDeclaredField("direction");
            directionField.setAccessible(true);
            directionField.setInt(board, 1);
            int xInicial = alien.getX();
            updateAliensMethod.invoke(board);
            // Al no estar visible, el alien no se desplaza
            assertEquals(xInicial, alien.getX());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateAlienCB8
    //Camino: A - B - L - M - N - O - P - Q - S - M
    @Test
    public void update_aliensCB8() {
        try {
            Board board = new Board();
            Method updateAliensMethod = Board.class.getDeclaredMethod("update_aliens");
            updateAliensMethod.setAccessible(true);

            Field aliensField = Board.class.getDeclaredField("aliens");
            aliensField.setAccessible(true);

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            List<Alien> aliens = (List<Alien>) aliensField.get(board);
            aliens.clear();

            // Posición Y por encima de la zona de invasión (Q evalúa falso)
            int ySegura = Commons.GROUND - Commons.ALIEN_HEIGHT - 10;
            Alien alien = new Alien(150, ySegura);
            campoVisible.set(alien, true); // O evalúa verdadero
            aliens.add(alien);

            Field directionField = Board.class.getDeclaredField("direction");
            directionField.setAccessible(true);
            directionField.setInt(board, 1);
            updateAliensMethod.invoke(board);
            // El juego continúa activo (no se entra en R)
            assertTrue(board.isInGame());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateAlienCB9
    //Camino: A - B - L - M - N - O - P - Q - R - S - M
    @Test
    public void update_aliensCB9() {
        try {
            Board board = new Board();
            Method updateAliensMethod = Board.class.getDeclaredMethod("update_aliens");
            updateAliensMethod.setAccessible(true);

            Field aliensField = Board.class.getDeclaredField("aliens");
            aliensField.setAccessible(true);

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            List<Alien> aliens = (List<Alien>) aliensField.get(board);
            aliens.clear();

            // Posición Y que supera la línea del suelo (Q evalúa verdadero)
            int yInvasion = Commons.GROUND * Commons.ALIEN_HEIGHT + 5;
            Alien alien = new Alien(150, yInvasion);
            campoVisible.set(alien, true);
            aliens.add(alien);

            Field directionField = Board.class.getDeclaredField("direction");
            directionField.setAccessible(true);
            directionField.setInt(board, 1);
            updateAliensMethod.invoke(board);
            // Se ejecuta R: inGame se vuelve false y se asigna el mensaje "Invasion!"
            assertFalse(board.isInGame());
            assertEquals("Invasion!", board.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //Test id: UpdateBombCB1
    //Camino: A-B-FIN
    @Test
    public void updateBombCB1() {
        try {
            Board board = new Board();
            board.getAliens().clear();

            Method updateBombMethod = Board.class.getDeclaredMethod("update_bomb");
            updateBombMethod.setAccessible(true);
            updateBombMethod.invoke(board);

            assertEquals(0, board.getAliens().size());

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateBombCB2
    //Camino: A-[B-C-D-F-G-J]*-FIN
    //Cambiar int rand en update_bomb a -1
    @Test
    public void updateBombCB2() {
        try {
            Board board = new Board();
            List<Alien> aliens = board.getAliens();
            for (Alien alien : aliens) {
                Alien.Bomb bomb = alien.getBomb();
                bomb.setDestroyed(true);
            }

            Method updateBombMethod = Board.class.getDeclaredMethod("update_bomb");
            updateBombMethod.setAccessible(true);
            updateBombMethod.invoke(board);

            for (Alien alien : aliens) {
                assertTrue(alien.getBomb().isDestroyed());
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateBombCB3
    //A-[B-C-D-F-G-J-K-L]*-FIN
    @Test
    public void updateBombCB3() {
        try {
            Board board = new Board();

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            List<Alien> aliens = board.getAliens();
            for (Alien alien : aliens) {
                Alien.Bomb bomb = alien.getBomb();
                bomb.setDestroyed(false);
                bomb.setY(0);
            }

            Player player = board.getPlayer();
            campoVisible.set(player, false);

            Method updateBombMethod = Board.class.getDeclaredMethod("update_bomb");
            updateBombMethod.setAccessible(true);

            updateBombMethod.invoke(board);

            for (Alien alien : aliens) {
                assertFalse(alien.getBomb().isDestroyed());
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateBombCB4
    //A-[B-C-D-F-G-J-K-L-M]*-FIN
    @Test
    public void updateBombCB4() {
        try {
            Board board = new Board();

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            List<Alien> aliens = board.getAliens();
            for (Alien alien : aliens) {
                Alien.Bomb bomb = alien.getBomb();
                bomb.setDestroyed(false);
                bomb.setY(Commons.GROUND - Commons.BOMB_HEIGHT);
            }

            Player player = board.getPlayer();
            campoVisible.set(player, false);

            Method updateBombMethod = Board.class.getDeclaredMethod("update_bomb");
            updateBombMethod.setAccessible(true);

            updateBombMethod.invoke(board);

            for (Alien alien : aliens) {
                assertTrue(alien.getBomb().isDestroyed());
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateBombCB5
    //A-[B-C-D-F-G-H-J-K-L]*-FIN
    @Test
    public void updateBombCB5() {
        try {
            Board board = new Board();

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            Player player = board.getPlayer();
            player.setX(Commons.BOARD_WIDTH / 2);
            player.setY(Commons.GROUND - 20);
            campoVisible.set(player, true);

            List<Alien> aliens = board.getAliens();
            int i = 0;
            for (Alien alien : aliens) {
                campoVisible.set(alien, false);
                Alien.Bomb bomb = alien.getBomb();
                bomb.setDestroyed(false);
                if (i == 0) {
                    bomb.setX(player.getX() - 1);
                    bomb.setY(player.getY() - 1);
                } else if (i == 1) {
                    bomb.setX(player.getX() + Commons.PLAYER_WIDTH + 1);
                    bomb.setY(player.getY() - 1);
                } else if (i == 2) {
                    bomb.setX(player.getX());
                    bomb.setY(player.getY() - 1);
                } else {
                    bomb.setX(player.getX());
                    bomb.setY(player.getY() + Commons.PLAYER_HEIGHT + 1);
                }
                i++;
                if (i == 4) i = 0;
            }

            Method updateBombMethod = Board.class.getDeclaredMethod("update_bomb");
            updateBombMethod.setAccessible(true);
            updateBombMethod.invoke(board);

            for (Alien alien : aliens) {
                assertFalse(alien.getBomb().isDestroyed());
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateBombCB6
    //A-[B-C-D-F-G-H-I-J]*-FIN
    @Test
    public void updateBombCB6() {
        try {
            Board board = new Board();

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            Player player = board.getPlayer();
            player.setX(Commons.BOARD_WIDTH / 2);
            player.setY(Commons.GROUND - 10);
            campoVisible.set(player, true);

            List<Alien> aliens = board.getAliens();
            for (Alien alien : aliens) {
                Alien.Bomb bomb = alien.getBomb();
                bomb.setDestroyed(false);
                bomb.setX(player.getX());
                bomb.setY(player.getY());
            }

            Method updateBombMethod = Board.class.getDeclaredMethod("update_bomb");
            updateBombMethod.setAccessible(true);
            updateBombMethod.invoke(board);

            for (Alien alien : aliens) {
                assertTrue(alien.getBomb().isDestroyed());
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateBombCB7
    //Camino: A-[B-C-D-E-F-G-J-K-L]*-FIN
    //Cambiar int rand en update_bomb a Commons.CHANCE (5)
    @Test
    public void updateBombCB7() {
        try {
            Board board = new Board();

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);

            List<Alien> aliens = board.getAliens();
            for (Alien alien : aliens) {
                campoVisible.set(alien, true);
                Alien.Bomb bomb = alien.getBomb();
                bomb.setDestroyed(true);
            }

            Player player = board.getPlayer();
            campoVisible.set(player, false);

            Method updateBombMethod = Board.class.getDeclaredMethod("update_bomb");
            updateBombMethod.setAccessible(true);

            updateBombMethod.invoke(board);

            for (Alien alien : aliens) {
                assertFalse(alien.getBomb().isDestroyed());
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}



