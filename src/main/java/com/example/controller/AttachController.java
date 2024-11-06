package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.attach.AttachDTO;
import com.example.service.AttachService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@EnableMethodSecurity(prePostEnabled = true)
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/attach")
@Tag(name = "Attach Controller", description = "Api list for Attach, attach and other ....")
public class AttachController {

    private AttachService attachService;

    @PostMapping("/upload")
//    @Operation(summary = "upload api", description = "Api list attach create")
    public ResponseEntity<AttachDTO> create(@RequestParam("file") MultipartFile file) {
        log.info("upload attach  = {}", file.getOriginalFilename());
        AttachDTO response = attachService.saveAttach(file);
        return ResponseEntity.ok(response);
    }

 /*   @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
        AttachDTO attachDTO = attachService.saveAttach(file);
        return ResponseEntity.ok().body(attachDTO);
    }*/

    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
        return attachService.loadImage(fileName);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity download(@PathVariable("fileName") String fileName) {
        return attachService.download(fileName);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/pagination")
    public ResponseEntity<PageImpl<AttachDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                          @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageImpl<AttachDTO> response = attachService.getAttachPagination(page - 1, size);
        return ResponseEntity.ok().body(response);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
        Boolean delete = attachService.delete(id);
        return ResponseEntity.ok().body(delete);
    }
}
