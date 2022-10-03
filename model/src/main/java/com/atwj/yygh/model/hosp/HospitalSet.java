package com.atwj.yygh.model.hosp;

import com.atwj.yygh.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 吴先森
 * @description:
 * @create 2022-09-27 9:19
 */
@Data
@ApiModel(description = "医院信息设置实体类")
@TableName("hospital_set")
public class HospitalSet extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "医院名称")
    @TableField("hosname")
    private String hosname;

    @ApiModelProperty(value = "医院编号")
    @TableField("hoscode")
    private String hoscode;

    @ApiModelProperty(value = "api基础路径")
    @TableField("api_url")
    private String apiUrl;

    @ApiModelProperty(value = "签名秘钥")
    @TableField("sign_key")
    private String signKey;

    @ApiModelProperty(value = "联系人姓名")
    @TableField("contacts_name")
    private String contactsName;

    @ApiModelProperty(value = "联系人手机")
    @TableField("contacts_phone")
    private String contactsPhone;

    @ApiModelProperty(value = "状态:0 已删除，1 良好")
    @TableField("status")
    private Integer status;
}
