package com.example.demo.controller;

import com.example.demo.bean.DemoDoctor;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
@Api(value = "DoctorTestController-医生信息接口模拟")
public class DoctorTestController {
    @ResponseBody
    @RequestMapping(value = "/doctor" ,method = RequestMethod.POST)
    @ApiOperation(value = "添加医生信息" ,notes = "")
    public String addDoctor(@RequestBody DemoDoctor doctor) throws Exception {
        if (null ==doctor || doctor.getId() ==null){
            throw new  Exception("请联系管理员");
        }
        System.out.println("成功--------------"+doctor.getName());

        return doctor.getId().toString();
    }
    @ResponseBody
    @RequestMapping(value = "/doctor/{doctorID}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除医生信息" , notes = "")
    @ApiImplicitParam(paramType = "query" ,name = "doctorID" ,value = "医生ID" ,required = true,dataType = "Integer")
    public  String  delDoctor(@RequestParam Integer doctorID){
        if (doctorID>2){
            return "删除失败";
        }
  return "删除成功";
    }
    @ResponseBody
    @RequestMapping(value = "/doctor/{doctorID}" ,method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "query" ,name = "doctorID" ,value = "医生ID" ,required = true,dataType = "Integer")
    @ApiOperation(value = "修改医生信息" ,notes = "")
    public  String updDoctor(@RequestBody DemoDoctor doctor ,@RequestParam Integer doctorID)throws Exception {
        if(null == doctorID || null == doctor){
            throw new Exception("修改失败，Id为空");
        }
        if(doctorID > 5 ){
            throw new Exception("修改失败，Id不存在");
        }
        System.out.println(doctorID);
        System.out.println(doctor);
        return "修改成功";

    }
    @ResponseBody
    @RequestMapping(value="/doctor/{doctorId}",  method= RequestMethod.GET )
    @ApiOperation(value="获取医生详细信息", notes="仅返回姓名..")
    @ApiImplicitParam(paramType="query", name = "doctorId", value = "医生ID", required = true, dataType = "Integer")
    public DemoDoctor getDoctorDetail(@RequestParam Integer doctorId) throws  Exception{
        System.out.println(doctorId);
        if(null == doctorId){
            throw new Exception("查看医生信息失败");
        }
        if(doctorId > 3){
            throw new Exception("医生不存在,请更换ID");
        }
        DemoDoctor doctor = new DemoDoctor();
        doctor.setId(1);
        doctor.setName("测试员");
        return doctor;
    }

    @ResponseBody
    @RequestMapping(value="/doctor",  method= RequestMethod.GET )
    @ApiOperation(value="获取医生列表", notes="目前一次全部取，不分页")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="header", name = "token", value = "token", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "pageIndex", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "每页记录数", required = true, dataType = "String"),
    })
    public PageInfo<DemoDoctor> getDoctorList(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                              HttpServletRequest request) throws  Exception{

        String token = request.getHeader("token");
        if(null == token){
            throw new Exception("没有权限,请查看操作文档") ;
        }
        if(null == pageSize){
            throw new Exception("每页记录数不粗安在,请查看操作文档") ;
        }

        DemoDoctor doctor1 = new DemoDoctor();
        doctor1.setId(1);
        doctor1.setName("测试员1");
        DemoDoctor doctor2 = new DemoDoctor();
        doctor2.setId(2);
        doctor2.setName("测试员2");

        List<DemoDoctor> doctorList = new ArrayList<DemoDoctor>();
        doctorList.add(doctor1);
        doctorList.add(doctor2);
        return new PageInfo<DemoDoctor>(doctorList);
    }



}
