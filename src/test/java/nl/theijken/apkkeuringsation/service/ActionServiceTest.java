package nl.theijken.apkkeuringsation.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionServiceTest {

    @Test
    @DisplayName("run  test")
    void createAction() {
        // Arrange
        int getal1 = 200;
        int getal2 = 300;
        // Act
        int result = getal1 * getal2;
        // Assert
        int espected = 60000;
        assertEquals(espected ,result);
    }

    @Test
    @DisplayName("run  test2")
    void getActions() {
        // Arrange
        // Act
        // Assert
    }

    @Test
    void getAction() {
        // Arrange
        // Act
        // Assert
    }

    @Test
    void deleteAction() {
        // Arrange
        // Act
        // Assert
    }

    @Test
    void updateAction() {
        // Arrange
        // Act
        // Assert
    }

    @Test
    void assignCarPartToAction() {
        // Arrange
        // Act
        // Assert
    }

    @Test
    void actionToDto() {
        // Arrange
        // Act
        // Assert
    }
}