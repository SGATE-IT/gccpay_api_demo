package com.gccpay.request;

import com.alibaba.fastjson.JSONObject;
import com.gccpay.entity.InqTranOrderEntity;
import com.gccpay.utils.AESUtils;
import com.gccpay.utils.RSAUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InqTranOrderDemo {
    private static RestTemplate restTemplate = new RestTemplate();
    public static void main(String[] args) {
        String url= "https://api-dev.gccpay.cn/pay/merchant/inquiryTransactions";
        ResponseEntity<String> response = null;
        try {
            InqTranOrderEntity inqTranOrder = new InqTranOrderEntity();
            inqTranOrder.setMerCode("M96620240924161327000862");
            inqTranOrder.setMerOrderId("");
            inqTranOrder.setTrdOrderId("TRD20241231131545000358");

            HttpHeaders headers = new HttpHeaders();
            // 商户私钥，公钥需要配置到管理平台
            String privateKey="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC55Y3+nuJilZMrpN5l9fk4neHn3BmDOhPbWtOP/OLdDBni7b0EF5Pwp12ggKXbjNyXvX46aEExnGmLtPySHNxomH4wQqA57R0oW5fx2iY8XO8XD+2yEqoHmTdONQ2llF0nUlLmpGh/b6RafKKBH0XrIV76ACLlFL6CuyQGJ1qVSN1Y4NbUPURGBcfGO8Hz+3shOLLGlppTSqPLP0K6+x6eSWXDCbCmKZybJoMmhYzi0fdeAf3kgmROxwVCpL6SynxTN7zyujZdcDE5+HFupiVfCPFDp68DzdYGIfMPz1YMLIh9SNOPTQqVaXmtjNx+PaSYqCiJzmxvBjPbwezw60gfAgMBAAECggEADn7Cz37a6JSaZCw35Aifa6jyWFHGP4BEBulFikdN9uFRrdGbcnoJx0yvNUVMXLRwIvjb1d9GB7Tj85VwnrTXaH4Z7aNt7cMto3VfTk5HVJLRv24Dh8yCxSyCSBV4Jr6AQQFFuF9+snXyTjsq5LMBxBYKQafIqmCV6YjnUMxSxavfaLcTZDg/txoTYEKB07niS8dA9sUDVWk23A1E1E5YioOzSeEYWfEEE17xe6su+V5KmhBITqHtEuSRkccxWMvptbKObXQKIV2fx1brobQt5yzJviVUqiu0moi3haOs3VBgXq32wBO9bq72dpZTbGQ9n5qOBvfbuSfewzLbHm6VkQKBgQDcqmj8JYwJ5RNx2Ar5gO1T7mlG7QEJUY4M5InotJ0zXuYU3eTyKMo0BFUlt77ZJ6lR2PyCW554nV6Zg6awNbHsKHOvNBJ4jlY+KnOYFchLQ4HeOSsnA9OO5XPl3oiIZsI5TItIoD4PclU3jsUBNhUwUeQxspzM2n0mBkvXDez2CQKBgQDXqeKabLQ3jkYaI6OCHPcHGps+isO4OHvsEie3bMYtFzLu6JdIJDGR6KxufBkthEAaWwG8ojJ0p3oNMA+LF8pRbSts4FirwREYZNZYsI3MZMDUO+raFPq1v1ZpNfc3PSI1Bws9bGTXAbcsOxhuJaimGX1q6qglSRI9nzJMxHCW5wKBgGRzxvsR9KAEgkeO+9/9CwzsOUyqU5B0aeAAoa8nmXBrQP46zSBX5USsvD5BWUXtwiyaRMjrAEcUDJ6Byf3pU6eX+qHFaKss0KHYHWscb2OjxZjuGXDXUxV36ry4Axtk/AGtkLJtEBNkDtsNySz1+8tVXDYrgynWRKZss1Wg50BRAoGACniXJgRNI71mrfI5CCI75D5odzrpkdI8QhQHlaJUZPARawQkBD6toXX4mUyxNEKNkjoE9ZGyfXN8O5OvzYMUMavpRdoGtCAloleTCK9Z0yi5LBTUrE4EdjqaCXWzUR1IweZbp1nR85aDvEQKRZ7Sd24ZZs2J6HWJyzAlkxCentUCgYEAmAOHIQI6V8aNYT0xOjKOWIAqyI3pPruQ51K43yoo/XhrRLygfDj7ikQdFYd+gN1W3mlsT3EN2Qjz3XhczVOqE1RCn15v3WTydikzXn8pEFmYf9kV2GS11eMTTKYv2UoLqN7j3a74fzfv67xsm5cJlbO6Dt3goBFZMerHdHSf7vY=";
            String signature = RSAUtils.sign(JSONObject.toJSONString(inqTranOrder), privateKey);
            headers.set("merKeyId", "12345678901");
            long currentTime = new Date().getTime()/1000;
            headers.set("timeStamp", String.valueOf(currentTime));
            headers.set("signature", signature);
            headers.set("Content-Type", "application/json");
            //平台提供的aeskey
            String aeskey="1af9206a4d0bd6becdbe188379a5f65d";

            String encrypt = AESUtils.encrypt(JSONObject.toJSONString(inqTranOrder), aeskey);
            Map<String,String> map = new HashMap<>();
            map.put("aesBuffer",encrypt);
            HttpEntity<String> entity = new HttpEntity<>(JSONObject.toJSONString(map),headers);
            response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            HttpStatus statusCode = response.getStatusCode();
            System.out.println("resp statusCode:"+statusCode.value());
            JsonObject responseJSON = new Gson().fromJson(response.getBody(), JsonObject.class);
            if("10000".equals(responseJSON.get("code").getAsString())){
                String data = responseJSON.get("data").getAsString();
                String decrypt = AESUtils.decrypt(data, aeskey);
                System.out.println("resp decData:"+ decrypt );
                HttpHeaders responseHeaders = response.getHeaders();
                //平台提供的公钥
                String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAueWN/p7iYpWTK6TeZfX5OJ3h59wZgzoT21rTj/zi3QwZ4u29BBeT8KddoICl24zcl71+OmhBMZxpi7T8khzcaJh+MEKgOe0dKFuX8domPFzvFw/tshKqB5k3TjUNpZRdJ1JS5qRof2+kWnyigR9F6yFe+gAi5RS+grskBidalUjdWODW1D1ERgXHxjvB8/t7ITiyxpaaU0qjyz9CuvsenkllwwmwpimcmyaDJoWM4tH3XgH95IJkTscFQqS+ksp8Uze88ro2XXAxOfhxbqYlXwjxQ6evA83WBiHzD89WDCyIfUjTj00KlWl5rYzcfj2kmKgoic5sbwYz28Hs8OtIHwIDAQAB";
                boolean signatureStatus = RSAUtils.verify(decrypt, responseHeaders.get("signature").get(0), publicKey);
                if(signatureStatus){
                    System.out.println("sign:验签成功");
                }else{
                    System.out.println("sign:验签失败");
                }
            };
            System.out.println("resp:"+response.getBody());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
