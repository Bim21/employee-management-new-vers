package com.ncc.controller;

import com.ncc.testbeanscope.PrototypeBean;
import com.ncc.testbeanscope.SingletonBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BeanScopeController {

    private final SingletonBean singletonBean;
    private final PrototypeBean prototypeBean;

    public BeanScopeController(SingletonBean singletonBean, PrototypeBean prototypeBean) {
        this.singletonBean = singletonBean;
        this.prototypeBean = prototypeBean;
    }

//    @GetMapping("/bean-scope")
//    public Map<String, LocalDateTime> getBeanScope() {
//        Map<String, LocalDateTime> beanScopeMap = new HashMap<>();
//        beanScopeMap.put("Singleton Bean", singletonBean.getCreationTime());
//        beanScopeMap.put("Prototype Bean", prototypeBean.getCreationTime());
//        return beanScopeMap;
//    }
}
