package com.xxbb.smybatis.executor;

import com.xxbb.smybatis.executor.statement.StatementHandler;

import javax.security.auth.callback.Callback;
import java.sql.PreparedStatement;

/**
 * 执行sql具体操作的回调接口
 *
 * @author xxbb
 */
public interface ExecutorCallback extends Callback {
    /**
     * 具体的数据库操作，区分查询和更新操作
     *
     * @param statementHandler  执行sql操作的对象
     * @param preparedStatement 完全处理好的sql语句对象
     * @return List集合或者受影响的行数
     */
    Object doExecutor(StatementHandler statementHandler, PreparedStatement preparedStatement);
}
