package aqs;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author linzherong
 * @date 2025/2/23 13:46
 */
public class Aqs {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
        lock.unlock();
    }

}
