package main.SpaceInvaders_Nivel2;

import main.Board;
import main.Commons;
import org.junit.Test;
import space_invaders.sprites.Alien;
import space_invaders.sprites.Player;
import space_invaders.sprites.Shot;
import space_invaders.sprites.Sprite;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    //Test id: GameInitCB1
    //Camino: A-[B-(C-D-C)6-B]4-E
    @Test
    public void gameInitCB1() {
        try{
            Board board = new Board();

            Field aliens = Board.class.getDeclaredField("aliens");
            aliens.setAccessible(true);
            List<Alien> listaAliens =(List<Alien>)  aliens.get(board);

            Method metodo = Board.class.getDeclaredMethod("gameInit");
            metodo.setAccessible(true);
            metodo.invoke(board);

            int k = 0;
            for(int j = 0; j < 4; j++) {
                for (int i = 0; i < 6; i++) {
                    assertEquals(Commons.ALIEN_INIT_X + i * Commons.ALIEN_SEPARATOR, listaAliens.get(k).getX());
                    assertEquals(Commons.ALIEN_INIT_Y + j * Commons.ALIEN_SEPARATOR, listaAliens.get(k).getY());
                    k++;
                }
            }
            //Prueba PLayer inicializado
            Field valX = Sprite.class.getDeclaredField("x");
            valX.setAccessible(true);
            int valorX = (int) valX.get(board.getPlayer());

            Field valy = Sprite.class.getDeclaredField("y");
            valy.setAccessible(true);
            int valorY = (int) valy.get(board.getPlayer());

            int xInicial = Commons.BOARD_WIDTH / 2;
            int yInicial = Commons.GROUND + 10;

            assertEquals(xInicial, valorX);
            assertEquals(yInicial, valorY);
            //Prueba shot inicializado
            Field h = Shot.class.getDeclaredField("H_SPACE");
            h.setAccessible(true);

            Field v = Shot.class.getDeclaredField("V_SPACE");
            v.setAccessible(true);

            assertEquals(valorX + (int) h.get(board.getShot()), board.getShot().getX());
            assertEquals(valorY - (int) v.get(board.getShot()), board.getShot().getY());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
    //Test id: UpdateShotsCB1
//Camino: A-[B-C-D]*-E-[B-C-B]*-F-G-FIN
    @Test
    public void updateShotsCB1() {
        try {
            Board board = new Board();

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);
            List<Alien> aliens = board.getAliens();

            Method updateShotsMethod = Board.class.getDeclaredMethod("update_shots");
            updateShotsMethod.setAccessible(true);

            board.getShot().setY(5);
            board.getShot().setX(152);
            campoVisible.set(board.getShot(), true);
            int muertes = board.getDeaths();
            updateShotsMethod.invoke(board);

            assertEquals(board.getDeaths(), muertes+1);
            assertFalse(board.getShot().isVisible());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Test id: updateShotsCB2
    //Camino: A-[B-C-D-B]*-F-H-FIN
    @Test
    public void updateShotsCB2() {
        try {
            Board board = new Board();
            board.getShot().setY(Commons.GROUND);
            int y = board.getShot().getY();
            Method updateShotsMethod = Board.class.getDeclaredMethod("update_shots");
            updateShotsMethod.setAccessible(true);

            updateShotsMethod.invoke(board);

            assertEquals(board.getShot().getY(), y-Commons.SHOT_SPEED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Test id: updateShotsCB3
    //Camino: A-[B-C-B]*-F-G-FIN
    @Test
    public void updateShotsCB3() {
        try {
            Board board = new Board();
            List<Alien> aliens = board.getAliens();

            Method updateShotsMethod = Board.class.getDeclaredMethod("update_shots");
            updateShotsMethod.setAccessible(true);

            for (Alien alien : aliens) {
                alien.die();
            }

            int muertes = board.getDeaths();
            updateShotsMethod.invoke(board);

            for (Alien alien : aliens) {
                assertFalse(alien.isVisible());
            }
            assertEquals(board.getDeaths(), muertes+1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Test id: updateShotsCB4
    //Camino: A-B-F-G-FIN
    @Test
    public void updateShotsCB4() {
        try {
            Board board = new Board();
            List<Alien> aliens = board.getAliens();
            aliens.clear();

            Method updateShotsMethod = Board.class.getDeclaredMethod("update_shots");
            updateShotsMethod.setAccessible(true);

            int muertes = board.getDeaths();
            updateShotsMethod.invoke(board);
            assertEquals(board.getDeaths(), muertes+1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Test id: updateShotsCB5
    //Camino: A-B-F-H-FIN
    @Test
    public void updateShotsCB5() {
        try {
            Board board = new Board();
            List<Alien> aliens = board.getAliens();
            aliens.clear();

            Method updateShotsMethod = Board.class.getDeclaredMethod("update_shots");
            updateShotsMethod.setAccessible(true);

            board.getShot().setY(5);
            int y = board.getShot().getY();
            updateShotsMethod.invoke(board);

            assertEquals((y-Commons.SHOT_SPEED), board.getShot().getY(), board.getShot().getY());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Test id: updateShotsCB6
    //Camino: A-FIN
    @Test
    public void updateShotsCB6() {
        try {
            Board board = new Board();

            Field campoVisible = Sprite.class.getDeclaredField("visible");
            campoVisible.setAccessible(true);
            campoVisible.set(board.getShot(), false);

            Method updateShotsMethod = Board.class.getDeclaredMethod("update_shots");
            updateShotsMethod.setAccessible(true);

            updateShotsMethod.invoke(board);

            assertEquals(board.getShot().isVisible(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
