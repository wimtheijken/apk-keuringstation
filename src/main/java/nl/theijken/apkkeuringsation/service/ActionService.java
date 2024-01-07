package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.repository.ActionRepository;
import org.springframework.stereotype.Service;

@Service
public class ActionService {
    private final ActionRepository actionRepos;

    public ActionService(ActionRepository repos) {
        this.actionRepos = repos;
    }
}
