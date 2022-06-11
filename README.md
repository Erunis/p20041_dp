# Fuzzy logika v rozpoznávání webových struktur
- **Typ práce:** diplomová
- **Autor:** Bc. Gabriela Muchová
- **Vedoucí práce:** doc. RNDr. PaedDr. Hashim Habiballa, PhD, Ph.D.

## Abstrakt
Cílem této práce je seznámení čtenáře s teoretickými postupy dnešních metod používaných pro fuzzy vyhledávání a dále i s principy fuzzy logiky a jejích struktur možných k využití pro řešení dané problematiky. V textu je tak představena teorie různých metod výpočtu Levenshteinovy vzdálenosti a teorie fuzzy konečných automatů. Ve své praktické části se práce snaží tyto modely realizovat, otestovat a ohodnotit především z hlediska možné využitelnosti fuzzy automatů v problematice efektivního vyhledávání textových řetězců v rozsáhlých databázích.

## Spuštění výsledné aplikace
Pro spuštění výsledné aplikace této diplomové práce je nejprve potřeba si stáhnout všechny zdrojové souboury, které jsou součástí příloh textového zpracování práce, případně jsou veřejně dostupné v tomto repozitáři ve složce ```dp/src/main/java/com/osu/dp```

Následně je potřeba spustit na svém zařízení MySQL databázi (pro lokální vývoj je možné toto emulovat pomocí softwaru XAMPP) a naimportovat do ní testovací data ze souboru dictionary.sql, případně ji naplnit vlastními daty ve formátu:
```id (bigint(20))``` ... jedinečný identifikátor vzorového řetězce
```pattern (varchar(255))``` ... text vzorového řetězce (př. "robot", "počítač",...)
```long_word (tinyint(1))``` ... identifikátor délky slova, 0 = krátké slovo, 1 = dlouhé slovo

Stažené soubory, či v případě příloh u diplomové práce soubory nacházející se v podadresáři ```main/```, je potřeba otevřít v jakémkoliv vývojovém prostředí podporujícím vývoj v programovacím jazyce Java. Praktický projekt byl vytvářen v Javě verze 17, je proto doporučeno si pro kompilaci nainstalovat odpovídající verzi jdk (Java Development Kit). Po otevření těchto souborů ve vývojovém prostředí je potřeba provést pár drobných změn v souboru application.properties:
- ```spring.datasource.url`` je potřeba změnit na link databáze, která obsahuje data pro testování
- ```spring.datasource.username``` a ```spring.datasource.password``` je potřeba změnit na uživatelské jméno a heslo nastavené pro přístup do databáze

Pro testování této aplikace je potřeba nad běžící databází spustit celý výsledný Spring Boot projekt skrz soubor DpApplication a v prohlížeči otevřít soubor p20041_dp.html, kde je možné nastavit veškeré požadované uživatelské vstupy, jako zdrojový řetězec a vybranou metodu.


