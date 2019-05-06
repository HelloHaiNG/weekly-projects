package com.ucar.weeklyadminwebservice.controller;

import com.ucar.weeklyadminwebapi.dto.BackUserDto;
import com.ucar.weeklyadminwebapi.dto.UserToken;
import com.ucar.weeklyadminwebapi.msg.WeeklyAdminWebRetEnum;
import com.ucar.weeklyadminwebapi.vo.RestFulVO;
import com.ucar.weeklyadminwebservice.base.BaseRest;
import com.ucar.weeklyadminwebservice.constants.CommonConstants;
import com.ucar.weeklyadminwebservice.entities.BackUser;
import com.ucar.weeklyadminwebservice.service.BackUserService;
import com.ucar.weeklyadminwebservice.service.RedisService;
import com.ucar.weeklyadminwebservice.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author liaohong
 * @since 2018/11/26 10:12
 */
@RestController
public class LoginController extends BaseRest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    public final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private RedisService redisService;

    @Autowired
    private BackUserService backUserService;


    /**
     * 后台用户注册
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/backUser/register")
    public RestFulVO register(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        if (username == null || password == null) {
            logger.info("用户注册时前端参数传递出错");
            return new RestFulVO(WeeklyAdminWebRetEnum.PARAM_ERROR);
        }
        password = encoder.encode(password);
        logger.info("注册加密之后的密码是,{}", password);
        BackUserDto backUserDto = new BackUserDto();
        backUserDto.setPassword(password);
        backUserDto.setUserName(username);
        backUserDto.setStatus(CommonConstants.STATUS_OPEN);
        BackUser backUser = new BackUser();
        BeanUtils.copyProperties(backUserDto, backUser);
        backUserService.add(backUser);
        logger.info("用户{}注册成功", username);
        return restSimpleSuccess();
    }

    /**
     * 后台用户登陆
     *
     * @param
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/backUser/login")
    public RestFulVO login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) throws Exception {
        if (username == null || password == null) {
            logger.error("登陆前端页面参数传参出错");
            return new RestFulVO(WeeklyAdminWebRetEnum.PARAM_ERROR);
        }
        Example example = new Example(BackUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName", username);
        criteria.andEqualTo("status", CommonConstants.STATUS_OPEN);
        List<BackUser> users = backUserService.findByUsername(example);
        if (users != null && users.size() > 0) {
            BackUser user = users.get(0);
            String sqlPassword = user.getPassword();
            if (encoder.matches(password, sqlPassword)) {
                logger.info("用户登陆成功{}", user.getUserName());
                UserToken userToken = new UserToken();
                BeanUtils.copyProperties(user, userToken);
                String token = JwtUtils.generateToken(userToken, 500000);
                //放入到redis中
                String key = CommonConstants.PRE_WEEKLY_USER_TOKEN + userToken.getUserId().toString();
                redisService.put(key, token, 1000000);
                return new RestFulVO(WeeklyAdminWebRetEnum.SUCCESS, token);
            } else {
                logger.info("用户名或者密码错误,{},{}", username, password);
                return new RestFulVO(WeeklyAdminWebRetEnum.LOGIN_ERROR);
            }
        } else {
            logger.info("用户名或者密码错误或者用户已经失效,{},{}", username, password);
            return new RestFulVO(WeeklyAdminWebRetEnum.LOGIN_ERROR);
        }
    }

    /**
     * 用户退出登陆
     *
     * @param token
     * @return
     */
    @PostMapping("/backUser/logout")
    public RestFulVO logout(@RequestParam(value = "token") String token) {
        if (token == null) {
            logger.info("用户退出登陆时token参数为空");
            return new RestFulVO(WeeklyAdminWebRetEnum.PARAM_ERROR);
        }
        logger.info("token: {}", token);
        try {
            UserToken userToken = JwtUtils.getInfoFromToken(token);
            if (userToken == null) {
                logger.info("token失效");
                return new RestFulVO(WeeklyAdminWebRetEnum.TOKEN_INVILD);
            }
            String key = CommonConstants.PRE_WEEKLY_USER_TOKEN + userToken.getUserId().toString();
            redisService.delete(key);
            logger.info("用户：{}退出成功", userToken.getUserName());
            return restSimpleSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("用户退出失败");
        return restSimpleFault();
    }
}
