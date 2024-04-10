package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.ActionDto;
import nl.theijken.apkkeuringsation.model.Action;
import nl.theijken.apkkeuringsation.repository.ActionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActionService {
    private final ActionRepository repos;

    public ActionService(ActionRepository repos) {
        this.repos = repos;
    }

    public ActionDto createAction(ActionDto actionDto) {
        Action action = new Action();
        action.setDescription(actionDto.description);
        action.setHrRate(actionDto.hrRate);
        action.setLabour(actionDto.labour);
//        action.setCarParts(actionDto.carParts);
        action.setTicket(actionDto.ticket);
        repos.save(action);
        actionDto.id = action.getId();

        return actionDto;
    }

    public List<ActionDto> getAction() {
        List<Action> actions = repos.findAll();
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
}
