package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.ActionDto;
import nl.theijken.apkkeuringsation.dto.CarPartDto;
import nl.theijken.apkkeuringsation.exceptions.RecordNotFoundException;
import nl.theijken.apkkeuringsation.model.Action;
import nl.theijken.apkkeuringsation.model.CarPart;
import nl.theijken.apkkeuringsation.repository.ActionRepository;
import nl.theijken.apkkeuringsation.repository.CarPartRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActionService {

        private final ActionRepository actionRepository;
        private final CarPartRepository carPartRepository;

        public ActionService(ActionRepository actionRepository,
                             CarPartRepository carPartRepository) {
            this.actionRepository = actionRepository;
            this.carPartRepository = carPartRepository;
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
            storedAction.setId(actionDto.getId());
            storedAction.setDescription(actionDto.description);
            storedAction.setHrRate(actionDto.hrRate);
            storedAction.setLabour(actionDto.labour);
            if (actionDto.carParts == null) {
                actionDto.carParts = new HashSet<>();
            } else {
                Set<CarPart> carParts = new HashSet<>();
                storedAction.setCarParts(carParts);
            }
            storedAction.setTicket(actionDto.ticket);
            return actionToDto(actionRepository.save(storedAction));
        }

        //PUT CarPart -> Action
        public ActionDto assignCarPartToAction(Long id, Long carPartId, ActionDto actionDto) {
            if(!actionRepository.existsById(String.valueOf(id))) {
                throw new RecordNotFoundException("No action found");
            }
            Action storedAction = actionRepository.findById(String.valueOf(id)).orElse(null);
            storedAction.setId(actionDto.getId());
            storedAction.setDescription(actionDto.description);
            storedAction.setHrRate(actionDto.hrRate);
            storedAction.setLabour(actionDto.labour);
            if(!carPartRepository.existsById(String.valueOf(carPartId))) {
                throw new RecordNotFoundException("No carpart found");
            }
            CarPart carPart = carPartRepository.findById(String.valueOf(carPartId)).orElse(null);
            if (storedAction.getCarParts() == null) {
                storedAction.setCarParts(new HashSet<>());
                Set<CarPart> carParts = new HashSet<>();
                carParts.add(carPart);
                storedAction.setCarParts(carParts);
            } else {
                Set<CarPart> carParts = new HashSet<>();
                carParts.add(carPart);
                storedAction.setCarParts(carParts);
            }
            storedAction.setTicket(actionDto.ticket);
            return actionToDto(actionRepository.save(storedAction));
        }

    // DTO -> MODEL
    private Action dtoToAction(ActionDto actionDto) {
        Action action = new Action();
        action.setDescription(actionDto.description);
        action.setHrRate(actionDto.hrRate);
        action.setLabour(actionDto.labour);
        if (actionDto.carParts == null) {
            actionDto.carParts = new HashSet<>();
        } else {
            Set<CarPart> carParts = new HashSet<>();
            action.setCarParts(carParts);
        }
        action.setTicket(actionDto.ticket);

        return action;
    }

    // MODEL -> DTO
    private ActionDto actionToDto(Action action) {
        ActionDto actionDto = new ActionDto();
        actionDto.id = action.getId();
        actionDto.description = action.getDescription();
        actionDto.hrRate = action.getHrRate();
        actionDto.labour = action.getLabour();
        if (action.getCarParts() == null) {
            action.setCarParts(new HashSet<>());
        } else {
            Set<CarPartDto> carParts = new HashSet<>();
//            for ( CarPart cartPart : action.getCarParts()) {
//                carParts.add(carpartService.carPartToDto(cartPart));
//            }
            actionDto.carParts = carParts;
        }
        actionDto.ticket = action.getTicket();
        return actionDto;
    }

    }
