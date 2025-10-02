package com.lememz.nexguard;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Validate {

    @PersistenceContext
    private EntityManager em;
    
    @PostMapping("/validate")
    public ResponseEntity<Validate.Response> validate(@RequestBody Validate.Body body) {
        Source source = em.find(Source.class, body.sourceId());
        List<String> sourcesToCheck = source.getValidAddresses().stream()
                .filter(a -> a.getType() == body.type()).map(ValidAddress::getAddress).toList();
        if(sourcesToCheck.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        boolean isSourceValid = sourcesToCheck.contains(body.source);
        List<String> templates = source.getValidAddresses().stream()
                .filter(a -> a.getType() == ValidAddress.Type.TEMPLATE).map(ValidAddress::getAddress).toList();
        boolean isContentValid;
        if(!templates.isEmpty()) {
            isContentValid = templates.stream().anyMatch(body.content()::matches);
        }else {
            isContentValid = true;
        }
        return ResponseEntity.ok(new Validate.Response(isSourceValid, isContentValid));
    }

    public record Body(String source, String content, int sourceId, ValidAddress.Type type) {}
    public record Response(boolean isSourceValid, boolean isContentValid) {}
}
