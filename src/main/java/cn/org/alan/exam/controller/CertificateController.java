package cn.org.alan.exam.controller;


import cn.org.alan.exam.common.group.CertificateGroup;
import cn.org.alan.exam.common.result.Result;
import cn.org.alan.exam.model.entity.Certificate;
import cn.org.alan.exam.model.form.cretificate.CertificateForm;
import cn.org.alan.exam.model.vo.certificate.MyCertificateVO;
import cn.org.alan.exam.service.ICertificateService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 证书管理书管理
 *
 * @author zsx
 * @since 2024-04-1
 */
@Api(tags = "证书管理相关接口")
@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

    @Resource
    private ICertificateService iCertificateService;

    /**
     * 添加证书，只有教师和管理员可以添加证书
     *
     * @param certificateForm 添加证书的前端参数
     * @return 返回响应结果
     */
    @ApiOperation("添加证书")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('role_admin')")
    public Result<String> addCertificate(@RequestBody @Validated(CertificateGroup.CertificateInsertGroup.class)
                                         CertificateForm certificateForm) {
        // 从token获取用户id，放入创建人id属性
        return iCertificateService.addCertificate(certificateForm);
    }

    /**
     * 分页查询证书
     *
     * @param pageNum           页码
     * @param pageSize          每页大小
     * @param certificateName   证书标题
     * @param certificationUnit 认证单位
     * @return 响应结果
     */
    @ApiOperation("分页查询证书")
    @GetMapping("/paging")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<IPage<Certificate>> pagingCertificate(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                        @RequestParam(value = "certificateName", required = false) String certificateName,
                                                        @RequestParam(value = "certificationUnit", required = false) String certificationUnit) {
        return iCertificateService.pagingCertificate(pageNum, pageSize, certificateName, certificationUnit);
    }

    /**
     * 修改证书
     *
     * @param id              证书ID
     * @param certificateForm 修改证书的前端参数
     * @return 返回响应结果
     */
    @ApiOperation("修改证书")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('role_admin')")
    public Result<String> updateCertificate(@PathVariable("id") Integer id,
                                            @RequestBody CertificateForm certificateForm) {
        certificateForm.setId(id);
        return iCertificateService.updateCertificate(certificateForm);
    }

    /**
     * 删除证书
     *
     * @param id 证书ID
     * @return 返回响应结果
     */
    @ApiOperation("删除证书")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('role_admin')")
    public Result<String> deleteCertificate(@PathVariable("id") Integer id) {
        return iCertificateService.deleteCertificate(id);
    }

    /**
     * 分页查已获证书
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @param examName 试卷标题
     * @return
     */
    @ApiOperation("分页查已获证书")
    @GetMapping("/paging/my")
    @PreAuthorize("hasAnyAuthority('role_student')")
    public Result<IPage<MyCertificateVO>> getMyCertificate(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                           @RequestParam(value = "examName", required = false) String examName) {
        return iCertificateService.getMyCertificatePaging(pageNum, pageSize, examName);
    }
}
