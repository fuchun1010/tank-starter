package com.tank.util.api;

import net.sf.cglib.proxy.Enhancer;

public class ClassProxy {


  private void init() {
    this.enhancer = new Enhancer();

    this.enhancer.setSuperclass(Weapon.class);
  }

  private Enhancer enhancer;
}
