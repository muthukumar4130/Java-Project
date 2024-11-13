package com.example.demo.service;

import com.example.demo.entity.State;

import java.util.List;

public interface StateService {

    State saveState(State state);

    List<State> getStates();
}
