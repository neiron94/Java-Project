package my_utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImagesTest {

    @Test
    void loadImagesDoesNotThrow() {
        assertDoesNotThrow(Images::loadAll);
    }
}