package com.baeldung.annotation.servletcomponentscan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringBootPlainApp.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = { "security.basic.enabled=false", "server.tomcat.additional-tld-skip-patterns=tomee-*.jar,tomcat-*.jar,openejb-*.jar,cxf-*.jar,activemq-*.jar" })
public class SpringBootWithoutServletComponentIntegrationTest {

    @Autowired private ServletContext servletContext;

    @Autowired private TestRestTemplate restTemplate;

    @Test
    public void givenServletContext_whenAccessAttrs_thenNotFound() {
        assertNull(servletContext.getAttribute("servlet-context-attr"));
    }

    @Test
    public void givenServletFilter_whenGetHello_thenEndpointNotFound() {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("/hello", String.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void givenServletContext_whenCheckFilterMappings_thenEmpty() {
        assertNotNull(servletContext);
        FilterRegistration filterRegistration = servletContext.getFilterRegistration("hello filter");

        assertNull(filterRegistration);
    }

}


