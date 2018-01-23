package myclass;

import utils.GenericPair;
import utils.NumberUtils;

import java.util.ArrayList;
import java.util.Comparator;

public class Point{
    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Point(Point p){
        this.x = p.x;
        this.y = p.y;
    }

    public Point(){
        this(0,0);
    }

    @Override
    public String toString() {
        return "( " + this.x + " , " + this.y + " )";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }else if(obj instanceof Point){
            Point b = (Point)obj;
            return this.x == b.x && this.y == b.y ? true : false;
        }else{
            return false;
        }

    }

    public static double dist(Point a, Point b){
        double x_b2a = b.x - a.x;
        double y_b2a = b.y - a.y;
        return Math.sqrt(x_b2a * x_b2a + y_b2a * y_b2a);
    }

    public static GenericPair<Integer, Integer> findIndex(Point[] pl, double start, double end, boolean flag){
        int n = pl.length;
        int s = 0;
        int e = 0;
        boolean s_flag = false;
        boolean e_flag = false;
        if(flag) {
            for (int i = 0; i < n; i++) {
                if (!s_flag && pl[i].x >= start) {
                    s = i;
                    s_flag = true;
                } else if (!e_flag && pl[i].x >= end) {
                    e = i;
                    e_flag = true;
                }
                continue;
            }
        }else {
            for (int i = 0; i < n; i++) {
                if (!s_flag && pl[i].y >= start) {
                    s = i;
                    s_flag = true;
                } else if (!e_flag && pl[i].y >= end) {
                    e = i;
                    e_flag = true;
                }
                continue;
            }
        }
        return new GenericPair<Integer, Integer>(new Integer(s), new Integer(e));
    }

    public static ArrayList<Point> getYPoints(Point[] pl, double xs, double xe, Point x, double d){
        ArrayList<Point> l = new ArrayList<>();
        double position = x.y;
        int n = pl.length, count = 0;
        for(int i = 0; i < n; i++){
            if(pl[i].x >= xs && pl[i].x < xe)
            {
                if(pl[i].equals(x) && count == 0){//考虑重复点
                    count++;
                    continue;
                }
                if(Math.abs(pl[i].y - position) < d){//考虑横不重复点
                    l.add(new Point(pl[i]));
                }

            }

        }
        l.sort(new CompareY());

        ArrayList<Point> result;
        if(l.size() > 6){
            result = new ArrayList<>(l.subList(0,6));
        }else{
            result = l;
        }

        return result;

    }

    public static double min_dist(Point[] pl, Point[] ply, int start, int end, GenericPair<Point, Point> r){//区间[start, end)
        int mid = (start + end) / 2;


        if(end - start == 2){
            double dd1 = dist(pl[start], pl[end - 1]);
            if(r.getFirst() == null){
                r.setFirst(pl[start]);
                r.setFirst(pl[end - 1]);
                return dd1;
            }else{
                Point p1 = r.getFirst();
                Point p2 = r.getSecond();
                double dd2 = dist(p1, p2);
                if(dd1 < dd2){
                    r.setFirst(pl[start]);
                    r.setSecond(pl[end - 1]);
                    return dd1;
                }
                return dd2;
            }
//            if(r != null){
//                Point p1 = r.getFirst();
//                Point p2 = r.getSecond();
//                double dd2 = dist(p1, p2);
//                if(dd1 < dd2){
//                    r.setFirst(pl[start]);
//                    r.setSecond(pl[end - 1]);
//                }else{
//                    return dd1;
//                }
//            }

//            r.setFirst(pl[start]);
//            r.setSecond(pl[end - 1]);
//
//            System.out.println(r + "," + dd1);
//            return dist(pl[start], pl[end - 1]);
/*横坐标相邻两点的距离。因为初始化时
                                                  lx.sort(new Point.CompareY());
                                                  lx.sort(new Point.CompareX());
                                                  使得pl在x相同时是按照y有序的*/
        }else if(end - start == 1){
            return 150;
        }
        double d = NumberUtils.min(min_dist(pl, ply, start, mid, r), min_dist(pl, ply, mid, end, r));
        double d_v = d;
        //找到在区间(mid - d,mid + d)之间的点的起始坐标和终止坐标。
        double mid_x = pl[mid].x;
        double s = mid_x - d;
        double e = mid_x + d;
        //找到位于区间[s, e)之间的点的下标
        GenericPair<Integer, Integer> res = findIndex(pl, s, e,true);
        //index_start表示大于等于mid - d 的最小点的下标，index_end 表示小于mid + d 的最大点的下标。
        int index_start = Integer.parseInt(res.getFirst().toString());

//        int index_end = res.getSecond().intValue();
        //Point i 是横坐标位于 index_start 到 mid 之间的点
        for(int i = index_start; i < mid; i++){
            ArrayList<Point> p_temp = getYPoints(ply, mid_x, e, pl[i], d_v);
            Point[] temp_pl = new Point[p_temp.size()];
            Point[] pyl = p_temp.toArray(temp_pl);
            int max_length_pyl = pyl.length;
            for(int j = 0; j < max_length_pyl; j++){
                if(d > dist(pl[i],pyl[j])) {
                    d = dist(pl[i],pyl[j]);
                    r.setFirst(pl[i]);
                    r.setSecond(pyl[j]);
//                    System.out.println(r + "," + d);
                }
            }
        }

//        System.out.println(r + "," + d);
        return d;
    }

    public static class CompareX implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            Point a = o1;
            Point b = o2;

            return a.x > b.x ? 1 : a.x == b.x ? 0 : -1;
        }
    }

    public static class CompareY implements Comparator<Point>{
        @Override
        public int compare(Point o1, Point o2) {
            Point a = o1;
            Point b = o2;

            return a.y > b.y ? 1 : a.y == b.y ? 0 : -1;
        }
    }


}
