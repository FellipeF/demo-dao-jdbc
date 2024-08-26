package model.dao.impl;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
    
    private Connection conn;
    
    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public void insert(Department d) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO DEPARTMENT (NAME) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            
            st.setString(1, d.getName());
            
            int affectedRows = st.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    d.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
    
    @Override
    public void update(Department d) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE DEPARTMENT SET NAME = ? WHERE ID = ?", Statement.RETURN_GENERATED_KEYS);
            
            st.setString(1, d.getName());
            st.setInt(2, d.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
    
    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM DEPARTMENT WHERE ID = ?");
            
            st.setInt(1, id);
            
            int affectedRows = st.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Delete completed!");
            } else {
                throw new DbException("Error! No user found!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
    
    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM DEPARTMENT WHERE ID = ?");
            
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Department d = new Department(rs.getInt("Id"), rs.getString("Name"));
                return d;
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
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try
        {
            st = conn.prepareStatement("SELECT * FROM DEPARTMENT ORDER BY NAME");
            rs = st.executeQuery();
            
            List<Department> list = new ArrayList<>();
            
            while (rs.next())
            {
                Department d = new Department(rs.getInt("Id"), rs.getString("Name"));
                list.add(d);
            }
            return list;
            
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
}
