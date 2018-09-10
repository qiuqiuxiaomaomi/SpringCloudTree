package com.bonaparte.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.karakal.commons.uc.beans.User;
import com.karakal.commons.uc.beans.UserModel;
import com.karakal.commons.uc.token.MyJwtToken;
import com.karakal.commons.util.ControllerUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yangmingquan on 2017/9/6.
 * 过滤器功能， 请求执行前执行，优先级最高（0），需要执行
 */
@Component
public class MyFilter extends ZuulFilter{
    public Log log = LogFactory.getLog(this.getClass());
    private int unauthorizedErrorCode = 401;

    /*
    * 1: pre过滤器
    * 2：routing过滤器
    * 3：post过滤器
    * 4：error过滤器
    * */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object authorization = request.getHeader("Authorization");
        if(authorization == null) {
            log.warn("authorization is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("authorization is empty");
            }catch (Exception e){}

            return null;
        }

        String token = authorization.toString().substring(0);
        if(!MyJwtToken.isMyToken(token)) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }

        JSONObject payload = MyJwtToken.decode(token);
        List<String> userOwnPermissions = (List)payload.get("permissions");
        String requiredPermissions = null;

        User user = null;
        JSONObject jsonObject = (JSONObject)payload.get("user");
        if(jsonObject != null) {
            String jsonString = jsonObject.toJSONString();
            user = (User) JSON.parseObject(jsonString, User.class);
        } else {
            Integer uid = (Integer)payload.get("uid");
            user = new User();
            user.setId(uid);
        }

        if(hasPermission(userOwnPermissions, requiredPermissions)) {
            UserModel userModel = new UserModel();
            userModel.setUser(user);
            userModel.setToken(token);
            request.setAttribute("REQUEST_CURRENT_KEY", userModel);
            return null;
        }

        log.warn("authorization is empty");
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(401);
        try {
            ctx.getResponse().getWriter().write("authorization is empty");
        }catch (Exception e){}

        return null;
    }

    public boolean hasPermission(List<String> owned, String required) {
        if(required != null && required.length() > 0) {
            if(owned != null && owned.size() != 0) {
                String[] requiredList = required.split(":");
                int var5 = requiredList.length;
                byte var6 = 0;
                if(var6 < var5) {
                    String requiredSingleAuth = requiredList[var6];
                    Iterator var8 = owned.iterator();

                    String ownedPermission;
                    do {
                        if(!var8.hasNext()) {
                            return false;
                        }

                        ownedPermission = (String)var8.next();
                    } while(!ownedPermission.contains(requiredSingleAuth));

                    return true;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
