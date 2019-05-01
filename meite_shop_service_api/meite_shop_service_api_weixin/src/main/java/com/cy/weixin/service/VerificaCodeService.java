package com.cy.weixin.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: cy
 * @Date: 2019/4/30 16:51
 * @Description: 微信注册码验证码接口
 */
@Api(tags = "微信注册码验证码接口")
public interface VerificaCodeService {

    /**
     * 功能说明:根据手机号码验证码token是否正确
     *
     * @return
     */
    @ApiOperation(value = "根据手机号码验证码token是否正确")
    @GetMapping("/verificaWeixinCode")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phone", dataType = "String", required = true, value = "用户手机号码"),
            @ApiImplicitParam(paramType = "query", name = "weixinCode", dataType = "String", required = true, value = "微信注册码") })
    public BaseResponse<JSONObject> verificaWeixinCode(@RequestParam("phone") String phone,@RequestParam("weixinCode")  String weixinCode);
}

