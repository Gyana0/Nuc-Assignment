package com.demo.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.Candidate;

@RestController
public class CandidateController {
    
    ConcurrentHashMap<String, Candidate> candidates = new ConcurrentHashMap<>();

    @PostMapping("/entercandidate")
    public String entercandidate(@RequestParam String name) {
    	
        // Check if candidate already exists
        if(candidates.containsKey(name)) {
            return "Candidate already exists";
        }
        // Creating new candidate with vote count of 0
        Candidate candidate = new Candidate(name, 0);
        
        // Adding candidate to the list
        candidates.put(name, candidate);
        return "Candidate added successfully";
    }

    @PostMapping("/castvote")
    public int castvote(@RequestParam String name) {
    	
        // Check if candidate exists
        if(!candidates.containsKey(name)) {
            return -1;
        }
        // Increment vote count of candidate
        Candidate candidate = candidates.get(name);
        candidate.getVoteCount();
        
        // Update candidate in the list
        candidates.put(name, candidate);
        return candidate.getVoteCount();
    }

    @GetMapping("/countvote")
    public int countvote(@RequestParam String name) {
    	
        // Check if candidate exists
        if(!candidates.containsKey(name)) {
            return -1;
        }
        
        // Return vote count of candidate
        return candidates.get(name).getVoteCount();
    }

    
    
    @GetMapping("/listvote")
    public List<Candidate> listvote() {
        // Return list of all candidates and their vote counts
        return new ArrayList<>(candidates.values());
    }

    @GetMapping("/getwinner")
    public String getwinner() {
        // Find candidate with highest vote count
        int maxVotes = 0;
        String winner = "";
        for(Candidate candidate : candidates.values()) {
            if(candidate.getVoteCount() > maxVotes) {
                maxVotes = candidate.getVoteCount();
                winner = candidate.getName();
            }
        }
        return winner;
    }
}
