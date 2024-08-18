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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.entities.Department;

public class SellerDaoJDBC implements SellerDao {

    //TODO: Replace the default created overriden methods.
    private Connection conn;

    //Forces dependency injection
    public SellerDaoJDBC(Connection conn) {
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
            if (rs.next()) {
                Department d = instantiateDepartment(rs);
                Seller s = instantiateSeller(rs, d);
                return s;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT SELLER.*,DEPARTMENT.NAME AS DEPNAME "
                    + "FROM SELLER INNER JOIN DEPARTMENT "
                    + "ON SELLER.DEPARTMENTID = DEPARTMENT.ID "
                    + "ORDER BY NAME");

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller s = instantiateSeller(rs, dep);
                list.add(s);
            }

            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department d) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT SELLER.*,DEPARTMENT.NAME AS DEPNAME "
                    + "FROM SELLER INNER JOIN DEPARTMENT "
                    + "ON SELLER.DEPARTMENTID = DEPARTMENT.ID "
                    + "WHERE DEPARTMENTID = ? "
                    + "ORDER BY NAME");

            st.setInt(1, d.getId());
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            //Query can return more than 1.
            while (rs.next()) {
                //Checks if department that's on the result set already exists
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller s = instantiateSeller(rs, dep);
                list.add(s);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    //No need for try catch exception, since it's already done in the other overriden methods
    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department d = new Department();
        d.setId(rs.getInt("DepartmentId"));
        d.setName(rs.getString("DEPNAME"));
        return d;
    }

    private Seller instantiateSeller(ResultSet rs, Department d) throws SQLException {
        Seller s = new Seller();
        s.setId(rs.getInt("Id"));
        s.setName(rs.getString("Name"));
        s.setEmail(rs.getString("Email"));
        s.setBaseSalary(rs.getDouble("BaseSalary"));
        s.setBirthDate(rs.getDate("BirthDate"));
        s.setDepartment(d);
        return s;
    }
}
