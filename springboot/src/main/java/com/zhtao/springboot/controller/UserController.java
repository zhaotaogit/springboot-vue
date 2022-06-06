package com.zhtao.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.core.util.StringUtils;
import com.zhtao.springboot.common.Constants;
import com.zhtao.springboot.common.Result;
import com.zhtao.springboot.controller.dto.UserDto;
import com.zhtao.springboot.entity.User;
import com.zhtao.springboot.service.IUserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;


/**
 * @author zhtao
 * @since 2022-06-04
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;


    @PostMapping("/login")
    public Result login(@RequestBody UserDto user) {
        String username = user.getUsername();
        String password = user.getPassword();
        // 判断用户名或密码是否为空
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        UserDto dto = userService.login(user);
        return Result.success(dto);
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        // 判断用户名或密码是否为空
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userService.register(userDto));
    }

    @PostMapping
    public Result save(@RequestBody User user) {
        return Result.success(userService.saveOrUpdate(user));
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    /**
     * 根据前端传入的数组，删除多个用户数据
     *
     * @param ids
     * @return
     */
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeBatchByIds(ids);

    }

    @GetMapping
    public List<User> findAll() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable Integer id) {
        return userService.getById(id);
    }


    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return Result.success(userService.getOne(queryWrapper));
    }

    // 分页查询-mybatis-plus
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address) {
//        创建page对象，传入从第几哥开始，每页取几个
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        StringUtils.isNullOrEmpty(username)判断数据是否为空或null，true则不拼接like，反之。
        queryWrapper.like(!StringUtils.isNullOrEmpty(username), "username", username)
                .like(!StringUtils.isNullOrEmpty(email), "email", email)
                .like(!StringUtils.isNullOrEmpty(address), "address", address);
        return Result.success(userService.page(page, queryWrapper));
    }

    /**
     * 导出接口
     *
     * @param response
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
//        从数据查询所有的数据
        List<User> list = userService.list();
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
//        writer.addHeaderAlias("username", "用户名");
//        writer.addHeaderAlias("password", "密码");
//        writer.addHeaderAlias("nickname", "昵称");
//        writer.addHeaderAlias("phone", "电话");
//        writer.addHeaderAlias("email", "邮箱");
//        writer.addHeaderAlias("address", "地址");
//        writer.addHeaderAlias("avatarUrl", "头像");

        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");


        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }


    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);

//        将传入的表格中的标题转换为User类中的变量名
//        reader.addHeaderAlias("用户名","username" );
//        reader.addHeaderAlias( "密码","password");
//        reader.addHeaderAlias("昵称","nickname");
//        reader.addHeaderAlias( "电话","phone");
//        reader.addHeaderAlias("邮箱","email");
//        reader.addHeaderAlias( "地址","address");
//        reader.addHeaderAlias("头像","avatarUrl");


        List<User> readAll = reader.readAll(User.class);
        System.out.println(readAll);
//        将数据保存到数据库
        userService.saveBatch(readAll);
        return true;
    }
}

