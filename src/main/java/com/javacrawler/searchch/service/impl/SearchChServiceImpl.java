package com.javacrawler.searchch.service.impl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.javacrawler.searchch.dto.PersonDto;
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
    @Override
    public int chServiceCrawler() {
        char[] swissList = { 'a', 'ä', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'ö', 'p', 'q', 'r', 's', 't', 'u', 'ü', 'v', 'w', 'x', 'y', 'z'
        };
        WebDriver driver = null;
        System.setProperty("webdriver.chrome.driver", "/Users/berkay/MyProjects/java/searchch/chromedriver");
        driver = new ChromeDriver();
        List<WebElement> resultWebelementsSonuc = null;
        List<WebElement> personList;
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

        HashSet<PersonDto> personDtoSet = new HashSet();

        for (int i = 0; i < swissList.length; i++) {
            driver.get(" https://tel.search.ch/?name=" + swissList[i] + "&kanton=AG&privat=1");
            personList = driver.findElements(By.xpath("//ol[contains(@class,'tel-results tel-entries')]//li"));

            for (int a = 0; a < personList.size(); a++) {

                PersonDto personDto = new PersonDto(
                        personList.get(a).findElement(By.xpath("//h1")).getText(),
                        personList.get(a).findElement(By.xpath("//div[contains(@class,'tel-occupation')]")).getText(),
                        personList.get(a).findElement(By.xpath("//div[contains(@class,'tel-address')]")).getText(),
                        personList.get(a).findElement(
                                By.xpath("//div[contains(@class,'tel-result-actions sl-screenonly sl-floatlist')]"))
                                .getText());
                personDtoSet.add(personDto);
            }

        }
        System.out.println(personDtoSet.size());

        return 0;
    }

}
