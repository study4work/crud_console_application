package com.lysenko.crudapp.utill;

import com.lysenko.crudapp.model.Status;

public class ConverterUtil {

     public static Status setStatus(String state) {
        return state.equals("ACTIVE") ? Status.ACTIVE : Status.DELETED;
     }
}
