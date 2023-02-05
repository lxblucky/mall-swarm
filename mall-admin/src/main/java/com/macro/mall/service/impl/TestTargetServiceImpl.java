package com.macro.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.macro.mall.dao.TestTargetDao;
import com.macro.mall.dto.TestTargetQueryParam;
import com.macro.mall.model.TestTarget;
import com.macro.mall.service.TestTargetService;
import com.macro.mall.util.TestTarget001;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: TODO
 * @Author: lss
 * @CreateTime: 2022-12-20 17:17
 * @Version: 1.0
 */
@Service
public class TestTargetServiceImpl implements TestTargetService {
    @Autowired
    private TestTargetDao testTargetDao;
    @Override
    public List<TestTarget> queryList(TestTargetQueryParam queryParam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return testTargetDao.queryList(queryParam);
    }

    @Override
    public int addtarget(TestTargetQueryParam testTargetQueryParam) {
        testTargetQueryParam.setCreateTime(new Date());
        return testTargetDao.addtarget(testTargetQueryParam);
    }

    @Override
    public int addtargetList(List<TestTarget001> list) {
        List<TestTargetQueryParam> list2 = Lists.transform(list,(en) ->{
            TestTargetQueryParam ret = new TestTargetQueryParam();
            ret.setZbname(en.getZbname());
            ret.setParentId(Long.valueOf(en.getParentId()));
            ret.setCreateTime(new Date());
            return ret;
        });
        return testTargetDao.addtargetList(list2);


//        ----------测试 方法一：
//        List<TestTargetQueryParam> list2 = new ArrayList<>();
//        TestTargetQueryParam rr = new TestTargetQueryParam();
//        rr.setId(72);
//        rr.setZbname("ce72");
//        rr.setParentId(2L);
//        rr.setCreateTime(new Date());
//        list2.add(rr);
//        return testTargetDao.addtarget(rr);
//        ----------测试 方法一：


        //测试方法二：------------jdbc-获取连接--可行说明是mapper没有注入成功
//        Connection conn = JdbcDbUtil.getConnection();
//        //sql
//        String sql = "INSERT INTO test_zb_zbgl(zbname,create_time,parent_id)"
//                +"values("+"?,CURRENT_DATE(),?)";
//        //预编译
//        PreparedStatement ptmt = null; //预编译SQL，减少sql执行
//        try {
//            ptmt = conn.prepareStatement(sql);
//            //传参
//            ptmt.setString(1, "ce72");
//            ptmt.setLong(2, 72);
//            //执行
//            ptmt.execute();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return 1;
        //------------jdbc-获取连接--可行说明是mapper没有注入成功

    }

    @Override
    public TestTarget detailTarget(Long id) {
        return testTargetDao.detailTarget(id);
    }

    @Override
    public int updateTarget(Long id, TestTarget testTarget) {
        testTarget.setId(id);
        testTarget.setCreateTime(new Date());
        return testTargetDao.updateTarget(testTarget);
    }
}
