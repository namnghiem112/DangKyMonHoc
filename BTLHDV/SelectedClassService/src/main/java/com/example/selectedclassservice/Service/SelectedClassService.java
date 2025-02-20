package com.example.selectedclassservice.Service;

import com.example.selectedclassservice.Dto.SelectedClassDto;
import com.example.selectedclassservice.Model.SelectedClass;
import com.example.selectedclassservice.Repository.SelectedClassRepository;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SelectedClassService {
    @Autowired
    private SelectedClassRepository selectedClassRepository;
    public SelectedClassDto convertToDto(SelectedClass selectedClass){
        ModelMapper modelMapper=new ModelMapper();
        SelectedClassDto selectedClassDto =modelMapper.map(selectedClass,SelectedClassDto.class);
        return selectedClassDto;
    }

    public List<SelectedClassDto> getAll(){
        List<SelectedClass> selectedClasses=selectedClassRepository.findAll();
        List<SelectedClassDto> selectedClassDtos = selectedClasses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return  selectedClassDtos;
    }

    public void save(SelectedClassDto selectedClassDto){
        ModelMapper modelMapper=new ModelMapper();
        SelectedClass selectedClass=modelMapper.map(selectedClassDto,SelectedClass.class);
        System.out.println(selectedClassDto);
        System.out.println(selectedClass);
        selectedClassRepository.save(selectedClass);
    }
    public void delete(int id){
        selectedClassRepository.deleteById(id);
    }
    public SelectedClassDto getSelectedClassById(int id){
        SelectedClass selectedClass=selectedClassRepository.getSelectedClassById(id);
        ModelMapper modelMapper=new ModelMapper();
        SelectedClassDto selectedClassDto=modelMapper.map(selectedClass,SelectedClassDto.class);
        return selectedClassDto;
    }
    public void update(SelectedClassDto selectedClassDto){
        ModelMapper modelMapper=new ModelMapper();
        SelectedClass selectedClass=modelMapper.map(selectedClassDto,SelectedClass.class);
        selectedClassRepository.save(selectedClass);
    }
}
