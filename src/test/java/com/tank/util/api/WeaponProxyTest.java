package com.tank.util.api;

import com.annimon.stream.Stream;
import com.tank.util.basic.Color;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class WeaponProxyTest {

  @Test
  public void fire() {
    this.weaponProxy.fire();
  }

  @Test
  public void fire2() {
    Object proxyApi = this.apiProxyHandler.<Weapon>done(new Ak47());
    if (proxyApi instanceof Weapon) {
      Weapon weapon = ((Weapon) proxyApi);
      weapon.fire();
    }
  }

  @Test
  @SneakyThrows
  public void testColor() {
    Color c = Color.class.newInstance();
    Stream.of(c.getClass().getDeclaredMethods()).map(Method::getModifiers).forEach(System.out::println);

  }

  @Before
  public void init() {
    this.weaponProxy = new WeaponProxy(new Ak47());
    this.apiProxyHandler = new ApiProxyHandler();
  }

  private WeaponProxy weaponProxy;

  private ApiProxyHandler apiProxyHandler;
}