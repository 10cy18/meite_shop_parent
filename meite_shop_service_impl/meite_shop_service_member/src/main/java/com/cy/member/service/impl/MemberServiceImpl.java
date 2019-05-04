package com.cy.member.service.impl;

import com.cy.base.BaseApiService;
import com.cy.base.BaseResponse;
import com.cy.constants.Constants;
import com.cy.core.bean.MiteBeanUtils;
import com.cy.core.token.GenerateToken;
import com.cy.core.type.TypeCastHelper;
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
    @Autowired
    private GenerateToken generateToken;
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

    @Override
    public BaseResponse<UserOutDTO> getInfo(String token) {
        // 1.参数验证
        if (StringUtils.isEmpty(token)) {
            return setResultError("token不能为空!");
        }
        // 2.使用token向redis中查询userId
        String redisValue = generateToken.getToken(token);
        if (StringUtils.isEmpty(redisValue)) {
            return setResultError("token已经失效或者不正确");
        }
        Long userId = TypeCastHelper.toLong(redisValue);
        // 3.根据userId查询用户信息
        UserDO userDo = userMapper.findByUserId(userId);
        if (userDo == null) {
            return setResultError("用户信息不存在!");
        }
        // 4.将Do转换为Dto
        UserOutDTO doToDto = MiteBeanUtils.doToDto(userDo, UserOutDTO.class);
        return setResultSuccess(doToDto);
    }

}
