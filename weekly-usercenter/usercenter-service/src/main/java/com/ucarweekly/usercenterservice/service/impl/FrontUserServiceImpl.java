package com.ucarweekly.usercenterservice.service.impl;

import com.ucarweekly.usercenterservice.entities.FrontUser;
import com.ucarweekly.usercenterservice.mapper.FrontUserMapper;
import com.ucarweekly.usercenterservice.service.FrontUserService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author liaohong
 * @since 2018/11/27 10:23
 */
@Service
public class FrontUserServiceImpl implements FrontUserService {

    @Autowired
    private FrontUserMapper mapper;

    @Override
    public Integer add(FrontUser frontUser) {
        return mapper.insert(frontUser);
    }

    @Override
    public FrontUser findByUserId(FrontUser frontUser) {
        return mapper.selectByPrimaryKey(frontUser);
    }

    @Override
    public List<FrontUser> selectByExample(Example example) {
        return mapper.selectByExample(example);
    }

    @Override
    public void update(FrontUser frontUser) {
        mapper.updateByPrimaryKey(frontUser);
    }
}
