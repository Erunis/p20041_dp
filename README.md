# Diplomová práce
## Fuzzy logika v rozpoznávání webových struktur
- **Autor:** Bc. Gabriela Muchová
- **Vedoucí práce:** doc. RNDr. PaedDr. Hashim Habiballa, PhD, Ph.D.

### Obsah
#### ÚVOD
- Struktura práce
- Motivace
- Cíle práce

#### TEORETICKÁ ČÁST
- Teorie webových vyhledávačů
	- Procházení webových stránek (web crawling)
	- Indexování (indexing)
	- Vyhledávací algoritmy (ranking)
- Fuzzy logika
	- Teorie fuzzy množin
	- Využití fuzzy logiky v reálném světě
	- Principy fuzzy vyhledávání (Fuzzy search)
		- Levenshteinova vzdálenost
		- Damerau-Levenshteinova vzdálenost
		- Levenshteinův automat
		- N-gram algoritmus
	- Fuzzy konečné automaty pro porovnávání řetězců
		- Klasický vs. Fuzzy konečný automat
		- 
	- Fuzzy logická analýza pro rozpoznávání vzorů
- Teorie použité architektury
	- Java Spring Boot
	- ReactJS 
- Rešerše existujících řešení (existující knihovny pro fuzzy vyhledávání)

#### PRAKTICKÁ ČÁST
- Návrh architektury, návrh knihovny metod (diagramy, apod.)
- Zprovoznit server (Springboot pro Javu)
- Vytvořit jednoduchou vyhledávací databázi stránek, textů a obrázků
- Vytvořit metody pro fuzzy matching (Java)
- Zrecyklovat a vylepšit metody pro porovnávání obrázků (Java)
- Nahodit jednoduchý frontend → vyhledávací políčko, vybírání obrázků apod. (ReactJS)
- Otestovat funkčnost metod
- Nahodit existující knihovny, vyzkoušet jejich funkčnost na databázi (pravděpodobně bude třeba dělat v JS, knihovny jsou převážně javascriptové)
- Porovnání vytvořené knihovny metod s existujícími knihovnami

#### Existující knihovny pro porovnávání
- List.js – https://listjs.com/, JavaScriptová knihovna
- Fuse.js – https://fusejs.io/
- Fuzzyset.js – https://glench.github.io/fuzzyset.js/
- Fuzzy.js – https://github.com/mattyork/fuzzy
- Levenary – https://npm.io/package/levenary, JavaScriptová knihovna pro výpočet nejmenější Levensteinovy vzdálenosti
- Talisman – https://github.com/yomguithereal/talisman, JavaScript knihovna pro fuzzy matching, data mining a processing přirozených jazyků
- TheFuzz – https://github.com/seatgeek/thefuzz, Python knihovna
- Fuzzball – https://npm.io/package/fuzzball, JavaScriptový port TheFuzz
- Fuzzysort – https://npm.io/package/fuzzysort, JavaScript knihovna
- Wuzzy – https://github.com/xupit3r/wuzzy, JavaScript knihovna zpracovávající různé algoritmy a přístupy k fuzzy string matching (levensteinova vzdálenost,...)
- Fuzzaldrin – https://github.com/atom/fuzzaldrin, CoffeeScriptová knihovna na fuzzy string matching
- Mongoose-fuzzy-searching – https://npm.io/package/mongoose-fuzzy-searching, fuzzy string matching plugin pro MongoDB
- Clj-fuzzy – https://github.com/Yomguithereal/clj-fuzzy – ClojureScript knihovna
- Typesense – https://typesense.org/docs/0.21.0/api/api-clients.html#libraries, knihovna C++ metod, pravděpodobně používá fuzzy
- Apache Lucene – https://lucene.apache.org/, vyhledávač psaný v Javě, zejména pro inspiraci
- Flexsearch – https://github.com/nextapps-de/flexsearch, JavaScriptová knihovna, obsahuje i ranking dalších podobných knihoven
