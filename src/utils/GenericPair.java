package utils;

public class GenericPair<E extends Object, F extends Object> {
    private E first;
    private E second;

    public GenericPair(){

    }

    public GenericPair(E first, E second){
        this.first = first;
        this.second = second;
    }

    public E getFirst(){
        return first;
    }

    public E getSecond() {
        return second;
    }

    public void setFirst(E first) {
        this.first = first;
    }

    public void setSecond(E second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "," + second.toString() + ")";
    }
}
