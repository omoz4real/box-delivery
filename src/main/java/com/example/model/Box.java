/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author omozegieaziegbe
 */
@Entity
public class Box {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 20, nullable = false)
    private String txref;

    @Max(500)
    private Integer weightLimit; // grams

    private Integer batteryCapacity; // percent 0-100

    @Enumerated(EnumType.STRING)
    private BoxState state;

    @OneToMany(mappedBy = "box", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;

    public int currentLoadWeight() {
        return items.stream().mapToInt(Item::getWeight).sum();
    }

    public Box() {
    }

    public Box(Long id, String txref, Integer weightLimit, Integer batteryCapacity, BoxState state, List<Item> items) {
        this.id = id;
        this.txref = txref;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTxref() {
        return txref;
    }

    public void setTxref(String txref) {
        this.txref = txref;
    }

    public Integer getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(Integer weightLimit) {
        this.weightLimit = weightLimit;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public BoxState getState() {
        return state;
    }

    public void setState(BoxState state) {
        this.state = state;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Box{" + "id=" + id + ", txref=" + txref + ", weightLimit=" + weightLimit + ", batteryCapacity=" + batteryCapacity + ", state=" + state + ", items=" + items + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.txref);
        hash = 53 * hash + Objects.hashCode(this.weightLimit);
        hash = 53 * hash + Objects.hashCode(this.batteryCapacity);
        hash = 53 * hash + Objects.hashCode(this.state);
        hash = 53 * hash + Objects.hashCode(this.items);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Box other = (Box) obj;
        if (!Objects.equals(this.txref, other.txref)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.weightLimit, other.weightLimit)) {
            return false;
        }
        if (!Objects.equals(this.batteryCapacity, other.batteryCapacity)) {
            return false;
        }
        if (this.state != other.state) {
            return false;
        }
        return Objects.equals(this.items, other.items);
    }

}
