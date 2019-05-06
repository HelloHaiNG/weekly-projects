package com.ucarweekly.usercenterservice.service;


import com.ucarweekly.usercenterservice.entities.FrontUser;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author liaohong
 * @since 2018/11/23 10:50
 */
public interface LoginService {

    List<FrontUser> selectByExample(Example example);
}
