package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Double.parseDouble;

public class Main {

    public static void main(String[] args) {



        System.out.println("1 - podaj k");
        System.out.println("2 - podaj swoje dane");
        System.out.println("3 - zakończ program");

        Scanner optionScanner = new Scanner(System.in);
        int option = optionScanner.nextInt();

        if (option == 1) {
            System.out.println("podaj k: ");
            int kn = optionScanner.nextInt();
            kChosen(kn);
        } else if (option == 2) {
            userData();
        } else if (option == 3) {
            System.exit(1);
        }


    }

    public static void userData(){
        int k = 3;
        String[] userLine = new String[5];
        String result = "";
        String answerer = "";

        DistanceWithSpecies test = new DistanceWithSpecies(100, "test");
        DistanceWithSpecies[] knn = new DistanceWithSpecies[k];

        Arrays.fill(knn, test);



        try {
            Scanner testScanner = new Scanner(System.in);
            System.out.println("podaj swoje dane");
            for (int i = 0; i < 5; i++) {
                String t = testScanner.next();
                userLine[i] = t;
            }

                Scanner trainScanner = new Scanner(new File("Pro_1/train.txt"));

                while (trainScanner.hasNext()) {

                    String[] trainLine = trainScanner.nextLine().split(",");

                    DistanceWithSpecies check = new DistanceWithSpecies(euclideanDistance(userLine, trainLine), trainLine[trainLine.length - 1]);

                    int maxIndex = 0;
                    for (int i = 0; i < knn.length - 1; i++) {
                        if(knn[i + 1].getDistance() > knn[i].getDistance()){
                            maxIndex = i + 1;
                        }
                    }
                    if(check.getDistance() < knn[maxIndex].getDistance()){
                        knn[maxIndex] = check;
                    }
                }

                int freq = 0;


                for (int i = 0; i < k; i++) {
                    int count = 0;
                    for (int j = i + 1; j < k; j++) {
                        if (knn[j].getSpecies().equals(knn[i].getSpecies())) {
                            count++;
                        }
                    }

                    if (count >= freq) {
                        result = knn[i].getSpecies();
                        freq = count;
                    }
                }

                answerer = userLine[userLine.length - 1];

                trainScanner.close();
                Arrays.fill(knn, test);

                System.out.println("Prediction: " + result + " correct answerer: " + answerer);


        }
        catch (FileNotFoundException e){}

        Arrays.fill(knn, test);
    }


    public static void kChosen(int kn){

        int k = kn;
        int lineCounter = 0;
        int precison = 0;
        String testFile = "Pro_1/test.txt";
        String result = "";
        String answerer = "";

        DistanceWithSpecies test = new DistanceWithSpecies(100, "test");
        DistanceWithSpecies[] knn = new DistanceWithSpecies[k];

        Arrays.fill(knn, test);



        try {
            Scanner testScanner = new Scanner(new File(testFile));


            while (testScanner.hasNext()) {

                lineCounter++;
                String[] testLine = testScanner.nextLine().split(",");

                Scanner trainScanner = new Scanner(new File("Pro_1/train.txt"));

                while (trainScanner.hasNext()) {

                    String[] trainLine = trainScanner.nextLine().split(",");

                    DistanceWithSpecies check = new DistanceWithSpecies(euclideanDistance(testLine, trainLine), trainLine[trainLine.length - 1]);

                    int maxIndex = 0;
                    for (int i = 0; i < knn.length - 1; i++) {
                        if(knn[i + 1].getDistance() > knn[i].getDistance()){
                            maxIndex = i + 1;
                        }
                    }
                    if(check.getDistance() < knn[maxIndex].getDistance()){
                        knn[maxIndex] = check;
                    }
                }

                int freq = 0;


                for (int i = 0; i < k; i++) {
                    int count = 0;
                    for (int j = i + 1; j < k; j++) {
                        if (knn[j].getSpecies().equals(knn[i].getSpecies())) {
                            count++;
                        }
                    }

                    if (count >= freq) {
                        result = knn[i].getSpecies();
                        freq = count;
                    }
                }

                if(result.equalsIgnoreCase(testLine[testLine.length - 1])){
                    precison++;
                }

                answerer = testLine[testLine.length - 1];

                trainScanner.close();
                Arrays.fill(knn, test);

                System.out.println("Prediction: " + result + " correct answerer: " + answerer);

            }
            System.out.println("-------------------------------------------------");
            System.out.println("Precision: " + precison + "/" + lineCounter);

        }
        catch (FileNotFoundException e){
            System.out.println("błąd pliku");
        }


    }

    public static double euclideanDistance(String[] testArr, String[] trainArr){
        double distance = 0;
        for(int i = 0;i < testArr.length - 1; i++){
            double beforeSqrt =  Double.parseDouble(testArr[i]) - Double.parseDouble(trainArr[i]);

            if(beforeSqrt < 0){
                beforeSqrt = beforeSqrt * (-1);
            }

            distance += Math.sqrt(beforeSqrt);
        }
        return distance;
    }
}
