package com.macro.mall.service.impl;

import com.macro.mall.dao.TestMinIoDao;
import com.macro.mall.dto.TestPhotowall;
import com.macro.mall.service.TestMinIoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lss
 * @CreateTime: 2023-02-12 20:11
 * @Version: 1.0
 */
@Service
public class TestMinIoServiceImpl implements TestMinIoService {
    @Autowired
    private TestMinIoDao testMinIoDao;
    @Override
    public int addPhotowall(TestPhotowall testPhotowall) {
        return testMinIoDao.addPhotowall(testPhotowall);
    }

    @Override
    public List<TestPhotowall> queryList(TestPhotowall queryParam) {
        return testMinIoDao.queryList(queryParam);
    }

    @Override
    public int removePic(int id) {
        return testMinIoDao.removePic(id);
    }

}
