package com.enjoyu.admin.mybatis.mapper.mbg;

import com.enjoyu.admin.mybatis.model.mbg.SmmMenu;
import com.enjoyu.admin.mybatis.model.mbg.SmmMenuExample;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmmMenuMapper {
    long countByExample(SmmMenuExample example);

    int deleteByExample(SmmMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmmMenu record);

    int insertSelective(SmmMenu record);

    List<SmmMenu> selectByExample(SmmMenuExample example);

    SmmMenu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmmMenu record, @Param("example") SmmMenuExample example);

    int updateByExample(@Param("record") SmmMenu record, @Param("example") SmmMenuExample example);

    int updateByPrimaryKeySelective(SmmMenu record);

    int updateByPrimaryKey(SmmMenu record);
}