package com.xxbb.test;

import com.xxbb.core.SqlSession;
import com.xxbb.core.SqlSessionFactory;
import com.xxbb.core.SqlSessionFactoryBuilder;
import com.xxbb.domain.Account;
import com.xxbb.mapper.AccountMapper;
import com.xxbb.utils.Resources;
import org.junit.Test;

import java.io.InputStream;

/**
 * @author xxbb
 */
public class TestMain {
    @Test
    public void testXml() {
        InputStream is = Resources.getResourcesAsStream("mybatis_config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = factory.openSession();
        AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);


        System.out.println(accountMapper.queryAccount());

        Account account = accountMapper.queryAccountById(1);
        System.out.println(account);


    }
}
