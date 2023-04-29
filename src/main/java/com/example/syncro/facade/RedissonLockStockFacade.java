package com.example.syncro.facade;

import com.example.syncro.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;

    private final StockService stockService;


    public void decrease(Long key, Long quantity) throws InterruptedException {
        RLock lock = redissonClient.getLock(key.toString());

        try {
            Boolean able = lock.tryLock(5, 1, TimeUnit.SECONDS);
            if (able) {
                log.info("get lock");
                return;
            }
            stockService.decrease(key, quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
