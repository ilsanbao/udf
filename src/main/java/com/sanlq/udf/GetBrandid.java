/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanlq.udf;

import com.sanlq.common.BrandModel;
import com.sanlq.common.bean.ModelBean;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 *
 * @author zhaochunlei
 */
public class GetBrandid extends UDF {

    private static final BrandModel BM = new BrandModel();

    public Integer evaluate(String orgModel) {
        if (orgModel == null) {
            return null;
        }
        ModelBean model = new ModelBean(orgModel);
        try {
            model = BM.getModel(orgModel);
            return model.getBrandId();
        } catch (Exception e) {
            e.printStackTrace();
            return model.getBrandId();
        }
    }

    public static void main(String[] args) {
        GetBrandid city = new GetBrandid();
        System.out.println("city: " + city.evaluate("114.229.108.98"));
        System.out.println("city: " + city.evaluate("180.106.15.16, 100.97.142.93"));
    }
}
