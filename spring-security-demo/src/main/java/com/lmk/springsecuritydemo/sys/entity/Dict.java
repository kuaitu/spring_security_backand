package com.lmk.springsecuritydemo.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lmk.springsecuritydemo.base.entity.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
  * 字典类别表
  * </p>
 *
 * @author linmk
 * @since 2019-11-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dict")
@ApiModel(value="Dict对象", description="字典类别表")
public class Dict extends BaseModel{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典主键")
    @TableId(value = "DICT_ID", type = IdType.AUTO)
    private Integer dictId;

    @ApiModelProperty(value = "代码")
    @TableField("DICT_CODE")
    private String dictCode;

    @ApiModelProperty(value = "名称")
    @TableField("DICT_NAME")
    private String dictName;

    @ApiModelProperty(value = "描述")
    @TableField("DICT_DESC")
    private String dictDesc;

    @ApiModelProperty(value = "类别 1:用户 2:系统")
    @TableField("DICT_TYPE")
    private String dictType;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("MODIFY_TIME")
    private Date modifyTime;

    @ApiModelProperty(value = "状态（1:展示可编辑，2:展示不可编辑，3:不展示）")
    @TableField("DICT_STATUS")
    private String dictStatus;


}
