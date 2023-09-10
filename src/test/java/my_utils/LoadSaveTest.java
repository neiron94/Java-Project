package my_utils;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LoadSaveTest {

    @Test
    void loadImageFromFileThrowsException() {
        assertThrows(FileNotFoundException.class, () -> LoadSave.loadImageFromFile("DoesntExist"));
    }

    @Test
    void loadLevelThrowsException() {
        assertThrows(IOException.class, () -> LoadSave.loadLevel("src/test/resources/CorruptedFile.ser"));
        assertThrows(IOException.class, () -> LoadSave.loadLevel("DoesntExist"));
    }

    @Test
    void loadPlayerThrowsException() {
        assertThrows(IOException.class, () -> LoadSave.loadPlayer("src/test/resources/CorruptedFile.ser"));
        assertThrows(IOException.class, () -> LoadSave.loadPlayer("DoesntExist"));
    }
}