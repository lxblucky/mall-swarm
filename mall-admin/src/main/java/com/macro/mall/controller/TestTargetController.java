package com.macro.mall.controller;

import com.alibaba.excel.EasyExcel;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dao.TestTargetDao;
import com.macro.mall.dto.TestTargetQueryParam;
import com.macro.mall.model.TestTarget;
import com.macro.mall.service.TestTargetService;
import com.macro.mall.util.TargetUploadDataListener001;
import com.macro.mall.util.TestTarget001;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @ApiOperation("添加指标")
    @RequestMapping(value = "/addtarget", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addtarget(@RequestBody TestTargetQueryParam testTargetQueryParam) {
        int count = testTargetService.addtarget(testTargetQueryParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("查询指标")
    @RequestMapping(value = "/detailTarget/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<TestTarget> detailTarget(@PathVariable Long id) {
        TestTarget testTarget = testTargetService.detailTarget(id);
        return CommonResult.success(testTarget);
    }
    @ApiOperation("更改指标")
    @PostMapping(value = "/updateTarget/{id}")
//    @RequestMapping(value = "/updateTarget/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateTarget(@PathVariable Long id,@RequestBody TestTarget testTarget) {
        int count = testTargetService.updateTarget(id,testTarget);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
    @ApiOperation("上传excel")
    @PostMapping("/upload")
    @ResponseBody
    public void upload(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("--打印："+file.getName());
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(file.getInputStream(), TestTarget001.class, new TargetUploadDataListener001(testTargetService)).sheet().doRead();

        //demo：泛型不一样 web用的是MultipartFile
//        String fileName = "C:/Users/lss/Desktop/test/测试target.xlsx";
//        EasyExcel.read(fileName, TestTarget001.class, new TargetUploadDataListener001()).sheet().doRead();
    }
}
