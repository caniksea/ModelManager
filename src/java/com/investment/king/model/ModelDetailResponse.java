/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investment.king.model;

/**
 *
 * @author @caniksea
 */
public class ModelDetailResponse extends GenericResponse {
    private ModelDetail model_detail;

    public ModelDetailResponse(String response, String response_code, ModelDetail model_detail) {
        super(response, response_code);
        this.model_detail = model_detail;
    }

    public ModelDetail getModel_detail() {
        return model_detail;
    }

    public void setModel_detail(ModelDetail model_detail) {
        this.model_detail = model_detail;
    }
}
