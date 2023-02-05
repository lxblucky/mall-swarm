package com.macro.mall.dao;

import com.macro.mall.dto.TestTargetQueryParam;
import com.macro.mall.model.TestTarget;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lss
 * @CreateTime: 2022-12-20 17:18
 * @Version: 1.0
 */

public interface TestTargetDao {
    List<TestTarget> queryList(@Param("queryParam") TestTargetQueryParam queryParam);
    int addtarget(TestTargetQueryParam testTargetQueryParam);
    int addtargetList(@Param("list") List<TestTargetQueryParam> list2);
    TestTarget detailTarget(Long id);
    int updateTarget(TestTarget testTarget);

}
