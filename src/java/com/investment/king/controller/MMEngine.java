/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investment.king.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.investment.king.db.DAO;
import com.investment.king.model.AddSecurityResponse;
import com.investment.king.model.MMUser;
import com.investment.king.model.MMUserResponse;
import com.investment.king.model.Model;
import com.investment.king.model.ModelDetailResponse;
import com.investment.king.model.ModelResponse;
import com.investment.king.model.Security;
import com.investment.king.model.SecurityResponse;
import com.investment.king.model.User;
import com.investment.king.model.UserRoleResponse;
import com.investment.king.model.UserStatusResponse;
import com.investment.king.util.PasswordSecurity;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author @caniksea
 */
public class MMEngine {

    private final DAO dao = new DAO();
    private final DecimalFormat df = new DecimalFormat("#0.00");

//    public static void main(String[] args) {
//        try {
//            String securePass = new PasswordSecurity().hashPassword("password", "john");
//            System.out.println("SECURE:: "+ securePass);
//        } catch (Exception ex) {
//            Logger.getLogger(MMEngine.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public User authenticateUser(String username, String password) {
        System.out.println(username + " - " + password);
        User u = getUserWithUsername(username);
        if (u != null) {
            if (u.getResponse_code() == 0) {
                boolean isUserActive = checkUserStatus(u);
                if (isUserActive) {
                    boolean isUserValid = validateUser(u, password);
//                    if (u.getUsername().trim().equalsIgnoreCase("john")) {
//                        isUserValid = u.getPassword().trim().equalsIgnoreCase(password);
//                    }
                    if (isUserValid) {
                        u.setResponse("User authenticated");
                    } else {
                        u.setResponse("Authentication failed!");
                        u.setResponse_code(-1);
                    }
                } else {
                    u.setResponse("User Inactive!");
                    u.setResponse_code(-1);
                }
            }
        } else {
            u = new User("Application Error!", username, 99, -99);
        }
        return u;
    }

    private User getUserWithUsername(String username) {
        return dao.getUserWithUsername(username);
    }

    private boolean validateUser(User u, String password) {
        String securePass = null;
        boolean secure = false;
        try {
            securePass = new PasswordSecurity().hashPassword(password, u.getUsername());
        } catch (Exception ex) {
            Logger.getLogger(MMEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (securePass != null) {
            secure = u.getPassword().trim().equalsIgnoreCase(securePass);
        }
        return secure;
    }

    public SecurityResponse getSecurities() {
        return fetchSecurities();
    }

    private SecurityResponse fetchSecurities() {
        return dao.fetchSecurities();
    }

    public AddSecurityResponse addSecurity(String request) {
        JsonObject object = (JsonObject) new JsonParser().parse(request);
        Security s = null;
        String response = "Security Added!", response_code = "00";
        String name = (object.get("security_name") == null || object.get("security_name").getAsString() == null) ? ""
                : object.get("security_name").getAsString();
        String unitPrice = (object.get("security_up") == null || object.get("security_up").getAsString() == null) ? ""
                : object.get("security_up").getAsString();
        String userID = (object.get("as_user_id") == null || object.get("as_user_id").getAsString() == null) ? ""
                : object.get("as_user_id").getAsString();
        try {
            unitPrice = df.format(Double.parseDouble(unitPrice));
            s = dao.addSecurity(userID, name, unitPrice);
            if (s == null) {
                response = "Application Error!";
                response_code = "-99";
            }
        } catch (NumberFormatException ex) {
            response = "User Error: " + ex.getMessage();
            response_code = "-99";
        }
        return new AddSecurityResponse(s, response, response_code);
    }

    public AddSecurityResponse getSecurity(String id) {
        return getSecurityDetails(id);
    }

    private AddSecurityResponse getSecurityDetails(String id) {
        return dao.getSecurity(id);
    }

    public AddSecurityResponse updateSecurity(String request) {
        JsonObject object = (JsonObject) new JsonParser().parse(request);
        Security s = null;
        String response = "Successfully Updated", response_code = "00";
        String name = (object.get("es_name") == null || object.get("es_name").getAsString() == null) ? ""
                : object.get("es_name").getAsString();
        String unitPrice = (object.get("es_unitPrice") == null || object.get("es_unitPrice").getAsString() == null) ? ""
                : object.get("es_unitPrice").getAsString();
        String securityID = (object.get("es_id") == null || object.get("es_id").getAsString() == null) ? ""
                : object.get("es_id").getAsString();
        try {
            unitPrice = df.format(Double.parseDouble(unitPrice));
            s = dao.updateSecurity(securityID, name, unitPrice);
            if (s == null) {
                response = "Application Error!";
                response_code = "-99";
            }
        } catch (NumberFormatException ex) {
            response = "User Error: " + ex.getMessage();
            response_code = "-99";
        }
        return new AddSecurityResponse(s, response, response_code);
    }

    public AddSecurityResponse deleteSecurity(String id) {
        return dao.deleteSecurity(id);
    }

    public UserRoleResponse getRoles() {
        return dao.getRoles();
    }

    public MMUserResponse addUser(String request) {
        JsonObject object = (JsonObject) new JsonParser().parse(request);
        List<MMUser> users = null;
        String response = "User Added", response_code = "00";
        String name = (object.get("au_name") == null || object.get("au_name").getAsString() == null) ? ""
                : object.get("au_name").getAsString();
        String username = (object.get("au_uname") == null || object.get("au_uname").getAsString() == null) ? ""
                : object.get("au_uname").getAsString();
        String password = (object.get("au_password") == null || object.get("au_password").getAsString() == null) ? ""
                : object.get("au_password").getAsString();
        String role = (object.get("au_role") == null || object.get("au_role").getAsString() == null) ? ""
                : object.get("au_role").getAsString();
        String status = (object.get("au_status") == null || object.get("au_status").getAsString() == null) ? ""
                : object.get("au_status").getAsString();
        try {
            String securePass = new PasswordSecurity().hashPassword(password, username);
            MMUser user = dao.addUser(username, securePass, name, role, status);
            if (user == null) {
                response = "Application Error!";
                response_code = "-99";
            } else {
                users = new ArrayList<>();
                users.add(user);
            }
        } catch (Exception ex) {
            Logger.getLogger(MMEngine.class.getName()).log(Level.SEVERE, null, ex);
            response = "User Error: " + ex.getMessage();
            response_code = "-99";
        }
        return new MMUserResponse(response, response_code, users);
    }

    public MMUserResponse getUserWithID(String id) {
        return dao.getUserWithID(id);
    }

    public MMUserResponse updateUser(String request) {
        JsonObject object = (JsonObject) new JsonParser().parse(request);
        List<MMUser> users = null;
        String response = "User Updated", response_code = "00";
        String name = (object.get("eu_name") == null || object.get("eu_name").getAsString() == null) ? ""
                : object.get("eu_name").getAsString();
        String userID = (object.get("eu_user_id") == null || object.get("eu_user_id").getAsString() == null) ? ""
                : object.get("eu_user_id").getAsString();
        String role = (object.get("eu_role") == null || object.get("eu_role").getAsString() == null) ? ""
                : object.get("eu_role").getAsString();
        String status = (object.get("eu_status") == null || object.get("eu_status").getAsString() == null) ? ""
                : object.get("eu_status").getAsString();
        MMUser user = dao.updateUser(name, role, userID, status);
        if (user == null) {
            response = "Application Error!";
            response_code = "-99";
        } else {
            users = new ArrayList<>();
            users.add(user);
        }
        return new MMUserResponse(response, response_code, users);
    }

//    public MMUserResponse deleteUser(String id) {
//        return dao.deleteUser(id);
//    }
    public UserStatusResponse getAllStatus() {
        return dao.getAllStatus();
    }

    public UserStatusResponse getStatus(String id) {
        return dao.getStatus(id);
    }

    public MMUserResponse getSpecificUsers(String userType) {
        return dao.getSpecificUsers(userType);
    }

    private boolean checkUserStatus(User u) {
        return u.getStatus_id() == 1;
    }

    public ModelResponse getModels() {
        return dao.getModels();
    }

    public ModelResponse addModel(String request) {
        JsonObject object = (JsonObject) new JsonParser().parse(request);
        List<Model> models = null;
        String response = "Model Added!", response_code = "00";
        String name = (object.get("am_name") == null || object.get("am_name").getAsString() == null) ? ""
                : object.get("am_name").getAsString();
        String desc = (object.get("am_desc") == null || object.get("am_desc").getAsString() == null) ? ""
                : object.get("am_desc").getAsString();
        String userID = (object.get("am_user_id") == null || object.get("am_user_id").getAsString() == null) ? ""
                : object.get("am_user_id").getAsString();

        models = dao.addModel(userID, name, desc);
        if (models == null) {
            response = "Application Error!";
            response_code = "-99";
        }
        return new ModelResponse(response, response_code, models);
    }

    public ModelResponse getModel(String id) {
        return dao.getModel(id);
    }

    public ModelResponse updateModel(String request) {
        JsonObject object = (JsonObject) new JsonParser().parse(request);
        List<Model> models = null;
        String response = "Successfully Updated", response_code = "00";
        String name = (object.get("em_name") == null || object.get("em_name").getAsString() == null) ? ""
                : object.get("em_name").getAsString();
        String description = (object.get("em_description") == null || object.get("em_description").getAsString() == null) ? ""
                : object.get("em_description").getAsString();
        String modelID = (object.get("em_model_id") == null || object.get("em_model_id").getAsString() == null) ? ""
                : object.get("em_model_id").getAsString();
        //        String security = (object.get("am_security") == null || object.get("am_security").getAsString() == null) ? ""
//                : object.get("am_security").getAsString();
//        String percentage = (object.get("am_percentage") == null || object.get("am_percentage").getAsString() == null) ? ""
//                : object.get("am_percentage").getAsString();
        models = dao.updateModel(modelID, name, description);
        if (models == null) {
            response = "Application Error!";
            response_code = "-99";
        }
        return new ModelResponse(response, response_code, models);
    }

    public ModelDetailResponse getSecurityInModel(String id) {
        return dao.getSecurityInModel(id);
    }

    public ModelDetailResponse addSecurity2Model(String request) {
        JsonObject object = (JsonObject) new JsonParser().parse(request);
        ModelDetailResponse mdr = null;
        String security = (object.get("s2m_security") == null || object.get("s2m_security").getAsString() == null) ? ""
                : object.get("s2m_security").getAsString();
        String percentage = (object.get("s2m_percentage") == null || object.get("s2m_percentage").getAsString() == null) ? ""
                : object.get("s2m_percentage").getAsString();
        String modelID = (object.get("s2m_model_id") == null || object.get("s2m_model_id").getAsString() == null) ? ""
                : object.get("s2m_model_id").getAsString();
        try {
            double percent = Double.parseDouble(percentage);
            mdr = dao.addSecurity2Model(modelID, security, percent);
        } catch (NumberFormatException ex) {
            mdr = new ModelDetailResponse("User Error:: "+ ex.getMessage(), "-99", null);
        }

        return mdr;
    }

    public ModelDetailResponse removeSecurityInModel(String id) {
        return dao.removeSecurityInModel(id);
    }

    public ModelResponse deleteModel(String id) {
        return dao.deleteModel(id);
    }

}
