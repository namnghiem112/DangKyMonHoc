package com.example.taskservice.Controller;

import com.example.taskservice.Dto.ScheduleClassDto;
import com.example.taskservice.Dto.SelectedClassDto;
import com.example.taskservice.Service.TASKSERVICE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/task")
public class taskcontroller {
    @Autowired
    private TASKSERVICE taskservice;

    @GetMapping("/getscheduleclass")
    public ResponseEntity<List<ScheduleClassDto>> getAllClass(){
        return ResponseEntity.ok(taskservice.getAllschedule());
    }
    @GetMapping("/getselectedclass")
    public ResponseEntity<List<SelectedClassDto>> getAllSelectedClass(){
        return ResponseEntity.ok(taskservice.getAllSelectedClass());
    }
    @PostMapping("/checkcapacity/{id}")
    public ResponseEntity<?> CheckCapacity(@PathVariable int id){
        if (taskservice.checkcapacity(id))
        {
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.badRequest().body("Lớp học đã full");
        }
    }

    @PostMapping("/checkduplicate/{id}")
    public ResponseEntity<?> CheckDuplicate(@PathVariable int id){
        if (taskservice.checkDulicate(id))
        {
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.badRequest().body("Lớp học đã bị trùng lịch");
        }
    }
    @DeleteMapping("/deleteselectedclass/{id}")
    public ResponseEntity<Void> DeleteSelectedClass(@PathVariable int id){
        taskservice.DeleteSelectedClass(id);
        return ResponseEntity.ok().build();
    }
}
