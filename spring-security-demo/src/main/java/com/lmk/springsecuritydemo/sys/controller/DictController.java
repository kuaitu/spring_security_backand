package com.lmk.springsecuritydemo.sys.controller;


import com.lmk.springsecuritydemo.base.controller.BaseController;
import com.lmk.springsecuritydemo.sys.entity.Dict;
import com.lmk.springsecuritydemo.sys.service.IDictService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * <p>
  * 字典类别表 前端控制器
  * </p>
 *
 * @author linmk
 * @since 2019-11-21
 */
@Api(tags = { "字典类别表" })
@RestController
@RequestMapping("/sys/dict")
public class DictController extends BaseController<Dict, Dict, IDictService> {
    public DictController() {
        super(Dict.class);
    }
}
