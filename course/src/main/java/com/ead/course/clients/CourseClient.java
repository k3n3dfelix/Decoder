package com.ead.course.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
public class CourseClient {

    @Autowired
    RestTemplate restTemplate;

    String REQUEST_URI = "http://localhost:8087";

    /* public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable) {
        List<CourseDto> searchResult = null;

        String url = REQUEST_URI + "/courses?userId= " + userId + "&page= " + pageable.getPageNumber() + "&size= "
                + pageable.getPageSize() + "&sort" + pageable.getSort().toString().replaceAll(": ", ",");

        log.debug("Request URL: {}", url);
        log.info("RequestURL: {}", url);

        try{
            log.debug("Response Number od Elements : {} ", searchResult.size());
        }catch (HttpStatusCodeException error) {
            log.error("Erro request /courses { }", error);
        }
        log.info("ending request /courses userId {}", userId);

        return null;
    } */

}
