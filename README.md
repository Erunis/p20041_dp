# Diplomová práce
- Autor: Bc. Gabriela Muchová
- Vedoucí práce: doc. RNDr. PaedDr. Hashim Habiballa, PhD, Ph.D.

## Obsah
### ÚVOD
- Struktura práce
- Motivace
- Cíle práce

### TEORETICKÁ ČÁST
- Teorie webových vyhledávačů
	- Procházení webových stránek (web crawling)
	- Indexování (indexing)
	- Vyhledávací algoritmy (ranking)
- Fuzzy logika
	- Teorie fuzzy množin
	- Web data mining pomocí fuzzy logiky (?)
	- Fuzzy webový vyhledávač
		- Principy fuzzy vyhledávání (fuzzy matching)
		- Fuzzy logická analýza pro rozpoznávání vzorů
	- Existující knihovny pro fuzzy vyhledávání (List.js, Typesense, Fuzzy.js,...)

### PRAKTICKÁ ČÁST
- Návrh architektury, návrh knihovny metod (diagramy, apod.)
- Zprovoznit server (Springboot pro Javu)
- Vytvořit jednoduchou vyhledávací databázi stránek, textů a obrázků
- Vytvořit metody pro fuzzy matching (Java)
- Zrecyklovat a vylepšit metody pro porovnávání obrázků (Java)
- Nahodit jednoduchý frontend → vyhledávací políčko, vybírání obrázků apod. (ReactJS)
- Otestovat funkčnost metod
- Nahodit existující knihovny, vyzkoušet jejich funkčnost na databázi (pravděpodobně bude třeba dělat v JS, knihovny jsou převážně javascriptové)
- Porovnání vytvořené knihovny metod s existujícími knihovnami

### Existující knihovny pro porovnávání
- List.js – https://listjs.com/
- Typesense – https://typesense.org/docs/0.21.0/api/api-clients.html#libraries
- Apache Lucene – https://lucene.apache.org/
