package cn.org.alan.exam.common.handler;

import cn.org.alan.exam.utils.DateTimeUtil;
import cn.org.alan.exam.utils.SecurityUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * mybatisPlus公共字段填充处理器
 *
 * @Author WeiJin
 * @Version 1.0
 * @Date 2024/3/31 10:00
 */
@Component
@Slf4j
public class FiledFullHandler implements MetaObjectHandler {
    /**
     * 添加数据拦截
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 没有创建人id就给他自动填充 放属性名而不是字段名
        Class<?> clazz = metaObject.getOriginalObject().getClass();
        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            // 填充创建人
            if ("userId".equals(field.getName()) && (Objects.isNull(getFieldValByName("userId", metaObject)))) {
                log.info("user_id字段满足公共字段自动填充规则，已填充");
                this.strictInsertFill(metaObject, "userId", Integer.class, SecurityUtil.getUserId());

            }
            // 填充创建时间
            if ("createTime".equals(field.getName()) && (Objects.isNull(getFieldValByName("createTime", metaObject)))) {
                log.info("create_time字段满足公共字段自动填充规则，已填充");
                this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, DateTimeUtil.getDateTime());
            }
        });
    }

    /**
     * 更新数据拦截
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
