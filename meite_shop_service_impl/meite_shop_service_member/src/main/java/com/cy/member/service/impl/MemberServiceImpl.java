package com.cy.member.service.impl;

import com.cy.base.BaseApiService;
import com.cy.base.BaseResponse;
import com.cy.constants.Constants;
import com.cy.core.bean.MiteBeanUtils;
import com.cy.member.feign.WeiXinAppServiceFeign;
import com.cy.member.mapper.UserMapper;
import com.cy.member.mapper.entity.UserDO;
import com.cy.member.output.dto.UserOutDTO;
import com.cy.member.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员服务接口
 */
@RestController
public class MemberServiceImpl extends BaseApiService<UserOutDTO> implements MemberService {
    @Autowired
    private WeiXinAppServiceFeign weiXinAppServiceFeign;
    @Autowired
    private UserMapper userMapper;

    /**
     * 会员调用微信
     * @return
     */
    @Override
    public BaseResponse<UserOutDTO> memberInvokeWeiXin() {
        return weiXinAppServiceFeign.getApp();
        //return setResultSuccess(new AppEntity("1018",name));
    }

    @Override
    public BaseResponse<UserOutDTO> existMobile(String mobile) {
        // 1.验证参数
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空!");
        }
        // 2.根据手机号查询用户信息
        UserDO userDO = userMapper.existMobile(mobile);
        if (userDO == null) {
            return setResultError(Constants.HTTP_RES_CODE_EXISTMOBILE_203, "用户不存在");
        }
        // 注意需要将敏感数据进行脱敏
        return setResultSuccess(MiteBeanUtils.doToDto(userDO,UserOutDTO.class));
    }



}
