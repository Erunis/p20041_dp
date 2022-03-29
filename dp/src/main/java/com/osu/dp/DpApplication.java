package com.osu.dp;

import com.osu.dp.string_matching.DamerauLevenshtein;
import com.osu.dp.string_matching.DynamicLevenshtein;
import com.osu.dp.string_matching.LevenshteinAutomaton.LevenshteinAutomaton;
import com.osu.dp.string_matching.LevenshteinTools;
import com.osu.dp.string_matching.RecursiveLevenshtein;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DpApplication {
	/*@Autowired
	private StringMatchService sms;*/

	public static void main(String[] args) {
		SpringApplication.run(DpApplication.class, args);
	}

	/* Example: http://localhost:8080/recursiveLevenshtein?source=robot&target=sobota */
	@GetMapping("/recursiveLevenshtein")
	public String recursiveLevenshtein(@RequestParam(value = "source", defaultValue = "") String source,
									   @RequestParam(value = "target", defaultValue = "") String target) {
		//sms.test();
		int distance = RecursiveLevenshtein.computeDist(source, target);
		return String.format("Levenshteinova vzdálenost pomocí rekurze, zdrojový řetězec: %s, " +
						"cílový řetězec: %s, vzdálenost: %d, podobnost slov: %.2f%%.",
				source, target, distance, LevenshteinTools.countSimilarity(source, target, distance));
	}

	/* Example 1: http://localhost:8080/dynamicLevenshtein?source=robot&target=sobota
	*  Example 2: http://localhost:8080/dynamicLevenshtein?source=xamarin&target=flutter */
	@GetMapping("/dynamicLevenshtein")
	public String dynamicLevenshtein(@RequestParam(value = "source", defaultValue = "") String source,
									 @RequestParam(value = "target", defaultValue = "") String target) {
		int distance = DynamicLevenshtein.computeDist(source, target);

		return String.format("Levenshteinova vzdálenost pomocí matic, zdrojový řetězec: %s," +
						" cílový řetězec: %s, vzdálenost: %d, podobnost slov: %.2f%%.",
				source, target, distance, LevenshteinTools.countSimilarity(source, target, distance));
	}

	@GetMapping("damerauLevenshtein")
	public String damerauLevenshtein(@RequestParam(value = "source", defaultValue = "") String source,
									 @RequestParam(value = "target", defaultValue = "") String target) {
		int distance = DamerauLevenshtein.computeDist(source, target);

		return String.format("Damerau-Levenshteinova vzdálenost, zdrojový řetězec: %s," +
						" cílový řetězec: %s, vzdálenost: %d, podobnost slov: %.2f%%.",
				source, target, distance, LevenshteinTools.countSimilarity(source, target, distance));
	}

	@GetMapping("levenshteinAutomata")
	public String levenshteinAutomata(@RequestParam(value = "source", defaultValue = "") String source,
									  @RequestParam(value = "target", defaultValue = "") String target,
									  @RequestParam(value = "distance", defaultValue = "") int distance) {

		boolean result = LevenshteinAutomaton.isAccepted(source, target, distance);

		if (result == true) {
			return String.format("Levenshteinův automat pro cílové slovo: %s o maximální Levenshteinově vzdálenosti: %d" +
					" přijal zdrojové slovo: %s.", target, distance, source);
		}
		else {
			return String.format("Levenshteinův automat pro cílové slovo: %s o maximální Levenshteinově vzdálenosti: %d" +
					" nebyl schopný přijmout zdrojové slovo: %s.", target, distance, source);

		}
	}

	@GetMapping("fuzzyAutomaton")
	public String fuzzyAutomaton(@RequestParam(value = "pattern", defaultValue = "") String pattern,
								 @RequestParam(value = "source", defaultValue = "") String source) {

		return null;
	}
}
