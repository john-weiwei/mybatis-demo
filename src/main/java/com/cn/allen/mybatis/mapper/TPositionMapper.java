package com.cn.allen.mybatis.mapper;


import com.cn.allen.mybatis.entity.TPosition;

public interface TPositionMapper {

    int deleteByPrimaryKey(Integer id);

   
    int insert(TPosition record);


    int insertSelective(TPosition record);


    TPosition selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(TPosition record);


    int updateByPrimaryKey(TPosition record);
}