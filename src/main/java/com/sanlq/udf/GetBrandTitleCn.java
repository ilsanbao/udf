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
public class GetBrandTitleCn extends UDF {

    private static final BrandModel BM = new BrandModel();

    public String evaluate(String orgModel) {
        if (orgModel == null) {
            return null;
        }
        ModelBean model = new ModelBean(orgModel);
        try {
            model = BM.getModel(orgModel);
            return model.getBrandTitleCn();
        } catch (Exception e) {
            e.printStackTrace();
            return model.getBrandTitleCn();
        }
    }

    public static void main(String[] args) {
        GetBrandTitleCn city = new GetBrandTitleCn();
        System.out.println("city: " + city.evaluate("114.229.108.98"));
        System.out.println("city: " + city.evaluate("180.106.15.16, 100.97.142.93"));
    }
}
