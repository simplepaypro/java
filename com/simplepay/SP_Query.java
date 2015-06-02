package com.simplepay;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class SP_Query {

    private String request_url;
    private SP_Signature signature;
    private HashMap <String, String> params = new HashMap <String, String>();

    public SP_Query(String request_url, String api_key){
        // Занесем URL запроса в свойства объекта
        this.request_url = request_url;

        // Теперь нужно получить имя скрипта
        String script_name = getScriptName(request_url);

        // Создаем инстанс SP_Signature и передаем ему параметры
        this.signature = new SP_Signature(api_key);
        this.signature.setScriptName(script_name);

        // Достаем сгенерированную соль и записываем ее в параметры создаваемого запроса
        String signature_salt = this.signature.getSalt();
        this.setParam("sp_salt", signature_salt);
    }

    public void setParamsMap(HashMap<String, String> params){
        this.params = params;
        this.signature.setParamMap(params);
    }

    public void setParam(String name, String value){
        this.params.put(name,value);
        this.signature.setParam(name, value);
    }

    //
    // Worker methods - рабочие функции

    // Get last URL part
    public String getScriptName(String request_url){
        String[] parts = request_url.split("/");
        return parts[parts.length-1];
    }

    // Make GET string
    public String makeQueryString() throws Exception{

        String concated = "";
        SortedSet<String> keys = new TreeSet<>(this.params.keySet());
        for (String key : keys) {
            String value = this.params.get(key);
            concated += key + "=" + URLEncoder.encode(value) + "&";
        }

        // формируем подпись
        String signature = this.signature.makeSignature();

        // собираем конечную строку GET-запроса
        concated = this.request_url + '?' +concated+ "sp_sig=" + signature;

        return concated;
    }
}
