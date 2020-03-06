package com.tank.jproxy;

import org.reflections.Reflections;

import java.util.Set;

public class JProxy {

  public static void main(String[] args) {

    StudentDAO studentDAO = new DefaultStudentDAO();

    Reflections reflections = new Reflections(StudentDAO.class);
    Set<Class<? extends StudentDAO>> clazz = reflections.getSubTypesOf(StudentDAO.class);
    Class<? extends StudentDAO> xx = clazz.iterator().next();

    
    LocalTransactionProxy localTransactionProxy = new LocalTransactionProxy();
    //StudentDAO studentDAOWithTransaction = localTransactionProxy.addTransactionProxy(studentDAO, DefaultStudentDAO.class);
    StudentDAO studentDAOWithTransaction = localTransactionProxy.addTransactionProxy(studentDAO, xx);
    Student student = new Student();
    studentDAOWithTransaction.add(student);
  }
}
