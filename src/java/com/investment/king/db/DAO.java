/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investment.king.db;

import com.investment.king.model.AddSecurityResponse;
import com.investment.king.model.MMUser;
import com.investment.king.model.MMUserResponse;
import com.investment.king.model.Model;
import com.investment.king.model.ModelDetail;
import com.investment.king.model.ModelSecurity;
import com.investment.king.model.ModelDetailResponse;
import com.investment.king.model.ModelResponse;
import com.investment.king.model.Security;
import com.investment.king.model.SecurityResponse;
import com.investment.king.model.User;
import com.investment.king.model.UserRole;
import com.investment.king.model.UserRoleResponse;
import com.investment.king.model.UserStatus;
import com.investment.king.model.UserStatusResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author @caniksea
 */
public class DAO {

    public User getUserWithUsername(String username) {
        User user = null;
        Connection con = DBConnection.getMySQLConnection();
        if (con != null) {
            String selectSQL = "SELECT * FROM user WHERE username = ?";
            System.out.println(selectSQL + " - " + username);
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                ps = con.prepareStatement(selectSQL);
                ps.setString(1, username.trim());
                rs = ps.executeQuery();
                if (rs.next()) {
                    int user_id = rs.getInt("user_id");
                    int role = rs.getInt("role_role_id");
                    int statusID = rs.getInt("status_id");
                    String name = rs.getString("name");
                    String password = rs.getString("password");
                    user = new User("User found", 0, username, password, name, role, user_id, statusID);
                    System.out.println("User found");
                } else {
                    user = new User("User not found!", username, -1, -99);
                }
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection(con, rs, ps);
            }
        }

