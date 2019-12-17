package com.ydp.ez.user.controller;


import com.ydp.ez.user.common.exception.UserErrorCode;
import com.ydp.ez.user.common.exception.UserException;
import com.ydp.ez.user.common.vo.Result;
import com.ydp.ez.user.service.impl.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 角色接口
 */
@Controller
@Slf4j
public class ModuleController extends BaseController {

    @Autowired
    private ModuleService moduleService;


    @RequestMapping("/module/add")
    @ResponseBody
    public Result register(Integer parentId, String moduleName, String moduleDesc, String interfaceName, String subSystem) {
        Result result = null;
        try {
            result = success(moduleService.addModule(parentId, moduleName, moduleDesc, interfaceName, subSystem));
        } catch (UserException e) {
            log.warn("/role/add error ", e);
            result = error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("/role/add error ", e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }


    @RequestMapping("/module/query")
    @ResponseBody
    public Result moduleQuery(Integer id, String moduleName, String subSystem) {
        Result result = new Result();
        try {
            result = success(moduleService.queryModuleList(id, moduleName, subSystem));
        } catch (Exception e) {
            log.error("/module/query system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }


    @RequestMapping("/module/update")
    @ResponseBody
    public Result moduleUpdate(Integer id, Integer parentId, String moduleName, String moduleDesc, String interfaceName, String subSystem) {
        Result result = new Result();
        try {
            result = success(moduleService.updateModule(id, parentId, moduleName, moduleDesc, interfaceName, subSystem));
        } catch (UserException e) {
            log.warn("/module/update error ", e);
            result = error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("/module/update system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }

    @RequestMapping("/module/delete")
    @ResponseBody
    public Result roleUpdate(Integer id) {
        Result result = new Result();
        try {
            result = success(moduleService.logicalDeleteModule(id));
        } catch (UserException e) {
            log.warn("/module/delete error ", e);
            result = error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("/module/delete system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }

}
