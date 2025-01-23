package nl.theijken.apkkeuringstation.service;

import nl.theijken.apkkeuringstation.dto.ActionDto;
import nl.theijken.apkkeuringstation.dto.CarPartDto;
import nl.theijken.apkkeuringstation.exceptions.RecordNotFoundException;
import nl.theijken.apkkeuringstation.model.Action;
import nl.theijken.apkkeuringstation.model.CarPart;
import nl.theijken.apkkeuringstation.repository.ActionRepository;
import nl.theijken.apkkeuringstation.repository.CarPartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActionServiceTest {

    @InjectMocks
    ActionService actionService;

    @Mock
    CarPartRepository carPartRepository;

    @Mock
    ActionRepository actionRepository;

    @Mock
    CarPartService carPartService;

    @Test
    void createAction() {
        // Arrange
        ActionDto actionDto = new ActionDto();
        actionDto.description = "description";
        actionDto.time = 2;
        actionDto.hrRate = 45;
        actionDto.labour = 90;
        actionDto.materials = 100;
        actionDto.price = 190;
        Action action = new Action();
        action.setId(1L);
        action.setDescription("description");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setMaterials(100);
        action.setPrice(190);
        when(actionRepository.save(any())).thenReturn(action);
        // Act
        ActionDto result = actionService.createAction(actionDto);
        // Assart
        assertEquals("description", result.description);
        assertEquals(2, result.time);
        assertEquals(45, result.hrRate);
        assertEquals(90, result.labour);
        assertEquals(100, result.materials);
        assertEquals(190, result.price);
        assertEquals(1, result.id);
    }

    @Test
    void createActionWithCarParts() {
        // Arrange
        Set<CarPart> carParts = new HashSet<>();
        CarPart carPart = new CarPart();
        carPart.setId(1L);
        carPart.setPrice(100);
        carPart.setName("name");
        carParts.add(carPart);
        CarPart carPart2 = new CarPart();
        carPart2.setId(2L);
        carPart2.setPrice(100);
        carPart2.setName("name");
        carParts.add(carPart2);
        CarPartDto carPartDto = new CarPartDto();
        carPartDto.id = 1L;
        carPartDto.name = "name";
        carPartDto.price = 100;
        ActionDto actionDto = new ActionDto();
        actionDto.description = "description";
        actionDto.time = 2;
        actionDto.hrRate = 45;
        actionDto.labour = 90;
        actionDto.materials = 100;
        actionDto.price = 190;
        actionDto.carParts = new HashSet<>();
        actionDto.carParts.add(carPartDto);
        actionDto.tickets = new HashSet<>();
        Action action = new Action();
        action.setId(1L);
        action.setDescription("description");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setMaterials(100);
        action.setPrice(190);
        action.setCarParts(carParts);
        when(actionRepository.save(any())).thenReturn(action);
        // Act
        ActionDto result = actionService.createAction(actionDto);
        // Assart
        assertEquals("description", result.description);
        assertEquals(2, result.time);
        assertEquals(45, result.hrRate);
        assertEquals(90, result.labour);
        assertEquals(100, result.materials);
        assertEquals(190, result.price);
//        assertEquals(190, result.carParts);
    }

    @Test
    void getActions() {
        // Arrange
        List<Action> actions = new ArrayList<>();
        Action action = new Action();
        action.setId(1L);
        action.setDescription("description");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setMaterials(100);
        action.setPrice(190);
        actions.add(action);
        Action action2 = new Action();
        action2.setId(2L);
        action2.setDescription("description");
        action2.setTime(2);
        action2.setHrRate(45);
        action2.setLabour(90);
        action2.setMaterials(100);
        action2.setPrice(190);
        actions.add(action2);
        Action action3 = new Action();
        action3.setId(3L);
        action3.setDescription("description");
        action3.setTime(2);
        action3.setHrRate(45);
        action3.setLabour(90);
        action3.setMaterials(100);
        action3.setPrice(190);
        actions.add(action3);
        when(actionRepository.findAll()).thenReturn(actions);
        // Act
        List<ActionDto> results = actionService.getActions();
        // Assart
        int counter = 0;
        for ( ActionDto result : results ) {
            counter++;
        }
        assertEquals(3, counter);
    }

    @Test
    void getAction() {
        // Arrange
        Action action = new Action();
        action.setId(2L);
        action.setDescription("description");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setMaterials(100);
        action.setPrice(190);
        when(actionRepository.findById(any())).thenReturn(Optional.of(action));
        // Act
        ActionDto result = actionService.getAction(2L);
        // Assert
        assertEquals(2, result.id);
    }

    @Test
    void getActionNotFound() {
        // Arrange
        String expected = "No action found";
        // Act
        RecordNotFoundException result = assertThrows(RecordNotFoundException.class, () -> actionService.getAction(5L));
        // Assert
        assertEquals(expected, result.getMessage());
    }

    @Test
    void deleteAction() {
        // Arrange
        when(actionRepository.existsById(any())).thenReturn(true);
        // Act
        Boolean exists = actionRepository.existsById(String.valueOf(1L));
        Boolean result = actionService.deleteAction(1L);
        // Assert
        assertEquals( true, exists);;
        assertEquals( true, result);;
    }

    @Test
    void deleteNoAction() {
        // Arrange
        when(actionRepository.existsById(any())).thenReturn(false);
        // Act
        Boolean exists = actionRepository.existsById(String.valueOf(1L));
        Boolean result = actionService.deleteAction(1L);
        // Assert
        assertEquals( false, exists);;
        assertEquals( false, result);;
    }

    @Test
    void updateAction() {
        // Arrange
        ActionDto actionDto = new ActionDto();
        actionDto.id = 1L;
        actionDto.description = "description";
        actionDto.time = 4;
        actionDto.hrRate = 45;
        actionDto.labour = 90;
        actionDto.materials = 100;
        actionDto.price = 190;
        Action action = new Action();
        action.setId(1L);
        action.setDescription("description");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setMaterials(100);
        action.setPrice(190);
        when(actionRepository.existsById(any())).thenReturn(true);
        when(actionRepository.findById(any())).thenReturn(Optional.of(action));
        when(actionRepository.save(any())).thenReturn(action);
        // Act
        Boolean exists = actionRepository.existsById(String.valueOf(1L));
        ActionDto result = actionService.updateAction(1L, actionDto);
        // Assert
        assertEquals( true, exists);;
        assertEquals( 4, result.time);;
    }

    @Test
    void updateActionNotFound() {
        // Arrange
        ActionDto actionDto = new ActionDto();
        actionDto.id = 1L;
        actionDto.description = "description";
        actionDto.time = 4;
        actionDto.hrRate = 45;
        actionDto.labour = 90;
        actionDto.materials = 100;
        actionDto.price = 190;
        String expected = "No action found";
        // Act
        RecordNotFoundException result = assertThrows(RecordNotFoundException.class, () -> actionService.updateAction(1L, actionDto));
        // Assert
        assertEquals(expected, result.getMessage());
    }


    @Test
    void updateActionWithCarPartAndTicket() {
        // Arrange
        ActionDto actionDto = new ActionDto();
        actionDto.id = 1L;
        actionDto.description = "description";
        actionDto.time = 4;
        actionDto.hrRate = 45;
        actionDto.labour = 90;
        actionDto.materials = 100;
        actionDto.price = 190;
        actionDto.carParts = new HashSet<>();
        actionDto.tickets = new HashSet<>();
        Action action = new Action();
        action.setId(1L);
        action.setDescription("description");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setMaterials(100);
        action.setPrice(190);
        when(actionRepository.existsById(any())).thenReturn(true);
        when(actionRepository.findById(any())).thenReturn(Optional.of(action));
        when(actionRepository.save(any())).thenReturn(action);
        // Act
        Boolean exists = actionRepository.existsById(String.valueOf(1L));
        ActionDto result = actionService.updateAction(1L, actionDto);
        // Assert
        assertEquals( true, exists);
        assertEquals( 4, result.time);
    }

    @Test
    void assignCarPartToAction() throws RecordNotFoundException {
        Set<CarPart> carParts = new HashSet<>();
        CarPart carPart = new CarPart();
        carPart.setId(1L);
        carPart.setName("car part name");
        carPart.setPrice(25);
        carParts.add(carPart);
        CarPart carPart2 = new CarPart();
        carPart2.setId(2L);
        carPart2.setName("car part name");
        carPart2.setPrice(25);
        carParts.add(carPart2);
        CarPart carPart3 = new CarPart();
        carPart3.setId(3L);
        carPart3.setName("car part name");
        carPart3.setPrice(25);
        Action action = new Action();
        action.setId(1L);
        action.setDescription("description");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setPrice(0);
        action.setCarParts(carParts);
        Action action2 = new Action();
        action2.setId(2L);
        action2.setDescription("description");
        action2.setTime(2);
        action2.setHrRate(45);
        action2.setLabour(90);
        action2.setPrice(0);
        action2.setCarParts(carParts);
        when(actionRepository.existsById(any())).thenReturn(true);
        when(actionRepository.findById(any())).thenReturn(Optional.of(action));
        when(carPartRepository.existsById(any())).thenReturn(true);
        when(carPartRepository.findById(any())).thenReturn(Optional.of(carPart3));
        when(actionRepository.save(any())).thenReturn(action);
        // Act
        Boolean exists = actionRepository.existsById(String.valueOf(1L));
        ActionDto result = actionService.assignCarPartToAction(1L,1L);
        // Assert
        assertEquals( true, exists);
        assertEquals( 25, result.materials);
        // clean uo
    }

    @Test
    void assignCarPartToActionNoActionFound() {
        String expected = "No action found";
        // Act
        RecordNotFoundException result = assertThrows(RecordNotFoundException.class, () -> actionService.assignCarPartToAction(1L, 1L));
        // Assert
        assertEquals(expected, result.getMessage());
    }

    @Test
    void assignCarPartToActionNoCarPartFound() {
        Action action = new Action();
        action.setId(1L);
        action.setDescription("description");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setMaterials(0);
        action.setPrice(0);
        when(actionRepository.existsById(any())).thenReturn(true);
        String expected = "No carpart found";
        // Act
        RecordNotFoundException result = assertThrows(RecordNotFoundException.class, () -> actionService.assignCarPartToAction(1L, 1L));
        // Assert
        assertEquals(expected, result.getMessage());
    }

    @Test
    void assignCarPartToActionCarPartUsed() {
        Set<CarPart> carParts = new HashSet<>();
        CarPart carPart1 = new CarPart();
        carPart1.setId(1L);
        carPart1.setName("This carpart");
        carPart1.setPrice(25);
        carParts.add(carPart1);
        CarPart carPart2 = new CarPart();
        carPart2.setId(1L);
        carPart2.setName("This carpart");
        carPart2.setPrice(25);
        carParts.add(carPart2);
        Action action = new Action();
        action.setCarParts(carParts);
        action.setId(1L);
        action.setDescription("description");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setMaterials(0);
        action.setPrice(0);
        when(actionRepository.existsById(any())).thenReturn(true);
        when(carPartRepository.existsById(any())).thenReturn(true);
        when(actionRepository.findById(any())).thenReturn(Optional.of(action));
        when(carPartRepository.findById(any())).thenReturn(Optional.of(carPart1));
        String expected = "This carpart is already used";
        // Act
        RecordNotFoundException result = assertThrows(RecordNotFoundException.class, () -> actionService.assignCarPartToAction(1L, 1L));
        // Assert
        assertEquals(expected, result.getMessage());
    }

}