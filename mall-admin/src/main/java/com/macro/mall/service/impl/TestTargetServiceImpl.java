package com.macro.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.dao.TestTargetDao;
import com.macro.mall.dto.TestTargetQueryParam;
import com.macro.mall.model.TestTarget;
import com.macro.mall.service.TestTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lss
 * @CreateTime: 2022-12-20 17:17
 * @Version: 1.0
 */
@Service
public class TestTargetServiceImpl implements TestTargetService {
    @Autowired
    private TestTargetDao testTargetDao;
    @Override
    public List<TestTarget> queryList(TestTargetQueryParam queryParam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return testTargetDao.queryList(queryParam);
    }
}
