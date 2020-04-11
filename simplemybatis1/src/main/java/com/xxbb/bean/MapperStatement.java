package com.xxbb.bean;

/**
 * 映射mapper.xml,对其中数据进行封装
 * @author xxbb
 */
public class MapperStatement {
    /**
     * 命名空间
     */
    private String namespace;

    /**
     * sql语句的标签id
     */
    private String id;
    /**
     * 传入参数类型
     */
    private String parameterType;
    /**
     * 结果集封装参数类型
     */
    private String resultType;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        return "MapperStatement{" +
                "namespace='" + namespace + '\'' +
                ", id='" + id + '\'' +
                ", parameterType='" + parameterType + '\'' +
                ", resultType='" + resultType + '\'' +
                '}';
    }
}
