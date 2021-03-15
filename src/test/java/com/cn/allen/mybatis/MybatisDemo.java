package com.cn.allen.mybatis;

import com.cn.allen.mybatis.entity.TUser;
import com.cn.allen.mybatis.mapper.TUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author:ZhangWeiWei
 * @Date:2021/3/12
 * @Description:
 */
public class MybatisDemo {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        //--------------------第一阶段---------------------------
        // 1.读取mybatis配置文件创SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 1.读取mybatis配置文件创SqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        inputStream.close();
    }

    @Test
    // 快速入门
    public void quickStart() throws IOException {
        //--------------------第二阶段---------------------------
        // 2.获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 3.获取对应mapper
        TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);

        //--------------------第三阶段---------------------------
        // 4.执行查询语句并返回单条数据
        TUser user = mapper.selectByPrimaryKey(2);
        System.out.println(user);

        System.out.println("----------------------------------");

        // 5.执行查询语句并返回多条数据
//		List<TUser> users = mapper.selectAll();
//		for (TUser tUser : users) {
//			System.out.println(tUser);
//		}
    }

    @Test
    // if用于select，并与where配合
    public void testSelectIfOper() {
        // 2.获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 3.获取对应mapper
        TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);

        String email = "qq.com";
        Byte sex = 1;
		List<TUser> list = mapper.selectIfOper(email, null);
//        List<TUser> list = mapper.selectIfandWhereOper(email, null);
//		List<TUser> list = mapper.selectChooseOper(email , sex);

        System.out.println(list.size());
        System.out.println("第二次查询=======");
        List<TUser> list2 = mapper.selectIfOper(email, null);
        System.out.println(list2.size());
    }
}
