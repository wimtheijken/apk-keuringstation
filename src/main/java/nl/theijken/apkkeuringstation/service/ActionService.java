package nl.theijken.apkkeuringstation.service;

import nl.theijken.apkkeuringstation.dto.ActionDto;
import nl.theijken.apkkeuringstation.exceptions.RecordNotFoundException;
import nl.theijken.apkkeuringstation.model.Action;
import nl.theijken.apkkeuringstation.model.CarPart;
import nl.theijken.apkkeuringstation.model.Ticket;
import nl.theijken.apkkeuringstation.repository.ActionRepository;
import nl.theijken.apkkeuringstation.repository.CarPartRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActionService {

        private final ActionRepository actionRepository;
        private final CarPartRepository carPartRepository;
        private final CarPartService carPartService;

    public ActionService(ActionRepository actionRepository,
                         CarPartRepository carPartRepository,
                         CarPartService carPartService) {
            this.actionRepository = actionRepository;
            this.carPartRepository = carPartRepository;
            this.carPartService = carPartService;
    }

        // POST
        public ActionDto createAction(ActionDto actionDto) {
            Action action = dtoToAction(actionDto);
            Action savedAction = actionRepository.save(action);
            return actionToDto(savedAction);
        }

        // GET ALL
        public List<ActionDto> getActions() {
            List<Action> actions = actionRepository.findAll();
            List<ActionDto> actionDtos = new ArrayList<>();
            for( Action action : actions ) {
                ActionDto actionDto = actionToDto(action);
                actionDtos.add(actionDto);
            }
            return actionDtos;
        }

        // GET ONE
        public ActionDto getAction(Long id) {
            Optional<Action> action = actionRepository.findById(String.valueOf(id));
            if( action.isPresent() ){
                return actionToDto(action.get());
            } else {
                throw new RecordNotFoundException("No action found");
            }
        }

        // DELETE
        public boolean deleteAction(Long id) {
            if(actionRepository.existsById(String.valueOf(id))) {
                actionRepository.deleteById(String.valueOf(id));
                return true;
            }
            return false;
        }

        // PUT
        public ActionDto updateAction(Long id, ActionDto actionDto) {
            if(!actionRepository.existsById(String.valueOf(id))) {
                throw new RecordNotFoundException("No action found");
            }
            Action storedAction = actionRepository.findById(String.valueOf(id)).orElse(null);
            storedAction.setId(actionDto.id);
            storedAction.setDescription(actionDto.description);
            storedAction.setHrRate(actionDto.hrRate);
            storedAction.setTime(actionDto.time);
            storedAction.setLabour(actionDto.hrRate * actionDto.time);
            storedAction.setMaterials(actionDto.materials);
            storedAction.setPrice(actionDto.price);
            if (actionDto.carParts == null) {
                actionDto.carParts = new HashSet<>();
            } else {
                Set<CarPart> carParts = new HashSet<>();
                storedAction.setCarParts(carParts);
            }
            if (actionDto.tickets == null) {
                actionDto.tickets = new HashSet<>();
            } else {
                Set<Ticket> tickets = new HashSet<>();
                storedAction.setTickets(tickets);
            }
            return actionToDto(actionRepository.save(storedAction));
        }

        //PUT CarPart -> Action
        public ActionDto assignCarPartToAction(Long id, Long carPartId) {
            if(!actionRepository.existsById(String.valueOf(id))) {
                throw new RecordNotFoundException("No action found");
            }
            Action storedAction = actionRepository.findById(String.valueOf(id)).orElse(null);
            if(!carPartRepository.existsById(String.valueOf(carPartId))) {
                throw new RecordNotFoundException("No carpart found");
            }
            CarPart carPart = carPartRepository.findById(String.valueOf(carPartId)).orElse(null);
            Set<CarPart> carParts2 = storedAction.getCarParts();
            for (CarPart carPart2 : carParts2){
                assert carPart != null;
                if(Objects.equals(carPart2.getId(), carPart.getId())) {
                    throw new RecordNotFoundException( carPart2.getName() + " is already used");
                }
            }
            if (storedAction.getCarParts() == null) {
                Set<CarPart> carParts = new HashSet<>();
                carParts.add(carPart);
                storedAction.setMaterials(carPart.getPrice());
                storedAction.setPrice(storedAction.getMaterials() + storedAction.getLabour());
                storedAction.setCarParts(carParts);
            } else {
                Set<CarPart> carParts = storedAction.getCarParts();
                carParts.add(carPart);
                storedAction.setMaterials(carPart.getPrice() + storedAction.getMaterials());
                storedAction.setPrice(storedAction.getMaterials() + storedAction.getLabour());
                storedAction.setCarParts(carParts);
            }
            return actionToDto(actionRepository.save(storedAction));
        }

    // DTO -> MODEL
    private Action dtoToAction(ActionDto actionDto) {
        Action action = new Action();
        action.setDescription(actionDto.description);
        action.setHrRate(actionDto.hrRate);
        action.setTime(actionDto.time);
        action.setLabour(actionDto.hrRate * actionDto.time);
        if (actionDto.carParts == null) {
            actionDto.carParts = new HashSet<>();
        } else {
            Set<CarPart> carParts = new HashSet<>();
            action.setCarParts(carParts);
            double total = 0.0;
            for (CarPart carPart : action.getCarParts()) {
                total = total + carPart.getPrice();
            }
            action.setMaterials(total);
        }
        if (actionDto.tickets == null) {
            actionDto.tickets = new HashSet<>();
        } else {
            Set<Ticket> tickets = new HashSet<>();
            action.setTickets(tickets);
        }
        action.setPrice(actionDto.price);
        return action;
    }

    // MODEL -> DTO
    public ActionDto actionToDto(Action action) {
        ActionDto actionDto = new ActionDto();
        actionDto.id = action.getId();
        actionDto.description = action.getDescription();
        actionDto.hrRate = action.getHrRate();
        actionDto.time = action.getTime();
        actionDto.labour = action.getLabour();
        actionDto.materials = action.getMaterials();
        actionDto.price = action.getPrice();
        actionDto.carParts = new HashSet<>();
        for (CarPart carPart : action.getCarParts()) {
            actionDto.carParts.add(carPartService.carPartToDto(carPart));
        }
        return actionDto;
    }
}
