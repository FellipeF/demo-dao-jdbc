package model.dao;

//Static operations that instantiate dao

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDao createSellerDao()
    {
        return new SellerDaoJDBC(DB.getConnection());
    }
    
    public static DepartmentDao createDepartmentDao()
    {
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
