package com.example.syncro.facade;

import com.example.syncro.service.OptimisticLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

    private final OptimisticLockService optimisticLockService;

    public void decrease(Long id, Long quantity) throws InterruptedException {
        try{
            optimisticLockService.decrease(id,quantity);
        }catch (Exception e){
            Thread.sleep(50);
        }
    }
}
