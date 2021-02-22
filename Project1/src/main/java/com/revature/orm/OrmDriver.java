package com.revature.orm;

import com.revature.orm.models.User;
import com.revature.orm.util.*;

import java.lang.annotation.Annotation;
import java.util.List;

public class OrmDriver {

    public static SessionFactoryIF sessionFactoryIF;

    public static void main(String[] args) {

        sessionFactoryIF = new AnnotationConfiguration().configure("jdbc:postgresql://java-enterprise-2101192.cb6ljnanwiyj.us-east-2.rds.amazonaws.com:5432/P1DB","postgres","Revature2020!")
                .addPackage("com.revature.orm.models").addAnnotatedClass(User.class).buildSessionFactory();

        SessionIF sessionIF = sessionFactoryIF.openSession();
        User user = new User();
        user.setId(2);
        user.setFirstName("First");
        user.setLastName("Last");
        user.setAge(30);

      // sessionIF.save(user);
       user.setFirstName("Seco");
//
//        sessionIF.close();
//
//        sessionIF = sessionFactoryIF.openSession();
//        List<Object> objectList = sessionIF.getAll();
//        System.out.println(objectList.toString());
//        sessionIF.close();
//
//        sessionIF = sessionFactoryIF.openSession();
        //sessionIF.delete(user);
        sessionIF.update(user);
        sessionIF.close();





        // Order of making a basic example
        /*
        * import java.util.List;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManageEmployee {
   private static SessionFactory factory;
   public static void main(String[] args) {

      try {
         factory = new AnnotationConfiguration().
                   configure().
                   //addPackage("com.xyz") //add package if used.
                   addAnnotatedClass(Employee.class).
                   buildSessionFactory();
      } catch (Throwable ex) {
         System.err.println("Failed to create sessionFactory object." + ex);
         throw new ExceptionInInitializerError(ex);
      }

      ManageEmployee ME = new ManageEmployee();

      /* Add few employee records in database
        Integer empID1 = ME.addEmployee("Zara", "Ali", 1000);
        Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
        Integer empID3 = ME.addEmployee("John", "Paul", 10000);

        /* List down all the employees
        ME.listEmployees();

        /* Update employee's records
        ME.updateEmployee(empID1, 5000);

        /* Delete an employee from the database
        ME.deleteEmployee(empID2);

        /* List down new list of the employees
        ME.listEmployees();
    }

    /* Method to CREATE an employee in the database
    public Integer addEmployee(String fname, String lname, int salary){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;

        try {
            tx = session.beginTransaction();
            Employee employee = new Employee();
            employee.setFirstName(fname);
            employee.setLastName(lname);
            employee.setSalary(salary);
            employeeID = (Integer) session.save(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeID;
    }

    /* Method to  READ all the employees
    public void listEmployees( ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Employee").list();
            for (Iterator iterator = employees.iterator(); iterator.hasNext();){
                Employee employee = (Employee) iterator.next();
                System.out.print("First Name: " + employee.getFirstName());
                System.out.print("  Last Name: " + employee.getLastName());
                System.out.println("  Salary: " + employee.getSalary());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to UPDATE salary for an employee
    public void updateEmployee(Integer EmployeeID, int salary ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee)session.get(Employee.class, EmployeeID);
            employee.setSalary( salary );
            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to DELETE an employee from the records
    public void deleteEmployee(Integer EmployeeID){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee)session.get(Employee.class, EmployeeID);
            session.delete(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
        *
        *
        *
        * */

//        Metamodel<User> userMetamodel = Metamodel.of(User.class);
//
//        IdField idField = userMetamodel.getPrimaryKey();
//        List<ColumnField> columnFieldList = userMetamodel.getColumns();
//
//        System.out.printf("The primary key of User is %s; which maps to the column with the name %s\n", idField.getName(), idField.getColumnName());
//
//        for (ColumnField columnField: columnFieldList) {
//            System.out.printf("The User class contains a column called %s; which maps to the column with the name %s\n", columnField.getName(), columnField.getColumnName());
//        }
    }

    // Expose the configuration object and entity manager and session, don't expose the metamodel
}
