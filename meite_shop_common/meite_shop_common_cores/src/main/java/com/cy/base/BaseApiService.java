package com.cy.base;

import org.springframework.stereotype.Component;

import com.cy.constants.Constants;

import lombok.Data;

/**
 * 
 * 
 * @description: 微服务接口实现该接口可以使用传递参数可以直接封装统一返回结果集
 * @author: cy
 * @date: 2019年1月3日 下午3:03:17
 * @version V1.0
 */
@Data
@Component
public class BaseApiService<T> {

	public BaseResponse<T> setResultError(Integer code, String msg) {
		return setResult(code, msg, null);
	}

	/**
	 * 功能描述: 返回错误，可以传msg
	 *
	 * @param:
	 * @return:
	 * @auther: cy
	 * @date: 2019/4/30 16:43
	 */
	public BaseResponse<T> setResultError(String msg) {
		return setResult(Constants.HTTP_RES_CODE_500, msg, null);
	}

	/**
	 * 功能描述: 返回成功，可以传data值
	 *
	 * @param:
	 * @return:
	 * @auther: cy
	 * @date: 2019/4/30 16:43
	 */
	public BaseResponse<T> setResultSuccess(T data) {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
	}

	/**
	 * 功能描述: 返回成功，沒有data值
	 *
	 * @param:
	 * @return:
	 * @auther: cy
	 * @date: 2019/4/30 16:43
	 */
	public BaseResponse<T> setResultSuccess() {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
	}

	/**
	 * 功能描述: 返回成功，沒有data值
	 *
	 * @auther: cy
	 * @date: 2019/4/30 16:43
	 */
	public BaseResponse<T> setResultSuccess(String msg) {
		return setResult(Constants.HTTP_RES_CODE_200, msg, null);
	}

	/**
	 * 功能描述: 通用封装
	 *
	 * @auther: cy
	 * @date: 2019/4/30 16:43
	 */
	public BaseResponse<T> setResult(Integer code, String msg, T data) {
		return new BaseResponse<T>(code, msg, data);
	}

	/**
	 * 功能描述: 调用数据库层判断
	 *
	 * @param: result
	 * @return: Boolean
	 * @auther: cy
	 * @date: 2019/4/30 16:43
	 */
	public Boolean toDaoResult(int result) {
		return result > 0 ? true : false;
	}

	/**
	 * 接口直接返回true 或者false
	 *
	 * @param: baseResp
	 * @auther: cy
	 * @date: 2019/4/30 16:43
	 */
	public Boolean isSuccess(BaseResponse<?> baseResp) {
		if (baseResp == null) {
			return false;
		}
		if (baseResp.getCode().equals(Constants.HTTP_RES_CODE_500)) {
			return false;
		}
		return true;
	}

}
