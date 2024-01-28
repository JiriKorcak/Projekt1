package com.engeto.restaurant;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Order {

    private int table;
    private Dish orderDish;
    private int numberOfDish;
    private LocalTime orderedTime;
    private LocalTime fulfilmentTime = null;
    private IsPaid isPaid;

private enum IsPaid {
    YES,
    NO
}

    public Order(int table, Dish orderDish, int numberOfDish) {
        this.table = table;
        this.orderDish = orderDish;
        this.numberOfDish = numberOfDish;
        this.orderedTime = LocalTime.now();
        this.isPaid = IsPaid.NO;


    }

    public int getTable() {
        return table;
    }

    public String getOrderDish() {
        return orderDish.getName();
    }

    public int getNumberOfDish() {
        return numberOfDish;
    }

    public LocalTime getOrderedTime() {
        DateTimeFormatter formatte = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(formatte.format(orderedTime));
    }

    public LocalTime getFulfilmentTime() {
        if (fulfilmentTime == null) {
            return null;
        }
        else {
            DateTimeFormatter formatte = DateTimeFormatter.ofPattern("HH:mm:ss");
            return LocalTime.parse(formatte.format(fulfilmentTime));
        }
    }

    public IsPaid getIsPaid() {
        return isPaid;
    }
    public void pay() {
        isPaid = IsPaid.YES;
        fulfilmentTime = LocalTime.now();
    }
    public String writeIsPaid() {
        if (isPaid.equals(IsPaid.NO)) {
            return "není";
        }
        else {
            return "je";
        }
    }

    @Override
    public String toString() {
        return "Orders{" +
                "table=" + table +
                ", orderDish=" + orderDish +
                ", numberOfDish=" + numberOfDish +
                ", orderedTime=" + getOrderedTime() +
                ", fulfilmentTime=" + getFulfilmentTime() + ", objednávka " +
                writeIsPaid() + " zaplacena" +
                '}';
    }
}
