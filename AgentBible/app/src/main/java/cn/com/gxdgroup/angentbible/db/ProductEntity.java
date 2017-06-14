package cn.com.gxdgroup.angentbible.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Ivy on 2017/6/13.
 */

@Table(name = "product_table", id = "_id")
public class ProductEntity extends Model {
    @Column
    private String productUsername;//生产者姓名
    @Column
    private String productDate;//生产日期2017-05-03
    @Column
    private String productNums;//产量
    @Column
    private String productPrice;//单价
    @Column
    private String productTotalPrice;//总价
    @Column
    private String productYear;//生产年份 2017
    @Column
    private String productMonth;//生产月份 05
    @Column
    private String productDay;// 生产日 03
    @Column
    private long dateUnixTime;// 生产日对应的时间戳 1497369600 --> 2017/06/14 00:00:00

    public ProductEntity() {
    }

    public String getProductYear() {
        return productYear;
    }

    public void setProductYear(String productYear) {
        this.productYear = productYear;
    }

    public String getProductMonth() {
        return productMonth;
    }

    public void setProductMonth(String productMonth) {
        this.productMonth = productMonth;
    }

    public String getProductDay() {
        return productDay;
    }

    public void setProductDay(String productDay) {
        this.productDay = productDay;
    }

    public String getProductUsername() {
        return productUsername;
    }

    public void setProductUsername(String productUsername) {
        this.productUsername = productUsername;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getProductNums() {
        return productNums;
    }

    public void setProductNums(String productNums) {
        this.productNums = productNums;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(String productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public long getDateUnixTime() {
        return dateUnixTime;
    }

    public void setDateUnixTime(long dateUnixTime) {
        this.dateUnixTime = dateUnixTime;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "productUsername='" + productUsername + '\'' +
                ", productDate='" + productDate + '\'' +
                ", productNums='" + productNums + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productTotalPrice='" + productTotalPrice + '\'' +
                ", dateUnixTime=" + dateUnixTime +
                '}';
    }
}