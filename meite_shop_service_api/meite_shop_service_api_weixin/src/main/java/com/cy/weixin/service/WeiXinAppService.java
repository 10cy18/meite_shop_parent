package com.cy.weixin.service;

import com.cy.weixin.entity.AppEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * 微信服务接口
 */
@Api(tags = "微信服务接口")
public interface WeiXinAppService {

    /**
     * 功能说明：应用服务接口
     * @return
     */
    @ApiOperation(value = "微信服务接口")
    @GetMapping("/getApp")
    public AppEntity getApp();
}
