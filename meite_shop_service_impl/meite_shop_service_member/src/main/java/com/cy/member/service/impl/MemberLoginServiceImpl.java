package com.cy.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cy.base.BaseApiService;
import com.cy.base.BaseResponse;
import com.cy.constants.Constants;
import com.cy.core.token.GenerateToken;
import com.cy.core.utils.MD5Util;
import com.cy.member.input.dto.UserLoginInpDTO;
import com.cy.member.mapper.UserMapper;
import com.cy.member.mapper.UserTokenMapper;
import com.cy.member.mapper.entity.UserDO;
import com.cy.member.mapper.entity.UserTokenDo;
import com.cy.member.service.MemberLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberLoginServiceImpl extends BaseApiService<JSONObject> implements MemberLoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserTokenMapper userTokenMapper;

    @Autowired
    private GenerateToken generateToken;

    @Autowired
    private com.cy.core.transaction.RedisDataSoureceTransaction redisDataSoureceTransaction;

    @Override
    public BaseResponse<JSONObject> login(@RequestBody UserLoginInpDTO userLoginInpDTO) {
        // 1.验证参数
        String mobile = userLoginInpDTO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空!");
        }
        String password = userLoginInpDTO.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空!");
        }
        String loginType = userLoginInpDTO.getLoginType();
        if (StringUtils.isEmpty(loginType)) {
            return setResultError("登陆类型不能为空!");
        }
        //限制登录类型的范围
        if (!(loginType.equals(Constants.MEMBER_LOGIN_TYPE_ANDROID) || loginType.equals(Constants.MEMBER_LOGIN_TYPE_IOS)
                || loginType.equals(Constants.MEMBER_LOGIN_TYPE_PC))) {
            return setResultError("登陆类型出现错误!");
        }

        // 设备信息
        String deviceInfor = userLoginInpDTO.getDeviceInfor();
        if (StringUtils.isEmpty(deviceInfor)) {
            return setResultError("设备信息不能为空!");
        }
        // 密码加密
        String newPassWord = MD5Util.MD5(password);
        // 2.用户名称与密码登陆
        UserDO userDo = userMapper.login(mobile, newPassWord);
        if (userDo == null) {
            return setResultError("用户名称与密码错误!");
        }
        // 用户登陆Token Session 区别
        // 用户每一个端登陆成功之后，会对应生成一个token令牌（临时且唯一）存放在redis中作为rediskey value userid
        TransactionStatus transactionStatus = null;
        try {
            // 3.查询之前是否有过登陆 获取userid
            Integer userId = userDo.getUserId();
            // 5.根据userId+loginType 查询当前登陆类型账号之前是否有登陆过，如果登陆过 清除之前redistoken
            UserTokenDo userTokenDo = userTokenMapper.selectByUserIdAndLoginType(userId, loginType);
            transactionStatus = redisDataSoureceTransaction.begin();
            if (userTokenDo != null) {
                // 4.清除之前的token
                String token = userTokenDo.getToken();
                //开启redis事务 删除的时候方法会返回false
                Boolean removeToken = generateToken.removeToken(token);
                // 把该token的状态改为1
                int updateTokenAvailability = userTokenMapper.updateTokenAvailability(token);
                if (!toDaoResult(updateTokenAvailability)) {
                    return setResultError("系统错误!");
                }
            }
            // 6.存入在数据库中
            UserTokenDo userToken = new UserTokenDo();
            userToken.setUserId(userId);
            userToken.setLoginType(userLoginInpDTO.getLoginType());
            // 5. 生成新的token
            String keyPrefix = Constants.MEMBER_TOKEN_KEYPREFIX + loginType;
            String newToken = generateToken.createToken(keyPrefix, userId + "", Constants.MEMBRE_LOGIN_TOKEN_TIME);
            userToken.setToken(newToken);
            userToken.setDeviceInfor(deviceInfor);
            int insertUserToken = userTokenMapper.insertUserToken(userToken);
            if(!toDaoResult(insertUserToken)){
                redisDataSoureceTransaction.rollback(transactionStatus);
                return  setResultError("系统错误！");
            }
            JSONObject tokenData = new JSONObject();
            tokenData.put("token", newToken);
            redisDataSoureceTransaction.commit(transactionStatus);
            return setResultSuccess(tokenData);
        }catch (Exception e){
            try{
                redisDataSoureceTransaction.rollback(transactionStatus);
            }catch (Exception e2){

            }
            return  setResultError("系统错误！");
        }
    }

}
