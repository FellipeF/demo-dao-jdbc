package application;

import java.util.Date;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

    public static void main(String[] args) {
        //Test Department Class
        Department d = new Department(1, "Books");
        System.out.println(d);

        //Test Seller Class
        Seller seller = new Seller(21, "Bob", "bob@gmail.com", new Date(), 3000.0, d);
        System.out.println(seller);

        //Test Seller DAO through dependency injection.
        SellerDao sellerDao = DaoFactory.createSellerDao();

        //Test findById
        Seller sellerTestId = sellerDao.findById(3);
        System.out.println(sellerTestId);
    }

}
