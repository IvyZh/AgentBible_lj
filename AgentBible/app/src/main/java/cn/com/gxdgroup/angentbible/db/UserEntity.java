package cn.com.gxdgroup.angentbible.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Ivy on 2017/6/13.
 */

@Table(name = "user_table", id = "_id")
public class UserEntity extends Model {
    @Column
    private String username;
    @Column
    private int age;
    @Column
    private boolean isCheckDelete;

    public UserEntity() {
    }

    public UserEntity(int age, String username) {
        this.age = age;
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public boolean isCheckDelete() {
        return isCheckDelete;
    }

    public void setCheckDelete(boolean checkDelete) {
        isCheckDelete = checkDelete;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", age=" + age +
                ", isCheckDelete=" + isCheckDelete +
                '}';
    }
}