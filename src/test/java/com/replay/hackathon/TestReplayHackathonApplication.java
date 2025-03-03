package com.replay.hackathon;

import org.springframework.boot.SpringApplication;

public class TestReplayHackathonApplication {

    public static void main(String[] args) {
        SpringApplication.from(ReplayHackathonApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
