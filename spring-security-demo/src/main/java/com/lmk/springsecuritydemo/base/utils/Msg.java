package com.lmk.springsecuritydemo.base.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Msg {
    private String title;
    private String content;
    private String etraInfo;
}
