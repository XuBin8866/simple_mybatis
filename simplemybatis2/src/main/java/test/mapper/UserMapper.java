/**
 *
 */
package test.mapper;


import test.domain.User;

import java.util.List;


/**
 * UserMapper.java
 *
 * @author PLF
 * @date 2019年3月6日
 */
public interface UserMapper {

    /**
     * 获取单个user
     *
     * @param id
     * @return
     * @see
     */
    User getUser(String id);

    /**
     * 获取所有用户
     *
     * @return
     * @see
     */
    List<User> getAll();

    /**
     * 更新用户（功能未完成）
     * @param name
     * @param id
     * @return 受影响的行数
     */
    int updateUser(String name, Integer id);
}
