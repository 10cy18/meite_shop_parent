package com.cy.member.mapper;

import com.cy.member.mapper.entity.UserTokenDo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

public interface UserTokenMapper {

	@Select("SELECT id as id ,token as token ,login_type as LoginType,device_infor as deviceInfor ,is_availability as isAvailability,user_id as userId,create_time as createTime,update_time as updateTime FROM cy_user_token WHERE user_id=#{0} AND login_type=#{1} and is_availability ='0'; ")
	UserTokenDo selectByUserIdAndLoginType(@Param("userId") Integer userId, @Param("loginType") String loginType);

	@Update("update cy_user_token set is_availability ='1',update_time=now() where token=#{token}")
	int updateTokenAvailability(@Param("token") String token);

	@Insert("INSERT INTO `cy_user_token` VALUES (null, #{token},#{loginType}, #{deviceInfor}, 0, #{userId} ,now(),null ); ")
	int insertUserToken(UserTokenDo userTokenDo);
}
