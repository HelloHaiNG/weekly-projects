package com.ucar.weeklyadminwebservice.service;

import com.ucar.weeklyadminwebservice.entities.BackUser;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author liaohong
 * @since 2018/12/27 10:44
 */
public interface BackUserService {

    void add(BackUser backUser);

    List<BackUser> findByUsername(Example example);
}
