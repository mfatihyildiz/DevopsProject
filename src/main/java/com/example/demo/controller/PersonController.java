package com.example.demo.controller;

import com.example.demo.repository.PersonRepository;
import com.example.demo.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PersonController {
    @Value("${application.bucket.name}")
    private String bucketName;
    private final S3Client s3Client;
    private final PersonRepository personRepository;

    @GetMapping("/")
    public String showAllPeople(Model model) {
        List<Person> people = personRepository.findAll();
        model.addAttribute("people", people);
        return "people";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("person", new Person());
        return "add";
    }

    /*
    @PostMapping("/add")
    public String addPerson(@ModelAttribute Person person, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get("src/main/resources/static/images/" + file.getOriginalFilename());
                Files.write(path, bytes);
                person.setImgUrl("/images/" + file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        personRepository.save(person);
        return "redirect:/";
    }
*/

    @PostMapping("/add")
    public String addPerson(@ModelAttribute Person person, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            uploadFile(file);
            person.setImgUrl("https://fatihsbucket.s3-eu-north-1.amazonaws.com/" + file.getOriginalFilename());
        }
        personRepository.save(person);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            model.addAttribute("person", optionalPerson.get());
            return "update";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/update/{id}")
    public String updatePerson(@PathVariable Long id, @ModelAttribute Person updatedPerson) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            person.setName(updatedPerson.getName());
            person.setAddress(updatedPerson.getAddress());
            personRepository.save(person);
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable Long id, Model model) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            model.addAttribute("person", optionalPerson.get());
            return "delete";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/delete/{id}")
    public String deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
        return "redirect:/";
    }

    private void uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            log.info("File uploaded successfully::{}",fileName);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("File upload error::{}",fileName);
        }
    }
}
