package com.company;

public class DistanceWithSpecies {
    private double distance;
    private String species;

    public DistanceWithSpecies(double distance, String species){
        this.distance = distance;
        this.species = species;
    }

    public double getDistance(){
        return distance;
    }

    public String getSpecies() {
        return species;
    }
}
