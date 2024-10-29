package com.qlda.nhom6.service;

import org.springframework.stereotype.Service;

import java.util.*;

import static com.qlda.nhom6.config.Config.hmacSHA512;
import static com.qlda.nhom6.config.Config.secretKey;

@Service
public class VNPayService {
    public static String hashAllFields(Map fields) {
        List fieldNames = new ArrayList(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                sb.append(fieldName);
                sb.append("=");
                sb.append(fieldValue);
            }
            if (itr.hasNext()) {
                sb.append("&");
            }
        }
        return hmacSHA512(secretKey,sb.toString());
    }


}
