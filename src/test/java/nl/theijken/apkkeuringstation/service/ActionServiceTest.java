package nl.theijken.apkkeuringstation.service;

import nl.theijken.apkkeuringstation.dto.ActionDto;
import nl.theijken.apkkeuringstation.dto.CarPartDto;
import nl.theijken.apkkeuringstation.exceptions.RecordNotFoundException;
import nl.theijken.apkkeuringstation.model.Action;
import nl.theijken.apkkeuringstation.model.CarPart;
import nl.theijken.apkkeuringstation.repository.ActionRepository;
import nl.theijken.apkkeuringstation.repository.CarPartRepository;
import org.checkerframework.common.value.qual.ArrayLenRange;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActionServiceTest {

    @InjectMocks
    ActionService actionService;

    @Mock
    CarPartRepository carPartRepository;

    @Mock
    CarPartService carPartService;

    @Mock
    ActionRepository actionRepository;

    @Mock
    Action action;

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
        ActionDto actionDto = new ActionDto();
        actionDto.description = "description";
        actionDto.time = 2;
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
    }

    @Test
    void getActions() {
        // Arrange
        List<ActionDto> actionDtos = new ArrayList<>();
        ActionDto actionDto = new ActionDto();
        actionDto.id = 1L;
        actionDto.description = "description";
        actionDto.time = 2;
        actionDto.hrRate = 45;
        actionDto.labour = 90;
        actionDto.materials = 100;
        actionDto.price = 190;
        actionDtos.add(actionDto);
        ActionDto actionDto2 = new ActionDto();
        actionDto2.id = 2L;
        actionDto2.description = "description";
        actionDto2.time = 2;
        actionDto2.hrRate = 45;
        actionDto2.labour = 90;
        actionDto2.materials = 100;
        actionDto2.price = 190;
        actionDtos.add(actionDto2);
        ActionDto actionDto3 = new ActionDto();
        actionDto3.id = 2L;
        actionDto3.description = "description";
        actionDto3.time = 2;
        actionDto3.hrRate = 45;
        actionDto3.labour = 90;
        actionDto3.materials = 100;
        actionDto3.price = 190;
        actionDtos.add(actionDto3);
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
        ActionDto actionDto = new ActionDto();
        actionDto.id = 1L;
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
        when(actionRepository.findById(any())).thenReturn(Optional.of(action));
        // Act
        ActionDto result = actionService.getAction(1L);
        // Assert
        assertEquals(1, result.id);
    }

    @Test
    void getActionNotFound() {
        // Arrange
//        Action action = new Action();
//        action.setId(1L);
//        action.setDescription("description");
//        action.setTime(2);
//        action.setHrRate(45);
//        action.setLabour(90);
//        action.setMaterials(100);
//        action.setPrice(190);
        when(actionRepository.findById(any())).thenReturn(Optional.of(action));
        Optional<Action> action = Optional.empty();
//        when(action.isPresent()).thenReturn(false);
        // Act
        ActionDto result = actionService.getAction(2L);
        // Assert
        assertEquals( 0L, result.id);
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

//    @Test
//    void updateActionWithNoActionFound() {
//        // Arrange
//        ActionDto actionDto = new ActionDto();
//        actionDto.id = 1L;
//        actionDto.description = "description";
//        actionDto.time = 4;
//        actionDto.hrRate = 45;
//        actionDto.labour = 90;
//        actionDto.materials = 100;
//        actionDto.price = 190;
//        when(!actionRepository.existsById(any())).thenReturn(false);
//        when(actionRepository.findById(any())).thenReturn(Optional.of(action));
//        when(actionRepository.save(any())).thenReturn(action);
//        // Act
//        Boolean exists = actionRepository.existsById(String.valueOf(1L));
////        ActionDto result = actionService.updateAction(1L, actionDto);
//        // Assert
//        assertEquals( false, exists);
////        assertEquals( 0, result.time);;
//    }

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
    void assignCarPartToAction() {
        CarPartDto carPartDto = new CarPartDto();
        carPartDto.id = 1L;
        carPartDto.name = "car part name";
        carPartDto.price = 25;
        CarPart carPart = new CarPart();
        carPart.setId(1L);
        carPart.setName("car part name");
        carPart.setPrice(25);
        ActionDto actionDto = new ActionDto();
        actionDto.id = 1L;
        actionDto.description = "description";
        actionDto.time = 4;
        actionDto.hrRate = 45;
        actionDto.labour = 90;
        actionDto.materials = 0;
        actionDto.price = 0;
        actionDto.carParts = new HashSet<>();
        actionDto.tickets = new HashSet<>();
        Action action = new Action();
        action.setId(1L);
        action.setDescription("description");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setMaterials(0);
        action.setPrice(0);
        when(actionRepository.existsById(any())).thenReturn(true);
        when(actionRepository.findById(any())).thenReturn(Optional.of(action));
        when(carPartRepository.existsById(any())).thenReturn(true);
        when(carPartRepository.findById(any())).thenReturn(Optional.of(carPart));
        when(actionRepository.save(any())).thenReturn(action);
        // Act
        Boolean exists = actionRepository.existsById(String.valueOf(1L));
        ActionDto result = actionService.assignCarPartToAction(1L,1L);
        // Assert
        assertEquals( true, exists);
        assertEquals( 25, result.materials);
    }

    @Test
    void assignMissingCarPartToAction() {
        // Arrange
        CarPartDto carPartDto = new CarPartDto();
        carPartDto.id = 1L;
        carPartDto.name = "car part name";
        carPartDto.price = 25;
        CarPart carPart = new CarPart();
        carPart.setId(1L);
        carPart.setName("car part name");
        carPart.setPrice(25);
        ActionDto actionDto = new ActionDto();
        actionDto.id = 1L;
        actionDto.description = "description";
        actionDto.time = 4;
        actionDto.hrRate = 45;
        actionDto.labour = 90;
        actionDto.materials = 0;
        actionDto.price = 0;
//        actionDto.carParts = null;
        actionDto.tickets = new HashSet<>();
        Action action = new Action();
        action.setId(1L);
        action.setDescription("description");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setMaterials(0);
        action.setPrice(0);
        when(actionRepository.existsById(any())).thenReturn(true);
        when(actionRepository.findById(any())).thenReturn(Optional.of(action));
        when(carPartRepository.existsById(any())).thenReturn(true);
        when(carPartRepository.findById(any())).thenReturn(Optional.of(carPart));
        when(actionRepository.save(any())).thenReturn(action);
        // Act
        Boolean exists = actionRepository.existsById(String.valueOf(1L));
        ActionDto result = actionService.assignCarPartToAction(1L,1L);
        // Assert
        assertEquals( true, exists);
        assertEquals( 25, result.materials);
    }

//    @Test
//    void dtoToAction() {
//        // Arrange
//        ActionDto actionDto = new ActionDto();
//        actionDto.id = 1L;
//        actionDto.description = "description";
//        actionDto.time = 4;
//        actionDto.hrRate = 45;
//        actionDto.labour = 90;
//        actionDto.materials = 0;
//        actionDto.price = 0;
//        actionDto.carParts = new HashSet<>();
//        actionDto.tickets = new HashSet<>();
//        CarPartDto carPartDto = new CarPartDto();
//        carPartDto.id = 1L;
//        carPartDto.name = "car part name";
//        carPartDto.price = 25;
//        actionDto.carParts.add(carPartDto);
//        CarPartDto carPartDto2 = new CarPartDto();
//        carPartDto.id = 2L;
//        carPartDto.name = "car part name2";
//        carPartDto.price = 25;
//        actionDto.carParts.add(carPartDto2);
////        Set<CarPart> carPartsDto = new HashSet<>();
////        action.setCarParts(carPartsDto);
//        action.setCarParts(actionDto.carParts);
//        // Act
//        Action result = actionService.dtoToAction(actionDto);
//        // Assert
//        assertEquals( 50, result.getMaterials());
//    }

//    @Test
//    void assignCarPartToActionWithNoActionFound() {
//        when(!actionRepository.existsById(any())).thenReturn(false);
//        // Act
//        Boolean exists = actionRepository.existsById(String.valueOf(1L));
//        ActionDto result = actionService.assignCarPartToAction(1L,1L);
//        // Assert
//        assertEquals( false, exists);
//    }

//    @Test
//    void assignCarPartToActionWithNoCarPartFound() {
//        when(!carpartRepository.existsById(any())).thenReturn(false);
//        // Act
////        Boolean exists = carPartRepository.existsById(String.valueOf(1L));
//        ActionDto result = actionService.assignCarPartToAction(1L,1L);
//
//        // Assert
//        assertEquals( 0, result.id);
//    }

    @Test
    @DisplayName("multiply Hourly rate with hours")
    void multiply() {
        // Arrange
        Action action = new Action();
        action.setHrRate(45);
        action.setTime(2);
        // Act
        Double result = action.getHrRate() * action.getTime();
        // Assert
        assertEquals(90, result);
    }


//    @Test
//    @DisplayName("Check calculations in actions")
//    void addPriceToMaterials() {
//        // Arrange
//        Action action = new Action();
//        action.setHrRate(45);
//        action.setTime(2);
//
//        Set<CarPart> carParts = new HashSet<>();
//        for (int i = 0; i < 4; i++) {
//            CarPart carPart = new CarPart();
//            carPart.setPrice(100);
//            carParts.add(carPart);
//        }
////        Mockito
////                .when(actionRepository.findById(String.valueOf(action.getId())))
////                .thenReturn(Optional.of(action));
//        // Act
//        Double labour = action.getHrRate() * action.getTime();
//        double price = 0.0;
//        for (CarPart carPart : carParts) {
//            price = carPart.getPrice() + price;
//            action.setMaterials(price);
//            action.setPrice(price + labour);
//        }
//        Double materials = action.getMaterials();
//
//        // Assert
//        assertEquals(90, labour);
//        assertEquals(400, materials);
//        assertEquals(490, action.getPrice());
//    }
}