package com.ydp.ez.user.mapper;

import com.ydp.ez.user.entity.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleMapper {
    int insert(@Param("parentId") Integer parentId, @Param("moduleName") String moduleName, @Param("moduleDesc") String moduleDesc,
               @Param("interfaceName") String interfaceName, @Param("subSystem") String subSystem);

    List<Module> queryModuleList(@Param("id") Integer id, @Param("moduleName") String moduleName, @Param("subSystem") String subSystem);

    int updateByPrimaryKeySelective(@Param("id") Integer id, @Param("parentId") Integer parentId, @Param("moduleName") String moduleName,
                                    @Param("moduleDesc") String moduleDesc, @Param("interfaceName") String interfaceName, @Param("subSystem") String subSystem);

    int logicalDelete(@Param("id") Integer id);
}