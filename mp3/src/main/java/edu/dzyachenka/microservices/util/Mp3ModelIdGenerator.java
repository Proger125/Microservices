package edu.dzyachenka.microservices.util;

public class Mp3ModelIdGenerator {
    private static Integer mp3Id = 0;

    public static Integer generateMp3Id() {
        return ++mp3Id;
    }
}
