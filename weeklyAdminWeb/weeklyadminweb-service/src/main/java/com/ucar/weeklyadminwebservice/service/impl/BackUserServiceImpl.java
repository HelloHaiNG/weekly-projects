package com.ucar.weeklyadminwebservice.service.impl;

import com.ucar.weeklyadminwebservice.entities.BackUser;
import com.ucar.weeklyadminwebservice.mapper.BackUserMapper;
import com.ucar.weeklyadminwebservice.service.BackUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author liaohong
 * @since 2018/12/27 10:44
 */
@Service
public class BackUserServiceImpl implements BackUserService {

    @Autowired
    private BackUserMapper backUserMapper;
    @Override
    public void add(BackUser backUser) {
        backUserMapper.insert(backUser);
    }

    @Override
    public List<BackUser> findByUsername(Example example) {
        return backUserMapper.selectByExample(example);
    }
}
