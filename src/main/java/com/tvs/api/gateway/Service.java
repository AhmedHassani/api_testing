package com.tvs.api.gateway;


import com.tvs.api.gateway.exception.ResourceNotFoundException;
import com.tvs.api.gateway.model.Model;
import com.tvs.api.gateway.model.Resopnse;
import com.tvs.api.gateway.model.Resopnse1;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Random;

@org.springframework.stereotype.Service
public class Service {


    public Resopnse tvsAPIGateway(Model model){
        System.out.println(model.getMti());
        try {
            if(model.getRequestID().isEmpty()
                    ||model.getSenderChannel().isEmpty()
                    ||model.getSessionId().isEmpty()
                    ||model.getRequestTime().isEmpty()
                    ||model.getRequestBody().isEmpty()
                    ||model.getMacIn().isEmpty()
                    ||model.getApplicationInfo().getAppVersion().isEmpty()
                    ||model.getApplicationInfo().getApplicationLanguage().isEmpty()
                    ||model.getDeviceInfo().getDeviceID().isEmpty()
                    ||model.getDeviceInfo().getOperatingSystem().isEmpty()) {
                throw new ResourceNotFoundException(model.getRequestID());
            } else if (model.getMti()==2000) {
                System.out.print("macout");
                DateFormat df = new SimpleDateFormat("YYYYMMDDHHMMS");
                String requiredDate = df.format(new Date()).toString();
                return new Resopnse1(
                        model.getRequestID(),
                        requiredDate,
                        "000",
                        "SUCCESSFULL",
                        model.getRequestBody(),
                        MD5("jfjfjfpdpdpdp"+ new Random().nextDouble())
                );
            } else{
                System.out.print("No macout");
                DateFormat df = new SimpleDateFormat("YYYYMMDDHHMMS");
                String requiredDate = df.format(new Date()).toString();
                return new Resopnse(
                        model.getRequestID(),
                        requiredDate,
                        "000",
                        "SUCCESSFULL",
                        model.getRequestBody()
                );
            }
        } catch (NoSuchElementException exception) {
            throw new ResourceNotFoundException(model.getRequestID());
        } catch (HttpClientErrorException.BadRequest exception) {
            throw new ResourceNotFoundException(model.getRequestID());
        }catch (ConstraintViolationException e) {
            throw new ResourceNotFoundException(model.getRequestID());
        }
    }


    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}
