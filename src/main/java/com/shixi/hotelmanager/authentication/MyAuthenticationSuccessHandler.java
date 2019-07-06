package com.shixi.hotelmanager.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shixi.hotelmanager.properties.LoginType;
import com.shixi.hotelmanager.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties myProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        logger.info("MyAuthenticationSuccessHandler login success!");
        if (LoginType.REDIRECT.equals(myProperties.getLoginType())){
            //response.setContentType("application/json;charset=UTF-8");
            //response.getWriter().write(objectMapper.writeValueAsString(authentication));

            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

            RequestCache requestCache = new HttpSessionRequestCache();
            SavedRequest savedRequest = requestCache.getRequest(request,response);
            String url = null;
            if(savedRequest != null){
                url = savedRequest.getRedirectUrl();
                logger.info("跳转原网页:"+url);
            }
            if(url == null){
                logger.info("跳转主页!");
                if (roles.contains("ROLE_ADMIN")){
                    getRedirectStrategy().sendRedirect(request,response,"/public/test1/index.html");
                }else{
                    getRedirectStrategy().sendRedirect(request,response,"/public/checkout/index.html");
                }

            }else{
                response.sendRedirect(url);
            }
        }else{
            super.onAuthenticationSuccess(request,response,authentication);
        }
    }
}
