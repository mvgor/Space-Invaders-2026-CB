package main.SpaceInvaders_Nivel1;

import main.Board;
import main.Commons;
import org.junit.jupiter.api.Test;
import space_invaders.sprites.Alien;
import space_invaders.sprites.Player;
import space_invaders.sprites.Shot;
import space_invaders.sprites.Sprite;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class BoardTest {

    //Test id: BoardCN1
    @Test
    public void specialValueTesting_board() {
        Board board = new Board();
        assertNotNull(board, "Board should be created succesfully");
    }

    //Test id: GameInitCN1
    @Test
    public void specialValueTesting_gameInit() {
        try {
            Board board = new Board();

            Field aliens = Board.class.getDeclaredField("aliens");
            aliens.setAccessible(true);
            List<Alien> listaAliens = (List<Alien>) aliens.get(board);

            Method metodo = Board.class.getDeclaredMethod("gameInit");
            metodo.setAccessible(true);
            metodo.invoke(board);

            int k = 0;
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 6; i++) {
                    assertEquals(Commons.ALIEN_INIT_X + i * Commons.ALIEN_SEPARATOR, listaAliens.get(k).getX(),
                            "El alien número: " + (k + 1) + " no está bien colocado en el eje X");
                    assertEquals(Commons.ALIEN_INIT_Y + j * Commons.ALIEN_SEPARATOR, listaAliens.get(k).getY(),
                            "El alien número: " + (k + 1) + " no está bien colocado en el eje Y");
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
            int yInicial = Commons.GROUND - 10;

            assertEquals(xInicial, valorX, "La posición X no es la mitad del ancho del tablero");
            assertEquals(yInicial, valorY, "La posición Y no está 10px sobre el suelo del tablero");
            //Prueba shot inicializado
            Field h = Shot.class.getDeclaredField("H_SPACE");
            h.setAccessible(true);

            Field v = Shot.class.getDeclaredField("V_SPACE");
            v.setAccessible(true);

            assertEquals(valorX + (int) h.get(board.getShot()), board.getShot().getX(),
                    "Shot no inicializado correctamente en el eje X, Y es: " + board.getShot().getY());
            assertEquals(valorY - (int) v.get(board.getShot()), board.getShot().getY(),
                    "Shot no inicializado correctamente en el eje Y, X es: " + board.getShot().getX());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Test id: UpdateBoardCN1
    @Test
    public void specialValueTesting_updateBoard1() {
        try {
            Board board = new Board();

            Method updateMethod = Board.class.getDeclaredMethod("update");
            updateMethod.setAccessible(true);

            Field campoDeaths = Board.class.getDeclaredField("deaths");
            campoDeaths.setAccessible(true);

            Field campoMessage = Board.class.getDeclaredField("message");
            campoMessage.setAccessible(true);

            Field campoInGame = Board.class.getDeclaredField("inGame");
            campoInGame.setAccessible(true);

            board.setDeaths(Commons.NUMBER_OF_ALIENS_TO_DESTROY);
            updateMethod.invoke(board);

            String valorMessage1 = board.getMessage();

            assertEquals("Game won!", valorMessage1);
            assertFalse(board.isInGame());


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateBoardCN2
    @Test
    public void specialValueTesting_updateBoard2() {
        try {
            Board board = new Board();

            Method updateMethod = Board.class.getDeclaredMethod("update");
            updateMethod.setAccessible(true);
            Field campoDeaths = Board.class.getDeclaredField("deaths");
            campoDeaths.setAccessible(true);
            Field campoMessage = Board.class.getDeclaredField("message");
            campoMessage.setAccessible(true);
            Field campoInGame = Board.class.getDeclaredField("inGame");
            campoInGame.setAccessible(true);
            board.setDeaths(0);

            Player player = board.getPlayer();
            Field valdx = Sprite.class.getDeclaredField("dx");
            valdx.setAccessible(true);
            valdx.set(player, 2);
            int x0 = player.getX();

            Shot shot = board.getShot();
            int xShot = shot.getX();
            int yShot = shot.getY();

            List<Alien> listaAliens = board.getAliens();
            int[] posX = new int[listaAliens.size()];
            for (int i = 0; i < posX.length; i++) {
                posX[i] = listaAliens.get(i).getX();
            }

            updateMethod.invoke(board);

            String valorMessage1 = board.getMessage();

            assertNotEquals("Game won!", valorMessage1);
            assertTrue(board.isInGame());

            //Player.act()
            assertEquals(x0 + 2, player.getX());

            //update_shots()
            assertEquals(xShot, shot.getX());
            assertEquals(yShot - Commons.SHOT_SPEED, shot.getY());

            // update_aliens() y update_bomb()
            for (int i = 0; i < listaAliens.size(); i++) {
                assertEquals(posX[i] - 1, listaAliens.get(i).getX(),
                        "El alien número: " + (i + 1) + " no está bien colocado en el eje Y");

                if (!listaAliens.get(i).getBomb().isDestroyed()) {
                    assertEquals(listaAliens.get(i).getX(), listaAliens.get(i).getBomb().getX());
                    assertEquals(listaAliens.get(i).getY(), listaAliens.get(i).getBomb().getY());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Test id: UpdateShotsCN1
    @Test
    public void weakNormalEquivalenceClass_updateShots1() {
        try {
            Board board = new Board();
            Method metodo = Board.class.getDeclaredMethod("update_shots");
            metodo.setAccessible(true);

            Field campoDeaths = Board.class.getDeclaredField("deaths");
            campoDeaths.setAccessible(true);

            //Vaciamos la lista de aliens para comprobar qué ocurre si un shot no toca un alien
            Field aliens = Board.class.getDeclaredField("aliens");
            aliens.setAccessible(true);
            List<Alien> listaAliens = (List<Alien>) aliens.get(board);
            listaAliens.clear();

            int preEjec = (int) campoDeaths.get(board);
            //ejecutamos metodo
            metodo.invoke(board);
            //Hay que comprobar que el numero de muertes se mantiene, o sea, no se elimino nigun alien
            int postEjec = (int) campoDeaths.get(board);
            assertEquals(preEjec, postEjec, "Se han eliminado aliens aunque no se haya alcanzado ninguno");

        } catch (Exception e) {
            System.out.println("Error al actualizar los aliens");
        }
    }

    //Test id: UpdateShotsCN2
    @Test
    public void weakNormalEquivalenceClass_updateShots2() {
        try {
            Board board = new Board();
            Method metodo = Board.class.getDeclaredMethod("update_shots");
            metodo.setAccessible(true);

            Field campoDeaths = Board.class.getDeclaredField("deaths");
            campoDeaths.setAccessible(true);

            Field aliens = Board.class.getDeclaredField("aliens");
            aliens.setAccessible(true);
            List<Alien> listaAliens = (List<Alien>) aliens.get(board);

            Field shot = Board.class.getDeclaredField("shot");

            int preEjec = (int) campoDeaths.get(board);
            metodo.invoke(board);
            int postEjec = (int) campoDeaths.get(board);

            assertEquals(postEjec, preEjec + 1, "No se actualizan las muertes correctamente");
        } catch (Exception e) {
            System.out.println("Error al actualizar los aliens");
        }
    }

    //Test id: UpdateShotsCN3
    @Test
    public void weakNormalEquivalenceClass_updateShots3() {
        try {
            Board board = new Board();
            Method metodo = Board.class.getDeclaredMethod("update_shots");
            metodo.setAccessible(true);

            Field campoDeaths = Board.class.getDeclaredField("deaths");
            campoDeaths.setAccessible(true);

            Field shot = Board.class.getDeclaredField("shot");
            //Vaciamos la lista de aliens para comprobar qué ocurre si un shot no toca un alien
            Field aliens = Board.class.getDeclaredField("aliens");
            aliens.setAccessible(true);
            List<Alien> listaAliens = (List<Alien>) aliens.get(board);
            listaAliens.clear();

            //Hay que probar que llega al limite
            metodo.invoke(board);
            int fin = board.getShot().getY();
            //Su posición en el eje Y deberá acabar siendo 0
            assertEquals(fin, 0, "El shot no se ha eliminado en la posición correcta");
        } catch (Exception e) {
            System.out.println("Error al actualizar los aliens");
        }
    }

    //Test id: UpdateAliensCN1
    @Test
    public void normalBoundaryValueAndSpecialValueTesting_updateAliens1() {
        try {
            Board board = new Board();
            Method metodo = Board.class.getDeclaredMethod("update_aliens");
            metodo.setAccessible(true);
            Field aliens = Board.class.getDeclaredField("aliens");
            aliens.setAccessible(true);
            List<Alien> listaAliens = (List<Alien>) aliens.get(board);

            listaAliens.get(0).setX(0 + Commons.ALIEN_WIDTH);
            int y = listaAliens.get(0).getY();

            int direccion = board.getDirection();
            metodo.invoke(board);

            boolean baja = true;

            for (Alien alien : listaAliens) {
                if (alien.getY() != y + 15) {
                    baja = false;
                    break;
                }
            }
            assertEquals(true, baja, "Los aliens no descendieron");
            assertNotEquals(board.getDirection(), direccion, "La dirección no ha cambiado");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Test id: UpdateAliensCN2
    @Test
    public void normalBoundaryValueAndSpecialValueTesting_updateAliens2() {
        try {
            Board board = new Board();
            Method metodo = Board.class.getDeclaredMethod("update_aliens");
            metodo.setAccessible(true);
            Field aliens = Board.class.getDeclaredField("aliens");
            aliens.setAccessible(true);
            List<Alien> listaAliens = (List<Alien>) aliens.get(board);

            int direccion = board.getDirection();
            int[] pos = new int[listaAliens.size()];

            for (int i = 0; i < pos.length; i++) {
                pos[i] = listaAliens.get(i).getX();
            }
            metodo.invoke(board);

            boolean desplaza = true;
            if (direccion == 1) {
                for (int i = 0; i < listaAliens.size(); i++) {
                    if (pos[i] != listaAliens.get(i).getX() + 1) {
                        desplaza = false;
                    }
                }
            } else {
                for (int i = 0; i < listaAliens.size(); i++) {
                    if (pos[i] != listaAliens.get(i).getX() - 1) {
                        desplaza = false;
                    }
                }
            }
            assertEquals(true, desplaza, "Los aliens no se desplazaron correctamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Test id: UpdateAliensCN3
    @Test
    public void normalBoundaryValueAndSpecialValueTesting_updateAliens3() {
        try {
            Board board = new Board();
            Method metodo = Board.class.getDeclaredMethod("update_aliens");
            metodo.setAccessible(true);
            Field aliens = Board.class.getDeclaredField("aliens");
            aliens.setAccessible(true);
            List<Alien> listaAliens = (List<Alien>) aliens.get(board);

            listaAliens.get(0).setX(Commons.BOARD_WIDTH - Commons.ALIEN_WIDTH);
            int y = listaAliens.get(0).getY();

            int direccion = board.getDirection();
            metodo.invoke(board);
            boolean baja = true;
            for (Alien alien : listaAliens) {
                if (alien.getY() != y + 15) {
                    baja = false;
                    break;
                }
            }
            assertEquals(true, baja, "Los aliens no descendieron");
            assertNotEquals(board.getDirection(), direccion, "La dirección no ha cambiado");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Test id: UpdateAliensCN4
    @Test
    public void normalBoundaryValueAndSpecialValueTesting_updateAliens4() {
        try {
            Board board = new Board();
            Method metodo = Board.class.getDeclaredMethod("update_aliens");
            metodo.setAccessible(true);
            Field aliens = Board.class.getDeclaredField("aliens");
            aliens.setAccessible(true);
            List<Alien> listaAliens = (List<Alien>) aliens.get(board);

            listaAliens.get(0).setY(Commons.GROUND - Commons.ALIEN_HEIGHT);

            metodo.invoke(board);
            String mensaje = board.getMessage();
            assertEquals("Invasion!", mensaje, "El juego no acaba");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Test id: UpdateBombCN1
    @Test
    public void decisionTableTesting_updateBomb1() {
        int rand = Commons.CHANCE; //Change in the original method
        Board board = new Board();
        Method metodo = null;
        Field aliens = null;
        List<Alien> listaAliens = null;
        Alien alien = null;
        Alien.Bomb bomb = null;
        try {
            metodo = Board.class.getDeclaredMethod("update_bomb");
            metodo.setAccessible(true);
            aliens = Board.class.getDeclaredField("aliens");
            aliens.setAccessible(true);
            listaAliens = (List<Alien>) aliens.get(board);

            alien = listaAliens.get(0);
            bomb = alien.getBomb();
            bomb.setDestroyed(true);

            metodo.invoke(board);

            assertFalse(bomb.isDestroyed());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Test id: UpdateBombCN2
    @Test
    public void decisionTableTesting_updateBomb2() {
        Board board = new Board();
        Method metodo = null;
        Field fieldAliens = null;
        Field fieldPlayer = null;
        List<Alien> listaAliens = null;
        Player player = null;
        Alien alien = null;
        Alien.Bomb bomb = null;
        try {
            metodo = Board.class.getDeclaredMethod("update_bomb");
            metodo.setAccessible(true);
            fieldAliens = Board.class.getDeclaredField("aliens");
            fieldAliens.setAccessible(true);
            listaAliens = (List<Alien>) fieldAliens.get(board);
            fieldPlayer = Board.class.getDeclaredField("player");
            fieldPlayer.setAccessible(true);
            player = (Player) fieldPlayer.get(board);

            alien = listaAliens.get(0);
            int x = player.getX() - 4;
            int y = player.getY() - Commons.BOMB_HEIGHT - 4;
            bomb = alien.getBomb();
            bomb.setDestroyed(false);
            bomb.setX(x);
            bomb.setY(y);

            metodo.invoke(board);

            assertEquals(y + Commons.BOMB_SPEED, bomb.getY(), "Bomb.getY() should be " + (player.getY() - Commons.BOMB_HEIGHT - 4 + Commons.BOMB_SPEED));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Test id: UpdateBombCN3
    @Test
    public void decisionTableTesting_updateBomb3() {
        Board board = new Board();
        Method metodo = null;
        Field fieldAliens = null;
        Field fieldPlayer = null;
        List<Alien> listaAliens = null;
        Alien alien = null;
        Alien.Bomb bomb = null;
        try {
            metodo = Board.class.getDeclaredMethod("update_bomb");
            metodo.setAccessible(true);
            fieldAliens = Board.class.getDeclaredField("aliens");
            fieldAliens.setAccessible(true);
            listaAliens = (List<Alien>) fieldAliens.get(board);
            fieldPlayer = Board.class.getDeclaredField("player");
            fieldPlayer.setAccessible(true);
            alien = listaAliens.get(0);
            bomb = alien.new Bomb(50, Commons.GROUND - Commons.BOMB_HEIGHT); //Con colision con Ground
            bomb.setDestroyed(false);

            metodo.invoke(board);

            assertTrue(bomb.isDestroyed(), "Bomb should be destroyed after collision");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Test id: UpdateBombCN4
    @Test
    public void decisionTableTesting_updateBomb4() {
        int rand = -1; //Change in the original method
        Board board = new Board();
        Method metodo = null;
        Field aliens = null;
        List<Alien> listaAliens = null;
        Alien alien = null;
        Alien.Bomb bomb = null;
        try {
            metodo = Board.class.getDeclaredMethod("update_bomb");
            metodo.setAccessible(true);
            aliens = Board.class.getDeclaredField("aliens");
            aliens.setAccessible(true);
            listaAliens = (List<Alien>) aliens.get(board);

            alien = listaAliens.get(0);
            bomb = alien.getBomb();
            bomb.setDestroyed(true);

            metodo.invoke(board);

            assertTrue(bomb.isDestroyed());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}



