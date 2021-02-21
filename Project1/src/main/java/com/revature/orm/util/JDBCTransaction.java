package com.revature.orm.util;

public class JDBCTransaction implements Transaction {

    Session session = null;
    public JDBCTransaction(Session session) {
        this.session = session;
    }
    // Begin a new transaction
    @Override
    public void begin(){

    }

    // Flush the assoicated Session and end the unit of work
    @Override
    public void commit() {

    }

    // Is this transaction still alive. This only returns information in relation to the local transaction, not the actual underlying transaction
    @Override
    public boolean isActive() {

    }

    // Force the underlying roll back. I don't know if this an SQL kind of roll back or something different
    @Override
    public void rollback() {

    }

    // Set the transaction timeout for any transaction started by a subsequent call to begin() on this instance
    @Override
    public void setTimeout(int seconds) {

    }

    // Check if this transaction was successfully committed
    @Override
    public boolean wasCommitted() {

    }

    // Was this transaction rolled back or set to rollback only. This only accounts for actions initialized from this local transaction.
    @Override
    public boolean wasRolledBack() {

    }
}
