package com.uniesp.infrastructure.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Migra o handler Go:
 *   http.HandleFunc("/health", func(w http.ResponseWriter, r *http.Request) {
 *       w.WriteHeader(http.StatusOK)
 *       w.Write([]byte("Sistema Operante"))
 *   })
 *
 * Agora retorna JSON estruturado — usado no Smoke Test do pipeline
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "status",    "UP",
                "message",   "Sistema Operante",
                "timestamp", LocalDateTime.now().toString()
        ));
    }
}