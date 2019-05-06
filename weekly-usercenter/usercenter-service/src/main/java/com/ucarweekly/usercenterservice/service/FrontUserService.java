package com.ucarweekly.usercenterservice.service;

import com.ucarweekly.usercenterservice.entities.FrontUser;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author liaohong
 * @since 2018/11/27 10:23
 */
public interface FrontUserService {

    Integer add(FrontUser frontUser);

    FrontUser findByUserId(FrontUser frontUser);

    List<FrontUser> selectByExample(Example example);

    void update(FrontUser frontUser);
}
