package com.example.syncro.service;

import com.example.syncro.entity.Stock;
import com.example.syncro.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

}
