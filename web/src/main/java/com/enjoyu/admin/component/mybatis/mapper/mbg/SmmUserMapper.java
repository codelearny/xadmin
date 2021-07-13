package com.enjoyu.admin.component.mybatis.mapper.mbg;

import com.enjoyu.admin.component.mybatis.model.mbg.SmmUser;
import com.enjoyu.admin.component.mybatis.model.mbg.SmmUserExample;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmmUserMapper {
    long countByExample(SmmUserExample example);

    int deleteByExample(SmmUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmmUser record);

    int insertSelective(SmmUser record);

    List<SmmUser> selectByExample(SmmUserExample example);

    SmmUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmmUser record, @Param("example") SmmUserExample example);

    int updateByExample(@Param("record") SmmUser record, @Param("example") SmmUserExample example);

    int updateByPrimaryKeySelective(SmmUser record);

    int updateByPrimaryKey(SmmUser record);
}