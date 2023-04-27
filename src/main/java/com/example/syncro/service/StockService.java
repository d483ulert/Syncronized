package com.example.syncro.service;

import com.example.syncro.entity.Stock;
import com.example.syncro.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;


    public synchronized void decrease(Long id, Long quantity){
        Stock stock =  stockRepository.findById(id).orElseThrow();

        stock.decrease(quantity);
        stockRepository.save(stock);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized void namedLockDecrease(Long id, Long quantity){
        Stock stock =  stockRepository.findById(id).orElseThrow();

        stock.decrease(quantity);
        stockRepository.save(stock);
    }

}
