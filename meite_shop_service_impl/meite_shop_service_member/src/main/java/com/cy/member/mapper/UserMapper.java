package com.cy.member.mapper;

import com.cy.member.mapper.entity.UserDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

public interface UserMapper {

	@Insert("INSERT INTO `cy_user` VALUES (null,#{mobile}, #{email}, #{password}, #{userName}, null, null, null, '1', null, null, null);")
	int register(UserDO userDO);

	@Select("SELECT * FROM cy_user WHERE MOBILE=#{mobile};")
	UserDO existMobile(@Param("mobile") String mobile);
}
