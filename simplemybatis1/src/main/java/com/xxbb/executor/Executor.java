package com.xxbb.executor;

import com.xxbb.bean.Configuration;
import com.xxbb.bean.MapperStatement;
import com.xxbb.pool.MyDataSourceImpl;
import com.xxbb.utils.ReflectUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 执行器
 *
 * @author xxbb
 */
public class Executor {
    private final DataSource dataSource;

    public Executor(Configuration configuration) {
        this.dataSource = MyDataSourceImpl.getInstance(configuration.getEnvironment());
    }

    public <T> List<T> query(MapperStatement mapperStatement, Object[] args) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(mapperStatement.getSql());
            ResultSet resultSet;

            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof Integer) {
                        preparedStatement.setInt(i + 1, (Integer) args[i]);
                    } else if (args[i] instanceof Long) {
                        preparedStatement.setLong(i + 1, (Long) args[i]);
                    } else if (args[i] instanceof Double) {
                        preparedStatement.setDouble(i + 1, (Double) args[i]);
                    } else if (args[i] instanceof String) {
                        preparedStatement.setString(i + 1, (String) args[i]);
                    }
                }
            }

            System.out.println("执行的查询语句：" + preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();

            //将查询结果转化为对象
            List<T> resultList = new ArrayList<>();
            handlerResultType(resultSet, mapperStatement.getResultType(), resultList);
            return resultList;
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables.getMessage());
        } finally {
            ((MyDataSourceImpl) dataSource).returnConnection(connection);
        }
    }

    private <T> void handlerResultType(ResultSet resultSet, String resultType, List<T> resultList) {
        //获取返回类型的对象
        try {
            Class<?> clazz = Class.forName(resultType);
            //获取结果集元数据
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            //遍历结果集，封装成bean对象存入resultList中
            while (resultSet.next()) {
                Object entity = clazz.newInstance();
                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                    //获取列名和值，从1开始,如果查询中该字段有别名则获取别名
                    String columnName = resultSetMetaData.getColumnLabel(i + 1);
                    Object columnValue = resultSet.getObject(i + 1);

                    //将字段值赋值给类对象
                    ReflectUtils.invokeSet(entity, columnName, columnValue);
                }
                resultList.add((T) entity);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
