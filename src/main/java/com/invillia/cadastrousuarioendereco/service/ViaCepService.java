package com.invillia.cadastrousuarioendereco.service;

import com.invillia.cadastrousuarioendereco.response.ViaCepResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {

    private final RestTemplate restTemplate;
    private final String VIACEPURL = "https://viacep.com.br/ws/";

    public ViaCepService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ViaCepResponse call(String cep){
        try {
            String url = VIACEPURL + cep + "/json/";

            ViaCepResponse response = this.restTemplate.getForObject(url, ViaCepResponse.class);
            return response;
        } catch (Exception e){
            return null;
        }
    }
}
