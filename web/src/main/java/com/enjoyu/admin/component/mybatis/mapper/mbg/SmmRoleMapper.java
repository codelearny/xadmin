package com.enjoyu.admin.component.mybatis.mapper.mbg;

import com.enjoyu.admin.component.mybatis.model.mbg.SmmRole;
import com.enjoyu.admin.component.mybatis.model.mbg.SmmRoleExample;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmmRoleMapper {
    long countByExample(SmmRoleExample example);

    int deleteByExample(SmmRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmmRole record);

    int insertSelective(SmmRole record);

    List<SmmRole> selectByExample(SmmRoleExample example);

    SmmRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmmRole record, @Param("example") SmmRoleExample example);

    int updateByExample(@Param("record") SmmRole record, @Param("example") SmmRoleExample example);

    int updateByPrimaryKeySelective(SmmRole record);

    int updateByPrimaryKey(SmmRole record);
}