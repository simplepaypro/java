/* 
SimplePay API Signature generation class for Java 
https://simplepay.pro/
*/

package com.simplepay;

import java.util.*;
import java.security.*;
import java.math.*;

public class SP_Signature {

    private String  api_key, script_name;
    private HashMap <String, String> params;

    // Constructor
    public SP_Signature(String api_key, String script_name) {

        // инициализируем переменные
        this.api_key = api_key;
        this.params = new HashMap<>();

        // сгенерируем соль
        Double random = Math.random()*1000000000;
        Integer random_int = random.intValue();

        // Занесем имя скрипта в свойства объекта
        this.script_name = script_name;

        // добавим соль в список параметров
        this.set_salt(random_int.toString());
    }

    // API key setter
    public void set_api_key(String api_key){
        this.api_key = api_key;
    }

    // API key getter
    private String get_api_key(){
        return this.api_key;
    }

    // Parameter setter
    public void set_param(String name, String value){
        this.params.put(name,value);
    }

    // Parameter getter
    public String get_param(String param_name){
        return this.params.get(param_name);
    }

    // All parameters map setter
    public void set_param_map(HashMap<String,String > params){
        this.params = params;
    }

    // Salt setter
    public void set_salt(String salt){
        this.set_param("sp_salt", salt);
    }


    // Make concat string
    public String make_concat(){
        String concated = "";
        SortedSet<String> keys = new TreeSet<String>(this.params.keySet());
        for (String key : keys) {
            String value = this.params.get(key);
            // соберем строчку конкатенации из значений параметров
            concated += value+";";
        }
        // первым добавим в строку имя скрипта
        concated = this.script_name+';'+concated;

        // последним добавим в полученную строку API-ключ
        concated += this.api_key;

        return concated;
    }

    // Make MD5 hash
    public String md5(String s) throws Exception{

        // Получаем инстанс для создания отпечатка
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(s.getBytes(),0,s.length());

        // такая реализация может отдать хеш длиной 31 символ без первого ноля
        // добавляем в начало ноль в случае его отсутствия
        String hash = new BigInteger(1,m.digest()).toString(16);
        if(hash.length() == 31) hash = '0'+hash;
        return hash;
    }

    // Make signature
    public String make_signature() throws Exception{
        String s = this.make_concat();
        return this.md5(s);
    }
}
