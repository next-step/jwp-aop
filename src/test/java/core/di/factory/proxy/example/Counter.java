package core.di.factory.proxy.example;

/**
 * @author KingCjy
 */
public class Counter {
    private int count = 0;

    public int getCount() {
        return count;
    }

    public void addCount() {
        this.count += 1;
    }
}
