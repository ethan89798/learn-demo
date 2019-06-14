package org.ethan.demo.jvm.ke.d02;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

/**
 * 简单实现连接池
 */
public class DBPool {

    private static final LinkedList<Connection> pool = new LinkedList<>();

    public DBPool(int coreNum) {
        synchronized (pool) {
            for (int i = 0; i < coreNum; i++) {
                // TODO 进行数据库连接创建工作
//                pool.add();
            }
        }
    }

    public static synchronized Connection getPoolConnection(long mils) {
        if (mils > 0) {
            long timeout = System.currentTimeMillis() + mils;
            while (pool.isEmpty() && timeout > 0) {
                try {
                    pool.wait(mils);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timeout = timeout - System.currentTimeMillis();
            }
            if (pool.isEmpty()) {
                return null;
            } else {
                return pool.getFirst();
            }
        } else {
            while (pool.isEmpty()) {
                try {
                    pool.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return pool.getFirst();
        }
    }

    public static synchronized void releasePoolConnection(Connection connection) {
        if (connection != null) {
            pool.addLast(connection);
            pool.notifyAll();
        }
    }
}
