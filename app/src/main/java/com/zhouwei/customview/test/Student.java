package com.zhouwei.customview.test;

/**
 * Created by zhouwei on 2017/4/7.
 */

public class Student {
	public String name;
	
	public int age;

    private String gender;

    private String birthday;
    
    public int add(int x,int y){
    	return x+y;	
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        /**
         * 生日 		// ���հ�
         */
        return birthday;
    }
}
