package org.iii.natsclient.util;

// import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.iii.natsclient.util.Utils;

@Service
public class RestApi {

    // @Value("${basalt.rapi}")
    // public String _rapi;

    // @Value("${basalt.bapi}")
    // public String _bapi;

    public static RestApi _ginst = null;

    public RestApi() {
        _ginst = this;
    }

    static RestTemplate restTemplate = new RestTemplate();

    public static String GetString(String url) {
        try {
            // String url = String.format("%s/%s", _ginst._bapi, postid);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            Utils.printException(e);
            return "";
        }
    }

    public static String PostString(String url, String si) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(si, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            Utils.printException(e);
            return "";
        }
    }

    // public static String PostString(String postid, String si) {
    // String url = String.format("%s/%s", _ginst._bapi, postid);
    // return _PostString(url, si);
    // }

    // public static String PostString(String postid, String si, String targethost)
    // {
    // String url = String.format("%s/%s", targethost, postid);
    // return _PostString(url, si);
    // }
}