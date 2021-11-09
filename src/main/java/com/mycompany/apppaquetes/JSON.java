/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apppaquetes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Nacho
 */
public interface JSON {
    public static String toJSONField(String fieldName, String value){
        return String.format("\"%s\": \"%s\"", fieldName, value);
    }
    public static String toJSONField(String fieldName, int value){
        return String.format("\"%s\": \"%d\"", fieldName, value);
    }
    public static String toJSONField(String fieldName, JSON value){
        return String.format("\"%s\": %s", fieldName, value.toJSON());
    }
    public static String toJSONField(String fieldName, ArrayList<Object> value){
        String arr = "";
        int i = 0;
        for(Object val : value){
            if(val instanceof JSON){
                arr = arr + ((JSON) val).toJSON();
            } else {
                arr = arr + val.toString();
            }
            if(i < value.size()-1) {
            	arr = arr + ",";
            }
            arr = arr + "\n";
            i++;
        }
        return String.format("\"%s\": [\n%s]", fieldName, arr);
    }
    @SuppressWarnings("unchecked")
	public static String objectBuilder(String[] fields, Object[] values){
        int i = 0;
        String obj = "{\n";
        for ( String field : fields ){
            switch(values[i].getClass().getCanonicalName()){
                case "java.util.ArrayList": 
                    obj = obj + JSON.toJSONField(field, (ArrayList<Object>)values[i]);
                    break;
                case "java.lang.Integer":
                    obj = obj + JSON.toJSONField(field, (Integer) values[i]);
                    break;
                case "java.lang.String":
                    obj = obj + JSON.toJSONField(field, (String) values[i]);
                    break;
                case "java.util.HashMap":
                    obj = obj + JSON.toJSONField(field, (HashMap<String, String>) values[i]);
                    break;
                default:
                    if(values[i] instanceof JSON){
                        obj = obj + JSON.toJSONField(field, (JSON)values[i]);
                    } else {
                        obj = obj + JSON.toJSONField(field, values[i].toString());
                    }
                    break;
            }
            if(i < values.length-1) {
            	obj = obj + ",";
            }
            obj = obj + "\n";
            i++;
        }
        obj = obj + "}";
        return obj;
    }
    public static String toJSONField(String fieldName, HashMap<String, String> hashMap) {
        String obj = "";
        int i = 0;
        for (Entry<String, String> entry: hashMap.entrySet()) {
        	obj = obj + String.format("\"%s\": \"%s\"", entry.getKey(), entry.getValue());
            if(i < hashMap.size() -1) {
            	obj = obj + ",";
            }
            obj = obj + "\n";
            i++;
        }
        return String.format("\"%s\": {\n%s}", fieldName, obj);
	}
	public abstract String toJSON();
}
