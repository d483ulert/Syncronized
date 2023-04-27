
package com.example.syncro.facade;

import com.example.syncro.repository.LockRepository;
import com.example.syncro.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NamedLockStockFacade {

    private final LockRepository repository;

    private final StockService stockService;


    public void decrease(Long id, Long quantity)  {
        try{
            repository.getLock(id.toString());
            stockService.namedLockDecrease(id,quantity);
        }finally {
            repository.relasesLock(id.toString());
        }
    }
}
