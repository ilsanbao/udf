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
public class GetModel extends UDF {

    private static final BrandModel BM = new BrandModel();

    public String evaluate(String orgModel, Integer type) {
        if (orgModel == null) {
            return null;
        }
        ModelBean model = new ModelBean();
        try {
            model = BM.getModel(orgModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (type) {
            case 1:
                return model.getBrandTitleCn();
            case 2:
                return model.getBrandTitleEn();
            case 3:
                return model.getTitleCn();
            case 4:
                return model.getTitleEn();
            default:
                return model.getTitleOrg();
        }
    }
}
