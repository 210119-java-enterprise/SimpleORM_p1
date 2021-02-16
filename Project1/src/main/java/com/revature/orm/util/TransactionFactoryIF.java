package com.revature.orm.util;

public interface TransactionFactoryIF {

    Transaction createTransaction(JDBCContext jdbcContext, TransactionFactory.Context context);
}
