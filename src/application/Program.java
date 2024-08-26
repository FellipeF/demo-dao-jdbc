package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        //Test Seller DAO through dependency injection.
        SellerDao sellerDao = DaoFactory.createSellerDao();

        //Test Seller Class
        System.out.println("---------------------------");
        Seller seller = new Seller(21, "Bob", "bob@gmail.com", new Date(), 3000.0, new Department(1, "Books"));
        System.out.println(seller);

        //Test findById
        System.out.println("---------------------------");
        Seller sellerTestId = sellerDao.findById(3);
        System.out.println(sellerTestId);

        //Test findByDepartment
        System.out.println("---------------------------");
        Department dTestDepartment = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(dTestDepartment);
        for (Seller s : list) {
            System.out.println(s);
        }

        //Test findAll
        System.out.println("---------------------------");
        list = sellerDao.findAll();
        for (Seller s : list) {
            System.out.println(s);
        }

        //Test Seller Insertion
        System.out.println("---------------------------");
        Seller insertSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, dTestDepartment);
        sellerDao.insert(insertSeller);
        System.out.println("Inserted! New id = " + insertSeller.getId());

        //Test Seller Update
        System.out.println("---------------------------");
        seller = sellerDao.findById(1);
        seller.setName("Martha Wayne");
        sellerDao.update(seller);
        System.out.println("Update complete");

        //Test Delete Seller
        System.out.println("---------------------------");
        System.out.print("Enter ID to be deleted: ");
        int id = input.nextInt();
        sellerDao.deleteById(id);

        //BEGIN DEPARTMENT TESTING
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        Department d = new Department(1, "Books");
        System.out.println(d);

        //INSERT DEPARTMENT
        System.out.println("---------------------------");
        Department dep = new Department(null, "Games");
        departmentDao.insert(dep);
        System.out.println("Inserted! New id = " + dep.getId());

        //FIND DEPARTMENT BY ID
        System.out.println("---------------------------");
        Department depFind = departmentDao.findById(3);
        System.out.println(depFind);

        //UPDATE DEPARTMENT
        System.out.println("---------------------------");
        dep = departmentDao.findById(6);
        dep.setName("Food");
        departmentDao.update(dep);
        System.out.println("Update complete!");

        //DELETE DEPARTMENT
        System.out.println("---------------------------");
        System.out.print("Enter ID to be deleted: ");
        id = input.nextInt();
        departmentDao.deleteById(id);
        
        //FIND ALL DEPARTMENTS
        System.out.println("---------------------------");
        List <Department> departments = departmentDao.findAll();
        for (Department department : departments) {
            System.out.println(department);
        }

        input.close();

    }

}
