package com.tank.util.basic;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author tank198435163.com
 */
public class KvConverterImporter implements ImportSelector {

  @Override
  public String[] selectImports(AnnotationMetadata importingClassMetadata) {
    return new String[]{KvConverter.class.getName()};
  }
}
