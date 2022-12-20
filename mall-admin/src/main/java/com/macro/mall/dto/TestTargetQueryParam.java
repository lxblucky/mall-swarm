package com.macro.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 指标查询参数
 * Created by macro on 2018/10/11.
 */
@Getter
@Setter
public class TestTargetQueryParam {
    @ApiModelProperty(value = "id")
    private long id;
    @ApiModelProperty(value = "指标名称")
    private String zbname;
    @ApiModelProperty(value = "订单提交时间")
    private String createTime;
    @ApiModelProperty(value = "parentId")
    private String parentId;
}
