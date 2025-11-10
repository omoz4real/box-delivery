/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.repository;

import com.example.model.Box;
import com.example.model.BoxState;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author omozegieaziegbe
 */
@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {

    boolean existsByTxref(String txref);

    Optional<Box> findByTxref(String txref);
    
    List<Box> findAllByStateIn(List<BoxState> states);

    List<Box> findByState(BoxState state);

    List<Box> findByStateAndBatteryCapacityGreaterThanEqual(BoxState state, Integer battery);
}
