package com.example.scheduleclassservice.Service;

import com.example.scheduleclassservice.Dto.ScheduleClassDto;
import com.example.scheduleclassservice.Dto.SelectedClassDto;
import com.example.scheduleclassservice.Dto.SubjectDto;
import com.example.scheduleclassservice.Model.ScheduleClass;
import com.example.scheduleclassservice.Repository.ScheduleClassRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ScheduleClassService {
    @Autowired
    private ScheduleClassRepository scheduleClassRepository;
    @Autowired
    private SubjectService subjectService;
    public static class Lich {
        private Date ngaybatdau, ngayketthuc;
        private int thu;
        private String giobatdau, gioketthuc;

        public Lich() {
        }

        public Lich(Date ngaybatdau, Date ngayketthuc, int thu, String giobatdau, String gioketthuc) {
            this.ngaybatdau = ngaybatdau;
            this.ngayketthuc = ngayketthuc;
            this.thu = thu;
            this.giobatdau = giobatdau;
            this.gioketthuc = gioketthuc;
        }

        public Date getNgaybatdau() {
            return ngaybatdau;
        }

        public void setNgaybatdau(Date ngaybatdau) {
            this.ngaybatdau = ngaybatdau;
        }

        public Date getNgayketthuc() {
            return ngayketthuc;
        }

        public void setNgayketthuc(Date ngayketthuc) {
            this.ngayketthuc = ngayketthuc;
        }

        public int getThu() {
            return thu;
        }

        public void setThu(int thu) {
            this.thu = thu;
        }

        public String getGiobatdau() {
            return giobatdau;
        }

        public void setGiobatdau(String giobatdau) {
            this.giobatdau = giobatdau;
        }

        public String getGioketthuc() {
            return gioketthuc;
        }

        public void setGioketthuc(String gioketthuc) {
            this.gioketthuc = gioketthuc;
        }

        @Override
        public String toString() {
            return "Lich{" + "ngaybatdau=" + ngaybatdau + ", ngayketthuc=" + ngayketthuc + ", thu=" + thu + ", giobatdau=" + giobatdau + ", gioketthuc=" + gioketthuc + '}';
        }
    }
    public static Lich chuyen(String input) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String[] mang = input.split("thu ");
        int thu = Integer.parseInt(String.valueOf(mang[1].charAt(0)));
        String regexDate = "\\d{1,2}/\\d{1,2}/\\d{4}";
        String regexTime = "\\d{1,2}:\\d{2}";

        Pattern patternDate = Pattern.compile(regexDate);
        Pattern patternTime = Pattern.compile(regexTime);

        Matcher matcherDate = patternDate.matcher(input);
        Matcher matcherTime = patternTime.matcher(input);

        int dem =0,dem1=0;
        String ngaybatdau ="",ngayketthuc="";
        while (matcherDate.find()) {
            if(dem==0) ngaybatdau = matcherDate.group();
            else ngayketthuc = matcherDate.group();
            dem++;
        }
        Date dateStart = formatter.parse(ngaybatdau);
        Date dateEnd = formatter.parse(ngayketthuc);
        String giobatdau="", gioketthuc="";
        while (matcherTime.find()) {
            if(dem1==0) giobatdau = matcherTime.group();
            else gioketthuc = matcherTime.group();
            dem1++;
        }
        return new Lich(dateStart,dateEnd,thu,giobatdau,gioketthuc);
    }
    private ScheduleClassDto convertToDto(ScheduleClass scheduleClass) {
        SubjectDto subjectDto=subjectService.getSubjectById(scheduleClass.getSubject_id());
        ScheduleClassDto dto=new ScheduleClassDto();
        dto.setId(scheduleClass.getId());
        dto.setMamh(subjectDto.getMamh());
        dto.setTenmh(subjectDto.getTen());
        dto.setTengiangvien(scheduleClass.getTengiangvien());
        dto.setNhom(scheduleClass.getNhom());
        dto.setTc(subjectDto.getTc());
        dto.setLichhoc(scheduleClass.getLichhoc());
        dto.setSlot(scheduleClass.getSlot());
        dto.setConlai(scheduleClass.getConlai());
        dto.setSubject_id(subjectDto.getId());
        return dto;
    }

    public List<ScheduleClassDto> getAllClass(){
        List<ScheduleClass> scheduleClasses=scheduleClassRepository.findAll();
        List<ScheduleClassDto> scheduleClassDtos = scheduleClasses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return  scheduleClassDtos;
    }
    public ScheduleClassDto getClassByID(int id){
        return convertToDto(scheduleClassRepository.findScheduleClassById(id));
    }
    public boolean checkCapacity(int id){
        ScheduleClass scheduleClass=scheduleClassRepository.findScheduleClassById(id);
        if (scheduleClass.getConlai()<1){
            return false;
        }
        else {
            return true;
        }
    }
    public void update(ScheduleClassDto scheduleClassDto){
        ModelMapper modelMapper=new ModelMapper();
        ScheduleClass scheduleClass=modelMapper.map(scheduleClassDto,ScheduleClass.class);
        scheduleClassRepository.save(scheduleClass);
    }
    public boolean checkDuplicate(int id, List<SelectedClassDto> selectedClassDtos) throws ParseException{

        for (SelectedClassDto x:selectedClassDtos){
            ScheduleClass scheduleClass=scheduleClassRepository.findScheduleClassById(id);
            Lich lich1 = chuyen(x.getLichhoc());
            Lich lich2 = chuyen(scheduleClass.getLichhoc());
            if(lich2.getNgaybatdau().compareTo(lich1.getNgayketthuc())>=0 ||  lich2.getNgayketthuc().compareTo(lich1.getNgaybatdau())<=0){
                continue;
            }
            else {
                if(lich2.getThu()!=lich1.getThu()){
                    continue;
                }
                else {
                    if(lich2.getGiobatdau().compareTo(lich1.getGioketthuc())>=0 || lich1.getGiobatdau().compareTo(lich2.getGioketthuc())>=0){
                        continue;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
