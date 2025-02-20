package com.example.taskservice.Service;

import com.example.taskservice.Dto.SelectedClassDto;
import com.example.taskservice.Dto.ScheduleClassDto;
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
public class ScheduleClassService {

    private final String scheduleclassurl = "http://localhost:8082/api/class";
    private final String checkcapacity = "http://localhost:8082/api/class/checkcapacity";

    private final String updateslot = "http://localhost:8082/api/class/updateslot";
    private final String checkduplicate = "http://localhost:8082/api/class/checkduplicate";
    private final RestTemplate restTemplate;
    public ScheduleClassService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ScheduleClassDto> getAllScheduleClass(){
        try {
            ResponseEntity<List<ScheduleClassDto>> response = restTemplate.exchange(scheduleclassurl, HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ScheduleClassDto>>() {});
            if (response.getStatusCode() == HttpStatus.OK) {
                return (List<ScheduleClassDto>) response.getBody();
            } else {
                return null;
            }
        } catch (RestClientException e) {
            return null;
        }
    }

    public ScheduleClassDto getAllScheduleByIdClass(int id){
        try {
            ResponseEntity<ScheduleClassDto> response = restTemplate.getForEntity(scheduleclassurl + "/" + id, ScheduleClassDto.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return null;
            }
        } catch (RestClientException e) {
            return null;
        }
    }
    public boolean checkCapacity(int id){
        try {
            ResponseEntity<ScheduleClassDto> response = restTemplate.postForEntity(checkcapacity+"/"+id,null,ScheduleClassDto.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return true;

            } else {
                return false;
            }
        } catch (RestClientException e) {
            return false;
        }
    }

    public boolean checkDuplicate(int id,List<SelectedClassDto> selectedClassDtos){
        try {
            ResponseEntity<ScheduleClassDto> response = restTemplate.postForEntity(checkduplicate+"/"+id,selectedClassDtos,ScheduleClassDto.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return true;

            } else {
                return false;
            }
        } catch (RestClientException e) {
            return false;
        }
    }

    public ScheduleClassDto updateslot (ScheduleClassDto scheduleClassDto){
        HttpEntity<ScheduleClassDto> requestEntity = new HttpEntity<>(scheduleClassDto);
        try {
            ResponseEntity<ScheduleClassDto> response = restTemplate.exchange(updateslot ,
                    HttpMethod.PUT,
                    requestEntity,
                    ScheduleClassDto.class);
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
