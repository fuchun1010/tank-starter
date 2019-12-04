package com.tank.util.json;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tank198435163.com
 */
@Data
@Accessors(chain = true)
public class Person {

  String name;

  String gender;
}
