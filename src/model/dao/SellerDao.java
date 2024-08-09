package model.dao;

//Control Seller operations on DB

import java.util.List;
import model.entities.Seller;

public interface SellerDao {
    void insert(Seller d);
    void update(Seller d);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
}