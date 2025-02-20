package com.example.taskservice.Service;

import com.example.taskservice.Dto.ScheduleClassDto;
import com.example.taskservice.Dto.SelectedClassDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SelectedClassService {
    private final String selectedclassurl = "http://localhost:8083/api/selectedclass";

    private final String saveselectedclassurl = "http://localhost:8083/api/selectedclass/save";
    private final String updateselectedclassurl = "http://localhost:8083/api/selectedclass/update";
    private final String deleteselectedclassurl = "http://localhost:8083/api/selectedclass/delete";
    private final RestTemplate restTemplate;
    public SelectedClassService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public List<SelectedClassDto> getAllSelectedClass(){
        try {
            ResponseEntity<List<SelectedClassDto>> response = restTemplate.exchange(selectedclassurl, HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<SelectedClassDto>>() {});
            if (response.getStatusCode() == HttpStatus.OK) {
                return (List<SelectedClassDto>) response.getBody();
            } else {
                return null;
            }
        } catch (RestClientException e) {
            return null;
        }
    }
    public SelectedClassDto getSelectedClassByid(int id){
        try {
            ResponseEntity<SelectedClassDto> response = restTemplate.getForEntity(selectedclassurl + "/" + id, SelectedClassDto.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return null;
            }
        } catch (RestClientException e) {
            return null;
        }
    }
    public void deleteSelectedClass(int id){
        try {
            ResponseEntity<Void> response = restTemplate.exchange(deleteselectedclassurl+"/"+id, HttpMethod.DELETE,
                    null,
                    Void.class);

        } catch (RestClientException e) {
            System.out.println(e.getMessage());
        }
    }

    public SelectedClassDto saveSelectedClass(SelectedClassDto selectedClassDto){
        try {
            ResponseEntity<SelectedClassDto> response = restTemplate.postForEntity(saveselectedclassurl,selectedClassDto,SelectedClassDto.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return null;
            }
        } catch (RestClientException e) {
            return null;
        }
    }

    public SelectedClassDto updateSelectedClassDto(SelectedClassDto selectedClassDto,int id){
        HttpEntity<SelectedClassDto> requestEntity = new HttpEntity<>(selectedClassDto);
        try {
            ResponseEntity<SelectedClassDto> response = restTemplate.exchange(updateselectedclassurl + "/" + id,
                    HttpMethod.PUT,
                    requestEntity,
                    SelectedClassDto.class);
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
