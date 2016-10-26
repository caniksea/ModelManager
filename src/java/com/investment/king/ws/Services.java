/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investment.king.ws;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.investment.king.controller.MMEngine;
import com.investment.king.model.AddSecurityResponse;
import com.investment.king.model.MMUserResponse;
import com.investment.king.model.ModelDetailResponse;
import com.investment.king.model.ModelResponse;
import com.investment.king.model.SecurityResponse;
import com.investment.king.model.UserRoleResponse;
import com.investment.king.model.UserStatusResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author caniksea
 */
@Path("")
public class Services {

    private final MMEngine engine = new MMEngine();
    private final Gson gson = new Gson();

    /**
     * This operation is responsible for login
     *
     * @return
     */

    @GET
    @Path("get_securities")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSecurities() {
        SecurityResponse sr = engine.getSecurities();
        if(sr == null){
            sr = new SecurityResponse("Application Error", "-99", null);
        }
        return gson.toJson(sr, SecurityResponse.class);
    }

    @GET
    @Path("get_security/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSecurity(@PathParam("id") String id) {
        AddSecurityResponse asr = engine.getSecurity(id);
        return gson.toJson(asr, AddSecurityResponse.class);
    }

    @GET
    @Path("delete_security/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteSecurity(@PathParam("id") String id) {
        AddSecurityResponse asr = engine.deleteSecurity(id);
        return gson.toJson(asr, AddSecurityResponse.class);
    }
    
    @GET
    @Path("get_roles")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRoles() {
        UserRoleResponse urr = engine.getRoles();
        if(urr == null){
            urr = new UserRoleResponse("Application Error", "-99", null);
        }
        return gson.toJson(urr, UserRoleResponse.class);
    }
    
    @GET
    @Path("get_all_status")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllStauts() {
        UserStatusResponse usr = engine.getAllStatus();
        if(usr == null){
            usr = new UserStatusResponse("Application Error", "-99", null);
        }
        return gson.toJson(usr, UserStatusResponse.class);
    }
    
    @GET
    @Path("get_status/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getStatus(@PathParam("id") String id) {
        UserStatusResponse usr = engine.getStatus(id);
        if(usr == null){
            usr = new UserStatusResponse("Application Error", "-99", null);
        }
        return gson.toJson(usr, UserStatusResponse.class);
    }
    
    @GET
    @Path("get_specific_users/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSpecificUsers(@PathParam("type") String userType) {
        MMUserResponse mmur = engine.getSpecificUsers(userType);
        if(mmur == null){
            mmur = new MMUserResponse("Application Error", "-99", null);
        }
        return gson.toJson(mmur, MMUserResponse.class);
    }

    @GET
    @Path("get_user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(@PathParam("id") String id) {
        MMUserResponse mmur = engine.getUserWithID(id);
        return gson.toJson(mmur, MMUserResponse.class);
    }
    
    @GET
    @Path("get_models")
    @Produces(MediaType.APPLICATION_JSON)
    public String getModels() {
        ModelResponse sr = engine.getModels();
        if(sr == null){
            sr = new ModelResponse("Application Error", "-99", null);
        }
        return gson.toJson(sr, ModelResponse.class);
    }
    
    @GET
    @Path("get_model/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getModel(@PathParam("id") String id) {
        ModelResponse sr = engine.getModel(id);
        if(sr == null){
            sr = new ModelResponse("Application Error", "-99", null);
        }
        return gson.toJson(sr, ModelResponse.class);
    }
    
    @GET
    @Path("get_securities_in_model/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSecurityInModel(@PathParam("id") String id) {
        ModelDetailResponse mdr = engine.getSecurityInModel(id);
        if(mdr == null){
            mdr = new ModelDetailResponse("Application Error", "-99", null);
        }
        return gson.toJson(mdr, ModelDetailResponse.class);
    }
    
    @GET
    @Path("remove_model_security/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String removeSecurityInModel(@PathParam("id") String id) {
        ModelDetailResponse mdr = engine.removeSecurityInModel(id);
        if(mdr == null){
            mdr = new ModelDetailResponse("Application Error", "-99", null);
        }
        return gson.toJson(mdr, ModelDetailResponse.class);
    }
    
