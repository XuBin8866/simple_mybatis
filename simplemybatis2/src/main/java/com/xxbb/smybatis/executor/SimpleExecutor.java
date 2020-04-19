package com.xxbb.smybatis.executor;

import com.xxbb.smybatis.constants.Constant;
import com.xxbb.smybatis.executor.parameter.DefaultParameterHandler;
import com.xxbb.smybatis.executor.parameter.ParameterHandler;
import com.xxbb.smybatis.executor.resultset.DefaultResultSetHandler;
import com.xxbb.smybatis.executor.resultset.ResultSetHandler;
import com.xxbb.smybatis.executor.statement.SimpleStatementHandler;
import com.xxbb.smybatis.executor.statement.StatementHandler;
import com.xxbb.smybatis.mapping.MappedStatement;
import com.xxbb.smybatis.session.Configuration;

import java.sql.*;
import java.util.List;

/**
 * mysql的执行器，操作数据库
 *
 * @author xxbb
 */
public class SimpleExecutor implements Executor {
    /**
     * 数据库连接
     */
    private static Connection connection;
    /**
     * 配置类
     */
    private final Configuration configuration;

    /**
     * 初始化连接
     */
    static {
        initConnect();
    }

    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 抽取出执行sql操作前的获取连接，预处理语句，赋值参数的操作成一个模板
     *
     * @param mappedStatement  封装sql信息的对象
     * @param parameter        参数
     * @param executorCallback 会调接口
     * @return List结果集或受影响的行数
     */
    public Object executeTemplate(MappedStatement mappedStatement, Object parameter, ExecutorCallback executorCallback) {
        try {
            //获取连接
            Connection connection = getConnection();
            //实例化StatementHandler对象
            StatementHandler statementHandler = new SimpleStatementHandler(mappedStatement);
            //对mapperStatement中的sql语句进行处理，去除头尾空格，将#{}替换成?,封装成preparedStatement对象
            PreparedStatement preparedStatement = statementHandler.prepared(connection);
            //给占位符?的参数赋值
            ParameterHandler parameterHandler = new DefaultParameterHandler(parameter);
            parameterHandler.setParameters(preparedStatement);
            return executorCallback.doExecutor(statementHandler, preparedStatement);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //TODO 归还数据库连接
        }
    }

    /**
     * 执行查询方法，使用了模板方法设计模式
     *
     * @param mappedStatement sql信息对象
     * @param parameter       参数
     * @param <E>             泛型
     * @return 结果集
     */
    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> doQuery(MappedStatement mappedStatement, Object parameter) {
        return (List<E>) executeTemplate(mappedStatement, parameter, (statementHandler, preparedStatement) -> {
            //执行sql语句
            try {
                //获取结果集
                ResultSet resultSet = statementHandler.query(preparedStatement);
                //处理结果集，封装成泛型List对象返回
                //这里可以获取mappedStatement对象是因为他是效果上的final，
                //验证，将mappedStatement赋一个新的值：mappedStatement=new MappedStatement(); 会报错——>lambda 表达式中使用的变量应为 final 或 effectively final
                ResultSetHandler resultSetHandler = new DefaultResultSetHandler(mappedStatement);
                //封装到目标resultType的List集合中
                return resultSetHandler.handlerResultSet(resultSet);

            } catch (SQLException throwable) {
                throw new RuntimeException(throwable.getMessage());
            }

        });
    }

    /**
     * 执行增删改方法，使用了模板方法设计模式
     *
     * @param mappedStatement sql信息对象
     * @param parameter       参数
     * @return 受影响的行数
     */
    @Override
    public int doUpdate(MappedStatement mappedStatement, Object parameter) {
        return (int) executeTemplate(mappedStatement, parameter, (statementHandler, preparedStatement) -> {
            try {
                return statementHandler.update(preparedStatement);
            } catch (SQLException throwable) {
                throw new RuntimeException(throwable.getMessage());
            }

        });
    }


    /**
     * 初始化连接
     */
    private static void initConnect() {
        String driver = Configuration.getProperty(Constant.JDBC_DRIVER);
        String url = Configuration.getProperty(Constant.JDBC_URL);
        String username = Configuration.getProperty(Constant.JDBC_USERNAME);
        String password = Configuration.getProperty(Constant.JDBC_PASSWORD);

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("数据库连接成功！！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     *
     * @return 当前执行器拥有的连接
     */
    public Connection getConnection() throws SQLException {
        if (null != connection) {
            return connection;
        } else {
            throw new SQLException("无法获取连接，请检查配置");
        }
    }


}
