package org.axonframework.common.transaction;

import lombok.extern.slf4j.Slf4j;

/**
 * Transaction manager doing nothing with transactions, but writing logs on essential operations.
 *
 * @author Simon Zambrovski, Holisticon AG
 */
@Slf4j
public enum LoggingTransactionManager implements TransactionManager {

  /**
   * Singleton instance of the TransactionManager
   */
  INSTANCE;

  /**
   * Logging empty transaction.
   */
  public static final Transaction EMPTY_FAKE = new Transaction() {
    @Override
    public void commit() {
      log.warn("Logging transaction committed. Use for debugging only.");
    }

    @Override
    public void rollback() {
      log.warn("Logging transaction rolled back. Use for debugging only.");
    }
  };

  @Override
  public Transaction startTransaction() {
    log.warn("Logging transaction started. Use for debugging only.");
    return EMPTY_FAKE;
  }

}
