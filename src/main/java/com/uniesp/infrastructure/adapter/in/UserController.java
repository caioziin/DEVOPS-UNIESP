package com.uniesp.infrastructure.adapter.in;

import com.uniesp.application.port.in.UserInputPort;
import com.uniesp.infrastructure.adapter.in.dto.UserRequest;
import com.uniesp.infrastructure.adapter.in.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // Depende da INTERFACE (port), nunca do UseCase diretamente — DIP
    private final UserInputPort userInputPort;

    public UserController(UserInputPort userInputPort) {
        this.userInputPort = userInputPort;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserResponse> body = userInputPort.findAll().stream()
                .map(UserResponse::fromDomain)
                .toList();
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(UserResponse.fromDomain(userInputPort.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest req) {
        UserResponse created = UserResponse.fromDomain(
                userInputPort.create(req.getName(), req.getEmail()));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest req) {
        return ResponseEntity.ok(UserResponse.fromDomain(
                userInputPort.update(id, req.getName(), req.getEmail())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userInputPort.delete(id);
        return ResponseEntity.noContent().build();
    }
}