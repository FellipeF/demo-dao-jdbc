package model.dao;

//Control Seller operations on DB

import java.util.List;
import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
    void insert(Seller s);
    void update(Seller s);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
    List<Seller> findByDepartment(Department d);
}