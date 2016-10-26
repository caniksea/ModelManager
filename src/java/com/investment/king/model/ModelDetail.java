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
public class ModelDetail {
    private int model_id;
    private List<ModelSecurity> model_securities;

    public ModelDetail(int model_id, List<ModelSecurity> model_securities) {
        this.model_id = model_id;
        this.model_securities = model_securities;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public List<ModelSecurity> getModel_securities() {
        return model_securities;
    }

    public void setModel_securities(List<ModelSecurity> model_securities) {
        this.model_securities = model_securities;
    }
}
