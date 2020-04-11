package com.xxbb.utils;

import com.xxbb.bean.Configuration;
import com.xxbb.bean.Environment;
import com.xxbb.bean.MapperStatement;
import com.xxbb.core.SqlSessionFactoryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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
            /**
             * 封装数据库配置和mapper映射配置信息
             */
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
        for(Element element:dataSource.getElementsByTag("properties")){
            String name=element.attr("name");
            if("driver".equals(name)){
                environment.setDriver(element.val());
            }else if("url".equals(name)){
                environment.setUrl(element.val());
            }else if("username".equals(name)){
                environment.setUsername(name);
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
        Map<String, MapperStatement> mapperStatementMap=new HashMap<>();
        Element mappers=document.getElementsByTag("mappers").get(0);

        for(Element element:mappers.getElementsByTag("mapper")){
            String resource=element.attr("resource");
            if(resource!=null&&!"".equals(resource)){
                //解析每一个mapper文件，将每一条sql语句的标签都封装到MapperStatement对象中
                String mapperFilePath= SqlSessionFactoryBuilder.class.getResource(resource).getPath();
                try {
                    Document mapperDocument=Jsoup.parse(new File(mapperFilePath),"UTF-8");
                    //获取命名空间
                    Element namespaceElement=mapperDocument.getElementsByAttribute("namespace").get(0);
                    String namespace=namespaceElement.attr("namespace");

                    //获取CRUD标签,并将其封装到mapperStatement中
                    MapperStatement mapperStatement;
                        //查询
                    Elements selects=mapperDocument.getElementsByTag("select");
                    for(Element select:selects){
                        mapperStatement=new MapperStatement();
                        String id=select.attr("id");
                        mapperStatement.setNamespace(namespace);
                        mapperStatement.setId(select.attr("id"));
                        mapperStatement.setParameterType(select.attr("parameterType"));
                        mapperStatement.setResultType(select.attr("resultType"));
                        //封装的Map中
                        String key=namespace+"."+id;
                        mapperStatementMap.put(key,mapperStatement);
                    }
                        //添加
                    Elements inserts=mapperDocument.getElementsByTag("insert");
                    for(Element insert:inserts){
                        mapperStatement=new MapperStatement();
                        String id=insert.attr("id");
                        mapperStatement.setNamespace(namespace);
                        mapperStatement.setId(insert.attr("id"));
                        mapperStatement.setParameterType(insert.attr("parameterType"));
                        //封装的Map中
                        String key=namespace+"."+id;
                        mapperStatementMap.put(key,mapperStatement);
                    }
                        //修改
                    Elements updates=mapperDocument.getElementsByTag("update");
                    for(Element update:updates){
                        mapperStatement=new MapperStatement();
                        String id=update.attr("id");
                        mapperStatement.setNamespace(namespace);
                        mapperStatement.setId(update.attr("id"));
                        mapperStatement.setParameterType(update.attr("parameterType"));
                        //封装的Map中
                        String key=namespace+"."+id;
                        mapperStatementMap.put(key,mapperStatement);
                    }
                    //修改
                    Elements deletes=mapperDocument.getElementsByTag("delete");
                    for(Element delete:deletes){
                        mapperStatement=new MapperStatement();
                        String id=delete.attr("id");
                        mapperStatement.setNamespace(namespace);
                        mapperStatement.setId(delete.attr("id"));
                        mapperStatement.setParameterType(delete.attr("parameterType"));
                        //封装的Map中
                        String key=namespace+"."+id;
                        mapperStatementMap.put(key,mapperStatement);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mapperStatementMap;
    }

}
