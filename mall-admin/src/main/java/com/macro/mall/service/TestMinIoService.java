package com.macro.mall.service;

import com.macro.mall.dto.TestPhotowall;

import java.util.List;

public interface TestMinIoService {
    int addPhotowall(TestPhotowall testPhotowall);
    List<TestPhotowall> queryList(TestPhotowall queryParam);
    int removePic(int id);
}
