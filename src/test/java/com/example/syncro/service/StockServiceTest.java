package com.example.syncro.service;

import com.example.syncro.entity.Stock;
import com.example.syncro.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class StockServiceTest {

    @Autowired
    private StockService service;

    @Autowired
    private StockRepository repository;

    @BeforeEach
    public void before() {
        Stock stock = new Stock(1L, 100L);
        repository.save(stock);
        repository.flush();
    }

    @AfterEach
    public void after() {
        repository.deleteAll();
    }

    @Test
    public void 재고감소(){
        service.decrease(1L,1L);
        // 100 -1 = 99
        Stock stock = repository.findById(1L).orElseThrow();

        assertEquals(99, stock.getQuantity());

    }

    @Test  //race condition
    public void 동시100개_요청() throws InterruptedException {
        int threadCnt = 100;
        CountDownLatch countDownLatch = new CountDownLatch(threadCnt);

        //비동기로 실행되는 작업을 단순화하여 사용가능하게끔 해주는 java api
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        for (int i=0; i< threadCnt; i++){
            executorService.submit(() -> {
                try{
                    service.decrease(1L,1L);
                }finally {
                    countDownLatch.countDown(); //다른 쓰레드 작업을 다 하도록 대기
                }
            });
        }
        countDownLatch.await();
        Stock stock = repository.findById(1L).orElseThrow();
        assertEquals(0L, stock.getQuantity());

    }

}