/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controller;

import com.example.dto.LoadItemsRequest.ItemDto;
import com.example.model.Box;
import com.example.model.Item;
import com.example.service.BoxService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author omozegieaziegbe
 */
@RestController
@RequestMapping("/api/boxes")
public class BoxController {

    private final BoxService boxService;

    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @PostMapping
    public ResponseEntity<Box> createBox(@RequestBody Box box) {
        Box created = boxService.createBox(box);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/{txref}/load")
    public ResponseEntity<List<Item>> loadItems(
            @PathVariable String txref,
            @RequestBody List<ItemDto> itemsDto
    ) {
        List<Item> loaded = boxService.loadItems(txref, itemsDto);
        return ResponseEntity.ok(loaded);
    }

    @GetMapping("/{txref}/items")
    public ResponseEntity<List<Item>> getItems(@PathVariable String txref) {
        List<Item> items = boxService.getItems(txref);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Box>> availableBoxes() {
        List<Box> boxes = boxService.availableForLoading();
        return ResponseEntity.ok(boxes);
    }

    @GetMapping("/{txref}/battery")
    public ResponseEntity<Integer> getBattery(@PathVariable String txref) {
        int battery = boxService.getBattery(txref);
        return ResponseEntity.ok(battery);
    }
}
