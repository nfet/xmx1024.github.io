package com.lagnada.xmx1024.integration.web;

import com.lagnada.xmx1024.integration.WebIntegrationTestCase;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountRepresentationTest extends WebIntegrationTestCase {

    private static final int NON_EXISTENT_ACCOUNT_ID = 1234;
    private static final String RESOURCE_NOT_FOUND_MSG = "Resource not found: http://localhost/api/account/" + NON_EXISTENT_ACCOUNT_ID;
    private static final String EXPECTED_RESPONSE = "{\"errors\":[{\"code\":\"RESOURCE-NOT-FOUND\",\"field\":\"\",\"message\":\"" + RESOURCE_NOT_FOUND_MSG + "\"}],\"count\":1}";

    @Test
    public void accountNotFound() throws Exception
    {
        final URI uri = UriComponentsBuilder.fromPath("/api/account/{accountId}")
                .buildAndExpand(NON_EXISTENT_ACCOUNT_ID).toUri();

        mvc.perform(get(uri)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().string("Error", "true"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(EXPECTED_RESPONSE));
    }

}
