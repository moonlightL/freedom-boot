package com.extlight.core.model;

import com.extlight.common.base.BaseResponse;
import com.extlight.common.component.mybatis.CreateTime;
import com.extlight.common.component.mybatis.UpdateTime;
import com.extlight.core.model.vo.SysRoleVO;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @Author MoonlightL
 * @ClassName: SysRole
 * @ProjectName freedom-boot
 * @Description: 系统角色
 * @Date 2019/7/1 15:41
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@Table(name = "t_sys_role")
public class SysRole extends BaseResponse<SysRoleVO> {

    /**
     *  id
     */
    @Id
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 描述
     */
    private String descr;

    /**
     * 创建时间
     */
    @CreateTime
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @UpdateTime
    private LocalDateTime updateTime;
}
