package com.engeto.restaurant;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CookBook {

    private List<Dish> cookBook = new ArrayList<>();

    public List<Dish> getCookBook() {
        return cookBook;
    }
    public void addDish(Dish dish) {
        cookBook.add(dish);
    }
    public void removeDish(int index) {
        cookBook.remove(index);
    }
    public Dish getDish(int index) {
        return cookBook.get(index);
    }
    public static CookBook loadFromFile(String filename) throws RestaurantException {
       CookBook result = new CookBook();

       try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
           while (scanner.hasNextLine()) {
               String line = scanner.nextLine();
               parseLine(line, result);
           }
       } catch (FileNotFoundException e) {
           throw new RestaurantException("Nepodařilo se nalézt soubor " + filename + ": " + e.getLocalizedMessage());
       }
       return result;
    }
    private static void parseLine(String line, CookBook cookBook) throws RestaurantException {
        String[] blocks = line.split("\t");
        int numOfBlocks = blocks.length;
        if (numOfBlocks != 4) {
            throw new RestaurantException(
                    "Nesprávný počet položek na řádku: " + line + "! Počet položek: " + numOfBlocks + ".");
        }
        String name = blocks[0].trim();
        int price = Integer.parseInt(blocks[1].trim());
        LocalTime preparationTime = LocalTime.parse(blocks[2].trim());
        String image = blocks[3].trim();

        Dish newDish = new Dish(name, price, preparationTime, image);
        cookBook.addDish(newDish);
    }



}
