package com.example.selectedclassservice.Controller;

import com.example.selectedclassservice.Dto.SelectedClassDto;
import com.example.selectedclassservice.Service.SelectedClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/selectedclass")
public class SelectedClassController {
    @Autowired
    private SelectedClassService selectedClassService;
    @GetMapping
    public  ResponseEntity<List<SelectedClassDto>> getAll(){
        return ResponseEntity.ok(selectedClassService.getAll());
    }

    @GetMapping("/{id}")
    public  ResponseEntity<SelectedClassDto> getSelectedClassById(@PathVariable int id){
        return ResponseEntity.ok(selectedClassService.getSelectedClassById(id));
    }
    @PostMapping("/save")
    public ResponseEntity<SelectedClassDto> addSelectedClass(@RequestBody SelectedClassDto selectedClassDto){
        selectedClassService.save(selectedClassDto);
        return ResponseEntity.ok(selectedClassDto);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<SelectedClassDto> updateSelectedClass(@RequestBody SelectedClassDto selectedClassDto,@PathVariable int id){
        selectedClassDto.setId(id);
        selectedClassService.update(selectedClassDto);
        return ResponseEntity.ok(selectedClassDto);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSelectedClass(@PathVariable int id){
        selectedClassService.delete(id);
        return ResponseEntity.ok().build();
    }
}
