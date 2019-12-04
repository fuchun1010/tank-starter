package com.tank.util.basic;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author tank198435163.com
 */
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({KvConverterImporter.class, ColorImporter.class})
public @interface EnableKvConverter {

}
