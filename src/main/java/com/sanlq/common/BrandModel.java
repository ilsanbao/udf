/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanlq.common;

import com.alibaba.fastjson.JSON;
import com.sanlq.common.bean.ModelBean;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
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
public final class BrandModel {

    public static final ConcurrentHashMap<String, ModelBean> BRANDMODEL = new ConcurrentHashMap<>();
    private static final Log log = LogFactory.getLog(BrandModel.class);
    public static byte[] BRANDMODEL_CONTENT = null;

    private void init() {
        if (BRANDMODEL_CONTENT == null) {
            BRANDMODEL_CONTENT = readHDFSFile("hdfs://hadoopcluster/data/lib/brandmodel/brandmodel.dat");
            try {
                String content = new String(BRANDMODEL_CONTENT, "UTF-8");
                List<ModelBean> list = JSON.parseArray(content, ModelBean.class);
                Integer cnt = 0;
                for (ModelBean modelBean : list) {
                    BRANDMODEL.put(modelBean.getTitleOrg(), modelBean);
                    cnt++;
                    if (cnt % 1000 == 0) {
                        log.info("ALREADY IMPORT " + cnt + " MODEL ");
                    }
                }
                log.info("DONE IMPORT " + list.size() + " MODEL ");
            } catch (UnsupportedEncodingException ex) {
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
        init();
        Long et = System.nanoTime();
        log.info((et - st) / 1000 / 1000);
    }

    public ModelBean getModel(String titleOrg) {
        if (BRANDMODEL.contains(titleOrg)) {
            return BRANDMODEL.get(titleOrg);
        }else{
            return new ModelBean(titleOrg);
        }
    }

}
