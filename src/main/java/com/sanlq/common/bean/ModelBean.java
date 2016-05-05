/**
 * SELECT
 * m.org_title titleOrg,
 * m.model_id modelId,
 * b.brand_id brandId,
 * b.title_cn brandTitleCn,
 * b.title_en brandTitleEn,
 * m.title_cn titleCn,
 * m.title_en titleEn
 * FROM
 * zplay_base.B_model m
 * LEFT JOIN zplay_base.B_brand b ON m.brand_id = b.brand_id
 * WHERE
 * m.brand_id IS NOT NULL
 */
package com.sanlq.common.bean;

/**
 *
 * @author zhaochunlei
 */
public class ModelBean {

    private Integer brandId;
    private String brandTitleCn;
    private String brandTitleEn;
    private String titleCn;
    private String titleEn;
    private String titleOrg;
    private Integer dpi;
    private Integer screenWidth;
    private Integer screenHeight;
    private Integer salePrice;
    private Integer sizeInch;
    private Integer netType;

    public ModelBean(String titleOrg) {
        this.brandId = 0;
        this.brandTitleCn = "unknown";
        this.brandTitleEn = "unknown";
        this.titleCn = titleOrg;
        this.titleEn = titleOrg;
        this.titleOrg = titleOrg;
    }

    
    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandTitleCn() {
        return brandTitleCn;
    }

    public void setBrandTitleCn(String brandTitleCn) {
        this.brandTitleCn = brandTitleCn;
    }

    public String getBrandTitleEn() {
        return brandTitleEn;
    }

    public void setBrandTitleEn(String brandTitleEn) {
        this.brandTitleEn = brandTitleEn;
    }

    public String getTitleCn() {
        return titleCn;
    }

    public void setTitleCn(String titleCn) {
        this.titleCn = titleCn;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleOrg() {
        return titleOrg;
    }

    public void setTitleOrg(String titleOrg) {
        this.titleOrg = titleOrg;
    }

    public Integer getDpi() {
        return dpi;
    }

    public void setDpi(Integer dpi) {
        this.dpi = dpi;
    }

    public Integer getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(Integer screenWidth) {
        this.screenWidth = screenWidth;
    }

    public Integer getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(Integer screenHeight) {
        this.screenHeight = screenHeight;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getSizeInch() {
        return sizeInch;
    }

    public void setSizeInch(Integer sizeInch) {
        this.sizeInch = sizeInch;
    }

    public Integer getNetType() {
        return netType;
    }

    public void setNetType(Integer netType) {
        this.netType = netType;
    }

}
