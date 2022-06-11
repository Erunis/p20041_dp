# Fuzzy logika v rozpoznávání webových struktur
- **Typ práce:** diplomová
- **Autor:** Bc. Gabriela Muchová
- **Vedoucí práce:** doc. RNDr. PaedDr. Hashim Habiballa, PhD, Ph.D.

## Abstrakt
Cílem této práce je seznámení čtenáře s teoretickými postupy dnešních metod používaných pro fuzzy vyhledávání a dále i s principy fuzzy logiky a jejích struktur možných k využití pro řešení dané problematiky. V textu je tak představena teorie různých metod výpočtu Levenshteinovy vzdálenosti a teorie fuzzy konečných automatů. Ve své praktické části se práce snaží tyto modely realizovat, otestovat a ohodnotit především z hlediska možné využitelnosti fuzzy automatů v problematice efektivního vyhledávání textových řetězců v rozsáhlých databázích.

Tato diplomová práce je sepsána pouze v českém jazyce a není přiložena v tomto repozitáři.

## Spuštění výsledné aplikace
Pro spuštění výsledné aplikace této diplomové práce je nejprve potřeba si stáhnout všechny zdrojové souboury, které jsou součástí příloh textového zpracování práce, případně jsou veřejně dostupné v tomto repozitáři ve složce ```dp/src/main/java/com/osu/dp```

Následně je potřeba spustit na svém zařízení MySQL databázi (pro lokální vývoj je možné toto emulovat pomocí softwaru XAMPP) a naimportovat do ní testovací data ze souboru ```dictionary.sql```, případně ji naplnit vlastními daty ve formátu:
- ```id (bigint(20))``` ... jedinečný identifikátor vzorového řetězce
- ```pattern (varchar(255))``` ... text vzorového řetězce (př. "robot", "počítač", ...)
- ```long_word (tinyint(1))``` ... identifikátor délky slova, 0 = krátké slovo, 1 = dlouhé slovo

Stažené soubory, či v případě příloh u diplomové práce soubory nacházející se v podadresáři ```main/```, je potřeba otevřít v jakémkoliv vývojovém prostředí podporujícím vývoj v programovacím jazyce Java. Praktický projekt byl vytvářen v Javě verze 17, je proto doporučeno si pro kompilaci nainstalovat odpovídající verzi jdk (Java Development Kit). Po otevření těchto souborů ve vývojovém prostředí je potřeba provést pár drobných změn v souboru ```application.properties```:
- ```spring.datasource.url``` je potřeba změnit na link databáze, která obsahuje data pro testování
- ```spring.datasource.username``` a ```spring.datasource.password``` je potřeba změnit na uživatelské jméno a heslo nastavené pro přístup do databáze

Pro testování této aplikace je potřeba nad běžící databází spustit celý výsledný Spring Boot projekt skrz soubor ```DpApplication``` a v prohlížeči otevřít soubor ```p20041_dp.html```, kde je možné nastavit veškeré požadované uživatelské vstupy, jako zdrojový řetězec a vybranou metodu.

# Fuzzy logic in web structures recognition
- **Type of thesis:** diploma
- **Author:** Bc. Gabriela Muchová
- **Supervisor:** doc. RNDr. PaedDr. Hashim Habiballa, PhD, Ph.D.

## Abstract
The goal of this thesis is to familiarize the reader with theoretical procedures of today’s methods used for fuzzy search and furthermore with the principles of fuzzy logic and its structures that might be used to address the described issue. The text presents the theory of various methods of calculating the Levenshtein distance and the theory of fuzzy finite automata. The thesis tries to implement, test, and evaluate these models in this practical section, especially in terms of possible usability of fuzzy automata in the field of efficient search of text strings in large databases.

The thesis is written only in Czech language and is not included in this repository.

## Running the application
To run the final application for the practical section of this thesis, it's requited to first download all the source files that are included as an attachment to the text file of this thesis, or are publicly available in folder ```dp/src/main/java/com/osu/dp``` in this repository.

Next, it's necessary to build a MySQL database on your device (or for local development it is possible to emulate it via XAMPP) and import test data from the ```dictionary.sql``` file, or if you want to you can fill up the database with your own test data in format:
- ```id (bigint(20))``` ... unique identifier of the pattern string
- ```pattern (varchar(255))``` ... pattern string (e.g. "robot", "computer", ...)
- ```long_word (tinyint(1))``` ... pattern length identifier, 0 = short word, 1 = long word

Downloaded files, or in the case of diploma thesis attachments files located in the ```main/``` subdirectory, must be opened in an development environment that supports development in Java programming language. The practical project was created via Java version 17, so it is recommended to install the appropriate version of jdk (Java Development Kit) for code compilation. After opening these files in the development environment, you need to make a few minor changes to the ```application.properties``` file:
- ```spring.datasource.url``` needs to be changed to a link of the database containing the data for testing
- ```spring.datasource.username``` and ```spring.datasource.password``` needs to be changed to your username and password set to access the database

For testing you need to run the entire Spring Boot project over the running database through the ```DpApplication``` file. Then open the ```p20041_dp.html``` file in browser, where you can set all the required input data, such as the source string and the selected method for computation.

