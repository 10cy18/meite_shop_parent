package com.cy.weixin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cy.base.BaseApiService;
import com.cy.base.BaseResponse;
import com.cy.constants.Constants;
import com.cy.core.utils.RedisUtil;
import com.cy.weixin.service.VerificaCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: cy
 * @Date: 2019/4/30 16:52
 * @Description:
 */
@RestController
public class VerificaCodeServiceImpl extends BaseApiService<JSONObject> implements VerificaCodeService {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public BaseResponse<JSONObject> verificaWeixinCode(String phone, String weixinCode) {
        // 1、验证码参数是否为空
        if (StringUtils.isEmpty(phone)) {
            return setResultError("手机号码不能为空!");
        }
        if (StringUtils.isEmpty(weixinCode)) {
            return setResultError("注册码不能为空!");
        }
        // 2、根据手机号查询redis返回对应的注册码
        String weixinCodeKey = Constants.WEIXINCODE_KEY + phone;
        String code = redisUtil.getString(weixinCodeKey);
        if (StringUtils.isEmpty(code)) {
            return setResultError("注册码已经过期,请重新发送验证码");
        }
        // 3、比较redis中的验证码与传参的注册码进行比对
        if (!code.equals(weixinCode)) {
            return setResultError("注册码不正确");
        }
        //移除对应验证码
        redisUtil.delKey(weixinCodeKey);
        return setResultSuccess("注册码比对正确");
    }

}

