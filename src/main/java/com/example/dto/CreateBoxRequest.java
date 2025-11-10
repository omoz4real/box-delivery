/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author omozegieaziegbe
 */
public record CreateBoxRequest(@NotBlank
        @Size(max = 20)
        String txref, @NotNull
        @Max(500)
        Integer weightLimit, @NotNull
        @Min(0)
        @Max(100)
        Integer batteryCapacity) {

}
