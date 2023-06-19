package com.ncc;

import com.ncc.configuration.Config;
import com.ncc.testbeanscope.PrototypeBean;
import com.ncc.testbeanscope.SingletonBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringdemoApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SpringdemoApplication.class, args);
        Config config = applicationContext.getBean(Config.class);
        String message = config.Getdata();
        System.out.println(message);

        SingletonBean bean1 = applicationContext.getBean(SingletonBean.class);
        System.out.println(bean1.getMessage());

        SingletonBean bean2 = applicationContext.getBean(SingletonBean.class);
        System.out.println(bean2.getMessage());

        System.out.println(bean1 == bean2);

        PrototypeBean prototypeBean1 = applicationContext.getBean(PrototypeBean.class);
        prototypeBean1.setMessage("Hello from prototype bean1");
        prototypeBean1.displayMessage();

        PrototypeBean prototypeBean2 = applicationContext.getBean(PrototypeBean.class);
        prototypeBean2.setMessage("Hello from prototype bean2");
        prototypeBean2.displayMessage();

    }
}

