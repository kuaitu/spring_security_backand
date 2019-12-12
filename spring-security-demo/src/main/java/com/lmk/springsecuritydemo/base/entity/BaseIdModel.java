package com.lmk.springsecuritydemo.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseIdModel<ID extends Serializable> extends BaseModel{

    @TableId(value = "id", type = IdType.AUTO)
    @XmlTransient
    private ID id;
}
