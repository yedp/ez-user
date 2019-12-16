package com.ydp.ez.user.service;

import com.ydp.ez.user.common.exception.UserException;
import com.ydp.ez.user.entity.Module;
import com.ydp.ez.user.entity.Role;

import java.util.List;

/**
 * @Author: yedp
 * @Date: 2019/12/16 13:48:00
 * @Description：角色权限相关服务
 */
public interface IModuleService {

    /**
     * 添加模块
     *
     * @param parentId
     * @param moduleName
     * @param moduleDesc
     * @param subSystem
     * @return
     */
    boolean addModule(Integer parentId, String moduleName, String moduleDesc, String subSystem) throws UserException;

    /**
     * 修改模块
     *
     * @param parentId
     * @param moduleName
     * @param moduleDesc
     * @param subSystem
     * @return
     */
    boolean updateModule(Integer id, Integer parentId, String moduleName, String moduleDesc, String subSystem) throws UserException;

    /**
     * 逻辑删除模块
     *
     * @param id
     * @return
     */
    boolean logicalDeleteModule(Integer id) throws UserException;

    /**
     * 查询模块
     *
     * @param id
     * @param moduleName
     * @param subSystem
     * @return
     */
    List<Module> queryModuleList(Integer id, String moduleName, String subSystem);

}
