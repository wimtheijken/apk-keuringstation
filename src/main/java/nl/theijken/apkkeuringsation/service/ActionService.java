package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.ActionDto;
import nl.theijken.apkkeuringsation.dto.CarPartDto;
import nl.theijken.apkkeuringsation.model.Action;
import nl.theijken.apkkeuringsation.model.CarPart;
import nl.theijken.apkkeuringsation.repository.ActionRepository;
import nl.theijken.apkkeuringsation.repository.CarPartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ActionService {
    private final ActionRepository actionRepository;
    private final CarPartService carpartService;
    public ActionService(ActionRepository actionRepository, CarPartService carpartService) {
        this.actionRepository = actionRepository;
        this.carpartService = carpartService;
    }

    public ActionDto createAction(ActionDto actionDto) {
        Action action = new Action();
        action.setDescription(actionDto.description);
        action.setHrRate(actionDto.hrRate);
        action.setLabour(actionDto.labour);
//        action.setCarParts(actionDto.carParts);
        action.setTicket(actionDto.ticket);
        if (actionDto.carParts == null) {
            actionDto.carParts = new HashSet<>();
        } else {
            Set<CarPart> carParts = new HashSet<>();
//            for ( CarPartDto carPart : actionDto.carParts ) {
//                if (carpartService.exists(carPart)) {
//                    carParts.add(carpartService.dtoToCarPart(carPart));
//                } else {
//                    carParts.add(carpartService.getCarParts(carPart));
//                }
//            }
            action.setCarParts(carParts);
        }
        Action savedAction = actionRepository.save(action);

        return actionToDto(savedAction);
    }

    public List<ActionDto> getAction() {
        List<Action> actions = actionRepository.findAll();
        List<ActionDto> actionDtos = new ArrayList<>();
        for(Action action : actions) {
            ActionDto actionDto = new ActionDto();
            actionDto.id = action.getId();
            actionDto.description = action.getDescription();
            actionDto.hrRate = action.getHrRate();
            actionDto.labour = action.getLabour();
//            actionDto.carParts = action.getCarParts();
            actionDto.ticket = action.getTicket();
            actionDtos.add(actionDto);
        }
        return actionDtos;
    }

    private Action dtoToAction(ActionDto actionDto) {
        Action action = new Action();
        action.setDescription(actionDto.description);
        action.setHrRate(actionDto.hrRate);
        action.setLabour(actionDto.labour);
        if (actionDto.carParts == null) {
            actionDto.carParts = new HashSet<>();
        } else {
            Set<CarPart> carParts = new HashSet<>();
//            for ( CarPartDto cartPart : actionDto.carParts ) {
////                carParts.add(carpartService.dtoToCarPart(cartPart));
//            }
            action.setCarParts(carParts);
        }
        action.setTicket(actionDto.ticket);

        return action;
    }

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

//        actionDto.customerFullName = car.getCustomer() != null ? car.getCustomer().getFirstName() + " " + car.getCustomer().getLastName() : null;
        return actionDto;
    }

//    public void updateAction(Long id, CIModuleDto ciModuleDto) {
//        if(!ciModuleRepository.existsById(id)) {
//            throw new RecordNotFoundException("No ci-module found");
//        }
//        CIModule storedCIModule = ciModuleRepository.findById(id).orElse(null);
//        storedCIModule.setId(ciModuleDto.getId());
//        storedCIModule.setType(ciModuleDto.getType());
//        storedCIModule.setName(ciModuleDto.getName());
//        storedCIModule.setPrice(ciModuleDto.getPrice());
//        ciModuleRepository.save(storedCIModule);
//    }
}
