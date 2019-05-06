package com.ucarweekly.usercenterservice.service.impl;

import com.ucarweekly.usercenterservice.entities.FrontUser;
import com.ucarweekly.usercenterservice.mapper.FrontUserMapper;
import com.ucarweekly.usercenterservice.service.FrontUserService;
import com.ucarweekly.usercenterservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author liaohong
 * @since 2018/11/23 10:50
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private FrontUserService frontUserService;

    @Override
    public List<FrontUser> selectByExample(Example example) {
        return frontUserService.selectByExample(example);
    }
}
