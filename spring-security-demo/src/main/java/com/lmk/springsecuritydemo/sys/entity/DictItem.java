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
  * 字典项
  * </p>
 *
 * @author linmk
 * @since 2019-11-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dict_item")
@ApiModel(value="DictItem对象", description="字典项")
public class DictItem extends BaseModel{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典项ID")
    @TableId(value = "DICT_ITEM_ID", type = IdType.AUTO)
    private Integer dictItemId;

    @ApiModelProperty(value = "字典主键")
    @TableField("DICT_ID")
    private Integer dictId;

    @ApiModelProperty(value = "字典类别代码")
    @TableField("DICT_CODE")
    private String dictCode;

    @ApiModelProperty(value = "代码")
    @TableField("DICT_ITEM_CODE")
    private String dictItemCode;

    @ApiModelProperty(value = "值")
    @TableField("DICT_ITEM_VALUE")
    private String dictItemValue;

    @ApiModelProperty(value = "状态（1:展示可编辑，2:展示不可编辑，3:不展示）")
    @TableField("DICT_ITEM_STATUS")
    private String dictItemStatus;

    @ApiModelProperty(value = "备注")
    @TableField("NOTE")
    private String note;

    @ApiModelProperty(value = "顺序号")
    @TableField("SORT_NUMBER")
    private Integer sortNumber;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("MODIFY_TIME")
    private Date modifyTime;

    @ApiModelProperty(value = "记录版本号")
    @TableField("ROW_VERSION")
    private Integer rowVersion;


}
