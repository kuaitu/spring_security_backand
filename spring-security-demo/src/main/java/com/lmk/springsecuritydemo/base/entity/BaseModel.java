package com.lmk.springsecuritydemo.base.entity;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseModel implements Serializable  {
}
