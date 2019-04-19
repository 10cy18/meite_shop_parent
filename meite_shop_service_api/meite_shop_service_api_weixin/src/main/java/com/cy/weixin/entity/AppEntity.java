package com.cy.weixin.entity;

import lombok.Data;

@Data
public class AppEntity {

    /**
     * appid
     */
    private String appId;
    /**
     * 应用名称
     */
    private String appName;

    public AppEntity(String appId, String appName) {
        this.appId = appId;
        this.appName = appName;
    }
}
