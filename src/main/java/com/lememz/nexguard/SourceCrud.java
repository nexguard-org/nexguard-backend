package com.lememz.nexguard;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.criteria.CriteriaQuery;

@RestController
@RequestMapping("/sources/")
public class SourceCrud {
    
    private final SessionFactory db;
    @Value("${API_KEY}")
    private String apiKey;

    public SourceCrud(SessionFactory db) {
        this.db = db;
    }

    @GetMapping("/{id}")
    public Source getSource(@PathVariable int id) {
        Session session = db.openSession();
        return session.find(Source.class, id);
    }

    @GetMapping("/")
    public List<Source> getSources() {
        Session session = db.openSession();
        CriteriaQuery<Source> query = session.getCriteriaBuilder().createQuery(Source.class);
        return session.createSelectionQuery(query.select(query.from(Source.class))).getResultList();
    }

    @PostMapping("/")
    public ResponseEntity<Source> postSource(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @RequestBody Source source) {
        if(!auth.equals(apiKey)) {
            return ResponseEntity.status(401).build();
        }
        Session session = db.openSession();
        session.beginTransaction();
        session.persist(source);
        session.getTransaction().commit();
        return ResponseEntity.ok(source);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Source> updateSource(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @PathVariable int id, @RequestBody Source sourceUpdate) {
        if(!auth.equals(apiKey)) {
            return ResponseEntity.status(401).build();
        }
        Session session = db.openSession();
        Source source = session.find(Source.class, id);
        if(source == null) {
            return ResponseEntity.notFound().build();
        }
        session.beginTransaction();
        session.merge(sourceUpdate);
        session.getTransaction().commit();
        return ResponseEntity.ok(source);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSource(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @PathVariable int id) {
        if(!auth.equals(apiKey)) {
            return ResponseEntity.status(401).build();
        }
        Session session = db.openSession();
        Source source = session.find(Source.class, id);
        if(source == null) {
            return ResponseEntity.notFound().build();
        }
        session.beginTransaction();
        session.remove(source);
        session.getTransaction().commit();
        return ResponseEntity.ok().build();
    }
}
