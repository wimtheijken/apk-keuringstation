package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.ActionDto;
import nl.theijken.apkkeuringsation.dto.CarDto;
import nl.theijken.apkkeuringsation.model.Action;
import nl.theijken.apkkeuringsation.model.Car;
import nl.theijken.apkkeuringsation.model.CarPart;
import nl.theijken.apkkeuringsation.model.Ticket;
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

    private final CarPartRepository carPartRepository;

    public ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
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
            Set<CarPart> carParts = carPartService.dtosToCarsPart(actionDto.carParts);
            action.setCarsParts(carParts);
        }
        actionRepository.save(action);
        actionDto.id = action.getId();

        return actionDto;
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
        action.setCarParts(actionDto.carParts);
        action.setTicket(actionDto.ticket);

        return action;
    }

    private ActionDto actionToDto(Action action) {
        ActionDto actionDto = new ActionDto();
        actionDto.id = action.getId();
        actionDto.description = action.getDescription();
        actionDto.hrRate = action.getHrRate();
        actionDto.labour = action.getLabour();
        actionDto.carParts = action.getCarParts();
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
