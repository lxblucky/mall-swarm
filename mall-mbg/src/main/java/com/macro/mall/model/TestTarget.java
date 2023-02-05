package com.macro.mall.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: TODO
 * @Author: lss
 * @CreateTime: 2022-12-20 17:10
 * @Version: 1.0
 */
@Data
public class TestTarget implements Serializable {

    @ApiModelProperty(value = "订单id")
    private Long id;

    @ApiModelProperty(value = "指标名称")
    private String zbname;

    @ApiModelProperty(value = "提交时间")
    private Date createTime;

    @ApiModelProperty(value = "父节点id")
    private String parentId;
}
