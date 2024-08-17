package model.dao.impl;

import db.DB;
import db.DbException;
import java.util.List;
import model.dao.SellerDao;
import model.entities.Seller;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import model.entities.Department;

public class SellerDaoJDBC implements SellerDao{
    
    //TODO: Replace the default created overriden methods.
    
    private Connection conn;
    
    //Forces dependency injection
    
    public SellerDaoJDBC (Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public void insert(Seller d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Seller d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT SELLER.*,DEPARTMENT.NAME AS DEPNAME "
                    + "FROM SELLER INNER JOIN DEPARTMENT "
                    + "ON SELLER.DEPARTMENTID = DEPARTMENT.ID "
                    + "WHERE SELLER.ID = ?");
            
            st.setInt(1, id);
            rs = st.executeQuery();     
            
            //Object Association and instantiated in memory
            //find anything when querying?
            if (rs.next())
            {
                Department d = new Department();
                d.setId(rs.getInt("DepartmentId"));
                d.setName(rs.getString("DEPNAME"));
                
                Seller s = new Seller();
                s.setId(rs.getInt("Id"));
                s.setName(rs.getString("Name"));
                s.setEmail(rs.getString("Email"));
                s.setBaseSalary(rs.getDouble("BaseSalary"));
                s.setBirthDate(rs.getDate("BirthDate"));
                s.setDepartment(d);
                
                return s;
            }  
            return null;
        } catch (SQLException e)
        {
            throw new DbException(e.getMessage());
        }
        finally
        {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
        
    @Override
    public List<Seller> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
