package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.TestTargetQueryParam;
import com.macro.mall.model.TestTarget;
import com.macro.mall.service.TestTargetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lss
 * @CreateTime: 2022-12-20 17:01
 * @Version: 1.0
 */
@Controller
@Api(tags = "TestTargetController", description = "测试指标触点")
@RequestMapping("/test/target")
public class TestTargetController {
    @Autowired
    private TestTargetService testTargetService;

    @ApiOperation("查询指标")
    @RequestMapping(value = "/queryList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<TestTarget>> queryList(TestTargetQueryParam queryParam,
                                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<TestTarget> orderList = testTargetService.queryList(queryParam,pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(orderList));
    }
}
