package com.example.taskservice.Service;

import com.example.taskservice.Dto.ScheduleClassDto;
import com.example.taskservice.Dto.SelectedClassDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TASKSERVICE {
    @Autowired
    private ScheduleClassService scheduleClassService;
    @Autowired
    private SelectedClassService selectedClassService;
    public List<ScheduleClassDto> getAllschedule(){
        return scheduleClassService.getAllScheduleClass();
    }
    public List<SelectedClassDto> getAllSelectedClass(){
        return selectedClassService.getAllSelectedClass();
    }
    public boolean checkcapacity(int id){
        if (scheduleClassService.checkCapacity(id)){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean checkDulicate(int id){
        List<SelectedClassDto> selectedClassDtos=selectedClassService.getAllSelectedClass();
        ScheduleClassDto scheduleClassDto=scheduleClassService.getAllScheduleByIdClass(id);
        ScheduleClassDto scheduleClassDto1=new ScheduleClassDto();
        if(scheduleClassService.checkDuplicate(id,selectedClassDtos)){
            boolean check=false;
            int idl=-1;
            for (SelectedClassDto x:selectedClassDtos){
                if (x.getTenmh().equals(scheduleClassDto.getTenmh())){
                    check=true;
                    idl=x.getId();
                    scheduleClassDto1=scheduleClassService.getAllScheduleByIdClass(x.getScheduleclass_id());
                }
            }
            if (check){
                SelectedClassDto selectedClassDto=new SelectedClassDto();
                selectedClassDto.setScheduleclass_id(scheduleClassDto.getId());
                selectedClassDto.setTenmh(scheduleClassDto.getTenmh());
                selectedClassDto.setTengiangvien(scheduleClassDto.getTengiangvien());
                selectedClassDto.setTc(scheduleClassDto.getTc());
                selectedClassDto.setNhom(scheduleClassDto.getNhom());
                selectedClassDto.setMamh(scheduleClassDto.getMamh());
                selectedClassDto.setLichhoc(scheduleClassDto.getLichhoc());
                selectedClassDto.setNgaydangky("22/04/2024");
                selectedClassService.updateSelectedClassDto(selectedClassDto,idl);
                scheduleClassDto.setConlai(scheduleClassDto.getConlai()-1);
                scheduleClassService.updateslot(scheduleClassDto);
                scheduleClassDto1.setConlai(scheduleClassDto1.getConlai()+1);
                scheduleClassService.updateslot(scheduleClassDto1);
                return true;
            }
            else{
                SelectedClassDto selectedClassDto=new SelectedClassDto();
                selectedClassDto.setScheduleclass_id(scheduleClassDto.getId());
                selectedClassDto.setTenmh(scheduleClassDto.getTenmh());
                selectedClassDto.setTengiangvien(scheduleClassDto.getTengiangvien());
                selectedClassDto.setTc(scheduleClassDto.getTc());
                selectedClassDto.setNhom(scheduleClassDto.getNhom());
                selectedClassDto.setMamh(scheduleClassDto.getMamh());
                selectedClassDto.setLichhoc(scheduleClassDto.getLichhoc());
                selectedClassDto.setNgaydangky("22/04/2024");
                selectedClassService.saveSelectedClass(selectedClassDto);
                scheduleClassDto.setConlai(scheduleClassDto.getConlai()-1);
                scheduleClassService.updateslot(scheduleClassDto);
                return true;
            }
        }
        else{
            return false;
        }
    }
    public void DeleteSelectedClass(int id){
        SelectedClassDto selectedClassDto= selectedClassService.getSelectedClassByid(id);
        ScheduleClassDto scheduleClassDto=scheduleClassService.getAllScheduleByIdClass(selectedClassDto.getScheduleclass_id());
        scheduleClassDto.setConlai(scheduleClassDto.getConlai()+1);
        System.out.println(scheduleClassDto);
        System.out.println(scheduleClassDto.getConlai());
        scheduleClassService.updateslot(scheduleClassDto);
        selectedClassService.deleteSelectedClass(id);
    }
}
