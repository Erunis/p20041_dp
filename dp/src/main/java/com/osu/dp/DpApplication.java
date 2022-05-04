package com.osu.dp;

import com.osu.dp.database.Dictionary;
import com.osu.dp.database.DictionaryRepository;
import com.osu.dp.string_matching.DamerauLevenshtein;
import com.osu.dp.string_matching.DynamicLevenshtein;
import com.osu.dp.string_matching.FuzzyAutomaton.AutomatonTest;
import com.osu.dp.string_matching.FuzzyAutomaton.FuzzyState;
import com.osu.dp.string_matching.LevenshteinAutomaton.LevenshteinAutomaton;
import com.osu.dp.string_matching.LevenshteinTools;
import com.osu.dp.string_matching.RecursiveLevenshtein;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		List<String> ret = new ArrayList<>();
		List<Dictionary> dictionary = dictionaryRepository.findAll();
		for (Dictionary entry : dictionary) {
			int distance = RecursiveLevenshtein.computeDist(source, entry.getPattern());
			ret.add(String.format("Levenshteinova vzdálenost pomocí rekurze, zdrojový řetězec: %s, " +
							"cílový řetězec: %s, vzdálenost: %d, podobnost slov: %.2f%%.",
					source, entry.getPattern(), distance, LevenshteinTools.countSimilarity(source, entry.getPattern(), distance)));
		}

		return ret;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/dynamicLevenshtein")
	public List<String> dynamicLevenshtein(@RequestParam(value = "source", defaultValue = "") String source) {
		source = source.toLowerCase();
		List<String> ret = new ArrayList<>();
		List<Dictionary> dictionary = dictionaryRepository.findAll();
		for (Dictionary entry : dictionary) {
			int distance = DynamicLevenshtein.computeDist(source, entry.getPattern());
			ret.add(String.format("Levenshteinova vzdálenost pomocí matic, zdrojový řetězec: %s," +
							" cílový řetězec: %s, vzdálenost: %d, podobnost slov: %.2f%%.",
					source, entry.getPattern(), distance, LevenshteinTools.countSimilarity(source, entry.getPattern(), distance)));
		}
		return ret;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/damerauLevenshtein")
	public List<String> damerauLevenshtein(@RequestParam(value = "source", defaultValue = "") String source) {
		source = source.toLowerCase();
		List<String> ret = new ArrayList<>();
		List<Dictionary> dictionary = dictionaryRepository.findAll();
		for (Dictionary entry : dictionary) {
			int distance = DamerauLevenshtein.computeDist(source, entry.getPattern());
			ret.add(String.format("Damerau-Levenshteinova vzdálenost, zdrojový řetězec: %s," +
							" cílový řetězec: %s, vzdálenost: %d, podobnost slov: %.2f%%.",
					source, entry.getPattern(), distance, LevenshteinTools.countSimilarity(source, entry.getPattern(), distance)));
		}
		return ret;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/levenshteinAutomata")
	public List<String> levenshteinAutomata(@RequestParam(value = "source", defaultValue = "") String source,
									  @RequestParam(value = "distance", defaultValue = "2") int distance) {
		source = source.toLowerCase();
		List<String> ret = new ArrayList<>();
		List<Dictionary> dictionary = dictionaryRepository.findAll();


		for (Dictionary entry : dictionary) {
			boolean result = LevenshteinAutomaton.isAccepted(source, entry.getPattern(), distance);
			if (result == true) {
				ret.add(String.format("Levenshteinův automat pro cílové slovo: %s o maximální Levenshteinově vzdálenosti: %d" +
						" přijal zdrojové slovo: %s.", entry.getPattern(), distance, source));
			}
			else {
				ret.add(String.format("Levenshteinův automat pro cílové slovo: %s o maximální Levenshteinově vzdálenosti: %d" +
						" nebyl schopný přijmout zdrojové slovo: %s.", entry.getPattern(), distance, source));

			}
		}
		return ret;

	}

	@CrossOrigin(origins = "*")
	@GetMapping("/fuzzyAutomaton")
	public List<String> fuzzyAutomaton(@RequestParam(value = "source", defaultValue = "") String source,
									   @RequestParam(value = "logic", defaultValue = "1") int logic) {
		source = source.toLowerCase();
		FuzzyState fuzzyAutomaton = new FuzzyState();
		List<String> ret = new ArrayList<>();
		//HashMap<Double, String> simMap = new HashMap<>();
		List<Dictionary> dictionary = dictionaryRepository.findAll();

		for (Dictionary entry : dictionary) {
			double similarity = fuzzyAutomaton.similarityFunc(entry.getPattern(), source, logic);
			ret.add(String.format("Řetězec %s pro pattern %s byl fuzzy automatem přijat ve stupni: %.2f",
					source, entry.getPattern(), similarity));
		}
		return ret;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/fuzzyAutShortTest")
	public String fuzzyAutShortTest(@RequestParam(value = "source", defaultValue = "") String source,
									@RequestParam(value = "logic", defaultValue = "1") int logic) {
		source = source.toLowerCase();
		AutomatonTest automatonTest = new AutomatonTest();
		double similarity = automatonTest.simTestShort(source, logic);

		return String.format("Řetězec " + source + " byl přijat ve stupni: " + similarity);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/fuzzyAutLongTest")
	public String fuzzyAutLongTest(@RequestParam(value = "source", defaultValue = "") String source,
									@RequestParam(value = "logic", defaultValue = "1") int logic) {
		source = source.toLowerCase();
		AutomatonTest automatonTest = new AutomatonTest();
		double similarity = automatonTest.simTestLong(source, logic);

		return String.format("Řetězec " + source + " byl přijat ve stupni: " + similarity);
	}
}
