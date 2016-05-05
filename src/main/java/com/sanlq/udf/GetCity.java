/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanlq.udf;

import com.sanlq.common.Data;
import com.sanlq.common.IPIP;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 *
 * @author zhaochunlei
 */
public class GetCity extends UDF {

    private static final IPIP ipdb = IPIP.init();

    public Text evaluate(String ipaddress) {
        if (ipaddress == null) {
            return null;
        }
        Data ipdata = new Data();
        try {
            if (IPIP.isboolIp(ipaddress)) {
                ipdata = ipdb.getData(ipaddress);
                return new Text(ipdata.getCity());
            } else {
                String[] ips = ipaddress.split(",");
                for (String ip : ips) {
                    if (ip != null && !ip.isEmpty() && IPIP.isboolIp(ip.trim())) {
                        ipdata = ipdb.getData(ip.trim());
                        break;
                    }
                }
            }
            return new Text(ipdata.getCity());
        } catch (Exception e) {
            e.printStackTrace();
            return new Text(ipdata.getCity());
        }
    }

    public static void main(String[] args) {
        GetCity city = new GetCity();
        System.out.println("city: " + city.evaluate("114.229.108.98"));
        System.out.println("city: " + city.evaluate("180.106.15.16, 100.97.142.93"));
    }
}
