/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanlq.udfa;

import com.sanlq.common.Data;
import com.sanlq.common.IPIP;
import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.hadoop.hive.ql.exec.UDAFEvaluator;
import org.apache.hadoop.io.Text;

/**
 *
 * @author zhaochunlei
 */
public class GetCountry extends UDAF {

    private static final IPIP ipdb = new IPIP();

    public static class GetCountryUDAFEvaluator implements UDAFEvaluator {

        private Text result;

        @Override
        public void init() {
            result = null;
        }

        public boolean iterate(Text value) {
            if (value == null) {
                return true;
            }
            if (result == null) {
                if (IPIP.isboolIp(value.toString())) {
                    Data ipdata = ipdb.getData(value.toString());
                    result = new Text(ipdata.getCountry());
                }
            } else {
//                result.set(Math.max(result.get(), value.get()));
            }
            return true;
        }

        public Text terminatePartial() {
            return result;
        }

        public boolean merge(Text other) {
            return iterate(other);
        }

        public Text terminate() {
            return result;
        }

    }
}
