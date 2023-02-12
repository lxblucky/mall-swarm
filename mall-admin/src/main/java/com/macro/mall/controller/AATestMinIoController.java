package com.macro.mall.controller;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.github.pagehelper.util.StringUtil;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.TestPhotowall;
import com.macro.mall.service.TestMinIoService;
import com.macro.mall.util.MinIoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.ehcache.core.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @Description: Minio测试
 * @Author: lss
 * @CreateTime: 2022-12-20 17:01
 * @Version: 1.0
 */
@Controller
@Api(tags = "TestTargetController", description = "minio测试")
@RequestMapping("/test/minio")
public class AATestMinIoController {
    @Value("${minio.endpoint}")
    private String endpoint;        //MinIO服务所在url
    @Value("${minio.bucketName}")
    private String bucketName;     //存储的桶名称

    @Autowired
    private TestMinIoService testMinIoService;
    @Autowired
    private MinIoUtil minIoUtil;

    private Logger logger = LoggerFactory.getLogger(AATestMinIoController.class);

    @ApiOperation("上传到minio")
    @RequestMapping(value = "/uploadpic", method = RequestMethod.POST)
    @ResponseBody
    public String uploadpic(@RequestParam("file") MultipartFile file) {
        String upload = minIoUtil.upload(file);
        logger.info("upload:"+upload);
        //入库 也要写回滚
        TestPhotowall testPhotowall = new TestPhotowall();
        testPhotowall.setFilename(upload);
//        LocalDateTime time = LocalDateTime.now();
        testPhotowall.setCreateTime(new Date());
        int i = testMinIoService.addPhotowall(testPhotowall);
        logger.info("加入："+i);

        return endpoint+"/"+bucketName+"/"+upload;
    }

    @ApiOperation("查询照片墙预览地址-Minio-mall库")
    @RequestMapping(value = "/queryMinioList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Map<String, String>>> queryMinioList() {
//        list.add("20230211/fd4fbaf9-c0e1-498d-af34-04af9140a8d9.png");
        List<TestPhotowall> list = testMinIoService.queryList(new TestPhotowall());

        List<Map<String,String>> retlist = new ArrayList<>();
        //获取预览地址
        for (TestPhotowall testPhotowall : list) {
            if(StringUtil.isNotEmpty(testPhotowall.getFilename())){
                String preview = minIoUtil.preview(testPhotowall.getFilename());
                if(StringUtil.isNotEmpty(preview)){
                    Map<String,String> map = new HashMap<>();
                    map.put("id",testPhotowall.getId().toString());
                    map.put("name","");
                    map.put("url",preview);
                    retlist.add(map);
                    logger.info(preview);
                }
            }
        }
        return CommonResult.success(CommonPage.restPage(retlist));
    }

    @ApiOperation("删除照片墙图片")
    @RequestMapping(value = "/removePic", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> removePic(@RequestParam("param1") String param1) {
        //根据id查询对应的filename
        TestPhotowall queryParam = new TestPhotowall();
        queryParam.setId(Integer.parseInt(param1));
        List<TestPhotowall> list = testMinIoService.queryList(queryParam);;

        if(CollectionUtils.isEmpty(list) || StringUtils.isBlank(list.get(0).getFilename())){
            return CommonResult.failed("查询数据库没找到对应的filename,id:"+param1);
        }
        Boolean ret = minIoUtil.remove(list.get(0).getFilename());
        logger.info("删除MinIo库中数据:"+ret+" : "+list.get(0).getFilename());

        //入库 也要写回滚
        int i = testMinIoService.removePic(Integer.parseInt(param1));
        logger.info("删除数据库的id："+param1);

        return CommonResult.success("","");
    }

}
