package com.macro.mall.dao;

import com.macro.mall.dto.TestPhotowall;
import com.macro.mall.dto.TestTargetQueryParam;
import com.macro.mall.model.TestTarget;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lss
 * @CreateTime: 2022-12-20 17:18
 * @Version: 1.0
 */

public interface TestMinIoDao {
    int addPhotowall(TestPhotowall testPhotowall);
    List<TestPhotowall> queryList(@Param("queryParam") TestPhotowall queryParam);
    int removePic(int id);
}
