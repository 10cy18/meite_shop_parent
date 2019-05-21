package com.cy.member.feign;

import com.cy.member.service.MemberService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-cy-member")
public interface MemberServiceFeign extends MemberService {

}
