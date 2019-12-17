package com.ydp.ez.user.common.vo;

import com.ydp.ez.user.entity.Role;
import com.ydp.ez.user.entity.RolePermission;
import com.ydp.ez.user.entity.User;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class SessionVO implements Serializable {
    private static final long serialVersionUID = 222159356576070759L;

    public SessionVO() {
    }

    public SessionVO(User user) {
        this.setUserId(user.getId());
        this.setUserName(user.getUserName());
        this.setEmail(user.getEmail());
        this.setPhone(user.getPhone());
        this.setPermitClientNum(user.getPermitClientNum());
        this.setNickName(user.getNickName());
        this.setRealName(user.getRealName());
    }

    public SessionVO(User user, List<Role> roleList, List<RolePermission> rolePermissionList) {
        this(user);
        if (!CollectionUtils.isEmpty(roleList)) {
            /**
             * toMap 如果集合对象有重复的key，会报错Duplicate key ...
             * 可以用 (key1,key2)->key1 来设置，如果有重复的key,则保留key1,舍弃key2
             */
            this.roleMap = roleList.stream().collect(Collectors.toMap(Role::getRoleName, role -> role, (key1, key2) -> key1));
        }
        if (!CollectionUtils.isEmpty(rolePermissionList)) {
            this.rolePermissionMap = rolePermissionList.stream().collect(Collectors.toMap(RolePermission::getInterfaceName, permission -> permission, (key1, key2) -> key1));
        }
    }

    private long userId;
    /**
     * 用户名
     */
    private String userName;


    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 允许客服端连接最大数量
     */
    private Integer permitClientNum;

    /**
     * token存储：token：ip
     */
    private String token;

    private String requsetIp;

    /**
     * 角色map
     * key：roleName
     */
    private Map<String, Role> roleMap;
    /**
     * 角色权限map
     * key：interfaceName
     */
    private Map<String, RolePermission> rolePermissionMap;

}