package com.tank.util.api;

import lombok.AllArgsConstructor;

@AllArgsConstructor()
public class WeaponProxy implements Weapon {

  @Override
  public void fire() {
    this.checkWeapon();
    this.weapon.fire();
    this.packWeapon();
  }

  private void packWeapon() {
    System.out.println("pack weapon");
  }

  private void checkWeapon() {
    System.out.println("check weapon status");
  }


  private Weapon weapon;
}
