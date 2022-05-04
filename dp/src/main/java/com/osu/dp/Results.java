package com.osu.dp;

import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Results implements Comparable<Results>{
    String pattern;
    double similarity;
    int distance;

    public Results(String pattern, double similarity) {
        this.pattern = pattern;
        this.similarity = similarity;
        this.distance = 0;
    }

    public Results(String pattern, double similarity, int distance) {
        this.pattern = pattern;
        this.similarity = similarity;
        this.distance = distance;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Results o) {
        return Double.compare(o.getSimilarity(), this.similarity);
    }

    public static HashMap<String, Double> sortByValue(HashMap<String, Double> similarityMap) {
        List<Map.Entry<String, Double> > list = new LinkedList<Map.Entry<String, Double> >(similarityMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<String, Double> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static Map<String,Double> getElements(Map<String,Double> sortedMap, int elementsToReturn) {
        return sortedMap.entrySet()
                .stream()
                .limit(elementsToReturn)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (v1,v2) -> v1, TreeMap::new));
    }
}
