package com.macro.mall.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FastByteArrayOutputStream;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.github.pagehelper.PageHelper;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description: Minio的工具类，操作方法
 * @Author: lss
 * @CreateTime: 2023-02-10 18:13
 * @Version: 1.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MinIoUtil {
    final MinIoClientConfig prop;

    final MinioClient minioClient;

    /**
     * 查看存储bucket是否存在
     * @return boolean
     */
    public Boolean bucketExists(String bucketName) {
        Boolean found;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return found;
    }

    /**
     * 创建存储bucket
     * @return Boolean
     */
    public Boolean makeBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 删除存储bucket
     * @return Boolean
     */
    public Boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 获取全部bucket
     */
    public List<Bucket> getAllBuckets() {
        try {
            List<Bucket> buckets = minioClient.listBuckets();
            return buckets;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件上传
     *
     * @param file 文件
     * @return Boolean
     */
    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)){
            throw new RuntimeException();
        }
        String fileName = UuidUtils.generateUuid() + originalFilename.substring(originalFilename.lastIndexOf("."));
//        String objectName = DateUtils.getDayStr() + "/" + fileName;
        //上面一行代码找不到工具包，下面代码代替
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        String objectName = date + "/" + fileName;
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(prop.getBucketName()).object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build();
            //文件名称相同会覆盖
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return objectName;
    }

    /**
     * 预览
     * @param fileName
     * @return
     */
    public String preview(String fileName){
        // 查看文件地址
        GetPresignedObjectUrlArgs build = new GetPresignedObjectUrlArgs().builder().bucket(prop.getBucketName()).object(fileName).method(Method.GET).build();
        try {
            String url = minioClient.getPresignedObjectUrl(build);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 预览
     * @param fileName
     * @Param: previewExpiry: 预览到期时间（小时）
     * @return
     */
    public String getPreviewUrl(String fileName,Integer previewExpiry) {
        if (StringUtils.isNotBlank(fileName)) {
            try {
                minioClient.statObject(StatObjectArgs.builder().bucket(prop.getBucketName()).object(fileName).build());
                if (null != previewExpiry){
                    return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(prop.getBucketName())
                            .object(fileName)
                            .expiry(previewExpiry, TimeUnit.HOURS)
                            .build());
                }else {
                    return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(prop.getBucketName())
                            .object(fileName)
                            .build());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 文件下载
     * @param fileName 文件名称
     * @param res response
     * @return Boolean
     */
    public void download(String fileName, HttpServletResponse res) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(prop.getBucketName())
                .object(fileName).build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)){
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()){
                while ((len=response.read(buf))!=-1){
                    os.write(buf,0,len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                res.setCharacterEncoding("utf-8");
                // 设置强制下载不打开
                // res.setContentType("application/force-download");
                res.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                try (ServletOutputStream stream = res.getOutputStream()){
                    stream.write(bytes);
                    stream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看桶里面下一层的所有文件/目录
     * @return 存储bucket内文件对象信息
     */
    public List<Item> listObjects() {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(prop.getBucketName()).build());
        List<Item> items = new ArrayList<>();
        try {
            for (Result<Item> result : results) {
                items.add(result.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return items;
    }

    /**
     * 分页查看桶里面所有文件/目录
     * @return 存储bucket内文件对象信息
     */
    public List<Map<String, String>> listPage(Integer pageNum,Integer pageSize) {
        List<Map<String, String>> list = CollectionUtil.newArrayList();
        if (!bucketExists(prop.getBucketName())) {
            System.out.println("文件桶'"+prop.getBucketName()+"'不存在，清闲创建！");
        }
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(prop.getBucketName()).build());
        Map<String, String> map;

        try {
            for (Result<Item> result : results) {
                map = new HashMap<>();
                Item item = result.get();
                String originalFilename = result.get().objectName();
                // 条件为空,不过滤,直接返回
//            if (StrUtil.isBlank(filePageDto.getFileName())) {
                map.put(originalFilename, originalFilename);
                list.add(map);
//            } else {//需要过滤
//                for (String fileName : filePageDto.getFileNameList()) {
//                    if (ObjectUtil.equals(originalFilename, fileName)) {
//                        map.put(originalFilename, minioConfig.getFilePrefix() + originalFilename);
//                        list.add(map);
//                    }
//                }
//            }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        PageHelper.startPage(pageNum,pageSize);
        return list;
    }

    /**
     * 删除
     * @param fileName
     * @return
     * @throws Exception
     */
    public boolean remove(String fileName){
        try {
            minioClient.removeObject( RemoveObjectArgs.builder().bucket(prop.getBucketName()).object(fileName).build());
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
