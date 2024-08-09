package model.dao;

//Static operations that instantiate dao

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDao createSellerDao()
    {
        return new SellerDaoJDBC();
    }
}
