/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.net;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import com.simplifide.core.HardwareLog;



public class LicenseConnection {
    public LicenseConnection() {}
    
    public void connect() {
        HttpClientParams params = new HttpClientParams();
        params.setBooleanParameter(HttpClientParams.ALLOW_CIRCULAR_REDIRECTS, true);
        params.setIntParameter(HttpClientParams.MAX_REDIRECTS, 4);
     
        
        HttpClient client = new HttpClient(params);
        client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
       

       
        
        
        GetMethod post = new GetMethod("http://simplifide.com/drupal/free_trial2");
        post.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        

       /* NameValuePair[] data = {
                new NameValuePair("user_name", "joe"),
                new NameValuePair("password", "aaa"),
                new NameValuePair("name", "joker"),
                new NameValuePair("email", "beta"),
              };
              post.setRequestBody(data);
              */
        try {
            int rettype = client.executeMethod(post);

            byte[] responseBody = post.getResponseBody();

            // Deal with the response.
            // Use caution: ensure correct character encoding and is not binary data
            String tstring = new String(responseBody);

           
        } catch (HttpException e) {
         
            HardwareLog.logError(e);
        } catch (IOException e) {
            
            HardwareLog.logError(e);
        }
        finally {
            post.releaseConnection();
        }

       
    }
}
