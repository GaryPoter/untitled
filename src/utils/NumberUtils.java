package utils;

public class NumberUtils {
    public static int max(int x, int y){
        return x > y? x : y;
    }

    public static double min(double x, double y){
        return x < y? x : y;
    }

    public static void print(int[] array, char ch){
        int len = array.length;
        for (int i = 0; i < len ; i++){
            System.out.print(array[i] + ch);
        }
    }

}
