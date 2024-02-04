package com.engeto.restaurant;

import java.math.BigDecimal;
import java.time.LocalTime;

public class Dish {

//    private static int nextId = 1;
//    private int id = nextId++;
    private int dishNumber;
    private String name;
    private BigDecimal price;
    private LocalTime preparationTime;
    private String image;


    public Dish(int dishNumber, String name, BigDecimal price, LocalTime preparationTime, String image) throws RestaurantException {
        this.dishNumber = dishNumber;
        this.name = name;
        this.price = price;
        int value = preparationTime.compareTo(LocalTime.MIN);
        if (value <= 0) {
            throw new RestaurantException("Čas přípravy nesmí být záporný ani nula! zadáno: " + preparationTime);
        }
        this.preparationTime = preparationTime;
        this.image = image;
    }

    public Dish(int dishNumber, String name, BigDecimal price, LocalTime preparationTime) throws RestaurantException{
        this.dishNumber = dishNumber;
        this.name = name;
        this.price = price;
        int value = preparationTime.compareTo(LocalTime.MIN);
            if (value <= 0) {
                throw new RestaurantException("Čas přípravy nesmí být záporný ani nula! zadáno: " + preparationTime);
            }
        this.preparationTime = preparationTime;
        this.image = "blank";
    }


    public int getDishNumber() {
        return dishNumber;
    }
    public void setDishNumber(int dishNumber) {
        this.dishNumber = dishNumber;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalTime getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(LocalTime preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Dish{" +
                "id=" + dishNumber +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", preparationTime=" + preparationTime +
                ", image='" + image + '\'' +
                '}' + "\n";
    }
}
