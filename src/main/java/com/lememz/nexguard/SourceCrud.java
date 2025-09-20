package com.lememz.nexguard;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.criteria.CriteriaQuery;

@RestController
@RequestMapping("/sources/")
public class SourceCrud {

    @PersistenceContext
    private EntityManager em;
    @Value("${API_KEY}")
    private String apiKey;

    @GetMapping("/{id}")
    public Source getSource(@PathVariable int id) {
        return em.find(Source.class, id);
    }

    @GetMapping("/")
    public List<Source> getSources() {
        CriteriaQuery<Source> query = em.getCriteriaBuilder().createQuery(Source.class);
        return em.createQuery(query.select(query.from(Source.class))).getResultList();
    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<Source> postSource(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @RequestBody Source source) {
        if(!auth.equals(apiKey)) {
            return ResponseEntity.status(401).build();
        }
        em.persist(source);
        return ResponseEntity.ok(source);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Source> updateSource(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @PathVariable int id, @RequestBody Source sourceUpdate) {
        Source source = em.find(Source.class, id);
        if(source == null) {
            return ResponseEntity.notFound().build();
        }
        if(source.getPassword() != null &&
                !apiKey.equals(auth) &&
                !new BCryptPasswordEncoder().matches(auth, source.getPassword())
        ) {
            return ResponseEntity.status(401).build();
        }
        if(sourceUpdate.getName() != null) source.setName(sourceUpdate.getName());
        if(sourceUpdate.getPassword() != null) source.setName(sourceUpdate.getPassword());
        if(sourceUpdate.getValidAddresses() != null) source.updateValidAddresses(sourceUpdate.getValidAddresses(), em);
        return ResponseEntity.ok(source);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSource(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @PathVariable int id) {
        if(!auth.equals(apiKey)) {
            return ResponseEntity.status(401).build();
        }
        Source source = em.find(Source.class, id);
        if(source == null) {
            return ResponseEntity.notFound().build();
        }
        em.remove(source);
        return ResponseEntity.ok().build();
    }
}