        return user;
    }

    private void closeConnection(Connection con, ResultSet rs, PreparedStatement ps) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            con.close();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SecurityResponse fetchSecurities() {
        List<Security> securities = new ArrayList<>();
        SecurityResponse sr = null;
        Connection con = DBConnection.getMySQLConnection();
        if (con != null) {
            String selectSQL = "SELECT * FROM security";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                ps = con.prepareStatement(selectSQL);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("security_id");
                    String name = rs.getString("name");
                    double unitPrice = rs.getDouble("unit_price");
                    Security s = new Security(id, name, unitPrice);
                    securities.add(s);
                }
                String response = "Approved/Successful";
                String responseCode = "00";
                if (securities.isEmpty()) {
                    response = "No Security!";
                    responseCode = "NIL";
                }
                sr = new SecurityResponse(response, responseCode, securities);
            } catch (SQLException ex) {
                sr = new SecurityResponse("Application Error", "-99", securities);
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection(con, rs, ps);
            }
        }

        return sr;
    }

    public Security addSecurity(String userID, String name, String unitPrice) {
        Security s = null;
        Connection con = DBConnection.getMySQLConnection();
        if (con != null) {
            String insertSQL = "INSERT INTO security (name, unit_price, created_by) VALUES (?, ?, ?)";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                double unitPriceD = Double.parseDouble(unitPrice);
                ps = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setDouble(2, unitPriceD);
                ps.setInt(3, Integer.parseInt(userID));
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int securityID = rs.getInt(1);
                    s = new Security(securityID, name, unitPriceD);
                }
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    private Security getSingleSecurity(String id) {
        Connection con = DBConnection.getMySQLConnection();
        Security s = null;
        if (con != null) {
            String selectSQL = "SELECT * FROM security WHERE security_id = ?";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                int sID = Integer.parseInt(id);
                ps = con.prepareStatement(selectSQL);
                ps.setInt(1, sID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    double unitPrice = rs.getDouble("unit_price");
                    s = new Security(sID, name, unitPrice);
                }
            } catch (SQLException ex) {
                s = new Security(-1, "");
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public AddSecurityResponse getSecurity(String id) {
        String response = "Found";
        String responseCode = "00";
        Security s = getSingleSecurity(id);
        if (s == null) {
            response = "Not Found";
            responseCode = "00";
        } else if (s.getId() == -1) {
            response = "Application Error";
            responseCode = "-99";
        }
        return new AddSecurityResponse(s, response, responseCode);
    }

    public Security updateSecurity(String securityID, String name, String unitPrice) {
        Security s = null;
        Connection con = DBConnection.getMySQLConnection();
        if (con != null) {
            String updateSQL = "UPDATE security SET name = ?, unit_price = ? WHERE security_id = ?";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                double unitPriceD = Double.parseDouble(unitPrice);
                int sID = Integer.parseInt(securityID);
                ps = con.prepareStatement(updateSQL);
                ps.setString(1, name);
                ps.setDouble(2, unitPriceD);
                ps.setInt(3, sID);
                ps.executeUpdate();
                s = new Security(sID, name, unitPriceD);
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public AddSecurityResponse deleteSecurity(String id) {
        Connection con = DBConnection.getMySQLConnection();
        AddSecurityResponse asr = new AddSecurityResponse(null, "Security Deleted", "00");
        if (con != null) {
            String deleteSQL = "DELETE FROM security WHERE security_id = ?";
            PreparedStatement ps = null;
            try {
                ps = con.prepareStatement(deleteSQL);
                ps.setInt(1, Integer.parseInt(id));
                ps.executeUpdate();
            } catch (SQLException ex) {
                asr.setResponse("Application Error:: " + ex.getMessage());
                asr.setResponse_code("-99");
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return asr;
    }

    public UserRoleResponse getRoles() {
        UserRoleResponse ur = null;
        List<UserRole> roles = new ArrayList<>();
        Connection con = DBConnection.getMySQLConnection();
        if (con != null) {
            String selectSQL = "SELECT * FROM role";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                ps = con.prepareStatement(selectSQL);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("role_id");
                    String desc = rs.getString("role_description");
                    UserRole role = new UserRole(id, desc);
                    roles.add(role);
                }
                String response = "Approved/Successful";
                String responseCode = "00";
                if (roles.isEmpty()) {
                    response = "No User Roles!";
                    responseCode = "NIL";
                }
                ur = new UserRoleResponse(response, responseCode, roles);
            } catch (SQLException ex) {
                ur = new UserRoleResponse("Application Error", "-99", roles);
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection(con, rs, ps);
            }
        }
        return ur;
    }

    public MMUser addUser(String username, String securePass, String name, String role, String status) {
        MMUser user = null;
        Connection con = DBConnection.getMySQLConnection();
        if (con != null) {
            String insertSQL = "INSERT INTO user (name, password, role_role_id, username, status_id) VALUES (?, ?, ?, ?, ?)";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                int roleID = Integer.parseInt(role);
                int statusID = Integer.parseInt(status);
                ps = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setString(2, securePass);
                ps.setInt(3, roleID);
                ps.setString(4, username);
                ps.setInt(5, statusID);
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int userID = rs.getInt(1);
                    user = new MMUser(userID, roleID, name, username, statusID, null, null, null);
                }
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return user;
    }

    public MMUserResponse getUserWithID(String id) {
        Connection con = DBConnection.getMySQLConnection();
        MMUserResponse mmur = null;
        String response = "Not Found";
        String responseCode = "00";
        List<MMUser> users = null;
        if (con != null) {
            String selectSQL = "SELECT * FROM user WHERE user_id = ?";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                int userID = Integer.parseInt(id);
                ps = con.prepareStatement(selectSQL);
                ps.setInt(1, userID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    int roleID = rs.getInt("role_role_id");
                    String username = rs.getString("username");
                    int statusID = rs.getInt("status_id");
                    String initialPass = rs.getString("initial_pass");
                    String changedPass = rs.getString("user_alt_pass");
                    MMUser user = new MMUser(userID, roleID, name, username, statusID, null, initialPass, changedPass);
                    response = "Found";
                    responseCode = "00";
                    users = new ArrayList<>();
                    users.add(user);
                }
                mmur = new MMUserResponse(response, responseCode, users);
            } catch (SQLException ex) {
                mmur = new MMUserResponse("Application Error", "-99", users);
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection(con, rs, ps);
            }
        }
        return mmur;
    }

    public MMUser updateUser(String name, String role, String userID, String status) {
        MMUser user = null;
        Connection con = DBConnection.getMySQLConnection();
        if (con != null) {
            String updateSQL = "UPDATE user SET name = ?, role_role_id = ?, status_id = ? WHERE user_id = ?";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                int uID = Integer.parseInt(userID);
                int roleID = Integer.parseInt(role);
                int statusID = Integer.parseInt(status);
                ps = con.prepareStatement(updateSQL);
                ps.setString(1, name);
                ps.setInt(2, roleID);
                ps.setInt(3, statusID);
                ps.setInt(4, uID);
                ps.executeUpdate();
                user = new MMUser(uID, roleID, name, null, statusID, null, null, null);
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return user;
    }

//    public MMUserResponse deleteUser(String id) {
//        Connection con = DBConnection.getMySQLConnection();
//        MMUserResponse asr = new MMUserResponse("User Deleted", "00", null);
//        if(con != null){
//            String deleteSQL = "DELETE FROM user WHERE security_id = ?";
//            PreparedStatement ps = null;
//            try {
//                ps = con.prepareStatement(deleteSQL);
//                ps.setInt(1, Integer.parseInt(id));
//                ps.executeUpdate();
//            } catch (SQLException ex) {
//                asr.setResponse("Application Error:: "+ex.getMessage());
//                asr.setResponse_code("-99");
//                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return asr;
//    }
    public UserStatusResponse getAllStatus() {
        UserStatusResponse usr = null;
        List<UserStatus> allStatus = new ArrayList<>();
        Connection con = DBConnection.getMySQLConnection();
        if (con != null) {
            String selectSQL = "SELECT * FROM user_status";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                ps = con.prepareStatement(selectSQL);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("status_id");
                    String desc = rs.getString("description");
                    UserStatus status = new UserStatus(id, desc);
                    allStatus.add(status);
                }
                String response = "Approved/Successful";
                String responseCode = "00";
                if (allStatus.isEmpty()) {
                    response = "No User Status!";
                    responseCode = "NIL";
                }
                usr = new UserStatusResponse(response, responseCode, allStatus);
            } catch (SQLException ex) {
                usr = new UserStatusResponse("Application Error", "-99", allStatus);
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection(con, rs, ps);
            }
        }
        return usr;
    }

    public UserStatusResponse getStatus(String id) {
        UserStatusResponse usr = null;
        List<UserStatus> allStatus = new ArrayList<>();
        Connection con = DBConnection.getMySQLConnection();
        if (con != null) {
            String selectSQL = "SELECT description FROM user_status WHERE status_id = ?";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                ps = con.prepareStatement(selectSQL);
                int statusID = Integer.parseInt(id);
                ps.setInt(1, statusID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String desc = rs.getString("description");
                    UserStatus status = new UserStatus(statusID, desc);
                    allStatus.add(status);
                }
                String response = "Approved/Successful";
                String responseCode = "00";
                if (allStatus.isEmpty()) {
                    response = "Status does not exit!";
                    responseCode = "NIL";
                }
                usr = new UserStatusResponse(response, responseCode, allStatus);
            } catch (SQLException ex) {
                usr = new UserStatusResponse("Application Error", "-99", allStatus);
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection(con, rs, ps);
            }
        }
        return usr;
    }

    public MMUserResponse getSpecificUsers(String userType) {
        MMUserResponse mmur = null;
        List<MMUser> users = new ArrayList<>();
        int user_type = userType.equals("Client") ? 3 : 2;
        Connection con = DBConnection.getMySQLConnection();
        if (con != null) {
            String selectSQL = "SELECT * FROM user WHERE role_role_id = ?";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                ps = con.prepareStatement(selectSQL);
                ps.setInt(1, user_type);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int userID = rs.getInt("user_id");
                    String name = rs.getString("name");
                    int roleID = rs.getInt("role_role_id");
                    String username = rs.getString("username");
                    int statusID = rs.getInt("status_id");
                    MMUser user = new MMUser(userID, roleID, name, username, statusID, null, null, null);
                    users.add(user);
                }
                String response = "Approved/Successful";
                String responseCode = "00";
                if (users.isEmpty()) {
                    response = "No Users!";
                    responseCode = "NIL";
                }
                mmur = new MMUserResponse(response, responseCode, users);
            } catch (SQLException ex) {
                mmur = new MMUserResponse("Application Error", "-99", users);
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection(con, rs, ps);
            }
        }
        return mmur;
    }

    public ModelResponse getModels() {
        List<Model> models = null;
        ModelResponse mr = null;
        Connection con = DBConnection.getMySQLConnection();
        if (con != null) {
            String selectSQL = "SELECT * FROM model";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                ps = con.prepareStatement(selectSQL);
                rs = ps.executeQuery();
                models = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("model_id");
                    int creator = rs.getInt("created_by");
                    String name = rs.getString("name");
                    String desc = rs.getString("description");
                    Model model = new Model(id, creator, name, desc);
                    models.add(model);
                }
                String response = "Approved/Successful";
                String responseCode = "00";
                if (models.isEmpty()) {
                    response = "No Model!";
                    responseCode = "NIL";
                }
                mr = new ModelResponse(response, responseCode, models);
            } catch (SQLException ex) {
                mr = new ModelResponse("Application Error", "-99", models);
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection(con, rs, ps);
            }
        }
        return mr;
    }

    public List<Model> addModel(String userID, String name, String desc) {
        List<Model> models = null;
        Connection con = DBConnection.getMySQLConnection();
        if (con != null) {
            String insertSQL = "INSERT INTO model (name, description, created_by) VALUES (?, ?, ?)";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                int uID = Integer.parseInt(userID);
                ps = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setString(2, desc);
                ps.setInt(3, uID);
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int modelID = rs.getInt(1);
                    models = new ArrayList<>();
                    Model m = new Model(modelID, uID, name, desc);
                    models.add(m);                  
                }
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return models;
    }

    public ModelResponse getModel(String id) {
        Connection con = DBConnection.getMySQLConnection();
        ModelResponse mr = null;
        List<Model> models = null;
        String response = "Not Found";
        String responseCode = "00";
        if (con != null) {
            String selectSQL = "SELECT * FROM model WHERE model_id = ?";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                int mID = Integer.parseInt(id);
                ps = con.prepareStatement(selectSQL);
                ps.setInt(1, mID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    String desc = rs.getString("description");
                    int creator = rs.getInt("created_by");
                    Model m = new Model(mID, creator, name, desc);
                    models = new ArrayList<>();
                    models.add(m);
                    response = "Found";
                    responseCode = "00";
                }
                mr = new ModelResponse(response, responseCode, models);
            } catch (SQLException ex) {
                mr = new ModelResponse("Application Error", "-99", models);
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection(con, rs, ps);
            }
        }
        return mr;
    }

    public List<Model> updateModel(String modelID, String name, String description) {
        List<Model> models = null;
        Connection con = DBConnection.getMySQLConnection();
        if (con != null) {
            String updateSQL = "UPDATE model SET name = ?, description = ? WHERE model_id = ?";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                int mID = Integer.parseInt(modelID);
                ps = con.prepareStatement(updateSQL);
                ps.setString(1, name);
                ps.setString(2, description);
                ps.setInt(3, mID);
                ps.executeUpdate();
                models = new ArrayList<>();
                models.add(new Model(mID, -1, name, description));
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return models;
    }

    public ModelDetailResponse getSecurityInModel(String id) {
        Connection con = DBConnection.getMySQLConnection();
        String response = "Security found!";
        String response_code = "00";
        ModelDetail md = null;
        if (con != null) {
            String selectSQL = "SELECT * FROM model_details WHERE model_id = ?";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                int modelID = Integer.parseInt(id);
                ps = con.prepareStatement(selectSQL);
                ps.setInt(1, modelID);
                rs = ps.executeQuery();
                List<ModelSecurity> modelSecurities = new ArrayList<>();
                while (rs.next()) {
                    int securityID = rs.getInt("security_id");
                    double percentage = rs.getDouble("percentage");
                    Security s = getSingleSecurity(String.valueOf(securityID));
                    ModelSecurity ms = new ModelSecurity(s, percentage);
                    modelSecurities.add(ms);
                }
                if (modelSecurities.isEmpty()) {
                    response = "No securities found!";
                } else if (modelSecurities.size() > 1) {
                    response = "Securities found!";
                }
                md = new ModelDetail(modelID, modelSecurities);
            } catch (SQLException ex) {
                response = "Application Error:: " + ex.getMessage();
                response_code = "-99";
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new ModelDetailResponse(response, response_code, md);
    }

    public ModelDetailResponse addSecurity2Model(String modelID, String security, double percent) {
        Connection con = DBConnection.getMySQLConnection();
        ModelDetail md = null;
        String response = "Security Added";
        String response_code = "00";
        if(con != null){
            String inserSQL = "INSERT INTO model_details (model_id, security_id, percentage) VALUES (?, ?, ?)";
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                int mID = Integer.parseInt(modelID);
                int sID = Integer.parseInt(security);
                ps = con.prepareStatement(inserSQL);
                ps.setInt(1, mID);
                ps.setInt(2, sID);
                ps.setDouble(3, percent);
                ps.executeUpdate();
                Security s = getSingleSecurity(security);
                ModelSecurity ms = new ModelSecurity(s, percent);
                List<ModelSecurity> modelSecurities = new ArrayList<>();
                modelSecurities.add(ms);
                md = new ModelDetail(mID, modelSecurities);
            } catch (SQLException ex) {
                response = "Application Error:: "+ex.getMessage();
                response_code = "-99";
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new ModelDetailResponse(response, response_code, md);
    }

    public ModelDetailResponse removeSecurityInModel(String id) {
        String response = "Security removed!";
        String response_code = "00";
        Connection con = DBConnection.getMySQLConnection();
        if(con != null){
            String deleteSQL = "DELETE FROM model_details WHERE security_id = ?";
            PreparedStatement ps = null;
            try {
                ps = con.prepareStatement(deleteSQL);
                ps.setInt(1, Integer.parseInt(id));
                ps.executeUpdate();
            } catch (SQLException ex) {
                response = "Application Error:: "+ex.getMessage();
                response_code = "-99";
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new ModelDetailResponse(response, response_code, null);
    }

    public ModelResponse deleteModel(String id) {
        ModelResponse mr = null;
        Connection con = DBConnection.getMySQLConnection();
        if (con != null) {
            String deleteSQL = "DELETE FROM model WHERE model_id = ?";
            PreparedStatement ps = null;
            try {
                int modelID = Integer.parseInt(id);
                ps = con.prepareStatement(deleteSQL);
                ps.setInt(1, modelID);
                ps.executeUpdate();
                Model m = new Model(modelID);
                List<Model> models = new ArrayList<>();
                models.add(m);
                mr = new ModelResponse("Model deleted!", "00", models);
            } catch (SQLException ex) {
                mr = new ModelResponse("Application Error:: Model NOT deleted - "+ex.getMessage(), "-99", null);
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection(con, null, ps);
            }
        }
        return mr;
    }

}
