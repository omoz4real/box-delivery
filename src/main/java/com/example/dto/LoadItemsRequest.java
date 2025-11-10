/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;

/**
 *
 * @author omozegieaziegbe
 */
public record LoadItemsRequest(
        @NotNull
        List<ItemDto> items
        ) {

    public record ItemDto(
            @NotBlank
            @Pattern(regexp = "[A-Za-z0-9_-]+")
            String name,
            @NotNull
            @Min(1)
            Integer weight,
            @NotBlank
            @Pattern(regexp = "[A-Z0-9_]+")
            String code
            ) {

    }
}
