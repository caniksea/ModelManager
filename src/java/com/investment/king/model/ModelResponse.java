/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investment.king.model;

import java.util.List;

/**
 *
 * @author @caniksea
 */
public class ModelResponse extends GenericResponse{
    private List<Model> models;

    public ModelResponse(String response, String response_code, List<Model> models) {
        super(response, response_code);
        this.models = models;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }
}
