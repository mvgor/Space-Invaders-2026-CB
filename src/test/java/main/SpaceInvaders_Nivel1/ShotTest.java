package main.SpaceInvaders_Nivel1;

import org.junit.jupiter.api.Test;
import space_invaders.sprites.Player;
import space_invaders.sprites.Shot;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShotTest {

    //Test id: ShotCN1
    @Test
    public void specialValueTesting_shot() {
        Shot shot = new Shot();
        assertNotNull(shot, "Shot should be created succesfully");
    }

    //Test id: ShotxyCN1
    @Test
    public void specialValueTesting_shotxy() {
        int x = 150;
        int y = 20;
        Shot shot = new Shot(x, y);
        assertNotNull(shot, "Shot should be created succesfully");
    }

    //Test id: InitShotCN1
    @Test
    public void specialValueTesting_InitShot() {
        try {
            Shot shot = new Shot();
            Player player = new Player();

            Field h = Shot.class.getDeclaredField("H_SPACE");
            h.setAccessible(true);

            Field v = Shot.class.getDeclaredField("V_SPACE");
            v.setAccessible(true);

            Method m = Shot.class.getDeclaredMethod("initShot", int.class, int.class);
            m.setAccessible(true);

            int x = player.getX();
            int y = player.getY();
            m.invoke(shot, x, y);

            assertEquals(x + (int) h.get(shot), shot.getX(),
                    "Shot no inicializado correctamente en el eje X, Y es: " + shot.getY());
            assertEquals(y - (int) v.get(shot), shot.getY(),
                    "Shot no inicializado correctamente en el eje Y, X es: " + shot.getX());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
