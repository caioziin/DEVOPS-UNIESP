package com.uniesp.infrastructure.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Adapter de ENTRADA — expõe o endpoint /health via HTTP.
 *
 * Substitui o handler Go original:
 *   http.HandleFunc("/health", func(w http.ResponseWriter, r *http.Request) {
 *       w.WriteHeader(http.StatusOK)
 *       w.Write([]byte("Sistema Operante"))
 *   })
 *
 * Melhorias em relação ao Go:
 *   - Retorna JSON estruturado em vez de texto puro
 *   - Inclui timestamp para rastreabilidade
 *   - Usado diretamente no Smoke Test do pipeline (etapa de produção)
 */
@RestController // @RestController = @Controller + @ResponseBody (serializa tudo como JSON)
public class HealthController {

    /**
     * GET /health
     *
     * Contrato do endpoint:
     *   - HTTP 200 OK  → aplicação está no ar e saudável
     *   - Corpo JSON   → { "status": "UP", "message": "...", "timestamp": "..." }
     *
     * O pipeline usa este endpoint no Smoke Test assim:
     *   curl --fail http://localhost:8080/health
     * Se retornar qualquer coisa diferente de 2xx, o pipeline falha.
     */
    @GetMapping("/health") // mapeia GET /health
    public ResponseEntity<Map<String, Object>> health() {

        // ResponseEntity.ok() define automaticamente HTTP 200
        return ResponseEntity.ok(Map.of(

                // "status": "UP" — padrão de mercado (mesmo padrão do Spring Actuator)
                "status",    "UP",

                // "message" — texto legível para humanos, mantém compatibilidade com Go
                "message",   "Sistema Operante",

                // "timestamp" — momento exato da checagem, útil para debug no pipeline
                "timestamp", LocalDateTime.now().toString()
        ));
    }
}