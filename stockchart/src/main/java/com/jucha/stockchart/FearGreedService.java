package com.jucha.stockchart;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FearGreedService {

    private final FearGreedRepository repository;

    public FearGreedService(FearGreedRepository repository) {
        this.repository = repository;
    }

    public List<FearGreedData> getAll() {
        return repository.findAll();
    }

    public FearGreedData getLatest() {
        return repository.findLatest();
    }
}