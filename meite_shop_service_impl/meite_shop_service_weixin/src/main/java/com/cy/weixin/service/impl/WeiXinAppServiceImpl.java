package com.cy.weixin.service.impl;

import com.cy.base.BaseApiService;
import com.cy.base.BaseResponse;
import com.cy.member.output.dto.UserOutDTO;
import com.cy.weixin.service.WeiXinAppService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeiXinAppServiceImpl extends BaseApiService<UserOutDTO> implements WeiXinAppService {
    @Value("${cy.weixin.name}")
    private String name;

    @Override
    public BaseResponse<UserOutDTO> getApp() {
        return setResultSuccess(new UserOutDTO("1018",name));
        //new AppEntity("1018",name);
    }
    //定义泛型T的有一定缺点，
}
