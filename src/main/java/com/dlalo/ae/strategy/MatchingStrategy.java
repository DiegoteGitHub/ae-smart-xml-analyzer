package com.dlalo.ae.strategy;


import org.jsoup.nodes.Element;

import java.util.List;

public interface MatchingStrategy {

    /**
     * Makes match for given elements with specific logic.
     * @param origin, original element
     * @param comparableElement, match with original
     * @param matches, list of matches
     * @return points of matching
     */
    int match(Element origin, Element comparableElement, List<String> matches);
}
