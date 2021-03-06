package com.xxbb.smybatis.executor.resultset;

import com.xxbb.smybatis.mapping.MappedStatement;
import com.xxbb.smybatis.utils.ReflectUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xxbb
 */
public class DefaultResultSetHandler implements ResultSetHandler {
    /**
     * 需要获取该对象的resultType信息
     */
    private final MappedStatement mappedStatement;


    public DefaultResultSetHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> handlerResultSet(ResultSet resultSet) {
        try {
            //封装结果集对象
            List<E> result = new ArrayList<>();
            if (null == resultSet) {
                return null;
            }
            //获取数据库元数据
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            //处理结果集
            while (resultSet.next()) {
                //实例化结果集对应的类
                E rowObject = (E) Class.forName(mappedStatement.getReturnType()).newInstance();
                //遍历每一行数据并封装进对象中
                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                    //获取列名和该列的值，从1开始，这里获取的是该列的别名
                    //一般多表查询时意味着所查询的类也可能是由多个数据库对应类组合而成，那么
                    //该类的属性名也将不会和数据库列名一一对应，故需要用别名去对应属性名
                    String columnName = resultSetMetaData.getColumnLabel(i + 1);
                    Object columnValue = resultSet.getObject(i + 1);

                    //将值赋值给类对象
                    ReflectUtils.invokeSet(rowObject, columnName, columnValue);
                }
                result.add(rowObject);

            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
