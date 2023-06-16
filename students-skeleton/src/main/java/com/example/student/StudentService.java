package com.example.student;

import com.example.student.dto.StudentDto;
import com.example.student.entity.StudentEntity;
import com.example.student.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public StudentDto createStudent(StudentDto dto) {
        StudentEntity entity = new StudentEntity();
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        return StudentDto.fromEntity(this.repository.save(entity));
        // 새로 등록된 DTO를 리턴
    }

    // READ
    public StudentDto readStudent(Long id) {
        Optional<StudentEntity> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) return StudentDto.fromEntity(optionalEntity.get());
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // READ ALL
    public List<StudentDto> readStudentAll() {
        // 모든 조회 entity 조회 repository method
        List<StudentDto> studentDtoList = new ArrayList<>();
        // 모든 학생 엔티티를 조회한 결과를 studentEntityList에 할당
        for (StudentEntity studentEntity : repository.findAll()) {
            studentDtoList.add(StudentDto.fromEntity(studentEntity));
        }
        return studentDtoList;
    }

    // UPDATE
    public StudentDto updateStudent(Long id, StudentDto dto) {
        // id로 해당 객체를 회수하고 save를 하는것이 좋음
        Optional<StudentEntity> optionalEntity
                =repository.findById(id);
        if(optionalEntity.isPresent()){
            StudentEntity targetEntity = optionalEntity.get();

            targetEntity.setName(dto.getName());
            targetEntity.setAge(dto.getAge());
            targetEntity.setPhone(dto.getPhone());
            targetEntity.setEmail(dto.getEmail());
            repository.save(targetEntity);
            return StudentDto.fromEntity(repository.save(targetEntity));
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // DELETE
    public void deleteStudent(Long id) {
        // existsById
        if(this.repository.existsById(id)) {
            this.repository.deleteById(id);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
