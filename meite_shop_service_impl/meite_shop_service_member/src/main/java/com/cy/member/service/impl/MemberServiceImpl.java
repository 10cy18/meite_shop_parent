package com.cy.member.service.impl;

import com.cy.base.BaseApiService;
import com.cy.base.BaseResponse;
import com.cy.constants.Constants;
import com.cy.core.bean.MiteBeanUtils;
import com.cy.core.token.GenerateToken;
import com.cy.core.type.TypeCastHelper;
import com.cy.core.utils.MD5Util;
import com.cy.member.feign.WeiXinAppServiceFeign;
import com.cy.member.input.dto.UserLoginInpDTO;
import com.cy.member.mapper.UserMapper;
import com.cy.member.mapper.entity.UserDO;
import com.cy.member.output.dto.UserOutDTO;
import com.cy.member.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Override
    public BaseResponse<UserOutDTO> ssoLogin(@RequestBody UserLoginInpDTO userLoginInpDTO) {
        // 1.验证参数
        String mobile = userLoginInpDTO.getMobile();
        if (org.apache.commons.lang.StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空!");
        }
        String password = userLoginInpDTO.getPassword();
        if (org.apache.commons.lang.StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空!");
        }
        // 判断登陆类型
        String loginType = userLoginInpDTO.getLoginType();
        if (org.apache.commons.lang.StringUtils.isEmpty(loginType)) {
            return setResultError("登陆类型不能为空!");
        }
        // 目的是限制范围
        if (!(loginType.equals(Constants.MEMBER_LOGIN_TYPE_ANDROID) || loginType.equals(Constants.MEMBER_LOGIN_TYPE_IOS)
                || loginType.equals(Constants.MEMBER_LOGIN_TYPE_PC))) {
            return setResultError("登陆类型出现错误!");
        }

        // 设备信息
        String deviceInfor = userLoginInpDTO.getDeviceInfor();
        if (org.apache.commons.lang.StringUtils.isEmpty(deviceInfor)) {
            return setResultError("设备信息不能为空!");
        }
        // 2.对登陆密码实现加密
        String newPassWord = MD5Util.MD5(password);
        // 3.使用手机号码+密码查询数据库 ，判断用户是否存在
        UserDO userDo = userMapper.login(mobile, newPassWord);
        if (userDo == null) {
            return setResultError("用户名称或者密码错误!");
        }
        return setResultSuccess(MiteBeanUtils.doToDto(userDo, UserOutDTO.class));
    }

}
