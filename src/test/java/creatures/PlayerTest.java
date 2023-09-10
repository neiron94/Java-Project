package creatures;

import items.boosters.ArmorBooster;
import my_utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static my_utils.Constants.CreatureStates.DEAD;
import static my_utils.Constants.CreatureStates.HURT;
import static my_utils.Constants.PlayerConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private static Player testPlayer;

    @BeforeEach
    void createPlayer() {
        testPlayer = new Player();
    }

    @Test
    void testPlayerConstructor() {
        assertAll(() -> assertEquals(START_MAX_HEALTH, testPlayer.getMaxHealth()),
                () -> assertEquals(START_SPEED, testPlayer.getSpeed()),
                () -> assertEquals(START_ARMOR, testPlayer.getArmor()),
                () -> assertEquals(START_DAMAGE, testPlayer.getDamage()),
                () -> assertEquals(0, testPlayer.getCoinsAmount()),
                () -> assertFalse(testPlayer.isHasKey()));
    }

    @Test
    void checkCommonHurt() {
        testPlayer.hurt((int)testPlayer.getArmor());
        assertAll(() -> assertEquals(0, testPlayer.getAnimIndex()),
                () -> assertEquals(Constants.HURT_ANIM_SPEED, testPlayer.getAnimSpeed()),
                () -> assertEquals(HURT, testPlayer.getState()));
    }

    @Test
    void checkKillingHurt() {
        testPlayer.hurt((int)testPlayer.getMaxHealth() + (int)testPlayer.getArmor());
        assertAll(() -> assertEquals(0, testPlayer.getAnimIndex()),
                () -> assertEquals(0, testPlayer.getCurrentHealth()),
                () -> assertEquals(Constants.DEAD_ANIM_SPEED, testPlayer.getAnimSpeed()),
                () -> assertEquals(DEAD, testPlayer.getState()));
    }

    @Test
    void testAddItemAddsItem() {
        ArmorBooster armorBooster = new ArmorBooster(0,0,false);
        testPlayer.addItem(armorBooster);
        assertTrue(testPlayer.getItems().contains(armorBooster));
    }

    @Test
    void testAddCoinAddsCoin() {
        testPlayer.addCoin();
        assertEquals(1, testPlayer.getCoinsAmount());
    }

    @Test
    void testDontAddsCoinsAfterMax() {
        for (int i = 0; i < MAX_COIN + 20; i++)
            testPlayer.addCoin();

        assertEquals(MAX_COIN, testPlayer.getCoinsAmount());
    }

    @Test
    void testUpgradeArmorByOne() {
        testPlayer.upgradeArmor(1);
        assertEquals(START_ARMOR + 1, testPlayer.getArmor());
    }

    @Test
    void testDontUpgradeArmorAfterMax() {
        testPlayer.upgradeArmor(MAX_ARMOR + 20);
        assertEquals(MAX_ARMOR, testPlayer.getArmor());
    }

    @Test
    void testUpgradeDamageByOne() {
        testPlayer.upgradeDamage(1);
        assertEquals(START_DAMAGE + 1, testPlayer.getDamage());
    }

    @Test
    void testDontUpgradeDamageAfterMax() {
        testPlayer.upgradeDamage(MAX_DAMAGE + 20);
        assertEquals(MAX_DAMAGE, testPlayer.getDamage());
    }

    @Test
    void testUpgradeHealthByOne() {
        testPlayer.upgradeHealth(1);
        assertEquals(START_MAX_HEALTH + 1, testPlayer.getMaxHealth());
    }

    @Test
    void testDontUpgradeHealthAfterMax() {
        testPlayer.upgradeHealth(MAX_HEALTH + 20);
        assertEquals(MAX_HEALTH, testPlayer.getMaxHealth());
    }

    @Test
    void testUpgradeSpeedByOne() {
        testPlayer.upgradeSpeed(1);
        assertEquals(START_SPEED + 1, testPlayer.getSpeed());
    }

    @Test
    void testDontUpgradeSpeedAfterMax() {
        testPlayer.upgradeSpeed((int)MAX_SPEED + 20);
        assertEquals(MAX_SPEED, testPlayer.getSpeed());
    }
}