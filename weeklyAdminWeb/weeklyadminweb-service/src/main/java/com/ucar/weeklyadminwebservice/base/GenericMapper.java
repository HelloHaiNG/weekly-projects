package com.ucar.weeklyadminwebservice.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author liaohong
 * @since 2018/11/23 9:32
 */
public interface GenericMapper <T> extends Mapper<T>,MySqlMapper<T> {
}
