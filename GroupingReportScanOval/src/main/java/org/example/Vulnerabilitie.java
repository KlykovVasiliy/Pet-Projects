package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Vulnerabilitie implements Comparator<Vulnerabilitie> {

    private String keyProgram;
    private String descProgram;
    private Set<String> bduSet;

    public Vulnerabilitie(String keyProgram, String descProgram, Set<String> bduSet) {
        this.keyProgram = keyProgram;
        this.descProgram = descProgram;
        this.bduSet = bduSet;
    }

    public String getDescProgram() {
        return descProgram;
    }

    public void addToBduList(String bdu) {
        bduSet.add(bdu);
    }

    public String getBdu() {
        return bduSet.stream()
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .sorted()
                .distinct()
                .collect(Collectors.joining(", "));
    }

    public String getKeyProgram() {
        return keyProgram;
    }

    @Override
    public String toString() {
        return keyProgram + "\n" + descProgram + "\n" + bduSet;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compare(Vulnerabilitie o1, Vulnerabilitie o2) {
        return o1.keyProgram.compareTo(o2.keyProgram);
    }
}