package com.engeto.restaurant;

import java.time.LocalTime;

public class Order {

    private static int nextId = 1;
    private int id;
    private int table;
    private int orderDishNumber;
    private Dish orderDish;
    private int numberOfDish;
    private LocalTime orderedTime;
    private LocalTime fulfilmentTime = LocalTime.MIN;
    private YesOrNot isPaid;
    private YesOrNot isSortedOut;


    public Order(int orderId, int table, int orderDishNumber, int numberOfDish, LocalTime orderedTime,
                 LocalTime fulfilmentTime, String isSortedOut, String isPaid, Cookbook cookbook) {
        this.id = orderId;
        this.table = table;
        this.orderDishNumber = orderDishNumber;
        this.orderDish = cookbook.getDish(orderDishNumber);
        this.numberOfDish = numberOfDish;
        this.orderedTime = orderedTime;
        this.fulfilmentTime = fulfilmentTime;
        this.isSortedOut = YesOrNot.valueOf(isSortedOut);
        this.isPaid = YesOrNot.valueOf(isPaid);
        nextId = id+1;


    }

    public Order(int table, int orderDishNumber, int numberOfDish, Cookbook cookbook) {
        this.id = nextId++;
        this.table = table;
        this.orderDishNumber = orderDishNumber;
        this.orderDish = cookbook.getDish(orderDishNumber);
        this.numberOfDish = numberOfDish;
        this.orderedTime = LocalTime.now();
        this.isSortedOut = YesOrNot.NO;
        this.isPaid = YesOrNot.NO;



    }

    public int getId() {
        return id;
    }

    public int getTable() {
        return table;
    }

    public String getOrderDishName() {
        return orderDish.getName();
    }

    public int getNumberOfDish() {
        return numberOfDish;
    }

    public LocalTime getOrderedTime() {
        return Settings.writeFormatteTime(orderedTime);
    }

    public LocalTime getFulfilmentTime() {
            return Settings.writeFormatteTime(fulfilmentTime);
    }

    public YesOrNot getIsPaid() {
        return isPaid;
    }
    public void pay() {
        isPaid = YesOrNot.YES;
    }
    public String writeIsPaid() {
        if (isPaid.equals(YesOrNot.NO)) {
            return "nezaplacena";
        }
        else {
            return "zaplacena";
        }
    }
    public int getOrderDishNumber() {
        return orderDishNumber;
    }
    public YesOrNot getIsSortedOut() {
        return isSortedOut;
    }
    public void sortedOut() {
        isSortedOut = YesOrNot.YES;
        fulfilmentTime = LocalTime.now();
    }

    public String writeIsSortedOut() {
        if (isSortedOut.equals(YesOrNot.NO)) {
            return "nevyřízena";
        }
        else {
            return "vyřízena";
        }
    }

    public String writeIsSortedOutWithTime() {
        if (isSortedOut.equals(YesOrNot.NO)) {
            return "nevyřízena";
        }
        else {
            return "vyřízena v " + Settings.writeFormatteTime(fulfilmentTime);
        }
    }

    @Override
    public String toString() {
        return "Objednávka č." + getId() +
                " ke stolu " + table +
                " : objednáno " + numberOfDish + " ks " + getOrderDishName() +
                " dnes v " + getOrderedTime() +
                ". Objednávka je " +
                writeIsSortedOutWithTime() + " a je " +
                writeIsPaid() + ".\n";
    }
}
