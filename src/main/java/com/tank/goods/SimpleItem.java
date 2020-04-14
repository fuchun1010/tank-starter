package com.tank.goods;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author tank198435163.com
 */
@Getter
@Setter
@Accessors(chain = true)
public class SimpleItem {


  private String skuId;

  private int dispatchWay;

  private String stock;

}
