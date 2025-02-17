package com.gccpay.request;

import com.alibaba.fastjson.JSONObject;
import com.gccpay.utils.AESUtils;
import com.gccpay.utils.RSAUtils;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadFileDemo {
    private static RestTemplate restTemplate = new RestTemplate();
    public static void main(String[] args) {

        String url= "http://127.0.0.1:7080/open-api/pay/merchant/downloadFile";
        ResponseEntity<String> response = null;
        try {
           Map<String,Object> createOrder = new HashMap<>();
            createOrder.put("trdDate","test");
            createOrder.put("fileType","settle");


            HttpHeaders headers = new HttpHeaders();
            // 商户私钥，公钥需要配置到管理平台
            String privateKey="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC55Y3+nuJilZMrpN5l9fk4neHn3BmDOhPbWtOP/OLdDBni7b0EF5Pwp12ggKXbjNyXvX46aEExnGmLtPySHNxomH4wQqA57R0oW5fx2iY8XO8XD+2yEqoHmTdONQ2llF0nUlLmpGh/b6RafKKBH0XrIV76ACLlFL6CuyQGJ1qVSN1Y4NbUPURGBcfGO8Hz+3shOLLGlppTSqPLP0K6+x6eSWXDCbCmKZybJoMmhYzi0fdeAf3kgmROxwVCpL6SynxTN7zyujZdcDE5+HFupiVfCPFDp68DzdYGIfMPz1YMLIh9SNOPTQqVaXmtjNx+PaSYqCiJzmxvBjPbwezw60gfAgMBAAECggEADn7Cz37a6JSaZCw35Aifa6jyWFHGP4BEBulFikdN9uFRrdGbcnoJx0yvNUVMXLRwIvjb1d9GB7Tj85VwnrTXaH4Z7aNt7cMto3VfTk5HVJLRv24Dh8yCxSyCSBV4Jr6AQQFFuF9+snXyTjsq5LMBxBYKQafIqmCV6YjnUMxSxavfaLcTZDg/txoTYEKB07niS8dA9sUDVWk23A1E1E5YioOzSeEYWfEEE17xe6su+V5KmhBITqHtEuSRkccxWMvptbKObXQKIV2fx1brobQt5yzJviVUqiu0moi3haOs3VBgXq32wBO9bq72dpZTbGQ9n5qOBvfbuSfewzLbHm6VkQKBgQDcqmj8JYwJ5RNx2Ar5gO1T7mlG7QEJUY4M5InotJ0zXuYU3eTyKMo0BFUlt77ZJ6lR2PyCW554nV6Zg6awNbHsKHOvNBJ4jlY+KnOYFchLQ4HeOSsnA9OO5XPl3oiIZsI5TItIoD4PclU3jsUBNhUwUeQxspzM2n0mBkvXDez2CQKBgQDXqeKabLQ3jkYaI6OCHPcHGps+isO4OHvsEie3bMYtFzLu6JdIJDGR6KxufBkthEAaWwG8ojJ0p3oNMA+LF8pRbSts4FirwREYZNZYsI3MZMDUO+raFPq1v1ZpNfc3PSI1Bws9bGTXAbcsOxhuJaimGX1q6qglSRI9nzJMxHCW5wKBgGRzxvsR9KAEgkeO+9/9CwzsOUyqU5B0aeAAoa8nmXBrQP46zSBX5USsvD5BWUXtwiyaRMjrAEcUDJ6Byf3pU6eX+qHFaKss0KHYHWscb2OjxZjuGXDXUxV36ry4Axtk/AGtkLJtEBNkDtsNySz1+8tVXDYrgynWRKZss1Wg50BRAoGACniXJgRNI71mrfI5CCI75D5odzrpkdI8QhQHlaJUZPARawQkBD6toXX4mUyxNEKNkjoE9ZGyfXN8O5OvzYMUMavpRdoGtCAloleTCK9Z0yi5LBTUrE4EdjqaCXWzUR1IweZbp1nR85aDvEQKRZ7Sd24ZZs2J6HWJyzAlkxCentUCgYEAmAOHIQI6V8aNYT0xOjKOWIAqyI3pPruQ51K43yoo/XhrRLygfDj7ikQdFYd+gN1W3mlsT3EN2Qjz3XhczVOqE1RCn15v3WTydikzXn8pEFmYf9kV2GS11eMTTKYv2UoLqN7j3a74fzfv67xsm5cJlbO6Dt3goBFZMerHdHSf7vY=";
            String signature = RSAUtils.sign(JSONObject.toJSONString(createOrder), privateKey);
            headers.set("merKeyId", "12345678901");
            long currentTime = new Date().getTime()/1000;
            headers.set("timeStamp", String.valueOf(currentTime));
            headers.set("signature", signature);
            headers.set("Content-Type", "application/json");
            //平台提供的aeskey
            String aeskey="1af9206a4d0bd6becdbe188379a5f65d";

            String encrypt = AESUtils.encrypt(JSONObject.toJSONString(createOrder), aeskey);
            Map<String,String> map = new HashMap<>();
            map.put("aesBuffer",encrypt);
            HttpEntity<String> entity = new HttpEntity<>(JSONObject.toJSONString(map),headers);
            response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            HttpStatus statusCode = response.getStatusCode();
            System.out.println("resp statusCode:" + response.getBody());
            String fileName = getFileNameFromContentDisposition(response.getHeaders());
            if (statusCode == HttpStatus.OK) {
                byte[] fileBytes = response.getBody().getBytes();
                saveFile(fileBytes, "/resources/"+fileName);
                System.out.println("File downloaded successfully.");
            } else {
                System.out.println("Failed to download file. Response: " + response.getBody());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void saveFile(byte[] fileBytes, String fileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(fileBytes);
        }
    }
    private static String getFileNameFromContentDisposition(HttpHeaders headers) {
        String contentDisposition = headers.getFirst(HttpHeaders.CONTENT_DISPOSITION);
        if (contentDisposition != null) {
            Pattern pattern = Pattern.compile("filename=([^\"]+)");
            Matcher matcher = pattern.matcher(contentDisposition);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }
}
