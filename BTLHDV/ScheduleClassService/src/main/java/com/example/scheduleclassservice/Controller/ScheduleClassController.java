package com.example.scheduleclassservice.Controller;

import com.example.scheduleclassservice.Dto.ScheduleClassDto;
import com.example.scheduleclassservice.Dto.SelectedClassDto;
import com.example.scheduleclassservice.Service.ScheduleClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/class")
public class ScheduleClassController {
    @Autowired
    private ScheduleClassService scheduleClassService;

    @GetMapping
    public ResponseEntity<List<ScheduleClassDto>> getAllClass(){
        return ResponseEntity.ok(scheduleClassService.getAllClass());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleClassDto> getAllClass(@PathVariable int id){
        return ResponseEntity.ok(scheduleClassService.getClassByID(id));
    }
    @PutMapping("/updateslot")
    public ResponseEntity<?> update(@RequestBody ScheduleClassDto scheduleClassDto){
        scheduleClassService.update(scheduleClassDto);
        return ResponseEntity.ok(scheduleClassDto);
    }
    @PostMapping("/checkcapacity/{id}")
    public ResponseEntity<?> CheckCapacity(@PathVariable int id){
        if (scheduleClassService.checkCapacity(id)){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.badRequest().body("Lớp học đã full");
        }
    }
    @PostMapping("/checkduplicate/{id}")
    public ResponseEntity<?> CheckDuplicate(@PathVariable int id,@RequestBody List<SelectedClassDto> selectedClassDtos) throws ParseException {
        if (scheduleClassService.checkDuplicate(id,selectedClassDtos)){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.badRequest().body("Lớp học đã bị trùng lịch");
        }
    }


 }
