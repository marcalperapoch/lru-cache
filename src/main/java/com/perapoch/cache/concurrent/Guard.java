package com.perapoch.cache.concurrent;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * Utility class to encapsulate mutual exclusion operations guarded by a ReadWriteLock.
 * Created by marcalperapochamado on 08/01/17.
 */
public class Guard {

    private final ReadWriteLock readWriteLock;

    private Guard(ReadWriteLock readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    /**
     * This function executes a writer unitOfWork in a thread safe way and returns its result.
     * @param unitOfWork the task to be accomplished.
     * @param <T> the type of the task result.
     * @return the result of executing the given unitOfWork.
     */
    public <T> T executeWrite(UnitOfWork<T> unitOfWork) {
        readWriteLock.writeLock().lock();
        try {
            return unitOfWork.execute();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * This function executes a read-only unitOfWork in a thread safe way and returns its result.
     * @param unitOfWork the task to be accomplished.
     * @param <T> the type of the task result.
     * @return the result of executing the given unitOfWork.
     */
    public <T> T executeRead(UnitOfWork<T> unitOfWork) {
        readWriteLock.readLock().lock();
        try {
            return unitOfWork.execute();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * This function executes a writer unitOfWork in a thread safe way.
     * @param unitOfWork the task to be accomplished.
     */
    public void executeWrite(VoidUnitOfWork unitOfWork) {
        readWriteLock.writeLock().lock();
        try {
            unitOfWork.execute();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * This FunctionalInterface represents a task to be performed in a thread safe way which result is wanted back.
     * @param <T> the type of the result of executing the task.
     */
    @FunctionalInterface
    public interface UnitOfWork<T> {

        /**
         * This function executes a task and return its result.
         * @return the result of executing the task.
         */
        T execute();
    }

    /**
     * This FunctionalInterface represents a task to be performed in a thread safe way which does not return anything.
     */
    @FunctionalInterface
    public interface VoidUnitOfWork {

        /**
         * This method executes the task to be done.
         */
        void execute();
    }

    /**
     * This function returns a new instance of a Guard with the given readWriteLock.
     * @param readWriteLock the lock to be used by the new Guard.
     * @return a new Guard instance.
     */
    public static Guard guardedBy(ReadWriteLock readWriteLock) {
        return new Guard(readWriteLock);
    }
}
