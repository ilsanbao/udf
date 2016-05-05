/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanlq.common;

import com.sanlq.common.bean.ModelBean;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 *
 * @author zhaochunlei
 */
public class BrandModel {

    public static ConcurrentHashMap<String, ModelBean> BRANDMODEL = new ConcurrentHashMap<>();
    private static final Log log = LogFactory.getLog(BrandModel.class);
    public static byte[] BRANDMODEL_CONTENT = null;

    private void inita() {
        if (BRANDMODEL_CONTENT == null) {
            BRANDMODEL_CONTENT = readHDFSFile("hdfs://hadoopcluster/data/lib/brandmodel/brandmodel.dat");
            try {
//                String content = new String(BRANDMODEL_CONTENT, "UTF-8");
//                List<ModelBean> list = JSON.parseArray(content, ModelBean.class);
//                Integer cnt = 0;
//                for (ModelBean modelBean : list) {
//                    BRANDMODEL.put(modelBean.getTitleOrg(), modelBean);
//                    cnt++;
//                    if (cnt % 1000 == 0) {
//                        log.info("ALREADY IMPORT " + cnt + " MODEL ");
//                    }
//                }
                Properties props = new Properties();
                BufferedReader bf = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(BRANDMODEL_CONTENT)));
                props.load(bf);
                System.out.println("props: " + props.size());
                Enumeration enu2 = props.propertyNames();
                Integer cnt = 0;
                while (enu2.hasMoreElements()) {
                    String key = (String) enu2.nextElement();
                    String d = props.getProperty(key);
                    String[] arrd = d.split("::");
                    ModelBean mbean = new ModelBean();
                    if (arrd.length == 6) {
                        mbean.setBrandId(Integer.valueOf(arrd[0]));
                        mbean.setBrandTitleEn(arrd[1]);
                        mbean.setBrandTitleCn(arrd[2]);
                        mbean.setTitleOrg(arrd[3]);
                        mbean.setTitleCn(arrd[4]);
                        mbean.setTitleEn(arrd[5]);
                        if (BRANDMODEL.contains(arrd[3])) {
                            System.out.println("Exist: " + d);
                        } else {
                            BRANDMODEL.put(arrd[3], mbean);
                        }
                    } else {
                        System.out.println("ERR: " + d);
                    }
                    cnt++;
                }
                log.info("DONE IMPORT " + cnt + " MODEL " + BRANDMODEL.size());
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(BrandModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BrandModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /* 
     * read the hdfs file content 
     * notice that the dst is the full path name 
     */
    public byte[] readHDFSFile(String dst) {
        try {
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(conf);
            // check if the file exists
            Path path = new Path(dst);
            if (fs.exists(path)) {
                byte[] buffer;
                // get the file info to create the buffer
                try (FSDataInputStream is = fs.open(path)) {
                    // get the file info to create the buffer
                    FileStatus stat = fs.getFileStatus(path);
                    // create the buffer
                    buffer = new byte[Integer.parseInt(String.valueOf(stat.getLen()))];
                    is.readFully(0, buffer);
                }
                return buffer;
            } else {
                log.info("the file is not found .");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public BrandModel() {
        Long st = System.nanoTime();
        inita();
        Long et = System.nanoTime();
        log.info((et - st) / 1000 / 1000);
    }

    public ModelBean getModel(String titleOrg) {
        if (BRANDMODEL.containsKey(titleOrg)) {
            return BRANDMODEL.get(titleOrg);
        } else {
            return new ModelBean();
        }
    }

    public void initb() {
        try {
            InputStream is = BrandModel.class.getClass().getResourceAsStream("/brandmodel.properties");
            Properties props = new Properties();
            props.load(is);
            System.out.println("props: " + props.size());
            Enumeration enu2 = props.propertyNames();
            Integer cnt = 0;
            while (enu2.hasMoreElements()) {
                String key = (String) enu2.nextElement();
                String d = props.getProperty(key);
                String[] arrd = d.split("::");
                ModelBean mbean = new ModelBean();
                if (arrd.length == 6) {
                    mbean.setBrandId(Integer.valueOf(arrd[0]));
                    mbean.setBrandTitleEn(arrd[1]);
                    mbean.setBrandTitleCn(arrd[2]);
                    mbean.setTitleOrg(arrd[3]);
                    mbean.setTitleCn(arrd[4]);
                    mbean.setTitleEn(arrd[5]);
                    if (BRANDMODEL.contains(arrd[3])) {
                        System.out.println("Exist: " + d);
                    } else {
                        BRANDMODEL.put(arrd[3], mbean);
                    }
                } else {
                    System.out.println("ERR: " + d);
                }
                cnt++;
            }
            System.out.println("cnt: " + cnt);
            System.out.println("BRANDMODEL: " + BRANDMODEL.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BrandModel bm = new BrandModel();
        bm.initb();
    }
}
