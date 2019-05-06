package com.ucarweekly.usercenterservice.controller;

import com.ucarweekly.usercenterapi.dto.FrontUserDto;
import com.ucarweekly.usercenterapi.dto.UserToken;
import com.ucarweekly.usercenterapi.msg.WeeklyUserCenterRetEnum;
import com.ucarweekly.usercenterapi.vo.RestFulVO;
import com.ucarweekly.usercenterservice.base.BaseRest;
import com.ucarweekly.usercenterservice.constants.CommonConstants;
import com.ucarweekly.usercenterservice.entities.FrontUser;
import com.ucarweekly.usercenterservice.service.LoginService;
import com.ucarweekly.usercenterservice.service.RedisService;
import com.ucarweekly.usercenterservice.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.validation.Valid;
import java.util.List;

/**
 * @author liaohong
 * @since 2018/11/23 10:34
 */
@RestController
public class LoginController extends BaseRest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisService redisService;


    /**
     * 前台用户登陆
     *
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/login")
    public RestFulVO login(@RequestParam(value = "username") String username,@RequestParam(value = "password") String password ) throws Exception {
        if (username == null || password == null) {
            logger.error("登陆前端页面参数传参出错");
            return new RestFulVO(WeeklyUserCenterRetEnum.PARAM_ERROR);
        }
        Example example = new Example(FrontUser.class);
        example.createCriteria().andEqualTo("userName", username);
        List<FrontUser> users = loginService.selectByExample(example);
        if (users != null && users.size() > 0) {
            FrontUser user = users.get(0);
            String sqlPassword = user.getPassword();
            if (encoder.matches(password, sqlPassword)) {
                logger.info("用户登陆成功{}", user.getUserName());
                UserToken userToken = new UserToken();
                BeanUtils.copyProperties(user, userToken);
                String token = JwtUtils.generateToken(userToken, 500000);
                //放入到redis中
                String key = CommonConstants.PRE_WEEKLY_USER_TOKEN + userToken.getUserId().toString();
                redisService.put(key, token, 1000000);
                return new RestFulVO(WeeklyUserCenterRetEnum.SUCCESS,token);
            } else {
                logger.info("用户名或者密码错误,{},{}", username, password);
                return new RestFulVO(WeeklyUserCenterRetEnum.LOGIN_ERROR);
            }
        } else {
            logger.info("用户名或者密码错误,{},{}", username, password);
            return new RestFulVO(WeeklyUserCenterRetEnum.LOGIN_ERROR);
        }
    }
}
