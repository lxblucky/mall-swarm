package com.macro.mall.service;

import com.macro.mall.dto.TestTargetQueryParam;
import com.macro.mall.model.TestTarget;

import java.util.List;

public interface TestTargetService {
    List<TestTarget> queryList(TestTargetQueryParam queryParam, Integer pageNum, Integer pageSize);
}
