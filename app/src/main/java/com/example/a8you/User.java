package com.example.a8you;

public class User {

    public String fullname,age, email, height, weight;

    public User(){

    }

    public enum genderEnum {
        MALE, FEMALE, Other;
    }
    public User(String fullName, String age, String emailAddress,String height , String weight) {
        this.fullname = fullName;
        this.age = age;
        this.email = emailAddress;
        this.height= height;
        this.weight = weight;



    }

//    public User( String height , String weight, String age){
//        this.height = height;
//        this.weight = weight;
//        this.age = age;
//    }

//    public double setBmi(){
//        double bmiD = Double.valueOf(weight) / Math.pow(Double.valueOf(height), 2);
////        bmi = String.valueOf(bmiD);
//        return bmiD;
//    }

//    public void readDatabase() {
//
//    }
//    public String gender(String gender) {
//        if (gender.equalsIgnoreCase("male")){
//            gender = "male";
//        } else
//        return gender
//    }
}
