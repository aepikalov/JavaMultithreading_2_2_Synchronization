public class Main {
    private static final int CARS = 10;
    public static final int PAUSE = 5000;
    public static final int BUYERS = 3;
    public static int soldCars;

    public static void main(String[] args) {
        final Dealer dealer = new Dealer();
        while (soldCars < CARS) {
            for (int i = 1; i <= BUYERS; i++) {
                Thread buyer = new Thread(null, dealer::sellCar, "Покупатель " + i);
                buyer.setDaemon(true);
                buyer.start();
            }
            new Thread(null, dealer::receiveCar, "Производитель").start();
            try {
                Thread.sleep(PAUSE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            soldCars++;
        }
    }
}