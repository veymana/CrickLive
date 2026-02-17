package com.crick.livecrick.config;

import com.crick.livecrick.entity.Player;
import com.crick.livecrick.entity.User;
import com.crick.livecrick.repository.PlayerRepository;
import com.crick.livecrick.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {
    
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PlayerRepository playerRepository) {
        return args -> {
            // Create default admin user if no admin exists
            boolean adminExists = userRepository.findAll().stream()
                    .anyMatch(user -> "ADMIN".equals(user.getRole()));
            
            if (!adminExists) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                User admin = new User();
                admin.setEmail("admin@cricklive.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.setName("Admin");
                admin.setRole("ADMIN");
                admin.setActive(true);
                admin.setCreatedAt(System.currentTimeMillis());
                
                userRepository.save(admin);
                log.info("===============================================");
                log.info("DEFAULT ADMIN USER CREATED");
                log.info("Email: admin@cricklive.com");
                log.info("Password: admin123");
                log.info("PLEASE CHANGE THE PASSWORD AFTER FIRST LOGIN");
                log.info("===============================================");
            } else {
                log.info("Admin user already exists");
            }
            
            // Create 24 sample players if database is empty
            if (playerRepository.findAll().isEmpty()) {
                List<Player> players = create24SamplePlayers();
                players.forEach(playerRepository::save);
                log.info("===============================================");
                log.info("24 SAMPLE PLAYERS CREATED");
                log.info("Players are ready for team assignment");
                log.info("===============================================");
            } else {
                log.info("Players already exist in database");
            }
        };
    }
    
    private List<Player> create24SamplePlayers() {
        List<Player> players = new ArrayList<>();
        
        // Team 1 Players (12 players)
        players.add(createPlayer("Virat Kohli", "BATSMAN", "RIGHT_HAND", null, "India"));
        players.add(createPlayer("Rohit Sharma", "BATSMAN", "RIGHT_HAND", null, "India"));
        players.add(createPlayer("KL Rahul", "WICKET_KEEPER", "RIGHT_HAND", null, "India"));
        players.add(createPlayer("Shubman Gill", "BATSMAN", "RIGHT_HAND", null, "India"));
        players.add(createPlayer("Hardik Pandya", "ALL_ROUNDER", "RIGHT_HAND", "RIGHT_ARM_FAST", "India"));
        players.add(createPlayer("Ravindra Jadeja", "ALL_ROUNDER", "LEFT_HAND", "LEFT_ARM_SPIN", "India"));
        players.add(createPlayer("Jasprit Bumrah", "BOWLER", "RIGHT_HAND", "RIGHT_ARM_FAST", "India"));
        players.add(createPlayer("Mohammed Shami", "BOWLER", "RIGHT_HAND", "RIGHT_ARM_FAST", "India"));
        players.add(createPlayer("Kuldeep Yadav", "BOWLER", "LEFT_HAND", "LEFT_ARM_SPIN", "India"));
        players.add(createPlayer("Ravichandran Ashwin", "ALL_ROUNDER", "RIGHT_HAND", "RIGHT_ARM_SPIN", "India"));
        players.add(createPlayer("Rishabh Pant", "WICKET_KEEPER", "LEFT_HAND", null, "India"));
        players.add(createPlayer("Shreyas Iyer", "BATSMAN", "RIGHT_HAND", null, "India"));
        
        // Team 2 Players (12 players)
        players.add(createPlayer("Joe Root", "BATSMAN", "RIGHT_HAND", null, "England"));
        players.add(createPlayer("Ben Stokes", "ALL_ROUNDER", "LEFT_HAND", "RIGHT_ARM_FAST", "England"));
        players.add(createPlayer("Jos Buttler", "WICKET_KEEPER", "RIGHT_HAND", null, "England"));
        players.add(createPlayer("Jonny Bairstow", "WICKET_KEEPER", "RIGHT_HAND", null, "England"));
        players.add(createPlayer("James Anderson", "BOWLER", "RIGHT_HAND", "RIGHT_ARM_FAST", "England"));
        players.add(createPlayer("Stuart Broad", "BOWLER", "LEFT_HAND", "RIGHT_ARM_FAST", "England"));
        players.add(createPlayer("Moeen Ali", "ALL_ROUNDER", "LEFT_HAND", "RIGHT_ARM_SPIN", "England"));
        players.add(createPlayer("Jofra Archer", "BOWLER", "RIGHT_HAND", "RIGHT_ARM_FAST", "England"));
        players.add(createPlayer("Mark Wood", "BOWLER", "RIGHT_HAND", "RIGHT_ARM_FAST", "England"));
        players.add(createPlayer("Adil Rashid", "BOWLER", "RIGHT_HAND", "RIGHT_ARM_SPIN", "England"));
        players.add(createPlayer("Harry Brook", "BATSMAN", "RIGHT_HAND", null, "England"));
        players.add(createPlayer("Sam Curran", "ALL_ROUNDER", "LEFT_HAND", "LEFT_ARM_FAST", "England"));
        
        return players;
    }
    
    private Player createPlayer(String name, String role, String battingStyle, String bowlingStyle, String country) {
        Player player = new Player();
        player.setName(name);
        player.setRole(role);
        player.setBattingStyle(battingStyle);
        player.setBowlingStyle(bowlingStyle);
        player.setCountry(country);
        player.setDateOfBirth("1990-01-01");
        player.setPhoto("https://via.placeholder.com/150");
        
        // Initialize statistics
        Player.PlayerStatistics stats = new Player.PlayerStatistics();
        stats.setMatches(50);
        stats.setInnings(50);
        stats.setRuns(2000);
        stats.setAverage(40.0);
        stats.setStrikeRate(130.0);
        stats.setHighestScore(150);
        stats.setHundreds(5);
        stats.setFifties(15);
        
        if (role.equals("BOWLER") || role.equals("ALL_ROUNDER")) {
            stats.setWickets(75);
            stats.setBowlingAverage(25.5);
            stats.setEconomy(5.5);
            stats.setBestBowling("5/25");
        } else {
            stats.setWickets(0);
            stats.setBowlingAverage(0.0);
            stats.setEconomy(0.0);
            stats.setBestBowling("");
        }
        
        player.setStatistics(stats);
        
        return player;
    }
}
