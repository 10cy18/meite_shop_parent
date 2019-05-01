package com.cy.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cy.base.BaseApiService;
import com.cy.base.BaseResponse;
import com.cy.constants.Constants;
import com.cy.core.bean.MiteBeanUtils;
import com.cy.core.utils.MD5Util;
import com.cy.member.feign.VerificaCodeServiceFeign;
import com.cy.member.input.dto.UserInpDTO;
import com.cy.member.mapper.UserMapper;
import com.cy.member.mapper.entity.UserDO;
import com.cy.member.service.MemberRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberRegisterServiceImpl extends BaseApiService<JSONObject> implements MemberRegisterService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private VerificaCodeServiceFeign verificaCodeServiceFeign;

	@Override
	@Transactional
	public BaseResponse<JSONObject> register(@RequestBody UserInpDTO userInpDTO, String registCode) {
		// 1.验证参数
		String userName = userInpDTO.getUserName();
		if (StringUtils.isEmpty(userName)) {
			return setResultError("用户名称不能为空!");
		}
		String mobile = userInpDTO.getMobile();
		if (StringUtils.isEmpty(mobile)) {
			return setResultError("手机号码不能为空!");
		}
		String password = userInpDTO.getPassword();
		if (StringUtils.isEmpty(password)) {
			return setResultError("密码不能为空!");
		}
		String newPassWord = MD5Util.MD5(password);
		// 将密码采用MD5加密
		userInpDTO.setPassword(newPassWord);
		// 调用微信接口,验证注册码是否正确
		BaseResponse<JSONObject> resultVerificaWeixinCode = verificaCodeServiceFeign.verificaWeixinCode(mobile,registCode);
		if (!resultVerificaWeixinCode.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			return setResultError(resultVerificaWeixinCode.getMsg());
		}
		UserDO userDO = MiteBeanUtils.dtoToDo(userInpDTO, UserDO.class);
		return userMapper.register(userDO) > 0 ? setResultSuccess("注册成功") : setResultError("注册失败");

	}

}
