import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Enum to represent the state of the system
enum State {
    ZERO, ODD, EVEN
}

// Class to handle printing
class NumberPrinter {
    public void printZero() {
        System.out.print("0 ");
    }

    public void printOdd(int number) {
        System.out.print(number + " ");
    }

    public void printEven(int number) {
        System.out.print(number + " ");
    }
}

// Controller class to manage thread execution
class ZeroOddEvenController {
    private int n;
    private int current = 1;
    private State state = State.ZERO;
    private final Lock lock = new ReentrantLock();
    private final Condition zeroCondition = lock.newCondition();
    private final Condition oddCondition = lock.newCondition();
    private final Condition evenCondition = lock.newCondition();
    private final NumberPrinter printer;

    public ZeroOddEvenController(int n, NumberPrinter printer) {
        this.n = n;
        this.printer = printer;
    }

    public void zero() throws InterruptedException {
        lock.lock();
        try {
            while (current <= n) {
                if (state != State.ZERO) {
                    zeroCondition.await();
                }
                if (current > n) break; // Exit if we've printed all numbers
                printer.printZero();
                if (current % 2 == 1) {
                    state = State.ODD;
                    oddCondition.signal();
                } else {
                    state = State.EVEN;
                    evenCondition.signal();
                }
                zeroCondition.await();
            }
            // Signal all threads to terminate
            oddCondition.signal();
            evenCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void odd() throws InterruptedException {
        lock.lock();
        try {
            while (current <= n) {
                if (state != State.ODD || current % 2 == 0) {
                    oddCondition.await();
                }
                if (current > n) break; // Exit if we've printed all numbers
                printer.printOdd(current);
                current++;
                state = State.ZERO;
                zeroCondition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void even() throws InterruptedException {
        lock.lock();
        try {
            while (current <= n) {
                if (state != State.EVEN || current % 2 == 1) {
                    evenCondition.await();
                }
                if (current > n) break; // Exit if we've printed all numbers
                printer.printEven(current);
                current++;
                state = State.ZERO;
                zeroCondition.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}

// Main class to start the threads
public class ZeroOddEvenPrinter {
    public static void main(String[] args) {
        int n = 10; // Change this value to print more or fewer numbers
        NumberPrinter printer = new NumberPrinter();
        ZeroOddEvenController controller = new ZeroOddEvenController(n, printer);

        Thread zeroThread = new Thread(() -> {
            try {
                controller.zero();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread oddThread = new Thread(() -> {
            try {
                controller.odd();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread evenThread = new Thread(() -> {
            try {
                controller.even();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        zeroThread.start();
        oddThread.start();
        evenThread.start();

        try {
            zeroThread.join();
            oddThread.join();
            evenThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}