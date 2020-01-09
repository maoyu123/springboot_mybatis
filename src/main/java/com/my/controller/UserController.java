package com.my.controller;

import com.my.entity.User;
import com.my.service.UserService;
import com.my.util.FileUtil;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@RestController
@RequestMapping(value = "/action", method={RequestMethod.GET,RequestMethod.POST})
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public String delete(int id){
         int result= userService.delete(id);
         if(result>=1){
             return "删除成功";
         }else {
             return "删除失败";
         }
    }

    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public String update(User user){
        int result = userService.Update(user);
        if (result>=1){
            return  "修改成功";
        }else{
            return "修改失败";
        }
    }

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public User insert(User user){
        return userService.insertUser(user);
    }

    @RequestMapping(value = "/findUserByName",method = RequestMethod.POST)
    public List<User> findUserByName(String name){
        return  userService.findByName(name);
    }

    @RequestMapping(value = "/ListUser",method = RequestMethod.POST)
    @ResponseBody
    public List<User> ListUser(){
        List<User> user = userService.ListUser();
        return user;
    }

    @RequestMapping(value = "/listUser",method = RequestMethod.POST)
    @ResponseBody
    public List<User> listUser(Model model){
        List<User> user = userService.ListUser();
        model.addAttribute("user",user);
        return user;
    }

    @RequestMapping(value = "/fuzzyQuery",method = RequestMethod.POST)
    public List<User> fuzzyQuery(User user){
        return userService.fuzzyQuery(user);
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        List<User> userList = userService.ListUser();
        // 创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建表
        HSSFSheet sheet = workbook.createSheet("用户信息");
        // 创建行
        HSSFRow row = sheet.createRow(0);
        // 创建单元格样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        // 表头
        String[] head = {"id","姓名", "密码","number"};
        HSSFCell cell;
        // 设置表头
        for(int iHead=0; iHead<head.length; iHead++) {
            cell = row.createCell(iHead);
            cell.setCellValue(head[iHead]);
            cell.setCellStyle(cellStyle);
        }
        // 设置表格内容
        for(int iBody=0; iBody<userList.size(); iBody++) {
            row = sheet.createRow(iBody+1);
            User u = userList.get(iBody);
            String[] userArray = new String[4];
            String userId = String.valueOf(u.getId());
            userArray[0]=userId;
            userArray[1]=u.getName()+"";
            userArray[2]=u.getPassword();
            userArray[3]=u.getNumber();
            for(int iArray=0; iArray<userArray.length; iArray++) {
                row.createCell(iArray).setCellValue(userArray[iArray]);
            }
        }
        // 生成Excel文件
        FileUtil.createFile(response, workbook);
    }

}
