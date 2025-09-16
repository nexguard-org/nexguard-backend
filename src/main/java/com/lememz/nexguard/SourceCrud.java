package com.lememz.nexguard;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.criteria.CriteriaQuery;

@RestController
@RequestMapping("/sources/")
public class SourceCrud {
    
    private final SessionFactory db;

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
    public Source postSource(@RequestBody Source source) {
        Session session = db.openSession();
        session.beginTransaction();
        session.persist(source);
        session.getTransaction().commit();
        return source;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Source> updateSource(@PathVariable int id, @RequestBody Source sourceUpdate) {
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
    public ResponseEntity<Source> deleteSource(@PathVariable int id) {
        Session session = db.openSession();
        Source source = session.find(Source.class, id);
        if(source == null) {
            return ResponseEntity.notFound().build();
        }
        session.beginTransaction();
        session.remove(source);
        session.getTransaction().commit();
        return ResponseEntity.ok(source);
    }
}
