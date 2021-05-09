package com.enjoyu.admin.mybatis.mapper.mbg;

import com.enjoyu.admin.mybatis.model.mbg.SmmUrl;
import com.enjoyu.admin.mybatis.model.mbg.SmmUrlExample;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmmUrlMapper {
    long countByExample(SmmUrlExample example);

    int deleteByExample(SmmUrlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmmUrl record);

    int insertSelective(SmmUrl record);

    List<SmmUrl> selectByExample(SmmUrlExample example);

    SmmUrl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmmUrl record, @Param("example") SmmUrlExample example);

    int updateByExample(@Param("record") SmmUrl record, @Param("example") SmmUrlExample example);

    int updateByPrimaryKeySelective(SmmUrl record);

    int updateByPrimaryKey(SmmUrl record);
}