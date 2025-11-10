/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.service;

import com.example.dto.LoadItemsRequest;
import com.example.dto.LoadItemsRequest.ItemDto;
import com.example.model.Box;
import com.example.model.BoxState;
import com.example.model.Item;
import com.example.repository.BoxRepository;
import com.example.repository.ItemRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author omozegieaziegbe
 */
@Service
public class BoxService {

    private final BoxRepository boxRepository;
    private final ItemRepository itemRepository;

    public BoxService(BoxRepository boxRepository, ItemRepository itemRepository) {
        this.boxRepository = boxRepository;
        this.itemRepository = itemRepository;
    }

    public Box createBox(Box box) {
        if (box.getWeightLimit() > 500) {
            throw new IllegalArgumentException("weightLimit cannot exceed 500 grams");
        }
        if (box.getTxref() == null || box.getTxref().length() > 20) {
            throw new IllegalArgumentException("txref required and max 20 chars");
        }
        if (box.getBatteryCapacity() < 0 || box.getBatteryCapacity() > 100) {
            throw new IllegalArgumentException("batteryCapacity must be between 0 and 100");
        }
        if (box.getState() == null) {
            box.setState(BoxState.IDLE);
        }
        return boxRepository.save(box);
    }

    @Transactional
    public List<Item> loadItems(String txref, List<ItemDto> itemsDto) {
        Box box = boxRepository.findByTxref(txref)
                .orElseThrow(() -> new IllegalArgumentException("Box not found"));

        if (box.getBatteryCapacity() < 25) {
            throw new IllegalStateException("Cannot start loading: battery below 25%.");
        }

        box.setState(BoxState.LOADING);
        boxRepository.save(box);

        int currentWeight = box.currentLoadWeight();
        int newWeight = itemsDto.stream().mapToInt(LoadItemsRequest.ItemDto::weight).sum();

        if (currentWeight + newWeight > box.getWeightLimit()) {
            throw new IllegalStateException("Total weight exceeds box weight limit");
        }

        List<Item> items = itemsDto.stream().map(dto -> {
            Item item = new Item();
            item.setName(dto.name());
            item.setWeight(dto.weight());
            item.setCode(dto.code());
            item.setBox(box);
            return item;
        }).collect(Collectors.toList());

        itemRepository.saveAll(items);

        box.getItems().addAll(items);
        box.setState(BoxState.LOADED);
        boxRepository.save(box);

        return items;
    }

    public List<Item> getItems(String txref) {
        Box box = boxRepository.findByTxref(txref)
                .orElseThrow(() -> new IllegalArgumentException("Box not found"));
        return box.getItems();
    }

    public List<Box> availableForLoading() {
        List<Box> boxes = boxRepository.findAllByStateIn(List.of(BoxState.IDLE));
        return boxes.stream()
                .filter(b -> b.getBatteryCapacity() >= 25)
                .filter(b -> b.currentLoadWeight() < b.getWeightLimit())
                .collect(Collectors.toList());
    }

    public int getBattery(String txref) {
        Box box = boxRepository.findByTxref(txref)
                .orElseThrow(() -> new IllegalArgumentException("Box not found"));
        return box.getBatteryCapacity();
    }
}
