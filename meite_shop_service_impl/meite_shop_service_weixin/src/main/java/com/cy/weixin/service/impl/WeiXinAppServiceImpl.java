package com.cy.weixin.service.impl;

import com.cy.weixin.entity.AppEntity;
import com.cy.weixin.service.WeiXinAppService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeiXinAppServiceImpl implements WeiXinAppService {
    @Override
    public AppEntity getApp() {
        return new AppEntity("1018","陈勇");
    }
}
