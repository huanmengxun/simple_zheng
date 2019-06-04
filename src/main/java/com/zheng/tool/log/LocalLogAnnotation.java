package com.zheng.tool.log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import lombok.extern.log4j.Log4j2;
@Retention(RetentionPolicy.RUNTIME )
public @interface LocalLogAnnotation {
	 String info() default "";  
}
