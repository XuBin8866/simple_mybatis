package com.xxbb.mapper;

import com.xxbb.domain.Account;

import java.util.List;

/**
 * @author xxbb
 */
public interface AccountMapper {
    /**
     * 查询所哟账号信息
     *
     * @return 账号集合
     */
    List<?> queryAccount();

    /**
     * 通过id查询账号
     *
     * @param id id
     * @return 账号
     */
    Account queryAccountById(Integer id);


}
