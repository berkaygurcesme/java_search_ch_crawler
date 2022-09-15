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
        WebDriver driver = null;
        System.setProperty("webdriver.chrome.driver", "/Users/berkay/MyProjects/java/searchch/chromedriver");
        driver = new ChromeDriver();
        List<WebElement> resultWebelementsSonuc = null;
        List<WebElement> personList = null;
        driver.get("https://tel.search.ch/?privat=1");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            // TODO: handle exception
        }
        driver.findElements(By.xpath("//div[contains(@class,'tel-form-toggle')]")).get(0).click();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            // TODO: handle exception
        }
        List<WebElement> cantonList = driver.findElements(
                By.xpath("//div[contains(@class,'tel-input')]//option"));
        List<String> cantons = new ArrayList<>();
        for (int i = 0; i < cantonList.size(); i++) {
            System.out.println(cantonList.get(i).getText());
            if (cantonList.get(i).getText().contains(" ") || cantonList.get(i).getText().contains("All"))
                cantonList.remove(i);
        }
        System.out.println(cantonList.size());

        HashSet<PersonEntity> personEntitySet = new HashSet();

        for (int i = 0; i < swissList.length; i++) {
            driver.get("https://tel.search.ch/?name=" + swissList[i] + "&kanton=" + cantonList.get(i).getText()
                    + "&privat=1&pages=20");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                // TODO: handle exception
            }
            try {
                personList = driver.findElements(
                        By.xpath(
                                "//ol[contains(@class,'tel-results tel-entries')]//li[contains(@class,'tel-person')]"));

            } catch (Exception e) {

                System.out.println(e.getMessage());
            }
            String[] inputArray;

            for (int a = 0; a < personList.size(); a++) {
                PersonEntity personEntity = null;
                inputArray = personList.get(a).getText().split("\\R");
                if (inputArray.length < 3) {
                    personEntity = new PersonEntity(
                            personList.get(a).getText().split("\\R")[0],

                            Optional.ofNullable(personList.get(a).getText().split("\\R")[1]).orElseGet(() -> ""),
                            Optional.ofNullable(personList.get(a).getText().split("\\R")[2]).orElseGet(() -> ""),

                            Optional.ofNullable(personList.get(a).getText().split("\\R")[3]).orElseGet(() -> ""),

                            cantonList.get(i).getText()

                    );
                } else {
                    personEntity = new PersonEntity(
                            personList.get(a).getText().split("\\R")[0],

                            Optional.ofNullable(personList.get(a).getText().split("\\R")[1]).orElseGet(() -> ""),
                            Optional.ofNullable(personList.get(a).getText().split("\\R")[2]).orElseGet(() -> ""),
                            "",
                            cantonList.get(i).getText()

                    );

                }

                personEntitySet.add(personEntity);
            }

        }
        personRepository.saveAll(personEntitySet);

        System.out.println(personEntitySet.size());

        return personRepository.count();
    }

}
