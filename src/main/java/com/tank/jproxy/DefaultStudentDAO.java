package com.tank.jproxy;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;

import java.sql.Connection;

public class DefaultStudentDAO implements StudentDAO {

  @Override
  public void add(@NonNull Student student) {

    System.out.println("add = " + this.connection);

  }

  @Override
  public void remove(@NonNull Integer id) {
    System.out.println("remove = " + this.connection);
  }


  @Setter(AccessLevel.PRIVATE)
  private Connection connection;
}
