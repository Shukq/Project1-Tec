package com.example.project1;

public class Restaurant {
    private String name;
    private int number;
    private String cost;
    private int coordX;
    private int coordY;
    private String adress;
    private int id;
    private int[] rating;
    private String[] images;


    public Restaurant(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public Restaurant(String name, int number, String cost, int coordX, int coordY, String adress) {
        this.name = name;
        this.number = number;
        this.cost = cost;
        this.coordX = coordX;
        this.coordY = coordY;
        this.adress = adress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getCost() {
        return cost;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public String getAdress() {
        return adress;
    }
}
