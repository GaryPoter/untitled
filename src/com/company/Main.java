package com.company;

import myclass.Point;
import utils.GenericPair;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static ArrayList<Point> lx;
    public static ArrayList<Point> ly;



    public static void main(String[] args) {
	// write your code here
       ArrayList<Point> l = new ArrayList<>();
        Random r = new Random(System.currentTimeMillis());
       for(int i = 0; i < 100; i++){
           int x = (int)(r.nextDouble() * 1000);
           int y = (int)(r.nextDouble() * 1000);
           Point p = new Point(x, y);
           l.add(p);
       }

        System.out.println();
        lx = new ArrayList<>(l);
        ly = new ArrayList<>(l);
        lx.sort(new Point.CompareY());
        lx.sort(new Point.CompareX());
        ly.sort(new Point.CompareY());

        int n = l.size();

        Point[] l1 = new Point[n];
        Point[] lxx = lx.toArray(l1);
        Point[] l2 = new Point[n];
        Point[] lyy = ly.toArray(l2);

       GenericPair<Point, Point> result = new GenericPair<>();


        System.out.println(Point.min_dist(lxx, lyy, 0,lx.size(),result) + "," + result);


//        System.out.println("Points order by x:");
//        for (Point p :
//                lx) {
//            System.out.print(p + " ");
//
//        }
//
//        System.out.println("\nPoints order by y:");
//        for (Point p :
//                ly) {
//            System.out.print(p + " ");
//
//        }


    }
}
