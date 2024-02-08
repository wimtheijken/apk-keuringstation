package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.TeacherDto;
import nl.theijken.apkkeuringsation.model.Teacher;
import nl.theijken.apkkeuringsation.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    // mapperfunctie maken
    public TeacherDto createTeacher(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherDto.firstName);
        teacher.setLastName(teacherDto.lastName);
        teacher.setDob(teacherDto.dob);
        repository.save(teacher);
        teacherDto.id = teacher.getId();

        return teacherDto;
    }

    public List<TeacherDto> GetTeacher() {
        List<Teacher> teachers = repository.findAll();
        List<TeacherDto> teacherDtos = new ArrayList<>();

        for(Teacher teacher : teachers) {
            TeacherDto teacherDto = new TeacherDto();

            teacherDto.id = teacher.getId();

            teacherDto.firstName = teacher.getFirstName();

            teacherDto.lastName = teacher.getLastName();

            teacherDto.dob = teacher.getDob();

            teacherDtos.add(teacherDto);

        }

        return teacherDtos;

    }
}
