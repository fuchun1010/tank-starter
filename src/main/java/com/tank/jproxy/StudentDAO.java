package com.tank.jproxy;

import lombok.NonNull;

public interface StudentDAO {

  void add(@NonNull final Student student);

  void remove(@NonNull final Integer id);

}
