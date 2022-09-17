package com.javacrawler.searchch.service.impl;

import com.javacrawler.searchch.entity.JsoupPersonEntity;
import com.javacrawler.searchch.entity.PersonEntity;
import com.javacrawler.searchch.service.JsoupSearchChService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import us.codecraft.xsoup.Xsoup;

@Slf4j
@Service
@RequiredArgsConstructor
public class JsoupSearchChServiceImpl implements JsoupSearchChService {

  @Override
  public long jsoupChServiceCrawler() {
    char[] swissList = {
      'a',
      'ä',
      'b',
      'c',
      'd',
      'e',
      'f',
      'g',
      'h',
      'i',
      'j',
      'k',
      'l',
      'm',
      'n',
      'o',
      'ö',
      'p',
      'q',
      'r',
      's',
      't',
      'u',
      'ü',
      'v',
      'w',
      'x',
      'y',
      'z',
    };

    String[] cantonArray = {
      "AI",
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
      "ZH",
    };
    String currentCanton;
    String[] inputArray;
    HashSet<JsoupPersonEntity> jsoupPersonEntitySet = new HashSet();
    Elements personList = null;
    Document doc = null;
    long startTime;

    for (int a = 0; a < cantonArray.length; a++) {
      startTime = System.nanoTime();
      for (int i = 0; i < swissList.length; i++) {
        try {
          doc =
            Jsoup
              .connect(
                "https://tel.search.ch/?name=" +
                swissList[i] +
                "&kanton=" +
                cantonArray[a] +
                "&privat=1&pages=20"
              )
              .userAgent("Opera")
              .timeout(10000)
              .get();
        } catch (Exception e) {
          log.info("hata aldi {}", e.getMessage());
        }

        waitForSec();

        personList = doc.getElementsByClass("tel-person");

        for (int b = 0; b < personList.size(); b++) {
          JsoupPersonEntity jsoupPersonEntity = null;

          jsoupPersonEntity =
            new JsoupPersonEntity(personList.get(b).text(), cantonArray[a]);

          jsoupPersonEntitySet.add(jsoupPersonEntity);
        }
      }

      log.info(
        a +
        " jsoup iteration " +
        (double) (System.nanoTime() - startTime) /
        1_000_000_000 +
        "personset size=" +
        jsoupPersonEntitySet.size()
      );
    }

    return 0;
  }

  void waitForSec() {
    try {
      Thread.sleep(100);
    } catch (Exception e) {
      log.info("Wait for sec error!");
    }
  }
}
