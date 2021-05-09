package com.enjoyu.admin.mybatis.mapper.mbg;

import com.enjoyu.admin.mybatis.model.mbg.SmmUserRole;
import com.enjoyu.admin.mybatis.model.mbg.SmmUserRoleExample;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmmUserRoleMapper {
    long countByExample(SmmUserRoleExample example);

    int deleteByExample(SmmUserRoleExample example);

    int insert(SmmUserRole record);

    int insertSelective(SmmUserRole record);

    List<SmmUserRole> selectByExample(SmmUserRoleExample example);

    int updateByExampleSelective(@Param("record") SmmUserRole record, @Param("example") SmmUserRoleExample example);

    int updateByExample(@Param("record") SmmUserRole record, @Param("example") SmmUserRoleExample example);
}