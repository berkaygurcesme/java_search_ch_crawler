package com.javacrawler.searchch.service.impl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.javacrawler.searchch.dto.PersonDto;
import com.javacrawler.searchch.entity.PersonEntity;
import com.javacrawler.searchch.repository.PersonRepository;
import com.javacrawler.searchch.service.SearchChService;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@Service
@RequiredArgsConstructor
public class SearchChServiceImpl implements SearchChService {

    private final PersonRepository personRepository;

    @Override
    public long chServiceCrawler() {
        char[] swissList = { 'a', 'ä', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'ö', 'p', 'q', 'r', 's', 't', 'u', 'ü', 'v', 'w', 'x', 'y', 'z'
        };
        String[] cantonArray = { "AI",
                "AR",
                "BE",
                "BL",
                "BS",
                "FR",
                "GE",
                "GL",
                "GR",
                "JU",
                "LU",
                "NE",
                "NW",
                "OW",
                "SG",
                "SH",
                "SO",
                "SZ",
                "TG",
                "TI",
                "UR",
                "VD",
                "VS",
                "ZG",
                "ZH" };
        String currentCanton;
        String[] inputArray;
        HashSet<PersonEntity> personEntitySet = new HashSet();
        List<WebElement> personList = null;
        WebDriver driver = null;
        System.setProperty("webdriver.chrome.driver", "/Users/berkay/MyProjects/java/searchch/chromedriver");
        driver = new ChromeDriver();

        for (int a = 0; a < cantonArray.length; a++) {

            for (int i = 0; i < swissList.length; i++) {

                driver.get("https://tel.search.ch/?name=" + swissList[i] + "&kanton=" + cantonArray[a]
                        + "&privat=1&pages=20");
                waitForSec();

                personList = driver.findElements(
                        By.xpath(
                                "//ol[contains(@class,'tel-results tel-entries')]//li[contains(@class,'tel-person')]"));

                for (int b = 0; b < personList.size(); b++) {
                    PersonEntity personEntity = null;
                    inputArray = personList.get(b).getText().split("\\R");
                    if (inputArray.length == 4) {
                        try {
                            personEntity = new PersonEntity(
                                    inputArray[0],
                                    inputArray[1],
                                    inputArray[2],
                                    inputArray[3],
                                    cantonArray[a]

                            );
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                    } else if (inputArray.length == 3) {
                        personEntity = new PersonEntity(
                                personList.get(a).getText().split("\\R")[0],

                                Optional.ofNullable(personList.get(a).getText().split("\\R")[1]).orElseGet(() -> ""),
                                Optional.ofNullable(personList.get(a).getText().split("\\R")[2]).orElseGet(() -> ""),
                                "",
                                cantonArray[a]

                        );
                    } else if (inputArray.length == 2) {
                        personEntity = new PersonEntity(
                                personList.get(a).getText().split("\\R")[0],

                                Optional.ofNullable(personList.get(a).getText().split("\\R")[1]).orElseGet(() -> ""),
                                "",
                                "",
                                cantonArray[a]

                        );

                    }

                    personEntitySet.add(personEntity);
                }

            }
        }
        personRepository.saveAll(personEntitySet);

        System.out.println(personEntitySet.size());

        return personRepository.count();
    }

    void waitForSec() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
