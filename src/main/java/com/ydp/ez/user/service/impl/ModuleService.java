package com.ydp.ez.user.service.impl;

import com.ydp.ez.user.common.exception.UserErrorCode;
import com.ydp.ez.user.common.exception.UserException;
import com.ydp.ez.user.entity.Module;
import com.ydp.ez.user.entity.Role;
import com.ydp.ez.user.mapper.ModuleMapper;
import com.ydp.ez.user.mapper.RoleMapper;
import com.ydp.ez.user.service.IModuleService;
import com.ydp.ez.user.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class ModuleService implements IModuleService {

    @Resource
    ModuleMapper moduleMapper;

    @Override
    public boolean addModule(Integer parentId, String moduleName, String moduleDesc, String subSystem) throws UserException {

        if (StringUtils.isEmpty(moduleName)) {
            throw new UserException(UserErrorCode.PARAM_NULL, "模块名称");
        }
        if (StringUtils.isEmpty(subSystem)) {
            throw new UserException(UserErrorCode.PARAM_NULL, "所属系统");
        }
        if (StringUtils.isEmpty(moduleDesc)) {
            throw new UserException(UserErrorCode.PARAM_NULL, "模块描述");
        }
        if (parentId == null) {
            parentId = 0;
        }
        return moduleMapper.insert(parentId, moduleName, moduleDesc, subSystem) > 0 ? true : false;
    }

    @Override
    public boolean updateModule(Integer id, Integer parentId, String moduleName, String moduleDesc, String subSystem) throws UserException {
        if (id == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "编号");
        }
        if (parentId == null && StringUtils.isEmpty(moduleName) && StringUtils.isEmpty(moduleDesc) && StringUtils.isEmpty(subSystem)) {
            throw new UserException(UserErrorCode.PARAM_NULL, "参数不能全为空");
        }
        return moduleMapper.updateByPrimaryKeySelective(id, parentId, moduleName, moduleDesc, subSystem) > 0 ? true : false;
    }

    @Override
    public boolean logicalDeleteModule(Integer id) throws UserException {
        if (id == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "编号");
        }
        return moduleMapper.logicalDelete(id) > 0 ? true : false;
    }

    @Override
    public List<Module> queryModuleList(Integer id, String moduleName, String subSystem) {
        return moduleMapper.queryModuleList(id, moduleName, subSystem);
    }
}
