package com.enjoyu.admin.mybatis.mapper.mbg;

import com.enjoyu.admin.mybatis.model.mbg.SmmRoleUrl;
import com.enjoyu.admin.mybatis.model.mbg.SmmRoleUrlExample;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmmRoleUrlMapper {
    long countByExample(SmmRoleUrlExample example);

    int deleteByExample(SmmRoleUrlExample example);

    int insert(SmmRoleUrl record);

    int insertSelective(SmmRoleUrl record);

    List<SmmRoleUrl> selectByExample(SmmRoleUrlExample example);

    int updateByExampleSelective(@Param("record") SmmRoleUrl record, @Param("example") SmmRoleUrlExample example);

    int updateByExample(@Param("record") SmmRoleUrl record, @Param("example") SmmRoleUrlExample example);
}