package com.osu.dp;

import com.osu.dp.database.Dictionary;
import com.osu.dp.database.DictionaryRepository;
import com.osu.dp.string_matching.DamerauLevenshtein;
import com.osu.dp.string_matching.DynamicLevenshtein;
import com.osu.dp.string_matching.FuzzyAutomaton.FuzzyState;
import com.osu.dp.string_matching.FuzzyAutomaton.Results;
import com.osu.dp.string_matching.LevenshteinAutomaton.LevenshteinAutomaton;
import com.osu.dp.string_matching.LevenshteinTools;
import com.osu.dp.string_matching.RecursiveLevenshtein;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@SpringBootApplication
@RestController
public class DpApplication {
	@Autowired
	DictionaryRepository dictionaryRepository;

	public static void main(String[] args) {
		SpringApplication.run(DpApplication.class, args);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/recursiveLevenshtein")
	public List<String> recursiveLevenshtein(@RequestParam(value = "source", defaultValue = "") String source) {
		source = source.toLowerCase();
		List<String> result = new ArrayList<>();
		List<Dictionary> dictionary = dictionaryRepository.findAll();
		int n = 3;
		List<Results> simList = new ArrayList<>();

		StopWatch stopWatch = new StopWatch("Recursive Levenshtein");
		stopWatch.start();
		for (Dictionary entry : dictionary) {
			int distance = RecursiveLevenshtein.computeDist(source, entry.getPattern());
			double similarity = LevenshteinTools.countSimilarity(source, entry.getPattern(), distance);

			simList.add(new Results(entry.getPattern(), similarity, distance));
		}
		stopWatch.stop();
		System.out.println(stopWatch);

		Collections.sort(simList);
		List<Results> elements;
		if (simList.size() > n) {
			elements = new ArrayList<>(simList.subList(0, n));
		}

		else {
			elements = new ArrayList<>();
		}

		for (Results element : elements) {
			result.add(String.format("Levenshteinova vzdálenost pomocí rekurze, zdrojový řetězec: %s, " +
					"cílový řetězec: %s, vzdálenost: %d, podobnost slov: %.2f%%.",
					source, element.getPattern(), element.getDistance(), element.getSimilarity()));
		}

		return result;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/dynamicLevenshtein")
	public List<String> dynamicLevenshtein(@RequestParam(value = "source", defaultValue = "") String source) {
		source = source.toLowerCase();
		List<String> result = new ArrayList<>();
		List<Dictionary> dictionary = dictionaryRepository.findAll();
		int n = 3;
		List<Results> simList = new ArrayList<>();

		StopWatch stopWatch = new StopWatch("Dynamic Levenshtein");
		stopWatch.start();
		for (Dictionary entry : dictionary) {
			int distance = DynamicLevenshtein.computeDist(source, entry.getPattern());
			double similarity = LevenshteinTools.countSimilarity(source, entry.getPattern(), distance);

			simList.add(new Results(entry.getPattern(), similarity, distance));
		}
		stopWatch.stop();
		System.out.println(stopWatch);

		Collections.sort(simList);
		List<Results> elements;
		if (simList.size() > n) {
			elements = new ArrayList<>(simList.subList(0, n));
		}

		else {
			elements = new ArrayList<>();
		}

		for (Results element : elements) {
			result.add(String.format("Levenshteinova vzdálenost dynamicky, zdrojový řetězec: %s, " +
							"cílový řetězec: %s, vzdálenost: %d, podobnost slov: %.2f%%.",
					source, element.getPattern(), element.getDistance(), element.getSimilarity()));
		}

		return result;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/damerauLevenshtein")
	public List<String> damerauLevenshtein(@RequestParam(value = "source", defaultValue = "") String source) {
		source = source.toLowerCase();
		List<String> result = new ArrayList<>();
		List<Dictionary> dictionary = dictionaryRepository.findAll();
		int n = 3;
		List<Results> simList = new ArrayList<>();

		StopWatch stopWatch = new StopWatch("Damerau-Levenshtein");
		stopWatch.start();
		for (Dictionary entry : dictionary) {
			int distance = DamerauLevenshtein.computeDist(source, entry.getPattern());
			double similarity = LevenshteinTools.countSimilarity(source, entry.getPattern(), distance);

			simList.add(new Results(entry.getPattern(), similarity, distance));
		}
		stopWatch.stop();
		System.out.println(stopWatch);

		Collections.sort(simList);
		List<Results> elements;
		if (simList.size() > n) {
			elements = new ArrayList<>(simList.subList(0, n));
		}

		else {
			elements = new ArrayList<>();
		}

		for (Results element : elements) {
			result.add(String.format("Damerau-Levenshteinova vzdálenost, zdrojový řetězec: %s, " +
							"cílový řetězec: %s, vzdálenost: %d, podobnost slov: %.2f%%.",
					source, element.getPattern(), element.getDistance(), element.getSimilarity()));
		}

		return result;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/levenshteinAutomata")
	public List<String> levenshteinAutomata(@RequestParam(value = "source", defaultValue = "") String source,
									  @RequestParam(value = "distance", defaultValue = "2") int distance) {
		source = source.toLowerCase();
		List<String> result = new ArrayList<>();
		List<Dictionary> dictionary = dictionaryRepository.findAll();

		StopWatch stopWatch = new StopWatch("Levenshtein automata");
		stopWatch.start();
		for (Dictionary entry : dictionary) {
			boolean ret = LevenshteinAutomaton.isAccepted(source, entry.getPattern(), distance);
			if (ret == true) {
				result.add(String.format("Levenshteinův automat pro vzorové slovo: %s o maximální Levenshteinově vzdálenosti: %d" +
						" přijal zdrojové slovo: %s.", entry.getPattern(), distance, source));
			}

			else {
				System.out.println(String.format("Levenshteinův automat pro cílové slovo: %s o maximální " +
						"Levenshteinově vzdálenosti: %d nebyl schopný přijmout zdrojové slovo: %s.",
						entry.getPattern(), distance, source));
			}
		}
		stopWatch.stop();
		System.out.println(stopWatch);

		return result;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/fuzzyAutomaton")
	public List<String> fuzzyAutomaton(@RequestParam(value = "source", defaultValue = "") String source,
									   @RequestParam(value = "logic", defaultValue = "1") int logic) {
		source = source.toLowerCase();
		FuzzyState fuzzyAutomaton = new FuzzyState();
		List<String> ret = new ArrayList<>();
		HashMap<String, Double> simMap = new HashMap<>();
		List<Dictionary> dictionary = dictionaryRepository.findAll();

		StopWatch stopWatch = new StopWatch("Fuzzy automata");
		stopWatch.start();
		for (Dictionary entry : dictionary) {
			double similarity = fuzzyAutomaton.similarityFunc(entry.getPattern(), source, logic);
			simMap.put(entry.getPattern(), similarity);
		}

		Map<String, Double> sorted = Results.sortByValue(simMap);
		int count = 0;
		int elementsToReturn = 20;

		for (String s : sorted.keySet()) {
			if (count < elementsToReturn) {
				ret.add(String.format("Řetězec %s pro vzor %s byl fuzzy automatem přijat ve stupni: %.2f",
						source, s, sorted.get(s)));
			}
			else {
				break;
			}
			count++;
		}
		stopWatch.stop();
		System.out.println(stopWatch.getTotalTimeNanos());

		return ret;
	}
}
