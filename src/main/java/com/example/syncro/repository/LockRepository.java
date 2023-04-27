package com.example.syncro.repository;

import com.example.syncro.entity.Stock;
import jdk.jshell.EvalException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LockRepository extends JpaRepository<Stock,Long> {

    @Query(value = "select get_lock(:key,3000)",nativeQuery = true)
    void getLock(String key);

    @Query(value="select relases_lock(:key)",nativeQuery = true)
    void relasesLock(String key);
}
