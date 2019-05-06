package com.ucarweekly.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.ucarweekly.zuul.constants.CommonConstants;
import com.ucarweekly.zuul.dto.UserToken;
import com.ucarweekly.zuul.service.RedisCacheService;
import com.ucarweekly.zuul.utils.JSONUtils;
import com.ucarweekly.zuul.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

/**
 * @author liaohong
 * @since 2018/11/26 14:41
 */
public class MyZuulFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisCacheService redisCacheService;

    @Value("${cache.time.token}")
    private String cacheTime;

    private String ignorePath = "/weeklyusercenter/login,/weeklyusercenter/frontUser/register," +
            "/weeklyusercenter/frontUser/logout,/weeklyservice/weeklyReport/pageList,[(\\w)-/]*v2/api-docs,/api-home/login," +
            "/api-home[(\\w)-/]*/frontPage,/weeklyservice/weeklyReport/findWeeklyByName,/weeklyadminweb/backUser/login," +
            "/weeklyadminweb/weeklyadminweb/pageList,/weeklyadminweb/weeklyadminweb/findWeeklyByName,/weeklyadminweb//backUser/logout," +
            "/weeklyadminweb/weeklyadminweb/pageListByGroupName,/weeklyadminweb/weeklyadminweb/pageListGroup";

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10000;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestURI = request.getRequestURI();
        if (isStartWith(requestURI)) {
            return null;
        }
        String accessToken = request.getHeader(CommonConstants.CONTEXT_TOKEN);
        if (null == accessToken || accessToken == "") {
            accessToken = request.getParameter(CommonConstants.TOKEN);
        }
        if (null == accessToken) {
            logger.info("没有token值");
            setFailedRequest(CommonConstants.ERROR_MSG, 401);
            return null;
        }

        logger.info("token: {}",accessToken);

        UserToken userToken = null;
        try {
            userToken = JwtUtils.getInfoFromToken(accessToken);
            logger.info("用户信息: " + JSON.toJSONString(userToken));
        } catch (Exception e) {
            logger.error("token校验出错: " + JSON.toJSONString(userToken));
            setFailedRequest(CommonConstants.ERROR_MSG, 401);
            return null;
        }

        String key = CommonConstants.PRE_WEEKLY_USER_TOKEN + userToken.getUserId().toString();
        String redisToken = redisCacheService.getString(key);

        if (StringUtils.isBlank(redisToken)) {
            logger.info("登陆token已经失效: " + JSON.toJSONString(userToken));
            setFailedRequest(CommonConstants.ERROR_MSG, 401);
            return null;
        }

        if (!StringUtils.equals(redisToken, accessToken) &&
                !StringUtils.equals(userToken.getUserId().toString(), "1")) {
            logger.info("用户已经重新登陆: " + JSON.toJSONString(userToken));
            setFailedRequest(CommonConstants.ERROR_LOGIN_AGAIN, 401);
            return null;
        }

        redisCacheService.expire(userToken.getUserId().toString(), Long.parseLong(cacheTime) * 60 * 1000);
        return null;
    }

    private void setFailedRequest(Object body, int code) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        HttpServletResponse response = ctx.getResponse();
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            out = response.getWriter();
            out.write(JSONUtils.beanToJson(body));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        ctx.setSendZuulResponse(false);
    }

    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        String preStr = "^";
        String suffer = "[\\w/-]{0,}$";
        for (String s : ignorePath.split(",")) {
            s = preStr + s + suffer;
            if (Pattern.matches(s, requestUri)) {
                return true;
            }
        }
        return flag;
    }
}
