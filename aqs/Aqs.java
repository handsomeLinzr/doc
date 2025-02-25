package aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author linzherong
 * @date 2025/2/23 13:46
 */
public class Aqs {

    public static void main(String[] args) throws Exception {

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Lock rLock = lock.readLock();
        Lock wLock = lock.writeLock();

    }

}
