package com.example.syncro.facade;

import com.example.syncro.repository.RedisRepository;
import com.example.syncro.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {

    private final RedisRepository repository;

    private final StockService stockService;


    public void decrease(Long key, Long quantity) throws InterruptedException {
        while (repository.lock(key)) {
            Thread.sleep(100);
        }

        try {
            stockService.decrease(key, quantity);
        } finally {
            repository.unlock(key);
        }
    }
}
