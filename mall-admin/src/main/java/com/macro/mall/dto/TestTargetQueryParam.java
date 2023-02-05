package com.macro.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * 指标查询参数
 * Created by macro on 2018/10/11.
 */
@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class TestTargetQueryParam {
    @ApiModelProperty(value = "id")
    private int id;
    @ApiModelProperty(value = "指标名称")
    private String zbname;
    @ApiModelProperty(value = "订单提交时间")
    private Date createTime;
    @ApiModelProperty(value = "parentId")
    private Long parentId;
}
