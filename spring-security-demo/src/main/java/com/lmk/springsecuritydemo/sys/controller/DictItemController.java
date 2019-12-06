package com.lmk.springsecuritydemo.sys.controller;


import com.lmk.springsecuritydemo.base.controller.BaseController;
import com.lmk.springsecuritydemo.sys.entity.DictItem;
import com.lmk.springsecuritydemo.sys.service.IDictItemService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * <p>
  * 字典项 前端控制器
  * </p>
 *
 * @author linmk
 * @since 2019-11-21
 */
@Api(tags = { "字典项" })
@RestController
@RequestMapping("/sys/dict-item")
public class DictItemController extends BaseController<DictItem, DictItem, IDictItemService> {
    public DictItemController() {
        super(DictItem.class);
    }
}
