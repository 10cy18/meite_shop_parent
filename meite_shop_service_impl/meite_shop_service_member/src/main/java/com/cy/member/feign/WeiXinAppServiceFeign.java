package com.cy.member.feign;

import com.cy.weixin.service.WeiXinAppService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "app-cy-weixin")
public interface WeiXinAppServiceFeign extends WeiXinAppService {
    /**
     * 应用服务接口
     */
    /*@GetMapping("/getApp")
    public AppEntity getApp();*/
}
