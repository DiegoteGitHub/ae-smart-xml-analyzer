package com.dlalo.ae.util;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dlalo.ae.result.MatchResult;
import com.dlalo.ae.strategy.MatchingStrategy;

public class SmartElementAnalyzer {

    private final Element originalElement;
    private final Elements potentialElements;
    private final List<MatchingStrategy> strategies;

    public SmartElementAnalyzer(Element originalElement, Elements potentialElements, List<MatchingStrategy> strategies) {
        this.originalElement = originalElement;
        this.potentialElements = potentialElements;
        this.strategies = strategies;
    }

    /**
     * Tries to find a potential element from given elements with specific strategies.
     * @return {@link MatchResult} with potential element
     */
    public MatchResult findPotentialElement() {

        final List<MatchResult> matchResults = new ArrayList<>(potentialElements.size());

        for (Element potentialElement : potentialElements) {
            final List<String> matches = new ArrayList<>();
            final int matchingPoints = strategies
                    .stream()
                    .mapToInt(strategy -> strategy.match(originalElement, potentialElement, matches))
                    .sum();
            matchResults.add(new MatchResult(potentialElement, matchingPoints, matches));
        }

        matchResults.sort(Comparator.comparing(MatchResult::getPoints).reversed());
        return matchResults.get(0);
    }
}
