package com.macro.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@ApiModel(value = "TestPhotowall对象", description = "")
public class TestPhotowall{

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty("目录")
    private String dirs;

    @ApiModelProperty("生成的文件名")
    private String filename;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创建时间")
    private Date createTime;

}
