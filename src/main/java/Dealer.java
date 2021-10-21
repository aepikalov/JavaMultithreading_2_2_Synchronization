import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Dealer {
    private static final int RECIEVE_TIME = 3000;
    private static final int SELL_TIME = 1000;
    private List<Car> cars = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void receiveCar() {
        try {
            lock.lock();
            Thread.sleep(RECIEVE_TIME);
            cars.add(new Car());
            System.out.println(Thread.currentThread().getName() + " выпустил 1 авто.");
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Car sellCar() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " зашел в автосалон.");
            while (cars.size() == 0) {
                System.out.println("Машин нет!");
                condition.await();
            }
            Thread.sleep(SELL_TIME);
            System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return cars.remove(0);
    }
}
