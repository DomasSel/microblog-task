package com.task.service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class WordOperationsService {
	
	public int getWordCount(String body) {
		try {
			Pattern pattern = Pattern.compile("\\b\\w+\\b");
	        Matcher matcher = pattern.matcher(body);
	        int wordCount = matcher.results().mapToInt(match -> 1).sum();
	        
	        return wordCount;
	    } catch (Exception e) {
	    	throw e;
	    }
	}
	
	public Map<String, Integer> getWordRecurrenceMap(String body) {
		try {
			Map<String, Integer> wordRecurrenceMap = new TreeMap<>();
			Pattern pattern = Pattern.compile("\\b\\w+\\b");
	        Matcher matcher = pattern.matcher(body);
	        List<String> wordList = matcher.results().map(MatchResult::group).toList();
	        
	        wordList.forEach(
	        		word -> wordRecurrenceMap.compute(
	        				word, (key, value) ->
	        					(value == null) ? 1 : value + 1)
    		);
	        
	        return wordRecurrenceMap;
	    } catch (Exception e) {
	    	throw e;
	    }
	}
	
	public Map<String, Integer> sortMapByDescendingOrder(Map<String, Integer> map) {
		LinkedHashMap<String, Integer> sortedMap = map.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey,
											Map.Entry::getValue,
											(key, value) -> key, LinkedHashMap::new));
		return sortedMap;
	}
	
	public Map<String, Integer> mergeMaps(Map<String, Integer> map1, Map<String, Integer> map2) {
		Map<String, Integer> mergedMap = new TreeMap<>(map1);
		map2.forEach((key, value) -> mergedMap.merge(key, value, Integer::sum));
		return mergedMap;
	}
}
