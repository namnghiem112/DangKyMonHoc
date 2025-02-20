package com.example.scheduleclassservice.Service;

import com.example.scheduleclassservice.Dto.SubjectDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class SubjectService {
    private final String subjectServiceUrl = "http://localhost:8081/api/subject";
    private final RestTemplate restTemplate;
    public SubjectService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SubjectDto getSubjectById(int id) {
        try {
            ResponseEntity<SubjectDto> response = restTemplate.getForEntity(subjectServiceUrl + "/" + id, SubjectDto.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return null;
            }
        } catch (RestClientException e) {
            return null;
        }
    }
}
