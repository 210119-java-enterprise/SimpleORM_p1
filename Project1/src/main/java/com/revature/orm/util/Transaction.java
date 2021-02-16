package com.revature.orm.util;

public interface Transaction {

    // Begin a new transaction
    void begin();

    // Flush the assoicated Session and end the unit of work
    void commit();

    // Is this transaction still alive. This only returns information in relation to the local transaction, not the actual underlying transaction
    boolean isActive();

    // Force the underlying roll back. I don't know if this an SQL kind of roll back or something different
    void rollback();

    // Set the transaction timeout for any transaction started by a subsequent call to begin() on this instance
    void setTimeout(int seconds);

    // Check if this transaction was successfully committed
    boolean wasCommitted();

    // Was this transaction rolled back or set to rollback only. This only accounts for actions initialized from this local transaction.
    boolean wasRolledBack();
}
