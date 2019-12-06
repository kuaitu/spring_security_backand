package com.lmk.springsecuritydemo.base.controller;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lmk.springsecuritydemo.base.entity.BaseModel;
import com.lmk.springsecuritydemo.base.service.IBaseService;
import com.lmk.springsecuritydemo.base.utils.Ret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class BaseController<T extends BaseModel, VO extends T, SERVICE extends IBaseService<T>> {
    protected Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @Autowired
    protected SERVICE service;
    private Class<? extends T> searchVoClass;

    public BaseController(Class<? extends VO> searchVoClass) {
        this.searchVoClass = searchVoClass;
    }

    @PostMapping({"search.action"})
    @ResponseBody
    public Ret<List<T>> search(@RequestParam int page, @RequestParam int rows, @RequestParam(required = false) String exampleJson, HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        T item = null;
        if (!Strings.isNullOrEmpty(exampleJson)) {
            item = gson.fromJson(exampleJson, this.searchVoClass);
        }

        Wrapper<T> q = null;
        if (null != item) {
            q = this.service.buildWrapper(item);
        }

        IPage<T> items = this.service.page(new Page((long)page, (long)rows), q);
        return Ret.ok(items.getRecords(), items.getTotal());
    }

    @PostMapping({"create.action"})
    @ResponseBody
    public Ret create(@RequestBody VO item, HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        this.service.save(item);
        return Ret.ok(getPrimaryKey(item));
    }

    @PostMapping({"update.action"})
    @ResponseBody
    public Ret<Void> update(@RequestBody VO item, HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        this.service.updateById(item);
        return Ret.ok();
    }

    @PostMapping({"delete.action"})
    @ResponseBody
    public Ret<Void> delete(@RequestBody VO item, HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        this.service.removeById(getPrimaryKey(item));
        return Ret.ok();
    }

    @PostMapping({"findById.action"})
    @ResponseBody
    public Ret<T> findById(@RequestBody VO item, HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        return Ret.ok(this.service.getById(getPrimaryKey(item)));
    }

    private Serializable getPrimaryKey(VO item){
        return getPrimaryKey(item.getClass() , item);
    }

    private Serializable getPrimaryKey(Class item, VO info){
        Field[] fields = item.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Annotation annotation = field.getAnnotation(TableId.class);
            if (annotation != null) {
                try {
                    return (Serializable) field.get(info);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return getPrimaryKey(item.getSuperclass(), info);
    }
}
