package com.macro.mall.service;

import com.macro.mall.dto.TestTargetQueryParam;
import com.macro.mall.model.TestTarget;
import com.macro.mall.util.TestTarget001;

import java.util.List;

public interface TestTargetService {
    List<TestTarget> queryList(TestTargetQueryParam queryParam, Integer pageNum, Integer pageSize);
    int addtarget(TestTargetQueryParam testTargetQueryParam);
    int addtargetList(List<TestTarget001> list);
    TestTarget detailTarget(Long id);
    int updateTarget(Long id,TestTarget testTarget);

}
