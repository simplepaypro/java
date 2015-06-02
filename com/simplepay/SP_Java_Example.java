/* 
SimplePay API Java example 
https://simplepay.pro/
*/

package com.simplepay;

public class SP_Java_Example {

    public static void main(String[] args) throws Exception{

        SP_Query query = new SP_Query("https://api.simplepay.pro/sp/payment", "");
        query.setParam("sp_outlet_id", "");
        query.setParam("sp_description", "Test from JAVA");
        query.setParam("sp_amount", "1000.50");

        String query_url = query.makeQueryString();
        System.out.println("Query URL: "+query_url);

    }
}
