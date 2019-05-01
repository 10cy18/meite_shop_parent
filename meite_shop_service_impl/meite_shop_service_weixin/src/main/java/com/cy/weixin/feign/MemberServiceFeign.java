package com.cy.weixin.feign;

import com.cy.member.service.MemberService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "app-cy-member")
public interface MemberServiceFeign extends MemberService {


}
