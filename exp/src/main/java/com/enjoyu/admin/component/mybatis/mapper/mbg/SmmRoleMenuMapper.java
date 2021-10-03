package com.enjoyu.admin.component.mybatis.mapper.mbg;

import com.enjoyu.admin.component.mybatis.model.mbg.SmmRoleMenu;
import com.enjoyu.admin.component.mybatis.model.mbg.SmmRoleMenuExample;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmmRoleMenuMapper {
    long countByExample(SmmRoleMenuExample example);

    int deleteByExample(SmmRoleMenuExample example);

    int insert(SmmRoleMenu record);

    int insertSelective(SmmRoleMenu record);

    List<SmmRoleMenu> selectByExample(SmmRoleMenuExample example);

    int updateByExampleSelective(@Param("record") SmmRoleMenu record, @Param("example") SmmRoleMenuExample example);

    int updateByExample(@Param("record") SmmRoleMenu record, @Param("example") SmmRoleMenuExample example);
}