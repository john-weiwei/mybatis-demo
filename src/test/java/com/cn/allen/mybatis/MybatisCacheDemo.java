package com.cn.allen.mybatis;

import com.cn.allen.mybatis.entity.TUser;
import com.cn.allen.mybatis.mapper.TJobHistoryMapper;
import com.cn.allen.mybatis.mapper.TUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author:ZhangWeiWei
 * @Date:2021/3/15
 * @Description:
 */
public class MybatisCacheDemo {

    private SqlSessionFactory sqlSessionFactory;

    private static final Logger log = LoggerFactory.getLogger(MybatisCacheDemo.class);

    @Before
    public void init() throws IOException {
        String config = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(config);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        inputStream.close();
    }

    @Test
    public void test1LevelCache() {
       SqlSession session1 = sqlSessionFactory.openSession();
       TUserMapper mapper = session1.getMapper(TUserMapper.class);
        String email = "qq.com";
        Byte sex = 1;
        List<TUser> list = mapper.selectByEmailAndSex2(email, sex);
        System.out.println(list.size());

        //增删改操作会清空一级缓存和二级缓存
        TUser userInsert = new TUser();
        userInsert.setUserName("test1");
        userInsert.setRealName("realname2");
        userInsert.setEmail("qq.com");
        userInsert.setMobile("13333333");
        userInsert.setNote("ddddddd");
        int result = mapper.insert1(userInsert);

        List<TUser> list2 = mapper.selectByEmailAndSex2(email, sex);
        System.out.println(list2.toString());
    }

    @Test
    public void test2LevelCache() {
        SqlSession session1 = sqlSessionFactory.openSession();
        TUserMapper mapper = session1.getMapper(TUserMapper.class);
        String email = "qq.com";
        Byte sex = 1;
        List<TUser> list = mapper.selectByEmailAndSex2(email, sex);
        log.warn("回话1");
        System.out.println(list.size());
        System.out.println(list.toString());
        session1.close();
        log.warn("会话1关闭");

        SqlSession session2 = sqlSessionFactory.openSession();
        TUserMapper mapper2 = session2.getMapper(TUserMapper.class);
        List<TUser> list2 = mapper2.selectByEmailAndSex2(email, sex);
        log.warn("回话2");
        System.out.println(list2.size());
        System.out.println(list2.toString());
        session2.close();
        log.warn("会话2关闭");

//        开启二级缓存，但在不同的命名空间
        SqlSession session3 = sqlSessionFactory.openSession();
        TJobHistoryMapper userMapper3 = session3.getMapper(TJobHistoryMapper.class);
        List<TUser> list4 = userMapper3.selectByEmailAndSex2(email, sex);
        log.warn("回话3");
        System.out.println(list4.size());
        System.out.println(list4.toString());
        session3.close();
        log.warn("会话3关闭");

        SqlSession session4 = sqlSessionFactory.openSession();
        TJobHistoryMapper userMapper4 = session4.getMapper(TJobHistoryMapper.class);
        List<TUser> list5 = userMapper4.selectByEmailAndSex2(email, sex);
        log.warn("回话4");
        System.out.println(list5.size());
        System.out.println(list5.toString());
        session3.close();
        log.warn("会话4关闭");

    }


}
