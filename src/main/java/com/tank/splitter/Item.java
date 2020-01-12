package com.tank.splitter;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {

  @Override
  public boolean equals(Object that) {
    if (this == that) return true;
    if (that == null || getClass() != that.getClass()) return false;
    Item item = (Item) that;
    return id == item.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  //货品id
  private long id;

  //货品描述
  private String desc;

  //门店现售(1)、心享次日达(2)、预售(3)
  private int type;

  //库房代码
  private String repositoryCode;

  //重量
  private int weight;
  
}
