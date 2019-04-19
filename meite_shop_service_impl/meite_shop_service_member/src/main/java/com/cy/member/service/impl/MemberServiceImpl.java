package com.cy.member.service.impl;

import com.cy.member.feign.WeiXinAppServiceFeign;
import com.cy.member.service.MemberService;
import com.cy.weixin.entity.AppEntity;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员服务接口
 */
@RestController
public class MemberServiceImpl implements MemberService {
    @Autowired
    private WeiXinAppServiceFeign weiXinAppServiceFeign;

    /**
     * 会员调用微信
     * @return
     */
    @Override
    public AppEntity memberInvokeWeiXin() {
        return weiXinAppServiceFeign.getApp();
    }
}
