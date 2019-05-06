package com.ucarweekly.usercenterservice.controller;

import com.ucarweekly.usercenterapi.dto.FrontUserDto;
import com.ucarweekly.usercenterapi.dto.UserToken;
import com.ucarweekly.usercenterapi.msg.WeeklyUserCenterRetEnum;
import com.ucarweekly.usercenterapi.vo.RestFulVO;
import com.ucarweekly.usercenterservice.base.BaseRest;
import com.ucarweekly.usercenterservice.constants.CommonConstants;
import com.ucarweekly.usercenterservice.entities.FrontUser;
import com.ucarweekly.usercenterservice.service.FrontUserService;
import com.ucarweekly.usercenterservice.service.RedisService;
import com.ucarweekly.usercenterservice.utils.JwtUtils;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liaohong
 * @since 2018/11/27 10:12
 */
@RestController
public class FrontUserController extends BaseRest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private FrontUserService userService;

    @Autowired
    private RedisService redisService;

    /**
     * 前台用户注册
     *
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/frontUser/register")
    public RestFulVO register(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password, @RequestParam(value = "kind") Integer kind) {
        if (userName == null || password == null || kind == null) {
            logger.info("用户注册时前端参数传递出错");
            return new RestFulVO(WeeklyUserCenterRetEnum.PARAM_ERROR);
        }
        password = encoder.encode(password);
        logger.info("注册加密之后的密码是,{}", password);
        FrontUserDto userDto = new FrontUserDto();
        userDto.setUserName(userName);
        userDto.setPassword(password);
        userDto.setKindOf(kind);
        userDto.setStatus(CommonConstants.STATUS_OPEN);
        userDto.setGroupId(CommonConstants.STATUS_CLOSE);
        FrontUser frontUser = new FrontUser();
        BeanUtils.copyProperties(userDto, frontUser);
        userService.add(frontUser);
        return new RestFulVO(WeeklyUserCenterRetEnum.SUCCESS);
    }

    /**
     * 用户退出登陆
     *
     * @param token
     * @return
     */
    @PostMapping("/frontUser/logout")
    public RestFulVO logout(@RequestParam(value = "token") String token) {
        if (token == null) {
            logger.info("用户退出登陆时token参数为空");
            return new RestFulVO(WeeklyUserCenterRetEnum.PARAM_ERROR);
        }
        logger.info("token: {}", token);
        try {
            UserToken userToken = JwtUtils.getInfoFromToken(token);
            if (userToken == null) {
                logger.info("token失效");
                return new RestFulVO(WeeklyUserCenterRetEnum.TOKEN_INVILD);
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

    /**
     * 根据UserId查找用户信息
     *
     * @param userId
     * @return
     */
    @PostMapping("/front/findByUserId")
    public RestFulVO findByUserId(@RequestParam(value = "userId") Integer userId) {
        if (userId == null) {
            logger.info("根据userid查找用户信息时参数为空");
            return new RestFulVO(WeeklyUserCenterRetEnum.PARAM_ERROR);
        }
        FrontUser frontUser = new FrontUser();
        frontUser.setUserId(userId);
        frontUser = userService.findByUserId(frontUser);
        if (frontUser == null) {
            logger.info("该用户不存在,{}", userId);
            return new RestFulVO(WeeklyUserCenterRetEnum.USER_NOT_EXIST);
        }
        FrontUserDto frontUserDto = new FrontUserDto();
        BeanUtils.copyProperties(frontUser, frontUserDto);
        return restSuccess(frontUserDto);

    }

    /**
     * 根据token查找用户信息
     *
     * @param token
     * @return
     */
    @PostMapping("/frontUser/findByUserIdToken")
    public RestFulVO findByUserIdToken(@RequestParam(value = "token") String token) {
        if (token == null) {
            logger.info("根据token查找用户信息时参数为空");
            return new RestFulVO(WeeklyUserCenterRetEnum.PARAM_ERROR);
        }
        try {
            UserToken userToken = JwtUtils.getInfoFromToken(token);
            if (userToken == null) {
                logger.info("token失效，{}", token);
                return new RestFulVO(WeeklyUserCenterRetEnum.TOKEN_INVILD);
            }
            FrontUser frontUser = new FrontUser();
            frontUser.setUserId(userToken.getUserId());
            frontUser = userService.findByUserId(frontUser);
            if (frontUser == null) {
                logger.info("该用户不存在,{}", userToken.getUserId());
                return new RestFulVO(WeeklyUserCenterRetEnum.USER_NOT_EXIST);
            }
            FrontUserDto frontUserDto = new FrontUserDto();
            BeanUtils.copyProperties(frontUser, frontUserDto);
            return restSuccess(frontUserDto);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return restSimpleFault();
    }

    /**
     * 更新组别信息
     *
     * @param token
     * @param groupId
     * @return
     */
    @PostMapping("/front/updateGroupId")
    public RestFulVO updateGroupId(@RequestParam(value = "token") String token, @RequestParam(value = "groupId") Integer groupId) {
        if (token == null || groupId == null) {
            logger.info("根据token更新用户信息时参数为空");
            return new RestFulVO(WeeklyUserCenterRetEnum.PARAM_ERROR);
        }
        try {

            UserToken userToken = JwtUtils.getInfoFromToken(token);
            if (userToken == null) {
                logger.info("token失效，{}", token);
                return new RestFulVO(WeeklyUserCenterRetEnum.TOKEN_INVILD);
            }
            Integer userId = userToken.getUserId();
            FrontUser frontUser = new FrontUser();
            frontUser.setUserId(userId);
            frontUser = userService.findByUserId(frontUser);
            frontUser.setGroupId(groupId);
            userService.update(frontUser);
            return restSimpleSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restSimpleFault();
    }
}
