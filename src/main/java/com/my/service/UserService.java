package com.my.service;

import com.my.common.MyException;
import com.my.entity.User;
import com.my.mapper.UserMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> findByName(String name) {
        return userMapper.findUserByName(name);
    }

    public User insertUser(User user) {
        userMapper.insertUser(user);
        return user;
    }
    public List<User> ListUser(){
        return	userMapper.ListUser();
    }


    public int Update(User user){
        return userMapper.Update(user);
    }

    public int delete(int id){
        return userMapper.delete(id);
    }
    public List<User> fuzzyQuery(User user ) {
        return userMapper.fuzzyQuery(user);
    }


    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
        boolean notNull = false;
        List<User> userList = new ArrayList<User>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new MyException("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }
        User user;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {//r = 2 表示从第三行开始循环 如果你的第三行开始是数据
            Row row = sheet.getRow(r);//通过sheet表单对象得到 行对象
            if (row == null){
                continue;
            }
            //sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException
            user = new User();
            if( row.getCell(0).getCellType() !=1){//循环时，得到每一行的单元格进行判断
                throw new MyException("导入失败(第"+(r+1)+"行,用户名请设为文本格式)");
            }
            String username = row.getCell(0).getStringCellValue();//得到每一行第一个单元格的值
            int userId = Integer.parseInt(username);
            if("".equals(userId)){//判断是否为空
                throw new MyException("导入失败(第"+(r+1)+"行,id未填写)");
            }
            String name = row.getCell(0).getStringCellValue();
            if(name==null || name.equals("")){
                throw new MyException("导入失败(第"+(r+1)+"行,name未填写)");
            }
            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第二个单元格的值
            String password = row.getCell(1).getStringCellValue();
            if(password==null || password.equals("")){
                throw new MyException("导入失败(第"+(r+1)+"行,password未填写)");
            }
            String number = row.getCell(0).getStringCellValue();
            if(number==null || number.equals("")){
                throw new MyException("导入失败(第"+(r+1)+"行,number未填写)");
            }
            //完整的循环一次 就组成了一个对象
            user.setId(userId);
            user.setName(name);
            user.setPassword(password);
            user.setNumber(number);
            userList.add(user);
        }
        for (User userResord : userList) {
            String name = userResord.getName();
            List<User> userByName = userMapper.findUserByName(name);
            if (userByName.size()==0) {
                userMapper.insertUser(userResord);
                System.out.println(" 插入 "+userResord);
            } else {
                userMapper.Update(userResord);
                System.out.println(" 更新 "+userResord);
            }
        }
        return notNull;
    }


}
