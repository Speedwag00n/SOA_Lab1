package ilia.nemankov.controller;

import ilia.nemankov.dto.ServiceDiscoveryDTO;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationPath("api")
public class JAXRSConfiguration extends Application {

    public JAXRSConfiguration() throws Exception {
        super();

        Client client = ClientBuilder.newBuilder().build();
        String sdUrl = System.getenv("CONSUL_URL");

        ServiceDiscoveryDTO configuration = new ServiceDiscoveryDTO();
        configuration.setId("l1");
        configuration.setName("lab1");
        configuration.setAddress(System.getenv("LAB1_IP"));
        configuration.setPort(Integer.valueOf(System.getenv("LAB1_PORT")));

        Response response = client
                .target(sdUrl + "/v1/agent/service/register")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(configuration, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200) {
            throw new Exception("Failed to register service in Service discovery");
        }
    }

}
