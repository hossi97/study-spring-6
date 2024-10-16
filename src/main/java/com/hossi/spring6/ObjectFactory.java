package com.hossi.spring6;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * BeanFactory 에 제공할 Bean 클래스 & 의존관계에 대한 구성 정보
 * 구성 정보를 바탕으로 스프링 컨테이녀(= BeanFactory)가 구현체를 생성 및 의존관계를 주입하고, 구현체를 제공
 * (기본적으로 구현체를 Singleton 으로 유지)
 */
@Configuration
@ComponentScan
public class ObjectFactory {
}
