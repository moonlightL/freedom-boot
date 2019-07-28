package com.extlight.core.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author MoonlightL
 * @Title: TreeNode
 * @ProjectName freedom-boot
 * @Description: ztree 封装类
 * @Date 2019/7/5 16:09
 */
@Setter
@Getter
@ToString
public class TreeNode implements Serializable {

    private Long id;

    private Long pid;

    private String name;

    private Boolean checked;

    private Boolean open;
}
