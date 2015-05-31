/* 
SimplePay API Java example 
https://simplepay.pro/
*/

package com.simplepay;

public class SP_Java_Example {

    public static void main(String[] args) throws Exception{
        SP_Signature signature = new SP_Signature("YOUR_SECRET_KEY","payment");
        signature.set_param("sp_outlet_id","ID_ТОРГОВОЙ_ТОЧКИ");
        signature.set_param("sp_description", "Тестовый заказ из Java");
        signature.set_param("sp_amount","10.00");

        System.out.println("Concatenation string: "+signature.make_concat());
        System.out.println("Signature string: "+signature.make_signature());

    }
}
