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
    private HashMap <String, String> params = new HashMap<>();

    //
    // Constructor - конструктор, в качестве аргумента передается ключ точки

    public SP_Signature(String api_key) {

        // задаем ключ торговой точки
        this.setApiKey(api_key);

        // сгенерируем и добавим соль
        this.setSalt(this.makeSalt());
    }

    //
    // Salt methods

    // Salt generator - сгенерировать соль
    public String makeSalt(){
        // Создаем случайное число и вернем в виде строки
        Random random = new Random();
        Integer random_int = random.nextInt(7661655);
        return random_int.toString();
    }

    // Salt setter - задать соль
    public void setSalt(String salt){
        this.setParam("sp_salt", salt);
    }

    // Salt getter - получить сохраненную в объекте соль
    public String getSalt(){
        return this.getParam("sp_salt");
    }

    //
    // Global setters

    // Single parameter setter - установить единичный параметр
    public void setParam(String name, String value){
        this.params.put(name, value );
    }

    // Single parameter getter - получить единичный параметр
    public String getParam(String param_name){
        return this.params.get(param_name);
    }

    // Parameters map setter - установить массив с параметрами
    public void setParamMap(HashMap<String, String> params){
        this.params = params;
    }

    //
    // Properties setters

    public void setScriptName(String script_name){ this.script_name = script_name; }
    public void setApiKey(String api_key){
        this.api_key = api_key;
    }

    //
    // Worker methods - рабочие функции

    // Make concat string - создание конкатенированной строки параметров
    public String makeConcat(){

        String concated = "";
        SortedSet<String> keys = new TreeSet<>(this.params.keySet());
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

    // Make MD5 hash - Создание хеша MD5
    public String md5(String s) throws Exception{

        // Получаем инстанс для создания отпечатка
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(s.getBytes(),0,s.length());

        // такая реализация может отдать хеш длиной 31 символ без первого ноля
        // добавляем в начало ноль в случае его отсутствия
        String hash = new BigInteger(1,m.digest()).toString(16);
        if(hash.length() == 31) hash = "0"+hash;
        return hash;
    }

    // Make signature - Формирование подписи
    public String makeSignature() throws Exception{

        String s = this.makeConcat();
        return this.md5(s);
    }

}
