package com.macro.mall.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: easyExcel上传的实体类
 * @Author: lss
 * @CreateTime: 2023-02-03 13:23
 * @Version: 1.0
 */
@Getter
@Setter
@EqualsAndHashCode
public class TestTarget001 {
    // 上传文档用的，字段顺序和excel的字段顺序必须保持一致
    private String zbname;
    private int parentId;
}
