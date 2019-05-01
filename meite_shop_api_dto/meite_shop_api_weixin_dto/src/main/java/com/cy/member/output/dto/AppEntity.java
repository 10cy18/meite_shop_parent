package com.cy.member.output.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 *@author: cy
 */
@Data
@ApiModel(value = "用户信息")
public class AppEntity {
    private String id;
    private String name;

    public AppEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
