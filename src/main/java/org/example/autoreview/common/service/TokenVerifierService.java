package org.example.autoreview.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;


import java.util.Base64;
import java.util.Map;

@Service
public class TokenVerifierService {

    public Map<String, Object> validateGoogleAccessToken(String idToken) throws JsonProcessingException {
        String[] parts = idToken.split("\\."); // Splits the token into header, payload, and signature parts

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid ID token format.");
        }

        // Decode the payload (Base64 URL decoding)
        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

        // Convert the payload to a map for further usage (e.g., extracting fields like email, userId)
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> payload = objectMapper.readValue(payloadJson, Map.class);

        return payload;
    }
}
