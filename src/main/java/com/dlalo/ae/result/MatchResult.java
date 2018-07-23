package com.dlalo.ae.result;


import org.jsoup.nodes.Element;

import java.util.List;

public class MatchResult {
    private final Element element;
    private final int points;
    private final List<String> matches;

    public MatchResult(Element element, int points, List<String> matches) {
        this.element = element;
        this.points = points;
        this.matches = matches;
    }

    public int getPoints() {
        return points;
    }

    public Element getElement() {
        return element;
    }

    public List<String> getMatches() {
        return matches;
    }
}
