package com.example.syncro.service;

import com.example.syncro.entity.Stock;
import com.example.syncro.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticLockService {

    private final StockRepository stockRepository;

    @Transactional
    public void decrease(Long id, Long quantity){
        Stock stock = stockRepository.findByidWithOptimisticLock(id);
        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);

    }
}
