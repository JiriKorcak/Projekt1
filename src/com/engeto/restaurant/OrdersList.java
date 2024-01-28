package com.engeto.restaurant;

import java.util.ArrayList;
import java.util.List;

public class OrdersList {

    private List<Order> orderList = new ArrayList<>();

    public List<Order> getOrderList() {
        return orderList;
    }
    public void addOrder(Order order) {
        orderList.add(order);
    }
    public void removeOrder(int index) {
        orderList.remove(index);
    }
    public Order getOrder(int index) {
        return orderList.get(index);
    }
}