    @GET
    @Path("delete_model/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteModel(@PathParam("id") String id) {
        ModelResponse sr = engine.deleteModel(id);
        if(sr == null){
            sr = new ModelResponse("Application Error", "-99", null);
        }
        return gson.toJson(sr, ModelResponse.class);
    }
    
//    @GET
//    @Path("delete_user/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String deleteUser(@PathParam("id") String id) {
//        MMUserResponse asr = engine.deleteUser(id);
//        return gson.toJson(asr, MMUserResponse.class);
//    }

    @POST
    @Path("add_security")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addSecurity(String request) {
        AddSecurityResponse asr = null;
        try {
            JsonElement element = new JsonParser().parse(request);
            if (element instanceof JsonNull) {
                asr = new AddSecurityResponse(null, "WARNING:: Data sent may be NULL!", "-99");
            } else {
                asr = engine.addSecurity(request);
            }
        } catch (JsonSyntaxException ex) {
            asr = new AddSecurityResponse(null, "User Error:: "+ ex.getMessage(), "99");
        }
        return gson.toJson(asr, AddSecurityResponse.class);
    }

    @POST
    @Path("update_security")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateSecurity(String request) {
        AddSecurityResponse asr = null;
        try {
            JsonElement element = new JsonParser().parse(request);
            if (element instanceof JsonNull) {
                asr = new AddSecurityResponse(null, "WARNING:: Data sent may be NULL!", "-99");
            } else {
                asr = engine.updateSecurity(request);
            }
        } catch (JsonSyntaxException ex) {
            asr = new AddSecurityResponse(null, "User Error:: "+ ex.getMessage(), "99");
        }
        return gson.toJson(asr, AddSecurityResponse.class);
    }

    @POST
    @Path("add_user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addUser(String request) {
        MMUserResponse mmur = null;
        try {
            JsonElement element = new JsonParser().parse(request);
            if (element instanceof JsonNull) {
                mmur = new MMUserResponse("WARNING:: Data sent may be NULL!", "-99", null);
            } else {
                mmur = engine.addUser(request);
            }
        } catch (JsonSyntaxException ex) {
            mmur = new MMUserResponse("User Error:: "+ ex.getMessage(), "99", null);
        }
        return gson.toJson(mmur, MMUserResponse.class);
    }

    @POST
    @Path("update_user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateUser(String request) {
        MMUserResponse mmur = null;
        try {
            JsonElement element = new JsonParser().parse(request);
            if (element instanceof JsonNull) {
                mmur = new MMUserResponse("WARNING:: Data sent may be NULL!", "-99", null);
            } else {
                mmur = engine.updateUser(request);
            }
        } catch (JsonSyntaxException ex) {
            mmur = new MMUserResponse("User Error:: "+ ex.getMessage(), "99", null);
        }
        return gson.toJson(mmur, MMUserResponse.class);
    }

    @POST
    @Path("add_model")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addModel(String request) {
        System.out.println("SEE:: "+request);
        ModelResponse mr = null;
        try {
            JsonElement element = new JsonParser().parse(request);
            if (element instanceof JsonNull) {
                mr = new ModelResponse("WARNING:: Data sent may be NULL!", "-99", null);
            } else {
                mr = engine.addModel(request);
            }
        } catch (JsonSyntaxException ex) {
            mr = new ModelResponse("User Error:: "+ ex.getMessage(), "99", null);
        }
        return gson.toJson(mr, ModelResponse.class);
    }

    @POST
    @Path("update_model")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateModel(String request) {
        ModelResponse mr = null;
        try {
            JsonElement element = new JsonParser().parse(request);
            if (element instanceof JsonNull) {
                mr = new ModelResponse("WARNING:: Data sent may be NULL!", "-99", null);
            } else {
                mr = engine.updateModel(request);
            }
        } catch (JsonSyntaxException ex) {
            mr = new ModelResponse("User Error:: "+ ex.getMessage(), "99", null);
        }
        return gson.toJson(mr, ModelResponse.class);
    }

    @POST
    @Path("add_security_to_model")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addSecurity2Model(String request) {
        ModelDetailResponse mdr = null;
        try {
            JsonElement element = new JsonParser().parse(request);
            if (element instanceof JsonNull) {
                mdr = new ModelDetailResponse("WARNING:: Data sent may be NULL!", "-99", null);
            } else {
                mdr = engine.addSecurity2Model(request);
            }
        } catch (JsonSyntaxException ex) {
            mdr = new ModelDetailResponse("User Error:: "+ ex.getMessage(), "99", null);
        }
        return gson.toJson(mdr, ModelDetailResponse.class);
    }
}
