package ilia.nemankov.controller;

import com.google.gson.Gson;
import ilia.nemankov.dto.ServiceDiscoveryDTO;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("api")
public class JAXRSConfiguration extends Application {

    public JAXRSConfiguration() throws Exception {
        super();

        String sdUrl = System.getenv("CONSUL_URL");

        ServiceDiscoveryDTO configuration = new ServiceDiscoveryDTO();
        configuration.setId("l1");
        configuration.setName("lab1");
        configuration.setAddress(System.getenv("LAB1_IP"));
        configuration.setPort(Integer.valueOf(System.getenv("LAB1_PORT")));

        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpPut request = new HttpPut(sdUrl + "/v1/agent/service/register");
        StringEntity params = new StringEntity(new Gson().toJson(configuration));
        request.addHeader("content-type", "application/x-www-form-urlencoded");
        request.setEntity(params);
        HttpResponse response = httpClient.execute(request);
    }

}
