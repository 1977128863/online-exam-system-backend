package cn.org.alan.exam.controller;


import cn.org.alan.exam.common.result.Result;
import cn.org.alan.exam.model.entity.Grade;
import cn.org.alan.exam.model.form.GradeForm;
import cn.org.alan.exam.model.vo.GradeVO;
import cn.org.alan.exam.service.IGradeService;
import cn.org.alan.exam.util.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班级管理
 *
 * @author Alan
 * @since 2024-03-21
 */
@RestController
@RequestMapping("/api/grades")
public class GradeController {
    @Resource
    private IGradeService gradeService;

    /**
     * 分页查询班级
     *
     * @param pageNum
     * @param pageSize
     * @param gradeName
     * @return
     */
    @GetMapping("/paging")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result getGrade(@RequestParam("pageNum") Integer pageNum,
                           @RequestParam("pageSize") Integer pageSize,
                           @RequestParam("gradeName") String gradeName) {
        return gradeService.getPaging(pageNum, pageSize, gradeName);
    }

    /**
     * 新增班级
     *
     * @param gradeForm
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> addGrade(@RequestBody GradeForm gradeForm) {
        return gradeService.addGrade(gradeForm);
    }

    /**
     * 修改班级
     *
     * @param id
     * @param gradeForm
     * @return
     */
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> updateGrade(@PathVariable("id") Integer id, @RequestBody GradeForm gradeForm) {
        return gradeService.updateGrade(id, gradeForm);
    }

    /**
     * 删除班级
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> deleteGrade(@PathVariable("id") Integer id) {
        return gradeService.deleteGrade(id);
    }

    /**
     * 退出班级
     * @param ids
     * @return
     */
    @PatchMapping("/remove/{ids}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> removeUserGrade(@PathVariable("ids") String ids) {
        return gradeService.removeUserGrade(ids);
    }

    /**
     * 获取所有班级列表
     * @return
     */
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<List<GradeVO>> getAllGrade(){
        return gradeService.getAllGrade();
    }
}
