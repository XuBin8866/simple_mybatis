package com.xxbb.utils;

import com.xxbb.bean.Configuration;
import com.xxbb.bean.Environment;
import com.xxbb.bean.MapperStatement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 解析配置文件的工具类
 * @author xxbb
 */
public class XmlConfigBuilder {

    /**
     * 解析配置
     * @return 封装好的配置信息
     */
    public Configuration parse(InputStream config){
        try {

             //封装数据库配置和mapper映射配置信息
            Configuration configuration = new Configuration();
            //通过jsoup解析
            Document document= Jsoup.parse(config,"UTF-8","");

            //解析数据库配置文件信息
            configuration.setEnvironment(parseEnvironment(document));

            //解析mapper文件信息
            configuration.setMapperStatementMap(parseMapperStatementMap(document));
            return configuration;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try{
                config.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 解析xml中数据库配置环境信息
     * @param document xml的数据对象
     * @return 封装好的数据库配置环境对象
     */
    private Environment parseEnvironment(Document document){
        //解析数据库配置信息
        Environment environment=new Environment();
        //目前只简单解析第一个配置
        Element dataSource=document.getElementsByTag("dataSource").get(0);
        for(Element element:dataSource.getElementsByTag("property")){
            String name=element.attr("name");
            if("driver".equals(name)){
                environment.setDriver(element.val());
            }else if("url".equals(name)){
                environment.setUrl(element.val());
            }else if("username".equals(name)){
                environment.setUsername(element.val());
            }else if("password".equals(name)){
                environment.setPassword(element.val());
            }
        }
        return environment;
    }

    /**
     * 解析xml中mapper映射文件信息
     * @param document xml的数据对象
     * @return 封装好mapper映射的map
     */
    private Map<String, MapperStatement> parseMapperStatementMap(Document document){
        //存放mapper的map
        Map<String, MapperStatement> mapperStatementMap=new HashMap<>(10);
        Element mappers=document.getElementsByTag("mappers").get(0);

        for(Element element:mappers.getElementsByTag("mapper")){
            String resource=element.attr("resource");
            if(resource!=null&&!"".equals(resource)){
                //解析每一个mapper文件，将每一条sql语句的标签都封装到MapperStatement对象中
                String mapperFilePath=
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource(resource)).getPath();

                try {
                    Document mapperDocument=Jsoup.parse(new File(mapperFilePath),"UTF-8");
                    //获取命名空间
                    Element namespaceElement=mapperDocument.getElementsByAttribute("namespace").get(0);
                    String namespace=namespaceElement.attr("namespace");

                    //获取CRUD标签,并将其封装到mapperStatementMap中
                        //查询
                    Elements selects=mapperDocument.getElementsByTag("select");
                    parseSql(mapperStatementMap, namespace, selects);
                        //添加
                    Elements inserts=mapperDocument.getElementsByTag("insert");
                    parseSql(mapperStatementMap, namespace, inserts);
                        //修改
                    Elements updates=mapperDocument.getElementsByTag("update");
                    parseSql(mapperStatementMap, namespace, updates);
                        //删除
                    Elements deletes=mapperDocument.getElementsByTag("delete");
                    parseSql(mapperStatementMap, namespace, deletes);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mapperStatementMap;
    }

    /**
     * 将从mapper.xml中解析出每一条sql标签封装到mapperStatementMap中
     * @param mapperStatementMap 存放所有sql语句标签信息的map对象
     * @param namespace 该mapper.xml的命名空间
     * @param elements 解析出的sql标签组，分select、update、insert、delete四种
     */
    private void parseSql(Map<String, MapperStatement> mapperStatementMap, String namespace, Elements elements) {
        MapperStatement mapperStatement;
        for(Element element:elements){
            mapperStatement=new MapperStatement();
            String id=element.attr("id");
            mapperStatement.setNamespace(namespace);
            mapperStatement.setId(element.attr("id"));
            mapperStatement.setSql(element.html());
            //无论是否该标签是否存在或者是否有值，它都会返回一个字符串(可能是一个空的字符串)，所以我们要对其空字符串进行判断
            if(!"".equals(element.attr("parameterType"))){
                mapperStatement.setParameterType(element.attr("parameterType"));
            }

            if(!"".equals(element.attr("resultType"))){
                mapperStatement.setResultType(element.attr("resultType"));
            }

            //封装的Map中
            String key=namespace+"."+id;
            mapperStatementMap.put(key,mapperStatement);
        }
    }

}
