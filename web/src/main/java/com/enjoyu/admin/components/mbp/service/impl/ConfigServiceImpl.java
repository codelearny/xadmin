package com.enjoyu.admin.components.mbp.service.impl;

import com.enjoyu.admin.components.mbp.entity.Config;
import com.enjoyu.admin.components.mbp.mapper.ConfigMapper;
import com.enjoyu.admin.components.mbp.service.IConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务端通用配置字典表 服务实现类
 * </p>
 *
 * @author mbp
 * @since 2022-01-04
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

}
