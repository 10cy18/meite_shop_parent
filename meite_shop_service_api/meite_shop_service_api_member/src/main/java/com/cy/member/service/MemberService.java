package com.cy.member.service;

import com.cy.base.BaseResponse;
import com.cy.member.output.dto.UserOutDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 会员服务接口
 */
@Api(tags = "会员服务接口")
public interface MemberService {
    /**
     *会员服务接口调用微信接口
     * @return
     */
    @ApiOperation(value = "会员服务调用微信")
    @GetMapping("/memberInvokeWeiXin")
    public BaseResponse<UserOutDTO> memberInvokeWeiXin();

    /**
     * 根据手机号码查询是否已经存在,如果存在返回当前用户信息
     *
     * @param mobile
     * @return
     */
    @ApiOperation(value = "根据手机号码查询是否已经存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "mobile", dataType = "String", required = true, value = "用户手机号码"), })
    @PostMapping("/existMobile")
    BaseResponse<UserOutDTO> existMobile(@RequestParam("mobile") String mobile);
}
