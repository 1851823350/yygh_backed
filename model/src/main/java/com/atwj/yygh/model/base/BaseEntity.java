package com.atwj.yygh.model.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 吴先森
 * @description:
 * @create 2022-09-27 8:48
 */
@Data
public class BaseEntity implements Serializable {
    //@ApiModelProperty()注解用于方法、字段，表示对model属性的说明或者数据操作更改
    /**
     * value：字段说明，
     * name：重写属性名字，
     * dataType：重写属性类型，
     * required：是否必须，默认false，
     * example：举例，
     * hidden：隐藏。
     */
    @ApiModelProperty(value = "id")
    //@TableId注解是专门用在主键上的注解，如果数据库中的主键字段名和实体中的属性名，不一样且不是驼峰之类的对应关系，
    //可以在实体中表示主键的属性上加@Tableid注解，并指定@Tableid注解的value属性值为表中主键的字段名既可以对应上。
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")//指定属性和表中的字段对应
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除(1:已删除，0:未删除)")
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    @ApiModelProperty(value = "其他参数")
    @TableField(exist = false)
    private Map<String,Object> param = new HashMap<>();

}
