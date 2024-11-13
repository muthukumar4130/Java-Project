package com.example.demo.service.impl;

import com.example.demo.entity.State;
import com.example.demo.repository.StateRepository;
import com.example.demo.service.StateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Log4j2
public class StateServiceImpl implements StateService {

    @Autowired
    private StateRepository stateRepository;

    @Override
    public State saveState(State state) {
        return stateRepository.save(state);
    }

    @Override
    public List<State> getStates() {
        return stateRepository.findAll();
    }
}
